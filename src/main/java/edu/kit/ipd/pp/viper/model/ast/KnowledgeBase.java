package edu.kit.ipd.pp.viper.model.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents an AST, also called a "Prolog program" or "knowledge base" in our
 * nomenclature. This is basically just a wrapper around a list of rules. Using
 * {@link #getMatchingRules}, you can get a list of rule whose heads have the
 * same name and arity as a given functor.
 */
public class KnowledgeBase {
    private final List<Rule> rules;

    /**
     * Initializes a knowledgebase with a list of rules.
     *
     * @param rules rules to put in the knowledgebase
     */
    public KnowledgeBase(List<Rule> rules) {
        this.rules = rules;
    }

    @Override
    public String toString() {
        String source = "";
        String lastHead = "";
        int lastArity = -1;
        for (Rule r : this.rules) {
            String currentHead = r.getHead().getName();
            int currentArity = r.getHead().getArity();
            if (source.isEmpty()) {
                lastHead = currentHead;
                lastArity = currentArity;
            } else {
                // Add empty line if previous Name was different
                if (!lastHead.equals(currentHead) || lastArity != currentArity) {
                    lastHead = currentHead;
                    lastArity = currentArity;
                    source += "\n";
                }
            }
            source += r.toString() + "\n";
        }

        return source;
    }

    /**
     * Fetches an immutable list of rules "matching" a functor. A rule matches a
     * functor if its head (which is a functor) has the same name and arity.
     *
     * @param head functor to compare to
     * @return list of matching functors (immutable)
     */
    public List<Rule> getMatchingRules(Functor head) {
        List<Rule> matchingRules = new ArrayList<>();

        for (Rule rule : this.rules) {
            Functor otherHead = rule.getHead();

            if (otherHead.getName().equals(head.getName()) && otherHead.getArity() == head.getArity()) {
                matchingRules.add(rule);
            }
        }

        return Collections.unmodifiableList(matchingRules);
    }

    /**
     * Creates a new knowledgebase that contains an additional rule.
     * 
     * @param rule rule to include in the new knowledgebase
     * @return new knowledgebase including all rules of this and the additional rule
     */
    public KnowledgeBase withRule(Rule rule) {
        List<Rule> newRules = new ArrayList<>();
        newRules.addAll(this.rules);
        newRules.add(rule);

        return new KnowledgeBase(newRules);
    }

    private List<Rule> getRules() {
        return this.rules;
    }

    /**
     * Checks whether this equals another object. Will only return true for functors
     * of the same name and parameters.
     *
     * @param other other Object
     * @return whether this is equal to object according to the rules defined above
     */
    @Override
    public boolean equals(Object other) {
        if (other == null) {
            return false;
        }

        if (!other.getClass().equals(KnowledgeBase.class)) {
            return false;
        }

        List<Rule> otherRules = ((KnowledgeBase) other).rules;
        return this.rules.equals(otherRules);
    }
}
