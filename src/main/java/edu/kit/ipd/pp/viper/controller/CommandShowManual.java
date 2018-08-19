package edu.kit.ipd.pp.viper.controller;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import edu.kit.ipd.pp.viper.view.GUIComponentID;

/**
 * Command for showing a popup with manual information.
 */
public class CommandShowManual extends Command implements Observer {
    private static final String ICON_CANCEL = "/icons_png/icon_cancel.png";
    private static final String ICON_NEXTSTEP = "/icons_png/icon_nextstep.png";
    private static final String ICON_NEXTSOLUTION = "/icons_png/icon_nextsolution.png";
    private static final String ICON_PREVIOUSSTEP = "/icons_png/icon_previousstep.png";
    private static final int ICON_SIZE = 25;

    private final JLabel cancelIcon;
    private final JLabel nextStepIcon;
    private final JLabel nextSolutionIcon;
    private final JLabel previousStepIcon;
    
    private JLabel cancelLabel;
    private JLabel nextStepLabel;
    private JLabel nextSolutionLabel;
    private JLabel previousStepLabel;
    
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JComponent tabContentManual;
    private JComponent tabContentControls;
    
    private JLabel manualText;
    private JLabel controlsText;
    
    private boolean isOpened;
    
    /**
     * Initializes a new show manual command.
     */
    public CommandShowManual() {
        this.isOpened = false;
        LanguageManager.getInstance().addObserver(this);
        
        this.cancelIcon = this.loadIcon(ICON_CANCEL);
        this.nextStepIcon = this.loadIcon(ICON_NEXTSTEP);
        this.nextSolutionIcon = this.loadIcon(ICON_NEXTSOLUTION);
        this.previousStepIcon = this.loadIcon(ICON_PREVIOUSSTEP);
    }
    
    @Override
    public void execute() {
        if (!this.isOpened) {
            this.setupFrame();
            this.setupTabbedPane();
            this.setText();
            this.isOpened = true;
        }
        
        this.frame.requestFocus();
        this.frame.toFront();
    }
    
    private JLabel loadIcon(String path) {
        ImageIcon icon = new ImageIcon(this.getClass().getResource(path));
        icon.setImage(icon.getImage().getScaledInstance(CommandShowManual.ICON_SIZE,
                                                        CommandShowManual.ICON_SIZE,
                                                        Image.SCALE_SMOOTH));
        return new JLabel(icon);
    }
    
    private void setupFrame() {
        final Dimension dim = new Dimension(650, 500);            
        this.frame = new JFrame();
        this.frame.setName(GUIComponentID.FRAME_MANUAL.toString());
        this.frame.setSize(dim);
        this.frame.setMinimumSize(dim);
        this.frame.setMaximumSize(dim);
        this.frame.setPreferredSize(dim);
        this.frame.setTitle(LanguageManager.getInstance().getString(LanguageKey.MANUAL));
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.frame.setVisible(true);
        
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                CommandShowManual.this.isOpened = false;
            }
        });
    }
    
    private void setupTabbedPane() {
        this.tabbedPane = new JTabbedPane();

        this.tabContentManual = this.makeManualPane();
        this.tabContentControls = this.makeControlsPane();
        
        LanguageManager langman = LanguageManager.getInstance();
        this.tabbedPane.addTab(langman.getString(LanguageKey.TAB_MANUAL), this.tabContentManual);
        this.tabbedPane.addTab(langman.getString(LanguageKey.TAB_CONTROLS), this.tabContentControls);
        this.frame.add(this.tabbedPane);
    }
    
    private JComponent makeManualPane() {
        JPanel panel = new JPanel();
        this.manualText = new JLabel();
        panel.add(this.manualText);
        return panel;
    }

    private JComponent makeControlsPane() {
        JPanel panel = new JPanel();
        this.controlsText = new JLabel();
        this.previousStepLabel = new JLabel();
        this.nextStepLabel = new JLabel();
        this.nextSolutionLabel = new JLabel();
        this.cancelLabel = new JLabel();
        
        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                  .addComponent(this.controlsText)
                  .addGroup(layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                      .addComponent(this.previousStepIcon)
                      .addComponent(this.previousStepLabel))
                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                      .addComponent(this.nextStepIcon)
                      .addComponent(this.nextStepLabel))
                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                      .addComponent(this.nextSolutionIcon)
                      .addComponent(this.nextSolutionLabel))
                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                      .addComponent(this.cancelIcon)
                      .addComponent(this.cancelLabel))
                ));
            
            layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addComponent(this.controlsText)
                    .addGroup(layout.createParallelGroup()
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(this.previousStepIcon)
                        .addComponent(this.previousStepLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(this.nextStepIcon)
                        .addComponent(this.nextStepLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(this.nextSolutionIcon)
                        .addComponent(this.nextSolutionLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(this.cancelIcon)
                        .addComponent(this.cancelLabel))
                    ));
        
        panel.setLayout(layout);
        return panel;
    }
    
    private void setText() {
        LanguageManager langman = LanguageManager.getInstance();
        this.frame.setTitle(langman.getString(LanguageKey.MANUAL));
        this.tabbedPane.setTitleAt(0, langman.getString(LanguageKey.TAB_MANUAL));
        this.tabbedPane.setTitleAt(1, langman.getString(LanguageKey.TAB_CONTROLS));
        
        this.manualText.setText(langman.getString(LanguageKey.MANUAL_MANUAL));
        this.controlsText.setText(langman.getString(LanguageKey.MANUAL_CONTROLS));
        
        this.previousStepLabel.setText(langman.getString(LanguageKey.TOOLTIP_PREVIOUSSTEP));
        this.nextStepLabel.setText(langman.getString(LanguageKey.TOOLTIP_NEXTSTEP));
        this.nextSolutionLabel.setText(langman.getString(LanguageKey.TOOLTIP_NEXTSOLUTION));
        this.cancelLabel.setText(langman.getString(LanguageKey.TOOLTIP_CANCEL));
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        if (this.isOpened)
            this.setText();
    }
}
