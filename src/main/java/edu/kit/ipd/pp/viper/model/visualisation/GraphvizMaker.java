package edu.kit.ipd.pp.viper.model.visualisation;

import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.LanguageManager;

import edu.kit.ipd.pp.viper.model.ast.Variable;

import edu.kit.ipd.pp.viper.model.interpreter.ActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.ActivationRecordVisitor;
import edu.kit.ipd.pp.viper.model.interpreter.ArithmeticActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.ComparisonActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.CutActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.FunctorActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.Interpreter;
import edu.kit.ipd.pp.viper.model.interpreter.UnificationActivationRecord;
import edu.kit.ipd.pp.viper.model.interpreter.UnificationResult;
import edu.kit.ipd.pp.viper.model.interpreter.VariableActivationRecord;
import edu.kit.ipd.pp.viper.view.ColorScheme;
import edu.kit.ipd.pp.viper.view.MainWindow;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Factory.to;
import static guru.nidi.graphviz.attribute.Attributes.attr;

import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Rank;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.attribute.Font;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.MutableNode;
import guru.nidi.graphviz.model.Node;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import static java.util.stream.Collectors.joining;

/**
 * Creates a new graph from an interpreters given current and next activation
 * record using Graphviz-Java. Contains visit methods for all the possible
 * activation records and recursively creates Graphviz nodes and links
 * accordingly.
 *
 * Specific nodes are linked differently (e.g. backtracking links). Successful
 * and failed unification boxes are colored green or red accordingly.
 */
