package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.FunctorGoal;
import edu.kit.ipd.pp.viper.model.ast.Goal;
import edu.kit.ipd.pp.viper.model.ast.Rule;
import edu.kit.ipd.pp.viper.model.ast.Term;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents the execution state of a functor goal.
 * There is a detailed description of how this how works at {@link #step()}.
 */
public class FunctorActivationRecord extends ActivationRecord {
    private final FunctorGoal goal;
    private final List<Rule> matchingRules;
    private int ruleIndex;
    private List<ActivationRecord> children;
    private UnificationResult unificationResult;
    private Functor matchingRuleHead;

    /**
     * Initializes a functor activation record with an interpreter, a parent and a
     * functor goal. Internally, this already fetches all matching rules from the
     * knowledgebase.
     *
     * @param interpreter interpreter reference (for access to the knowledgebase)
     * @param parent optional parent AR
     * @param goal goal that corresponds to this AR
     */
    public FunctorActivationRecord(Interpreter interpreter, Optional<FunctorActivationRecord> parent,
            FunctorGoal goal) {
        super(interpreter, parent);
        this.goal = goal;
        this.matchingRules = this.getInterpreter().getKnowledgeBase().getMatchingRules(goal.getFunctor());
        this.children = new ArrayList<>();
    }

    /**
     * Getter-method for the index of the current matching rule. This method is
     * public because this information is going to be used in the visualisation.
     *
     * @return index of the current matching rule
     */
    public int getRuleIndex() {
        return this.ruleIndex;
    }

    /**
     * Getter-method for the result of the last unification attempt.
     *
     * @return result of the last unification attempt
     */
    public UnificationResult getUnificationResult() {
        return this.unificationResult;
    }

    /**
     * Getter-method for this functor ARs functor. This returns a term because it
     * has to apply substitutions first.
     *
     * @return functor of this functor ARs goal, with all previous substitutions
     *         applied
     */
    public Term getFunctor() {
        return this.applyPreviousSubstitutions(this.getGoal().getFunctor());
    }

    /**
     * Getter-method for the head of the rule of the last unification.
     *
     * @return head of the rule of the last unification
     */
    public Functor getMatchingRuleHead() {
        return this.matchingRuleHead;
    }

    /**
     * Getter-method for this ARs children ("substeps").
     * This method may return a non-empty list even though isVisited() is false.
     * If isVisited() is false, treat this AR as if it does not have any children.
     *
     * @return this ARs substeps
     */
    public List<ActivationRecord> getChildren() {
        return this.children;
    }

    @Override
    public <T> T accept(ActivationRecordVisitor<T> visitor) {
        return visitor.visit(this);
    }

    /**
     * A functor AR is fulfilled if ALL of the following criteria are met: - It has
     * been visited (isVisited()) - It had a successful unification
     * (getUnificationResult().isSuccess()) - All of its children are fulfilled.
     */
    @Override
    public boolean isFulfilled() {
        if (!this.isVisited()) {
            return false;
        }

        if (!this.getUnificationResult().isSuccess()) {
            return false;
        }

        for (ActivationRecord child : this.getChildren()) {
            if (!child.isFulfilled()) {
                return false;
            }
        }

        return true;
    }

    /**
     * Getter-method for the functor goal that corresponds to this functor AR.
     */
    @Override
    public FunctorGoal getGoal() {
        return this.goal;
    }

    // ---

    @Override
    protected ActivationRecord getRightmost() {
        if (!this.isVisited() || this.getChildren().size() == 0) {
            return this;
        }

        return this.getChildren().get(this.getChildren().size() - 1).getRightmost();
    }

    /**
     * Executes a single step of a functor AR. If this AR has not been visited, set
     * the index of the internal matching rule to 0. If the index of the internal
     * matching rule does not point into the list of matching rules, set visited to
     * false and return getPrevious(). If there is a matching rule at the current
     * index, try to unify it with the functor of the functor goal. Increase the
     * index. If unification failed, return this AR, thus trying it again. If
     * unification was successful, create a new environment for this AR with the
     * resulting substitutions. Get the subgoals and sets their corresponding ARs as
     * this ARs children. If there are any subgoals, return the first subgoal; else,
     * return this.
     *
     * @return an optional next step (may be empty if the absolute end has been
     *         reached)
     */
    @Override
    public Optional<ActivationRecord> step() {
        if (!this.isVisited()) {
            this.ruleIndex = 0;
            this.setVisited(true);
        }

        if (this.getRuleIndex() >= this.matchingRules.size()) {
            this.setVisited(false);
            return this.getPrevious();
        }

        Rule matchingRule = this.matchingRules.get(this.getRuleIndex());
        this.ruleIndex = this.getRuleIndex() + 1;
        Indexifier indexifier = new Indexifier(this.getInterpreter().getUnificationIndex());
        this.getInterpreter().incrementUnificationIndex();

        this.matchingRuleHead = matchingRule.getHead().transform(indexifier);

        Term lhs = this.getFunctor();
        Term rhs = this.getMatchingRuleHead();

        UnificationResult result = rhs.accept(lhs.accept(new UnifierCreator()));
        this.unificationResult = result;

        if (!result.isSuccess()) {
            return Optional.of(this);
        }

        this.setEnvironment(new Environment(this, result.getSubstitutions()));
        List<ActivationRecord> children = new ArrayList<>();

        for (Goal subgoal : matchingRule.getSubgoals()) {
            Goal indexified = subgoal.transform(indexifier);
            children.add(indexified.createActivationRecord(this.getInterpreter(), Optional.of(this)));
        }

        this.children = children;

        if (children.size() != 0) {
            return Optional.of(children.get(0));
        }

        return Optional.of(this.getNext());
    }
}
