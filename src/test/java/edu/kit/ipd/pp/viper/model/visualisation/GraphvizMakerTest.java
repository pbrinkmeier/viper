package edu.kit.ipd.pp.viper.model.visualisation;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import edu.kit.ipd.pp.viper.controller.SharedTestConstants;
import edu.kit.ipd.pp.viper.model.ast.Goal;
import edu.kit.ipd.pp.viper.model.ast.KnowledgeBase;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;
import edu.kit.ipd.pp.viper.model.interpreter.StepResult;
import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.model.parser.PrologParser;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;

public class GraphvizMakerTest {
    /**
     * Tests the creation of a graph with a functor AR.
     * 
     * @throws IOException if the file couldn't be read properly
     * @throws ParseException if the code couldn't be parsed
     */
    @Test
    public void functorVisitTest() throws IOException, ParseException {
        final String source = SharedTestConstants.SIMPSONS_FORMATTED;
        this.testProgramAndQuery(source, "grandfather(X, Y).");
    }
    
    /**
     * Tests the creation of a graph with arithmetic ARs.
     * 
     * @throws IOException if the file couldn't be read properly
     * @throws ParseException if the code couldn't be parsed
     */
    @Test
    public void arithmeticVisitTest() throws IOException, ParseException {
        final String source = "";
        this.testProgramAndQuery(source, "X is 2 + 2.");
        this.testProgramAndQuery(source, "X is 2 - 2.");
        this.testProgramAndQuery(source, "X is 2 * 2.");
        this.testProgramAndQuery(source, "X is 2 + (2 * 2).");
        this.testProgramAndQuery(source, "X is 2 + 2 - 2 * 2.");
        this.testProgramAndQuery(source, "X is 2 * Y.");
    }

    /**
     * Tests the creation of a graph with unification ARs.
     * 
     * @throws IOException if the file couldn't be read properly
     * @throws ParseException if the code couldn't be parsed
     */
    @Test
    public void unificationVisitTest() throws IOException, ParseException {
        final String source = SharedTestConstants.SIMPSONS_FORMATTED;
        this.testProgramAndQuery(source, "father(homer, X) = father(Y, lisa).");
    }
    
    /**
     * Tests the creation of a graph with comparison ARs.
     * 
     * @throws IOException if the file couldn't be read properly
     * @throws ParseException if the code couldn't be parsed
     */
    @Test
    public void comparisonVisitTest() throws IOException, ParseException {
        final String source = "";
        this.testProgramAndQuery(source, "1 > 0.");
        this.testProgramAndQuery(source, "1 >= 0.");
        this.testProgramAndQuery(source, "1 < 0.");
        this.testProgramAndQuery(source, "1 =< 0.");
        this.testProgramAndQuery(source, "1 =:= 0.");
        this.testProgramAndQuery(source, "1 =\\= 0.");
    }
    
    /**
     * Tests the creation of a graph with cut ARs.
     * 
     * @throws IOException if the file couldn't be read properly
     * @throws ParseException if the code couldn't be parsed
     */
    @Test
    public void cutVisitTest() throws IOException, ParseException {
        final String source = SharedTestConstants.CUT_EXAMPLE_PROGRAM;
        this.testProgramAndQuery(source, "magic(50, A).");
    }
    
    private void testProgramAndQuery(String programCode, String queryCode) throws ParseException, IOException {
        KnowledgeBase kb = new PrologParser(programCode).parse();
        Goal query = new PrologParser(queryCode).parseGoalList().get(0);
        Interpreter ip = new Interpreter(kb, query);

        int i = 0;
        while (ip.step() != StepResult.NO_MORE_SOLUTIONS) {
            Graph g = GraphvizMaker.createGraph(ip);
            Graphviz viz = Graphviz.fromGraph(g);
            viz.render(Format.SVG).toFile(new File(String.format("src/test/resources/simpsons_advanced%d.svg", i)));

            i++;
        }
    }
}
