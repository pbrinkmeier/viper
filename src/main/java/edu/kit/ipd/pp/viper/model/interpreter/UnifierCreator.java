package edu.kit.ipd.pp.viper.model.interpreter;

import edu.kit.ipd.pp.viper.model.ast.Functor;
import edu.kit.ipd.pp.viper.model.ast.Number;
import edu.kit.ipd.pp.viper.model.ast.TermVisitor;
import edu.kit.ipd.pp.viper.model.ast.Variable;

public class UnifierCreator implements TermVisitor<Unifier> {
    @Override
    public FunctorUnifier visit(Functor functor) {
        return new FunctorUnifier(functor);
    }

    @Override
    public VariableUnifier visit(Variable variable) {
        return new VariableUnifier(variable);
    }

    @Override
    public NumberUnifier visit(Number number) {
        return new NumberUnifier(number);
    }
}
