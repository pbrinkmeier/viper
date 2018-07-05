package edu.kit.ipd.pp.viper.model.ast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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

        List<Rule> otherRules = ((KnowledgeBase) other).getRules();
        
        if (this.rules.isEmpty() && otherRules.isEmpty())
            return true;
        
        if (this.rules.size() != otherRules.size())
            return false;
        
        Iterator<Rule> otherIter = otherRules.iterator();
        Iterator<Rule> thisIter = this.rules.iterator();
        
        while (thisIter.hasNext()) {
            if (!thisIter.next().equals(otherIter.next()))
                return false;
        }
        
        return true;
    }
}
