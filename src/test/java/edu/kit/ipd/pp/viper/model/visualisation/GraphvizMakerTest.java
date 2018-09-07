package edu.kit.ipd.pp.viper.model.visualisation;

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

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Test;
import static org.junit.Assert.fail;

public class GraphvizMakerTest {
    /**
     * Tests the visualisation of functor activation records.
     */
    @Test
    public void functorVisualisationTest() {
        this.visualizeQuery("functor", "grandfather(abe, bart).");
        this.visualizeQuery("functor_nohead", "succeed.");
    }

    /**
     * Tests the visualisation of unification activation records.
     */
    @Test
    public void unificationVisualisationTest() {
        this.visualizeQuery("unification", "doubleEq(42, 42).");
        this.visualizeQuery("unification_fail", "doubleEq(42, 69).");
    }

    /**
     * Tests the visualisation of cut activation records.
     */
    @Test
    public void cutVisualisationTest() {
        this.visualizeQuery("cut", "cutMyLife.");
        this.visualizeQuery("cut_query", "!.");
    }

    /**
     * Tests the visualisation of arithmetic activation records.
     */
    @Test
    public void arithmeticVisualisationTest() {
        this.visualizeQuery("arithmetic", "triple(23, X).");
        this.visualizeQuery("arithmetic_error", "triple(nice, X).");
    }

    /**
     * Test the visualisation of comparison activation records.
     */
    @Test
    public void comparisonVisualisationTest() {
        this.visualizeQuery("comparison", "ordered(17, 50, 42).");
        this.visualizeQuery("comparison_error", "ordered(17, 42, x).");
    }
    
    private void visualizeQuery(String prefix, String queryCode) {
        try {
            String programCode = new String(Files.readAllBytes(Paths.get("src/test/resources/visualisation.pl")));
            KnowledgeBase kb = new PrologParser(programCode).parse();
            Goal query = new PrologParser(queryCode).parseGoalList().get(0);
            Interpreter ip = new Interpreter(kb, query);

            int i = 0;
            Optional<StepResult> lastResult = Optional.empty();
            while (true) {
                Graph g = GraphvizMaker.createGraph(ip);
                Graphviz viz = Graphviz.fromGraph(g);
                viz.render(Format.SVG).toFile(new File(String.format("src/test/resources/%s_%d.svg", prefix, i)));

                if (lastResult.isPresent() && lastResult.get() == StepResult.NO_MORE_SOLUTIONS) {
                    break;
                }
                lastResult = Optional.of(ip.step());
                i++;
            }

            
        } catch (ParseException e) {
            fail(String.format("ParseException while visualising %s: %s", prefix, e.getMessage()));
        } catch (IOException e) {
            fail(String.format("IOException while visualising %s: %s", prefix, e.getMessage()));
        }

    }
}
