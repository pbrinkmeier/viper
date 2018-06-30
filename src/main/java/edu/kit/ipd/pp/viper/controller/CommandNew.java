package edu.kit.ipd.pp.viper.controller;

import javax.swing.JOptionPane;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;
import edu.kit.ipd.pp.viper.view.VisualisationPanel;

public class CommandNew extends Command {
	private final String KEY_NO = "key_no";
	private final String KEY_YES = "key_yes";
	private final String KEY_CANCEL = "key_cancel";
	private final String KEY_CONFIRMATION = "key_confirm_new";
	private final String KEY_CONFIRMATION_TITLE = "key_confirm_new_title";
	
	private ConsolePanel console;
	private EditorPanel editor;
	private VisualisationPanel visualisation;
	
	/**
     * @param console 
     * @param editor 
     * @param visualisation
     */
    public CommandNew(ConsolePanel console, EditorPanel editor, VisualisationPanel visualisation) {
    	this.console = console;
    	this.editor = editor;
    	this.visualisation = visualisation;
    }

    /**
     * @return
     */
    public void execute() {
        if (editor.hasChanged()) {
        	LanguageManager langman = LanguageManager.getInstance();
        	Object options[] = { langman.getString(KEY_YES), langman.getString(KEY_NO), langman.getString(KEY_CANCEL) };
        	int rv = JOptionPane.showOptionDialog(null, langman.getString(KEY_CONFIRMATION),
        			langman.getString(KEY_CONFIRMATION_TITLE),
        			JOptionPane.YES_NO_CANCEL_OPTION,
        			JOptionPane.QUESTION_MESSAGE,
        			null,
        			options,
        			options[2]);
        	
        	switch (rv) {
	        	// Yes option
	    		case 0:
	    			CommandSave save = new CommandSave(console, editor, SaveType.SAVE);
	    			save.execute();
	    			break;
	    		// No option
	    		case 1:
	    			break;
	    		// Cancel option
	    		default:
	    			return;
        	}
        }
        
        editor.setSourceText("");
        editor.setHasChanged(false);
        console.clearAll();
        visualisation.clearVisualization();
    }
}
