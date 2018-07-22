package edu.kit.ipd.pp.viper.model.ast;

import java.util.Optional;

final class ListFormatter implements TermVisitor<Optional<String>> {
    private final boolean asHtml;
    private boolean isFirst;

    private ListFormatter(boolean asHtml) {
        this.asHtml = asHtml;
        this.isFirst = true;
    }

    @Override
    public Optional<String> visit(Variable variable) {
        return Optional.empty();
    }

    @Override
    public Optional<String> visit(Number number) {
        return Optional.empty();
    }

    @Override
    public Optional<String> visit(Functor functor) {
        if (functor.getName().equals("[]") && functor.getArity() == 0) {
            return Optional.of("");
        }

        if (!functor.getName().equals("[|]") || functor.getArity() != 2) {
            return Optional.empty();
        }

        boolean isFirst = this.isFirst;
        this.isFirst = false;
        Term element = functor.getParameters().get(0);

        return functor.getParameters().get(1).accept(this).map(rest -> {
            return (isFirst ? "" : ", ") + (this.asHtml ? element.toHtml() : element.toString()) + rest;
        });
    }

    /**
     * Returns a string representation of a list, if possible.
     * 
     * @param term term to format
     * @return list representation
     */
    public static Optional<String> asString(Term term) {
        return term.accept(new ListFormatter(false)).map(list -> "[" + list + "]");
    }

    /**
     * Returns an HTML representation of a list, if possible.
     *
     * @param term term to format
     * @return list representation as Graphviz-compatible HTML
     */
    public static Optional<String> asHtml(Term term) {
        return term.accept(new ListFormatter(true)).map(list -> "[" + list + "]");
    }
}
