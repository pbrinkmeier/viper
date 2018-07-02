package edu.kit.ipd.pp.viper.model.ast;

import java.util.List;

public class KnowledgeBase {
    /**
     * Initializes a knowledgebase with a list of rules.
     *
     * @param rules rules to put in the knowledgebase
     */
    public KnowledgeBase(List<Rule> rules) {
        // TODO
    }

    /**
     * Fetches an immutable list of rules "matching" a functor.
     * A rule matches a functor, if its head (which is a functor)
     * has the same name and arity.
     *
     * @param head functor to compare to
     * @return list of matching functors (immutable)
     */
    public List<Rule> getMatchingRules(Functor head) {
        // TODO
        return null;
    }
}
