package edu.kit.ipd.pp.viper.model.visualisation;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Optional;

import org.junit.Test;

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
     * Tests the visualisation of functor activation records.
     */
    @Test
    public void functorVisualisationTest() {
        GraphvizMakerTest.visualizeQuery("functor", "grandfather(abe, bart).");
        GraphvizMakerTest.visualizeQuery("functor_nohead", "succeed.");
    }

    /**
     * Tests the visualisation of unification activation records.
     */
    @Test
    public void unificationVisualisationTest() {
        GraphvizMakerTest.visualizeQuery("unification", "doubleEq(42, 42).");
        GraphvizMakerTest.visualizeQuery("unification_fail", "doubleEq(42, 69).");
    }

    /**
     * Tests the visualisation of cut activation records.
     */
    @Test
    public void cutVisualisationTest() {
        GraphvizMakerTest.visualizeQuery("cut", "cutMyLife.");
        GraphvizMakerTest.visualizeQuery("cut_query", "!.");
    }

    /**
     * Tests the visualisation of arithmetic activation records.
     */
    @Test
    public void arithmeticVisualisationTest() {
        GraphvizMakerTest.visualizeQuery("arithmetic", "triple(23, X).");
        GraphvizMakerTest.visualizeQuery("arithmetic_error", "triple(nice, X).");
    }

    /**
     * Test the visualisation of comparison activation records.
     */
    @Test
    public void comparisonVisualisationTest() {
        GraphvizMakerTest.visualizeQuery("comparison", "ordered(17, 50, 42).");
        GraphvizMakerTest.visualizeQuery("comparison_error", "ordered(17, 42, x).");
    }
    
    private static void visualizeQuery(String prefix, String queryCode) {
        try {
            String programCode = new String(Files.readAllBytes(Paths.get("src/test/resources/visualisation.pl")));
            KnowledgeBase kb = new PrologParser(programCode).parse();
            Goal query = new PrologParser(queryCode).parseQuery().get(0);
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
