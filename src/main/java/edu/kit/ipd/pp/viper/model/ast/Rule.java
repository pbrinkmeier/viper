package edu.kit.ipd.pp.viper.model.ast;

import java.util.Collections;
import java.util.List;

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
     * Getter-method for the subgoals of this rule.
     * The returned list is immutable.
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
    public String toString() {
        String repr = this.getHead().toString();
        List<Goal> subgoals = this.getSubgoals();

        if (subgoals.size() != 0) {
            repr += " :- \n";

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
}
