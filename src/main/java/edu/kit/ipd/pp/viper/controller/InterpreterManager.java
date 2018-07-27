package edu.kit.ipd.pp.viper.controller;

import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.FunctorGoal;
import edu.kit.ipd.pp.viper.model.ast.Goal;
import edu.kit.ipd.pp.viper.model.ast.KnowledgeBase;
import edu.kit.ipd.pp.viper.model.ast.Rule;
import edu.kit.ipd.pp.viper.model.ast.Term;
import edu.kit.ipd.pp.viper.model.ast.Variable;
import edu.kit.ipd.pp.viper.model.interpreter.Environment;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;
import edu.kit.ipd.pp.viper.model.interpreter.StepResult;
import edu.kit.ipd.pp.viper.model.interpreter.Substitution;
import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.model.parser.PrologParser;
import edu.kit.ipd.pp.viper.model.visualisation.GraphvizMaker;
import edu.kit.ipd.pp.viper.view.ClickableState;
import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.LogType;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;
import guru.nidi.graphviz.model.Graph;

/**
 * Manager class for interpreters. This class holds references to all
 * visualisation graphs created and serves as an interface for commands to control the
 * interpretation and parse new programs.
 */
public class InterpreterManager {
    private static final String STANDARD_LIB
        = "% append list with another list and return the new list\n"
        + "append([], L, L).\n"
        + "append([H|T], L, [H|R]) :-\n"
        + "  append(T, L, R).\n"
        + "\n"
        + "% length of empty list is zero\n"
        + "length([], 0).\n"
        + "% length of non-empty list is 1 + rest of list\n"
        + "length([First | Rest], N) :-\n"
        + "  length(Rest, M),\n"
        + "  N is M + 1.\n"
        + "\n";

    private Optional<KnowledgeBase> knowledgeBase;
    private Optional<Goal> query;
    private Optional<Interpreter> interpreter;
    private List<Graph> visualisations;
    private int current;
    private Optional<List<Variable>> variables;
    private boolean useStandardLibrary = true;
    private boolean running = false;
    private StepResult result;
    private Consumer<ClickableState> toggleStateFunc;
    private Optional<Thread> nextSolutionThread = Optional.empty();
    private boolean noMoreSolutions;

    /**
     * Initializes an interpreter manager. This method calls reset() internally.
     * 
     * @param toggleStateFunc Consumer function that switches the state of clickable
     *        elements in the GUI
     */
    public InterpreterManager(Consumer<ClickableState> toggleStateFunc) {
        this.toggleStateFunc = toggleStateFunc;
        this.reset();
    }

    /**
     * Resets the instance to make it ready for a new interpreter.
     */
    public void reset() {
        this.cancel();
        this.knowledgeBase = Optional.empty();
        this.query = Optional.empty();
        this.interpreter = Optional.empty();
        this.variables = Optional.empty();
        this.result = null;
        this.visualisations = new ArrayList<Graph>();
        this.current = 0;
        this.noMoreSolutions = false;
    }

    /**
     * Parses a knowledgebase and remembers it.
     *
     * @param kbSource source code to parse
     * @throws ParseException if the source code is malformed
     */
    public void parseKnowledgeBase(String kbSource) throws ParseException {
        String source = "";

        if (this.useStandardLibrary)
            source += STANDARD_LIB;
        source += kbSource;

        this.knowledgeBase = Optional.of(new PrologParser(source).parse());
    }

    /**
     * Parses a query and initializes an interpreter, if a parsed knowledgebase is
     * available.
     *
     * @param querySource source code of the query to parse
     * @throws ParseException if the source code is malformed
     */
    public void parseQuery(String querySource) throws ParseException {
        if (!this.knowledgeBase.isPresent()) {
            return;
        }

        KnowledgeBase knowledgeBase = this.knowledgeBase.get();

        List<Goal> goals = new PrologParser(querySource).parseGoalList();

        // goals.size may never be 0 at this point
        // if that were to happen the parser would fail
        if (goals.size() == 1) {
            this.query = Optional.of(goals.get(0));
        } else {
            // TODO: use a Set for this
            List<Term> solveFor = new ArrayList<>();

            for (Goal goal : goals) {
                for (Variable var : goal.getVariables()) {
                    if (!solveFor.contains(var)) {
                        solveFor.add(var);
                    }
                }
            }

            Functor head = new Functor("main", solveFor);
            this.query = Optional.of(new FunctorGoal(head));
            knowledgeBase = knowledgeBase.withRule(new Rule(head, goals));
        }

        // Reset visualisations from previous query
        this.visualisations = new ArrayList<Graph>();
        this.current = 0;
        this.noMoreSolutions = false;
        this.result = null;

        this.interpreter = Optional.of(new Interpreter(knowledgeBase, this.query.get()));
        this.variables = Optional.of(this.query.get().getVariables());
        this.visualisations.add(GraphvizMaker.createGraph(this.interpreter.get()));
    }

