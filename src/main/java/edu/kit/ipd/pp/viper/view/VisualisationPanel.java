package edu.kit.ipd.pp.viper.view;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

import edu.kit.ipd.pp.viper.controller.CommandNextStep;
import edu.kit.ipd.pp.viper.controller.LanguageKey;
import edu.kit.ipd.pp.viper.controller.ZoomType;
import guru.nidi.graphviz.model.Graph;

/**
 * Represents a panel containing a SVG viewer
 */
public class VisualisationPanel extends JPanel {
    /**
     * Main window
     */
    private final MainWindow main;

    /**
     * Viewer to use
     */
    private final VisualisationViewer viewer;

    /**
     * Creates a new viewer panel
     * 
     * @param gui Reference of main class
     */
    public VisualisationPanel(MainWindow gui) {
        super();

        this.main = gui;

        this.setLayout(new GridBagLayout());
        this.setBackground(Color.WHITE);

        this.viewer = new VisualisationViewer(this.main);

        GridBagConstraints viewerConstraints = new GridBagConstraints();
        viewerConstraints.fill = GridBagConstraints.HORIZONTAL;
        viewerConstraints.gridx = 0;
        viewerConstraints.gridy = 0;
        viewerConstraints.gridwidth = 1;
        viewerConstraints.gridheight = 1;
        viewerConstraints.weightx = 1.0;
        viewerConstraints.weighty = 0.8;

        this.add(this.viewer, viewerConstraints);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        GridBagConstraints panelConstraints = new GridBagConstraints();
        panelConstraints.fill = GridBagConstraints.HORIZONTAL;
        panelConstraints.gridx = 0;
        panelConstraints.gridy = 1;
        panelConstraints.gridwidth = 1;
        panelConstraints.gridheight = 1;
        panelConstraints.weightx = 1.0;
        panelConstraints.weighty = 0.2;

        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(new Button(LanguageKey.BUTTON_STEP, new CommandNextStep(this.main.getConsolePanel(),
                this.main.getVisualisationPanel(), this.main.getInterpreterManager())));

        this.add(buttonPanel, panelConstraints);
    }

    /**
     * Clears the currently shown visualisation
     */
    public void clearVisualization() {
        this.viewer.clear();
    }

    /**
     * Performs a zoom operation on the currently shown graph
     * 
     * @param direction Direction to zoom (in or out)
     */
    public void zoom(ZoomType direction) {
    }

    /**
     * Sets the displayed Graph. A previously shown graph will be cleared.
     * 
     * @param graph The graph to show
     */
    public void setFromGraph(Graph graph) {
        this.clearVisualization();
        this.viewer.setFromGraph(graph);
    }
}
