package edu.kit.ipd.pp.viper.model.visualisation;

import edu.kit.ipd.pp.viper.model.ast.Goal;
import edu.kit.ipd.pp.viper.model.ast.KnowledgeBase;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;
import edu.kit.ipd.pp.viper.model.parser.ParseException;
import edu.kit.ipd.pp.viper.model.parser.PrologParser;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.*;
import static org.junit.Assert.*;

public class GraphvizMakerTest {
    @Test
    public void createGraphtest() throws Exception, IOException, ParseException {
        String source = new String(Files.readAllBytes(Paths.get("src/test/resources/simpsons.pl"))); 
        KnowledgeBase kb = new PrologParser(source).parse();
        Goal query = new PrologParser("grandfather(X, Y).").parseGoalList().get(0);
        Interpreter ip = new Interpreter(kb, query);

        for (int i = 0; i < 8; i++) {
            ip.step();

            Graph g = GraphvizMaker.createGraph(ip);
            Graphviz viz = Graphviz.fromGraph(g);
            viz.render(Format.SVG).toFile(new File(String.format("src/test/resources/simpsons%d.svg", i)));
        }
    }
}
