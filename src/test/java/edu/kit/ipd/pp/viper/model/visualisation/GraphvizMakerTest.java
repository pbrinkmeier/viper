package edu.kit.ipd.pp.viper.model.visualisation;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

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
     * Tests the creation of a graph.
     * 
     * @throws IOException if the file couldn't be read properly
     * @throws ParseException if the code couldn't be parsed
     */
    @Test
    public void createGraphtest() throws IOException, ParseException {
        String source = new String(Files.readAllBytes(Paths.get("src/test/resources/simpsons_advanced.pl")));
        KnowledgeBase kb = new PrologParser(source).parse();
        Goal query = new PrologParser("grandfather(X, Y).").parseGoalList().get(0);
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