    /**
     * Takes an interpreter step. If this method is called after a call to
     * {@link #reset()} (that includes the constructor) and before calls to
     * {@link #parseKnowledgeBase(String)} and {@link #parseQuery(String)}, i.e.
     * before an interpreter instance has been created, it will have no effect and
     * return.
     * Also takes care of the console output.
     *
     * @param console The console panel of the main window
     */
    public void nextStep(ConsolePanel console) {
        if (!this.interpreter.isPresent())
            return;

        this.toggleStateFunc.accept(ClickableState.PARSED_QUERY);

        if (this.noMoreSolutions) {
            this.toggleStateFunc.accept(ClickableState.LAST_STEP);
            this.current++;
            this.result = StepResult.NO_MORE_SOLUTIONS;
            return;
        }

        if (this.current < this.visualisations.size() - 1) {
            this.current++;
            this.result = StepResult.FROM_STEPBACK;
            return;
        }

        this.result = this.interpreter.get().step();

        this.visualisations.add(GraphvizMaker.createGraph(this.interpreter.get()));

        this.current++;

        if (this.result == StepResult.SOLUTION_FOUND) {
            String prefix = LanguageManager.getInstance().getString(LanguageKey.SOLUTION_FOUND);
            List<Substitution> solution = this.getSolution();

            String solutionString = solution.size() == 0
                    ? ("  " + LanguageManager.getInstance().getString(LanguageKey.SOLUTION_YES))
                    : solution.stream().map(s -> "  " + s.toString()).collect(joining(",\n"));

            console.printLine(String.format("%s:\n%s.", prefix, solutionString), LogType.SUCCESS);
        }

        if (this.result == StepResult.NO_MORE_SOLUTIONS) {
            console.printLine(LanguageManager.getInstance().getString(LanguageKey.NO_MORE_SOLUTIONS),
                    LogType.INFO);
            this.toggleStateFunc.accept(ClickableState.LAST_STEP);
            this.noMoreSolutions = true;
        }
    }

    /**
     * Shows a previously generated and saved visualisation
     */
    public void previousStep() {
        if (this.current > 0)
            this.current--;

        this.toggleStateFunc.accept(this.current == 0 ? ClickableState.FIRST_STEP : ClickableState.PARSED_QUERY);
    }

    /**
     * Runs the interpreter until a new solution is found. This is done in a
     * separate thread to ensure the GUI is still responsive and the execution can
     * be canceled if it's going on for too long. Because of that the Thread has to
     * set the visualisation and the console output
     * 
     * @param console The console panel of the gui
     * @param visualisation The visualisation panel of the gui
     */
    public void nextSolution(ConsolePanel console, VisualisationPanel visualisation) {
        if (!this.running) {
            this.running = true;
            this.assignThread(console, visualisation);
        }
    }

    /**
     * Actually initializes and starts the thread and also takes care of the console
     * output and the visualization inside the thread
     * 
     * @param console The console panel of the gui
     * @param visualisation The visualisation panel of the gui
     */
    private void assignThread(ConsolePanel console, VisualisationPanel visualisation) {
        if (this.nextSolutionThread.isPresent()) {
            return;
        }

        this.nextSolutionThread = Optional.of(new Thread(() -> {
            if (this.result == StepResult.NO_MORE_SOLUTIONS
                    && this.current == this.visualisations.size() - 1)
                return;

            while (this.running) {
                this.nextStep(console);
                if ((this.result == StepResult.NO_MORE_SOLUTIONS 
                            || this.result == StepResult.SOLUTION_FOUND)
                            && this.current == this.visualisations.size() - 1)
                    this.running = false;
            }

            visualisation.setFromGraph(this.getCurrentVisualisation());

            return;
        }));

        this.nextSolutionThread.get().start();
    }

    /**
     * Cancels any currently running search for the next solution invoked by calling
     * runUntilNextSolution() by stopping the thread. The currently executed
     * interpretation step is finished before the process is stopped. The
     * visualisation gets updated to the respective current step.
     */
    public void cancel() {
        this.running = false;

        if (!this.nextSolutionThread.isPresent())
            return;

        try {
            this.nextSolutionThread.get().join();
        } catch (InterruptedException e) {

        }

        this.nextSolutionThread = Optional.empty();
    }

    /**
     * Returns a solution for the query. This method assumes that on the current
     * interpreter instance, interpreter.getCurrent().isPresent() holds. This means
     * that at least one step must have been executed.
     * 
     * @return string list of substitutions that form a solution
     */
    public List<Substitution> getSolution() {
        Environment currentEnv = this.interpreter.get().getCurrent().get().getEnvironment();

        List<Substitution> solution = this.variables.get().stream().map(variable -> {
            return new Substitution(variable, currentEnv.applyAllSubstitutions(variable));
        }).collect(toList());

        return solution;
    }

    /**
     * Getter-Method for the current visualisation of the interpretation
     * 
     * @return Current visualisation of the interpretation
     */
    public Graph getCurrentVisualisation() {
        return this.visualisations.get(this.current);
    }

    /**
     * Toggles the standard library on or off.
     */
    public void toggleStandardLibrary() {
        this.useStandardLibrary = !this.useStandardLibrary;
    }

    /**
     * Returns whether the standard library is enabled.
     * 
     * @return boolean value describing whether the standard library is enabled
     */
    public boolean isStandardEnabled() {
        return this.useStandardLibrary;
    }
}
