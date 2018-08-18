package edu.kit.ipd.pp.viper.controller;

import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;

import edu.kit.ipd.pp.viper.view.GUIComponentID;

public class CommandShowHelp extends Command {
    private JFrame frame;
    private JTabbedPane tabbedPane;
    private JComponent tabContentIntroduction;
    private JComponent tabContentEditor;
    private JComponent tabContentConsole;
    private JComponent tabContentVisualisation;
    
    private boolean isOpened;
    
    public CommandShowHelp() {
        this.isOpened = false;
    }
    
    @Override
    public void execute() {
        if (!this.isOpened) {
            this.setupFrame();
            this.setupTabs();
            this.setupTabbedPane();
            this.isOpened = true;
        }
        
        this.frame.requestFocus();
        this.frame.toFront();
    }
    
    private void setupFrame() {
        final Dimension dim = new Dimension(400, 300);            
        this.frame = new JFrame();
        this.frame.setName(GUIComponentID.FRAME_WELCOME.toString());
        this.frame.setSize(dim);
        this.frame.setMinimumSize(dim);
        this.frame.setTitle(LanguageManager.getInstance().getString(LanguageKey.WELCOME));
        this.frame.setLocationRelativeTo(null);
        this.frame.setResizable(true);
        this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        this.frame.setVisible(true);
        
        this.frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent windowEvent) {
                CommandShowHelp.this.isOpened = false;
            }
        });
    }
    
    private void setupTabs() {
        this.tabContentIntroduction = new JPanel();
        this.tabContentEditor = new JPanel();
        this.tabContentConsole = new JPanel();
        this.tabContentVisualisation = new JPanel();    
    }
    
    private void setupTabbedPane() {
        this.tabbedPane = new JTabbedPane();
        
        LanguageManager langman = LanguageManager.getInstance();
        this.tabbedPane.addTab(langman.getString(LanguageKey.TAB_INTRODUCTION), this.tabContentIntroduction);
        this.tabbedPane.addTab(langman.getString(LanguageKey.TAB_EDITOR), this.tabContentEditor);
        this.tabbedPane.addTab(langman.getString(LanguageKey.TAB_CONSOLE), this.tabContentConsole);
        this.tabbedPane.addTab(langman.getString(LanguageKey.TAB_VISUALISATION), this.tabContentVisualisation);
        
    }
}
