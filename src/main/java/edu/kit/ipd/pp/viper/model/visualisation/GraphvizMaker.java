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
import edu.kit.ipd.pp.viper.view.ColorScheme;

import static guru.nidi.graphviz.model.Factory.graph;
import static guru.nidi.graphviz.model.Factory.node;
import static guru.nidi.graphviz.model.Factory.to;
import static guru.nidi.graphviz.attribute.Attributes.attr;
import static guru.nidi.graphviz.attribute.Label.html;

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
 * Creates a new graph from an interpreters given current and next activation record using Graphviz-Java.
 * Contains visit methods for all the possible activation records and recursively creates
 * Graphviz nodes and links accordingly.
 *
 * Specific nodes are linked differently (e.g. backtracking links).
 * Successful and failed unification boxes are colored green or red accordingly.
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
        Node node = node(this.createUniqueNodeName()).with(html(far.getFunctor().toHtml()));

        if (!far.isVisited()) {
            return this.addBacktrackingEdge(far, node);
        }

        /*
         * In case of backtracking we reach the "next" node before we reach the
         * "current" node so we have to save the information that backtracking has to be
         * done for later
         */
        if (this.next.isPresent() && this.next.get() == far) {
            this.backtrackingNode = Optional.of(node);
        }

        // Create box with the unification status and message
        UnificationResult result = far.getUnificationResult();

        // Add rule index to matching rules head
        String ruleRepr = String.format("%s%s",
            far.getMatchingRuleHead().getName(),
            Variable.toHtmlSubscript(far.getRuleIndex())
        );

        if (far.getMatchingRuleHead().getParameters().size() != 0) {
            ruleRepr += "(";
            ruleRepr += far.getMatchingRuleHead().getParameters().stream().map(param -> param.toHtml())
                    .collect(joining(", "));
            ruleRepr += ")";
        }

        // create the result box node
        Node resultBox = node(this.createUniqueNodeName()).with(html("{" + ruleRepr + "|" + result.toHtml() + "}"))
                .with(attr("shape", "record"));

        if (this.current.isPresent() && this.current.get() == far) {
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
                    to(
                        previous.isPresent()
                        ? childNode.link(to(previous.get()).with(Style.INVIS))
                        : childNode
                    ).with(Style.DASHED)
                );

                previous = Optional.of(node(((MutableNode) childNode).name()));
            }
        }

        return node.link(resultBox);
    }

    @Override
    public Node visit(UnificationActivationRecord uar) {
        Node node = node(this.createUniqueNodeName())
        .with(html(String.format("%s = %s", uar.getLhs().toHtml(), uar.getRhs().toHtml())));

        if (!uar.isVisited()) {
            return this.addBacktrackingEdge(uar, node);
        }

        if (this.next.isPresent() && this.next.get() == uar) {
            this.backtrackingNode = Optional.of(node);
        }

        Node resultBox = node(this.createUniqueNodeName())
        .with(html(String.format("{<font point-size=\"10\">%s</font>|%s = %s|%s}",
            LanguageManager.getInstance().getString(LanguageKey.UNIFICATION),
            uar.getLhs().toHtml(),
            uar.getRhs().toHtml(),
            uar.getResult().toHtml()
        )))
        .with(attr("shape", "record"));

        // TODO: after merge: create method isCurrent()
        if (this.current.isPresent() && this.current.get() == uar) {
            resultBox = resultBox.with(uar.getResult().isSuccess() ? ColorScheme.VIS_GREEN : ColorScheme.VIS_RED);
        }

        return node.link(resultBox);
    }

    @Override
    public Node visit(CutActivationRecord cutAr) {
        Node node = node(this.createUniqueNodeName()).with(html("!"));

        if (!cutAr.isVisited()) {
            return this.addBacktrackingEdge(cutAr, node);
        }

        if (this.next.isPresent() && this.next.get() == cutAr) {
            this.backtrackingNode = Optional.of(node);
        }

        String parentHtml
            = cutAr.getParent().isPresent()
            ? cutAr.getParent().get().getFunctor().toHtml()
            : "[no parent]";
        Node cutNoteBox = node(this.createUniqueNodeName())
        .with(html(String.format(
            LanguageManager.getInstance().getString(LanguageKey.VISUALISATION_CUT_NOTE),
            parentHtml
        )))
        .with(attr("shape", "record"));

        if (this.current.isPresent() && this.current.get() == cutAr) {
            cutNoteBox = cutNoteBox.with(ColorScheme.VIS_GREEN);
        }

        return node.link(cutNoteBox);
    }

    @Override
    public Node visit(ArithmeticActivationRecord aar) {
        Node node = node(this.createUniqueNodeName())
        .with(html(String.format("%s is %s", aar.getLhs().toHtml(), aar.getRhs().toHtml())));

        if (!aar.isVisited()) {
            return this.addBacktrackingEdge(aar, node);
        }

        if (this.next.isPresent() && this.next.get() == aar) {
            this.backtrackingNode = Optional.of(node);
        }

        Node resultBox = node(this.createUniqueNodeName())
        .with(attr("shape", "record"));

        if (aar.getResult().isError()) {
            resultBox = resultBox
            .with(html(aar.getResult().toHtml()));
        } else {
            resultBox = resultBox
            .with(html(String.format("{<font point-size=\"10\">%s</font>|%s = %s|%s}",
                LanguageManager.getInstance().getString(LanguageKey.UNIFICATION),
                aar.getLhs().toHtml(),
                // evaluated rhs is guaranteed to be set because the result was not an error
                aar.getEvaluatedRhs().toHtml(),
                aar.getResult().toHtml()
            )));
        }


        // TODO: after merge: create method isCurrent()
        if (this.current.isPresent() && this.current.get() == aar) {
            resultBox = resultBox.with(aar.getResult().isSuccess() ? ColorScheme.VIS_GREEN : ColorScheme.VIS_RED);
        }

        return node.link(resultBox);
    }

    @Override
    public Node visit(ComparisonActivationRecord car) {
        Node node = node(this.createUniqueNodeName())
        .with(html(String.format(
            "%s %s %s",
            car.getLhs().toHtml(),
            car.getGoal().getHtmlSymbol(),
            car.getRhs().toHtml()
        )));

        // TODO: these things have been moved into their own methods over at code_cut
        if (!car.isVisited()) {
            return this.addBacktrackingEdge(car, node);
        }

        if (this.next.isPresent() && this.next.get() == car) {
            this.backtrackingNode = Optional.of(node);
        }

        Node resultBox = node(this.createUniqueNodeName())
        .with(attr("shape", "record"));

        if (!car.getErrorMessage().isPresent()) {
            resultBox = resultBox
            .with(html(LanguageManager.getInstance().getString(LanguageKey.ARITHMETIC_COMPARISON_SUCCEEDED)))
            .with(ColorScheme.VIS_GREEN);
        } else {
            resultBox = resultBox
            .with(html(car.getErrorMessage().get()))
            .with(ColorScheme.VIS_RED);
        }

        return node.link(resultBox);
    }

    private Node addBacktrackingEdge(ActivationRecord ar, Node node) {
        if (this.current.isPresent() && this.current.get() == ar && this.backtrackingNode.isPresent()) {
            Node withEdge = node
            .link(
                to(this.backtrackingNode.get())
                .with(Style.DOTTED)
                .with(ColorScheme.VIS_RED)
                .with(attr("constraint", "false"))
            );

            return withEdge;
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
        Graph g = graph("visualisation").directed().with(rootNode).nodeAttr().with(Font.name("Times New Roman"));

        for (Graph rankEnforcer : maker.rankEnforcers) {
            g = g.with(rankEnforcer);
        }

        return g;
    }
}
