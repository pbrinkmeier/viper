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
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import edu.kit.ipd.pp.viper.view.GUIComponentID;
import edu.kit.ipd.pp.viper.view.ToolBar;

/**
 * Command for showing a popup with manual information.
 */
public class CommandShowManual extends Command implements Observer {
    /**
     * The size of the icons used for explanation purposes
     */
    public static final int ICON_SIZE = 25;

    /**
     * The dimensions of the popup
     */
    public static final Dimension DIMENSION = new Dimension(800, 600);

    private final JLabel cancelIcon;
    private final JLabel nextStepIcon;
    private final JLabel nextSolutionIcon;
    private final JLabel previousStepIcon;
    
    private final JLabel newIcon;
    private final JLabel openIcon;
    private final JLabel saveIcon;
    private final JLabel parseIcon;
    private final JLabel formatIcon;    
    
    private JLabel cancelLabel;
    private JLabel nextStepLabel;
    private JLabel nextSolutionLabel;
    private JLabel previousStepLabel;
    
    private JLabel newLabel;
    private JLabel openLabel;
    private JLabel saveLabel;
    private JLabel parseLabel;
    private JLabel formatLabel;

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
        
        this.cancelIcon = this.loadIcon(ToolBar.ICON_CANCEL);
        this.nextStepIcon = this.loadIcon(ToolBar.ICON_NEXTSTEP);
        this.nextSolutionIcon = this.loadIcon(ToolBar.ICON_NEXTSOLUTION);
        this.previousStepIcon = this.loadIcon(ToolBar.ICON_PREVIOUSSTEP);

        this.newIcon = this.loadIcon(ToolBar.ICON_NEW);
        this.openIcon = this.loadIcon(ToolBar.ICON_OPEN);
        this.saveIcon = this.loadIcon(ToolBar.ICON_SAVE);
        this.parseIcon = this.loadIcon(ToolBar.ICON_PARSE);
        this.formatIcon = this.loadIcon(ToolBar.ICON_FORMAT);
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
    
    /**
     * Helper method for loading an icon from disk. Public for testing purposes.
     * 
     * @param path The path of the icon to be loaded
     * @return The JLabel containing the icon
     */
    public JLabel loadIcon(String path) {
        ImageIcon icon = new ImageIcon(this.getClass().getResource(path));
        icon.setImage(icon.getImage().getScaledInstance(CommandShowManual.ICON_SIZE,
                                                        CommandShowManual.ICON_SIZE,
                                                        Image.SCALE_SMOOTH));
        return new JLabel(icon);
    }
    
    private void setupFrame() {
        this.frame = new JFrame();
        this.frame.setName(GUIComponentID.FRAME_MANUAL.toString());
        this.frame.setSize(CommandShowManual.DIMENSION);
        this.frame.setMinimumSize(CommandShowManual.DIMENSION);
        this.frame.setMaximumSize(CommandShowManual.DIMENSION);
        this.frame.setPreferredSize(CommandShowManual.DIMENSION);
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
        this.manualText = new JLabel("", SwingConstants.CENTER);

        this.newLabel = new JLabel("", SwingConstants.CENTER);
        this.openLabel = new JLabel("", SwingConstants.CENTER);
        this.saveLabel = new JLabel("", SwingConstants.CENTER);
        this.parseLabel = new JLabel("", SwingConstants.CENTER);
        this.formatLabel = new JLabel("", SwingConstants.CENTER);
        
        GroupLayout layout = new GroupLayout(panel);
        layout.setAutoCreateContainerGaps(true);
        layout.setAutoCreateGaps(true);
        
        layout.setHorizontalGroup(
                layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                  .addComponent(this.manualText)
                  .addGroup(layout.createSequentialGroup()
                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                      .addComponent(this.newIcon)
                      .addComponent(this.newLabel))
                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                      .addComponent(this.openIcon)
                      .addComponent(this.openLabel))
                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                      .addComponent(this.saveIcon)
                      .addComponent(this.saveLabel))
                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                      .addComponent(this.parseIcon)
                      .addComponent(this.parseLabel))
                  .addGroup(layout.createParallelGroup(GroupLayout.Alignment.CENTER)
                      .addComponent(this.formatIcon)
                      .addComponent(this.formatLabel))
                ));
            
            layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addComponent(this.manualText)
                    .addGroup(layout.createParallelGroup()
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(this.newIcon)
                        .addComponent(this.newLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(this.openIcon)
                        .addComponent(this.openLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(this.saveIcon)
                        .addComponent(this.saveLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(this.parseIcon)
                        .addComponent(this.parseLabel))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(this.formatIcon)
                        .addComponent(this.formatLabel))
                    ));
        
        panel.setLayout(layout);
        return panel;
    }

    private JComponent makeControlsPane() {
        JPanel panel = new JPanel();
        this.controlsText = new JLabel("", SwingConstants.CENTER);
        this.previousStepLabel = new JLabel("", SwingConstants.CENTER);
        this.nextStepLabel = new JLabel("", SwingConstants.CENTER);
        this.nextSolutionLabel = new JLabel("", SwingConstants.CENTER);
        this.cancelLabel = new JLabel("", SwingConstants.CENTER);
        
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

        this.newLabel.setText(langman.getString(LanguageKey.TOOLTIP_NEW));
        this.openLabel.setText(langman.getString(LanguageKey.TOOLTIP_OPEN));
        this.saveLabel.setText(langman.getString(LanguageKey.TOOLTIP_SAVE));
        this.parseLabel.setText(langman.getString(LanguageKey.TOOLTIP_PARSE));
        this.formatLabel.setText(langman.getString(LanguageKey.TOOLTIP_FORMAT));
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        if (this.isOpened)
            this.setText();
    }
}
