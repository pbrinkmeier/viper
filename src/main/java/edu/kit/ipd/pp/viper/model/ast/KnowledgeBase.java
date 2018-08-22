package edu.kit.ipd.pp.viper.model.ast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
        Optional<Functor> previousHead = Optional.empty();
        String repr = "";

        for (Rule rule : this.rules) {
            Functor currentHead = rule.getHead();

            // insert empty line if between blocks of non-matching functors
            if (previousHead.isPresent() && !previousHead.get().matches(currentHead)) {
                repr += "\n";
            }

            repr += rule.toString() + "\n";
            previousHead = Optional.of(currentHead);
        }

        return repr;
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

            if (otherHead.matches(head)) {
                matchingRules.add(rule);
            }
        }

        return Collections.unmodifiableList(matchingRules);
    }

    /**
     * Finds all rules in another knowledgebase that "conflict" with any of this knowledgebases rules.
     * Two rules are "conflicting" if their heads have the same name and arity.
     *
     * @param otherKb knowledgebase to search for conflicts in
     * @return immutable list of rules from this knowledgebase that have a conflicting rule in otherKb
     */
    public List<Rule> getConflictingRules(KnowledgeBase otherKb) {
        List<Rule> conflicting = new ArrayList<>();

        for (Rule rule : this.rules) {
            List<Rule> matching = otherKb.getMatchingRules(rule.getHead());

            // TODO: this is hacky af, but DRY I guess
            if (!matching.isEmpty() && new KnowledgeBase(conflicting).getMatchingRules(rule.getHead()).isEmpty()) {
                conflicting.add(rule);
            }
        }

        return Collections.unmodifiableList(conflicting);
    }

    /**
     * Creates a new knowledgebase that combines two knowledgebases.
     * The newly created knowledgebase will contain <em>all</em> rules from the initial knowledgebases,
     * even those that have "conflicting" heads (i.e. same name and arity).
     * The rules from this knowledgebase will come first in the ordering of the combined rules.
     *
     * @param otherKb knowledgebase to combine with this knowledgebase
     * @return new knowledgebase including all rules of this knowledgebase and all rules of the other
     */
    public KnowledgeBase combine(KnowledgeBase otherKb) {
        List<Rule> newRules = new ArrayList<>();
        newRules.addAll(this.rules);
        newRules.addAll(otherKb.rules);
        
        return new KnowledgeBase(newRules);
    }

    /**
     * Creates a new knowledgebase that contains an additional rule.
     * 
     * @param rule rule to include in the new knowledgebase
     * @return new knowledgebase including all rules of this and the additional rule
     */
    public KnowledgeBase withRule(Rule rule) {
        return this.combine(new KnowledgeBase(Arrays.asList(rule)));
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
