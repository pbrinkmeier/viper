package edu.kit.ipd.pp.viper.controller;

import java.awt.Color;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JFileChooser;
import javax.swing.filechooser.FileFilter;

import org.apache.commons.io.FilenameUtils;

import edu.kit.ipd.pp.viper.view.ConsolePanel;
import edu.kit.ipd.pp.viper.view.EditorPanel;

public class CommandSave extends Command {
	private ConsolePanel console;
	private EditorPanel editor;
	private SaveType saveType;
	
	private final String KEY_PROLOG_FILES = "key_prolog_files";
	private final String KEY_SAVE_FILE_ERROR = "key_save_file_error";
	private final String KEY_SAVE_FILE_SUCCESS = "key_save_file_success";
	
	/**
     * @param console 
     * @param editor 
     * @param saveAs
     */
    public CommandSave(ConsolePanel console, EditorPanel editor, SaveType saveType) {
        this.console = console;
        this.editor = editor;
        this.saveType = saveType;
    }

    /**
     * @return
     */
    public void execute() {
    	boolean editorHasFileRef = false;
    	if (saveType == SaveType.SAVE && editorHasFileRef)
    		save();
    	else
    		saveAs();
    }
    
    private void save() {
    	String filePath = "";
    	File file = new File(filePath);
    	try {
			FileOutputStream out = new FileOutputStream(file);
			out.write(editor.getSourceText().getBytes());
			out.flush();
			out.close();
		} catch (FileNotFoundException e) {
			String err = LanguageManager.getInstance().getString(KEY_SAVE_FILE_ERROR);
			console.printLine(err + ": " + file.getAbsolutePath(), Color.RED);
			e.printStackTrace();
		} catch (IOException e) {
			String err = LanguageManager.getInstance().getString(KEY_SAVE_FILE_ERROR);
			console.printLine(err + ": " + file.getAbsolutePath(), Color.RED);
			e.printStackTrace();
		}
    }
    
    private void saveAs() {
    	FileFilter filter = new FileFilter() {
			@Override
			public String getDescription() {
				return LanguageManager.getInstance().getString(KEY_PROLOG_FILES);
			}
			
			@Override
			public boolean accept(File f) {
				return FilenameUtils.getExtension(f.getName()).toLowerCase().equals("pl");
			}
		};
    	
    	JFileChooser chooser = new JFileChooser();
    	chooser.setFileFilter(filter);
    	int rv = chooser.showSaveDialog(null);
    	
    	if (rv == JFileChooser.APPROVE_OPTION) {
    		try {
				FileOutputStream out = new FileOutputStream(chooser.getSelectedFile());
				out.write(editor.getSourceText().getBytes());
				out.flush();
				out.close();
				
				String msg = LanguageManager.getInstance().getString(KEY_SAVE_FILE_SUCCESS);
				console.printLine(msg + ": " + chooser.getSelectedFile().getAbsolutePath(), Color.BLACK);
			} catch (FileNotFoundException e) {
				String err = LanguageManager.getInstance().getString(KEY_SAVE_FILE_ERROR);
				console.printLine(err + ": " + chooser.getSelectedFile().getAbsolutePath(), Color.RED);
				e.printStackTrace();
			} catch (IOException e) {
				String err = LanguageManager.getInstance().getString(KEY_SAVE_FILE_ERROR);
				console.printLine(err + ": " + chooser.getSelectedFile().getAbsolutePath(), Color.RED);
				e.printStackTrace();
			}
    	}
    }
}