public final class GraphvizMaker implements ActivationRecordVisitor<Node> {
    private final Optional<ActivationRecord> current;
    private final Optional<ActivationRecord> next;
    private Optional<Node> backtrackingNode;
    private List<Graph> rankEnforcers;
    private int nodeIndex;

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
        this.rankEnforcers = new ArrayList<>();
        this.nodeIndex = 0;
    }

    private String createUniqueNodeName() {
        return String.format("node%d", this.nodeIndex++);
    }

    @Override
    public Node visit(FunctorActivationRecord far) {
        Node node = this.createGoalNode(far, far.getFunctor().toHtml());

        if (!far.isVisited()) {
            return node;
        }

        // Create box with the unification status and message
        UnificationResult result = far.getUnificationResult();

        // Add rule index to matching rules head
        String ruleRepr = String.format("%s%s", far.getMatchingRuleHead().getName(),
                Variable.toHtmlSubscript(far.getRuleIndex()));

        if (far.getMatchingRuleHead().getParameters().size() != 0) {
            ruleRepr += "(";
            ruleRepr += far.getMatchingRuleHead().getParameters().stream().map(param -> param.toHtml())
                    .collect(joining(", "));
            ruleRepr += ")";
        }

        // create the result box node
        Node resultBox = node(this.createUniqueNodeName())
        .with(Label.html("{" + ruleRepr + "|" + result.toHtml() + "}"))
        .with(attr("shape", "record"));

        if (this.isCurrent(far)) {
            resultBox = resultBox.with(result.isSuccess() ? ColorScheme.VIS_GREEN : ColorScheme.VIS_RED);
        }

        // if the unification was a success there definitely will be child nodes
        if (result.isSuccess()) {
            List<Node> childNodes = new ArrayList<>();
            Graph rankEnforcer = graph().graphAttr().with(Rank.SAME);

            for (ActivationRecord child : far.getChildren()) {
                Node childNode = child.accept(this);
                rankEnforcer = rankEnforcer.with(node(((MutableNode) childNode).name()));
                childNodes.add(childNode);
            }
            this.rankEnforcers.add(rankEnforcer);

            Optional<Node> previous = Optional.empty();
            Collections.reverse(childNodes);

            for (Node childNode : childNodes) {
                resultBox = resultBox.link(
                        to(previous.isPresent() ? childNode.link(to(previous.get()).with(Style.INVIS)) : childNode)
                                .with(Style.DASHED));

                previous = Optional.of(node(((MutableNode) childNode).name()));
            }
        }

        return node.link(resultBox);
    }

    @Override
    public Node visit(UnificationActivationRecord uar) {
        Node node = this.createGoalNode(uar, String.format("%s = %s", uar.getLhs().toHtml(), uar.getRhs().toHtml()));

        if (!uar.isVisited()) {
            return node;
        }

        Node resultBox = node(this.createUniqueNodeName())
                .with(Label.html(String.format("{<font point-size=\"10\">%s</font>|%s = %s|%s}",
                        LanguageManager.getInstance().getString(LanguageKey.UNIFICATION), uar.getLhs().toHtml(),
                        uar.getRhs().toHtml(), uar.getResult().toHtml())))
                .with(attr("shape", "record"));

        if (this.isCurrent(uar)) {
            resultBox = resultBox.with(uar.getResult().isSuccess() ? ColorScheme.VIS_GREEN : ColorScheme.VIS_RED);
        }

        return node.link(resultBox);
    }

    @Override
    public Node visit(CutActivationRecord cutAr) {
        Node node = this.createGoalNode(cutAr, "!");

        if (!cutAr.isVisited()) {
            return node;
        }

        String cutNote;
        if (cutAr.getParent().isPresent()) {
            cutNote = String.format(
                LanguageManager.getInstance().getString(LanguageKey.VISUALISATION_CUT_NOTE),
                cutAr.getParent().get().getFunctor().toHtml()
            );
        } else {
            cutNote = LanguageManager.getInstance().getString(LanguageKey.VISUALISATION_CUT_ABORT);
        }

        Node cutNoteBox = node(this.createUniqueNodeName())
        .with(Label.html(cutNote))
        .with(attr("shape", "record"));

        if (this.isCurrent(cutAr)) {
            cutNoteBox = cutNoteBox.with(ColorScheme.VIS_GREEN);
        }

        return node.link(cutNoteBox);
    }

    @Override
    public Node visit(ArithmeticActivationRecord aar) {
        Node node = this.createGoalNode(aar, String.format("%s is %s", aar.getLhs().toHtml(), aar.getRhs().toHtml()));

        if (!aar.isVisited()) {
            return node;
        }

        Node resultBox = node(this.createUniqueNodeName()).with(attr("shape", "record"));

        if (aar.getResult().isError()) {
            resultBox = resultBox.with(Label.html(aar.getResult().toHtml()));
        } else {
            resultBox = resultBox.with(Label.html(String.format("{<font point-size=\"10\">%s</font>|%s = %s|%s}",
                    LanguageManager.getInstance().getString(LanguageKey.UNIFICATION), aar.getLhs().toHtml(),
                    // evaluated rhs is guaranteed to be set because the result was not an error
                    aar.getEvaluatedRhs().toHtml(), aar.getResult().toHtml())));
        }

        if (this.isCurrent(aar)) {
            resultBox = resultBox.with(aar.getResult().isSuccess() ? ColorScheme.VIS_GREEN : ColorScheme.VIS_RED);
        }

        return node.link(resultBox);
    }

    @Override
    public Node visit(ComparisonActivationRecord car) {
        Node node = this.createGoalNode(car, String.format("%s %s %s", car.getLhs().toHtml(),
        car.getGoal().getHtmlSymbol(), car.getRhs().toHtml()));

        if (!car.isVisited()) {
            return node;
        }

        Node resultBox = node(this.createUniqueNodeName()).with(attr("shape", "record"));

        if (!car.getErrorMessage().isPresent()) {
            resultBox = resultBox
            .with(Label.html("&#x2713;"));
        } else {
            resultBox = resultBox
            .with(Label.html(car.getErrorMessage().get()));
        }

        if (this.isCurrent(car)) {
            resultBox = resultBox
            .with(!car.getErrorMessage().isPresent() ? ColorScheme.VIS_GREEN : ColorScheme.VIS_RED);
        }

        return node.link(resultBox);
    }

    @Override
    public Node visit(VariableActivationRecord varAr) {
        Node node = this.createGoalNode(varAr, String.format("%s", varAr.getGoal().getVariable().toHtml()));

        if (!varAr.isVisited()) {
            return node;
        }

        Node subBox = varAr.getAr().get().accept(this);

        return node.link(subBox);
    }

    private boolean isCurrent(ActivationRecord ar) {
        return this.current.isPresent() && this.current.get() == ar;
    }

    private boolean isNext(ActivationRecord ar) {
        return this.next.isPresent() && this.next.get() == ar;
    }

    /**
     * Creates a node for a given activation record.
     * This method takes care of given the node an unique name and adding the arrow for backtracking.
     *
     * @param ar activation record to create a node for
     * @return node that represents the activation records goal
     */
    private Node createGoalNode(ActivationRecord ar, String htmlLabel) {
        Node node = node(this.createUniqueNodeName());

        // Mark fulfilled nodes as fulfilled
        if (ar.isFulfilled()) {
            node = node
            .with(Style.FILLED)
            .with(ColorScheme.VIS_FULFILLED.fill())
            .with(Label.html(htmlLabel + " &#x2713;"));
        } else {
            node = node.with(Label.html(htmlLabel));
        }

        /*
         * If the current ar has not been visited and the next ar has been visited already,
         * i.e. it's been saved as the backtracking node, add the backtracking edge.
         */
        if (this.isCurrent(ar) && !ar.isVisited() && this.backtrackingNode.isPresent()) {
            node = node.link(to(this.backtrackingNode.get())
            .with(Style.DOTTED)
            .with(ColorScheme.VIS_RED)
            .with(attr("constraint", "false")));
        }

        // This should be the very last block of code before returning
        if (this.isNext(ar)) {
            this.backtrackingNode = Optional.of(node);
        }

        return node;
    }

    /**
     * Calls the private constructor to create an new instance and creates a graph
     * with the new instance by visiting all the available ActivationRecords in the
     * interpreter
     *
     * @param interpreter The interpreter to create the graph from
     * @return the resulting graph object
     */
    public static Graph createGraph(Interpreter interpreter) {
        GraphvizMaker maker = new GraphvizMaker(interpreter.getCurrent(), interpreter.getNext());
        Node rootNode = interpreter.getQuery().accept(maker);
        Graph g = graph("visualisation").directed().with(rootNode)
        .nodeAttr().with(Font.name(MainWindow.VISUALISATION_FONT_NAME));

        for (Graph rankEnforcer : maker.rankEnforcers) {
            g = g.with(rankEnforcer);
        }

        return g;
    }
}
