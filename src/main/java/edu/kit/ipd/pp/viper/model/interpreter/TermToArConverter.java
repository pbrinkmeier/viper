package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.FunctorGoal;
import edu.kit.ipd.pp.viper.model.ast.Number;
import edu.kit.ipd.pp.viper.model.ast.TermVisitor;
import edu.kit.ipd.pp.viper.model.ast.Term;
import edu.kit.ipd.pp.viper.model.ast.Variable;
import java.util.Optional;

class TermToArConverter implements TermVisitor<Optional<ActivationRecord>> {
    private final Interpreter interpreter;
    private final Optional<FunctorActivationRecord> parent;

    public TermToArConverter(Interpreter interpreter, Optional<FunctorActivationRecord> parent) {
        this.interpreter = interpreter;
        this.parent = parent;
    }
    
    @Override
    public Optional<ActivationRecord> visit(Number number) {
        return Optional.empty();
    }

    @Override
    public Optional<ActivationRecord> visit(Variable variable) {
        return Optional.empty();
    }

    @Override
    public Optional<ActivationRecord> visit(Functor f) {
        return Optional.of(new FunctorActivationRecord(this.interpreter, this.parent, new FunctorGoal(f)));
    }
}
