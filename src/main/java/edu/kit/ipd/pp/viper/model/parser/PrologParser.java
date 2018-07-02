package edu.kit.ipd.pp.viper.model.parser;

import java.util.List;

import edu.kit.ipd.pp.viper.model.ast.Goal;
import edu.kit.ipd.pp.viper.model.ast.KnowledgeBase;

/**
 * A parser to turn a given program into an abstract syntax tree (ast) that can be used by the interpreter.
 */
public class PrologParser {
    private final String program;
    
    /**
     * Initializes a new parser with the given program.
     * 
     * @param program The given program
     */
    public PrologParser(String program) {
        this.program = program;
    }
    
    /**
     * Creates a knowledge base from the previously parsed program and transforms
     * it into an ast.
     * 
     * @return The generated ast
     */
    public KnowledgeBase parseKnowledgeBase() {
        // TODO
        return null;
    }

    /**
     * Extract all goals from the parsed program and add them to a list.
     * 
     * @return A list containing all goals in the parsed program
     */
    public List<Goal> parseGoalList() {
        // TODO
        return null;
    }
}
