package edu.kit.ipd.pp.viper.controller;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import edu.kit.ipd.pp.viper.view.GUIComponentID;

public class CommandShowWelcome extends Command implements Observer {
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JComponent tabContentIntroduction;
    private JComponent tabContentEditor;
    private JComponent tabContentConsole;
    private JComponent tabContentVisualisation;
    private JComponent tabContentInterpreter;
    private JComponent tabContentExtras;

    private JLabel introductionText;
    private JLabel editorText;
    private JLabel consoleText;
    private JLabel visualisationText;
    private JLabel interpreterText;
    private JLabel extrasText;
    
    private boolean isOpened;
    
    public CommandShowWelcome() {
        this.isOpened = false;
        LanguageManager.getInstance().addObserver(this);
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
    
    private void setupFrame() {
        final Dimension dim = new Dimension(800, 400);            
        this.frame = new JFrame();
        this.frame.setName(GUIComponentID.FRAME_WELCOME.toString());
        this.frame.setSize(dim);
        this.frame.setMinimumSize(dim);
        this.frame.setTitle(LanguageManager.getInstance().getString(LanguageKey.WELCOME));
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(false);
        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.frame.setVisible(true);
        
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                CommandShowWelcome.this.isOpened = false;
            }
        });
    }
    
    private void setupTabbedPane() {
        this.tabbedPane = new JTabbedPane();

        this.tabContentIntroduction = makeIntroductionPane();
        this.tabContentEditor = makeEditorPane();
        this.tabContentConsole = makeConsolePane();
        this.tabContentVisualisation = makeVisualisationPane();
        this.tabContentInterpreter = makeInterpreterPane();
        this.tabContentExtras = makeExtrasPane();
        
        LanguageManager langman = LanguageManager.getInstance();
        this.tabbedPane.addTab(langman.getString(LanguageKey.TAB_INTRODUCTION), this.tabContentIntroduction);
        this.tabbedPane.addTab(langman.getString(LanguageKey.TAB_EDITOR), this.tabContentEditor);
        this.tabbedPane.addTab(langman.getString(LanguageKey.TAB_CONSOLE), this.tabContentConsole);
        this.tabbedPane.addTab(langman.getString(LanguageKey.TAB_VISUALISATION), this.tabContentVisualisation);
        this.tabbedPane.addTab(langman.getString(LanguageKey.TAB_INTERPRETER), this.tabContentInterpreter);
        this.tabbedPane.addTab(langman.getString(LanguageKey.TAB_EXTRAS), this.tabContentExtras);
        this.frame.add(this.tabbedPane);
    }
    
    private JComponent makeIntroductionPane() {
        JPanel panel = new JPanel();
        this.introductionText = new JLabel();
        panel.add(this.introductionText);
        return panel;
    }
    
    private JComponent makeEditorPane() {
        JPanel panel = new JPanel();
        this.editorText = new JLabel();
        panel.add(this.editorText);
        return panel;
    }
    
    private JComponent makeConsolePane() {
        JPanel panel = new JPanel();
        this.consoleText = new JLabel();
        panel.add(this.consoleText);
        return panel;
    }
    
    private JComponent makeVisualisationPane() {
        JPanel panel = new JPanel();
        this.visualisationText = new JLabel();
        panel.add(this.visualisationText);
        return panel;
    }

    private JComponent makeInterpreterPane() {
    	JPanel panel = new JPanel();
    	this.interpreterText = new JLabel();
    	panel.add(this.interpreterText);
    	return panel;
    }
    
    private JComponent makeExtrasPane() {
    	JPanel panel = new JPanel();
    	this.extrasText = new JLabel();
    	panel.add(this.extrasText);
    	return panel;
    }
    
    private void setText() {
        LanguageManager langman = LanguageManager.getInstance();
        this.frame.setTitle(langman.getString(LanguageKey.WELCOME));
        this.tabbedPane.setTitleAt(0, langman.getString(LanguageKey.TAB_INTRODUCTION));
        this.tabbedPane.setTitleAt(1, langman.getString(LanguageKey.TAB_EDITOR));
        this.tabbedPane.setTitleAt(2, langman.getString(LanguageKey.TAB_CONSOLE));
        this.tabbedPane.setTitleAt(3, langman.getString(LanguageKey.TAB_VISUALISATION));
        this.tabbedPane.setTitleAt(4, langman.getString(LanguageKey.TAB_INTERPRETER));
        this.tabbedPane.setTitleAt(5, langman.getString(LanguageKey.TAB_EXTRAS));
        
        this.introductionText.setText(langman.getString(LanguageKey.WELCOME_INTRODUCTION));
        this.editorText.setText(langman.getString(LanguageKey.WELCOME_EDITOR));
        this.consoleText.setText(langman.getString(LanguageKey.WELCOME_CONSOLE));
        this.visualisationText.setText(langman.getString(LanguageKey.WELCOME_VISUALISATION));
        this.interpreterText.setText(langman.getString(LanguageKey.WELCOME_INTERPRETER));
        this.extrasText.setText(langman.getString(LanguageKey.WELCOME_EXTRAS));
    }

    @Override
    public void update(Observable arg0, Object arg1) {
        if (this.isOpened)
            setText();
    }
}
