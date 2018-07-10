package edu.kit.ipd.pp.viper.model.visualisation;

import edu.kit.ipd.pp.viper.model.interpreter.ActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.ActivationRecordVisitor;
import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;
import edu.kit.ipd.pp.viper.model.interpreter.UnificationResult;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Factory.to;
import static guru.nidi.graphviz.attribute.Attributes.attr;
import static guru.nidi.graphviz.attribute.Label.html;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;

import java.util.Optional;

public final class GraphvizMaker implements ActivationRecordVisitor<Node> {
    private final Optional<ActivationRecord> current;
    private final Optional<ActivationRecord> next;
    private Optional<Node> backtrackingNode;

    /**
     * Creates new instance with the current and the next step
     *
     * @param current The last visited ActivationRecord
     * @param next The next ActivationRecord to visit
     */
    private GraphvizMaker(Optional<ActivationRecord> current, Optional<ActivationRecord> next) {
        this.current = current;
        this.next = next;
        this.backtrackingNode = Optional.empty();
    }

    @Override
    public Node visit(FunctorActivationRecord far) {
        Node node = node(html(far.getFunctor().toHtml()));

        if (!far.isVisited()) {
            if (this.backtrackingNode.isPresent()) {
                node = node.link(
                    to(this.backtrackingNode.get())
                    .with(Style.DOTTED)
                    .with(Color.RED)
                )
                .with(Color.RED);
            }

            return node;
        }

        /*
         * In case of backtracking we reach the "next" node before we reach the "current" node
         * so we have to save the information that backtracking has to be done for later
        */
        if (this.next.isPresent() && this.next.get() == far)
            this.backtrackingNode = Optional.of(node);

        // Create box with the unification status and message
        UnificationResult result = far.getUnificationResult();
        Node resultBox
            = node(html("{" + far.getMatchingRuleHead().toHtml() + "|" + result.toHtml() + "}"))
            .with(
                attr("shape", "record")
            );

        if (this.current.isPresent() && this.current.get() == far) {
            resultBox = resultBox.with(result.isSuccess() ? Color.GREEN : Color.RED);
        }

        // if the unification was a success there definitely will be child nodes
        if (result.isSuccess()) {
            for (ActivationRecord child : far.getChildren()) {
                resultBox = resultBox.link(child.accept(this));
            }
        }

        return node.link(resultBox);
    }

    /**
     * Calls the private constructor to create an new instance and creates a graph with the
     * new instance by visiting all the available ActivationRecords in the interpreter
     *
     * @param interpreter The interpreter to create the graph from
     * @return the resulting graph object
     */
    public static Graph createGraph(Interpreter interpreter) {
        GraphvizMaker maker = new GraphvizMaker(interpreter.getCurrent(), interpreter.getNext());
        Node rootNode = interpreter.getQuery().accept(maker);

        return graph("visualisation").directed().with(rootNode);
    }
}
