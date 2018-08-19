package edu.kit.ipd.pp.viper.model.ast;

import java.util.Collections;
import java.util.List;

/**
 * Represents a rule in an AST. A rule has a functor as its head and a list of
 * goals as its subgoals. In Prolog code a rule is expressed as
 * <code>H :- G.</code> where H is the head and G is the subgoals.
 */
public class Rule {
    private final Functor head;
    private final List<Goal> subgoals;

    /**
     * Initializes a rule with a functor as head and a list of subgoals.
     *
     * @param head head of this rule
     * @param subgoals subgoals of this rule
     */
    public Rule(Functor head, List<Goal> subgoals) {
        this.head = head;
        this.subgoals = Collections.unmodifiableList(subgoals);
    }

    /**
     * Getter-method for the head of this rule.
     *
     * @return head of this rule
     */
    public Functor getHead() {
        return this.head;
    }

    /**
     * Getter-method for the subgoals of this rule. The returned list is immutable.
     *
     * @return subgoals of this rule
     */
    public List<Goal> getSubgoals() {
        return this.subgoals;
    }

    /**
     * Getter-method for a string representation of this rule.
     *
     * @return string representation of this rule
     */
    @Override
    public String toString() {
        String repr = this.getHead().toString();
        List<Goal> subgoals = this.getSubgoals();

        if (subgoals.size() != 0) {
            repr += " :-\n";

            for (int index = 0; index < subgoals.size(); index++) {
                repr += "  " + subgoals.get(index);

                if (index != subgoals.size() - 1) {
                    repr += ",\n";
                }
            }
        }

        repr += ".";

        return repr;
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

        if (!other.getClass().equals(Rule.class)) {
            return false;
        }

        Functor otherHead = ((Rule) other).getHead();

        if (!this.head.equals(otherHead)) {
            return false;
        }

        List<Goal> otherGoals = ((Rule) other).getSubgoals();

        if (!this.subgoals.equals(otherGoals)) {
            return false;
        }

        return true;
    }
}
