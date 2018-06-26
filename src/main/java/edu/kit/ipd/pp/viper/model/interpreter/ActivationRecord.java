package edu.kit.ipd.pp.viper.model.interpreter;

import java.util.Optional;

public abstract class ActivationRecord<ResultType> {
    /**
     * @param interpreter 
     * @param parent
     */
    public ActivationRecord(Interpreter interpreter, Optional<FunctorActivationRecord> parent) {
        // TODO
    }

    /**
     * @param visitor 
     * @return
     */
    public abstract ResultType accept(ActivationRecordVisitor<ResultType> visitor);

    /**
     * @return
     */
    protected abstract ActivationRecord<?> step();

    /**
     * @return
     */
    protected abstract ActivationRecord<?> createCopy();

    /**
     * @param env 
     * @return
     */
    protected void setEnvironment(Environment env) {
        // TODO
    }

    /**
     * @return
     */
    protected ActivationRecord<?> getPrevious() {
        // TODO
        return null;
    }

    /**
     * @return
     */
    protected Environment getEnvironment() {
        // TODO
        return null;
    }

    /**
     * @return
     */
    protected ActivationRecord<?> getNext() {
        // TODO
        return null;
    }

    /**
     * @return
     */
    protected Interpreter getInterpreter() {
        // TODO
        return null;
    }

    /**
     * @return
     */
    protected Optional<FunctorActivationRecord> getParent() {
        // TODO
        return null;
    }

    /**
     * @return
     */
    protected boolean hasBeenVisited() {
        // TODO
        return false;
    }

    /**
     * @param visited 
     * @return
     */
    protected void setVisited(boolean visited) {
        // TODO
    }

}
