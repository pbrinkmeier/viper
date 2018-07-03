package edu.kit.ipd.pp.viper.model.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

    /**
     * Getter-method for all rules in this knowledgebase.
     *
     * @return all rules in this knowledgebase
     */
    private List<Rule> getRules() {
        return this.rules;
    }

    /**
     * Fetches an immutable list of rules "matching" a functor.
     * A rule matches a functor if its head (which is a functor)
     * has the same name and arity.
     *
     * @param head functor to compare to
     * @return list of matching functors (immutable)
     */
    public List<Rule> getMatchingRules(Functor head) {
        List<Rule> matchingRules = new ArrayList<>();

        for (Rule rule : this.getRules()) {
            Functor otherHead = rule.getHead();

            if (otherHead.getName().equals(head.getName()) && otherHead.getArity() == head.getArity()) {
                matchingRules.add(rule);
            }
        }

        return Collections.unmodifiableList(matchingRules);
    }
}
