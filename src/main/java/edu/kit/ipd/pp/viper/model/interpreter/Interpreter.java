package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Goal;
import edu.kit.ipd.pp.viper.model.ast.KnowledgeBase;

import java.util.Optional;

public class Interpreter {
    private final KnowledgeBase knowledgeBase;
    private final ActivationRecord query;
    private Optional<ActivationRecord> current;
    private Optional<ActivationRecord> next;

    /**
     * Initializes an interpreter with a knowledgebase and a query.
     *
     * @param kb knowledgebase
     * @param queryGoal query to run on the knowledgebase
     */
    public Interpreter(KnowledgeBase kb, Goal queryGoal) {
        this.knowledgeBase = kb;
        this.query = queryGoal.createActivationRecord(this, Optional.empty());

        this.setCurrent(Optional.empty());
        this.setNext(Optional.of(this.getQuery()));
    }

    /**
     * Getter-method for this interpreters knowledgebase.
     *
     * @return this interpreters knowledgebase
     */
    public KnowledgeBase getKnowledgeBase() {
        return this.knowledgeBase;
    }

    /**
     * Getter-method for this interpreters query.
     *
     * @return this interpreters query
     */
    public ActivationRecord getQuery() {
        return this.query;
    }

    /**
     * Getter-method for this interpreters "current" step.
     * This refers to the step that has just been completed.
     * In case the interpreter was just created, this will return empty.
     *
     * @return optional current step of this interpreter
     */
    public Optional<ActivationRecord> getCurrent() {
        return this.current;
    }

    /**
     * Getter-method for this interpreters next step.
     * In case the interpreter has reached the absolute end of execution
     * (that is, no more solutions are possible at all), this will
     * return empty.
     *
     * @return optional next step of this interpreter
     */
    public Optional<ActivationRecord> getNext() {
        return this.next;
    }

    /**
     * Setter-method for this interpreters current step.
     * For internal use only.
     *
     * @param current optional current step
     */
    private void setCurrent(Optional<ActivationRecord> current) {
        this.current = current;
    }

    /**
     * Setter-method for this interpreters next step.
     * For interal use only.
     *
     * @param next optional next step
     */
    private void setNext(Optional<ActivationRecord> next) {
        this.next = next;
    }

    // ---

    /**
     * Executes the next step and sets it as the current step.
     * Sets the next step to the one that follows.
     * If there is no next step (getNext() == Optional.empty()),
     * calling this has no effect.
     *
     * @return a StepResult
     */
    public StepResult step() {
        // TODO
        return null;
    }
}
