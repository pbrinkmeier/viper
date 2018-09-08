package edu.kit.ipd.pp.viper.model.ast;

import java.util.Comparator;

/**
 * Compares rules by their heads' names and arities only.
 */
public class MatchingHeadComparator implements Comparator<Rule> {
    /*
     * In order to create a list of unique conflicting rules (in KnowledgeBase), we'd like to use a Set instance.
     * But Rule#equals and Rule#hashCode are already defined in terms of actual equality.
     * A TreeSet allows us to use a Comparator object for custom comparison.
     * This Comparator sorts by the rules' heads, name first, arity second.
     */
    @Override
    public int compare(Rule ruleA, Rule ruleB) {
        Functor headA = ruleA.getHead();
        Functor headB = ruleB.getHead();

        if (!headA.getName().equals(headB.getName())) {
            return headA.getName().compareTo(headB.getName());
        }

        return headA.getArity() - headB.getArity();
    }
}
