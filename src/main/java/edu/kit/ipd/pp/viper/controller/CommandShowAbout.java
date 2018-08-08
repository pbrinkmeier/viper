package edu.kit.ipd.pp.viper.controller;

import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Observable;
import java.util.Observer;

import javax.swing.GroupLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

/**
 * Command for showing an about window for further information about the project.
 */
public class CommandShowAbout extends Command implements Observer {
    private static final String ICON_PATH = "/icons_png/viper-icon.png";
    
    private boolean isOpened;

    private JFrame frame;
    private JLabel labelName;
    private JLabel labelAuthors;
    private JLabel labelLibraries;
    private JLabel labelIcon;
    
    /**
     * Initializes a new show about command.
     */
    public CommandShowAbout() {
        this.isOpened = false;
        
        ImageIcon icon = new ImageIcon(this.getClass().getResource(ICON_PATH));
        icon.setImage(icon.getImage().getScaledInstance(160, 160, Image.SCALE_SMOOTH));
        this.labelIcon = new JLabel(icon);
        
        LanguageManager.getInstance().addObserver(this);
    }
    
    private void setText() {
        LanguageManager langman = LanguageManager.getInstance();

        this.frame.setTitle(langman.getString(LanguageKey.MENU_ABOUT));
        
        this.labelName.setText("<html><b>" + langman.getString(LanguageKey.ABOUT_NAME)
                + "</b></html>");
        
        this.labelAuthors.setText("<html><b>" + langman.getString(LanguageKey.ABOUT_AUTHORS)
                + "</b><br/>"
                + "Lukas Brocke<br/"
                + "Paul Brinkmeier<br/>"
                + "Jannik Koch<br/"
                + "Aaron Maier<br/"
                + "Christian Oder" + "</html>");

        this.labelLibraries.setText("<html><b>" + langman.getString(LanguageKey.ABOUT_LIBRARIES)
                + "</b><br/>"
                + "Fifesoft RSyntaxTextArea<br/>"
                + "Apache Batik<br/>"
                + "Graphviz-Java" + "</html>");
    }
    
    @Override
    public void execute() {
        if (!this.isOpened) {
            this.frame = new JFrame();
            this.frame.setSize(400, 300);
            this.frame.setLocationRelativeTo(null);
            this.frame.setResizable(false);
            this.frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            this.frame.setVisible(true);

            this.labelName = new JLabel();
            this.labelName.setVisible(true);
            this.labelName.setHorizontalAlignment(SwingConstants.CENTER);

            this.labelAuthors = new JLabel();
            this.labelAuthors.setVisible(true);
            this.labelAuthors.setHorizontalAlignment(SwingConstants.LEFT);

            this.labelLibraries = new JLabel();
            this.labelLibraries.setVisible(true);
            this.labelLibraries.setHorizontalAlignment(SwingConstants.LEFT);

            this.setText();
            
            GroupLayout layout = new GroupLayout(this.frame.getContentPane());
            layout.setAutoCreateContainerGaps(true);
            layout.setAutoCreateGaps(true);
            
            layout.setHorizontalGroup(
                layout.createParallelGroup()
                  .addComponent(this.labelName)
                  .addGroup(layout.createSequentialGroup()
                      .addComponent(this.labelIcon)
                      .addGroup(layout.createParallelGroup()
                              .addComponent(this.labelAuthors)
                              .addComponent(this.labelLibraries)))
            );
            
            layout.setVerticalGroup(
                layout.createSequentialGroup()
                    .addComponent(this.labelName)
                    .addGroup(layout.createParallelGroup()
                            .addComponent(this.labelIcon)
                            .addGroup(layout.createSequentialGroup()
                                    .addComponent(this.labelAuthors)
                                    .addComponent(this.labelLibraries)))
            );
            
            
            this.frame.getContentPane().setLayout(layout);
            this.frame.pack();
            
            this.frame.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent windowEvent) {
                    isOpened = false;
                }
            });
            
            this.isOpened = true;
        }
        
        this.frame.requestFocus();
        this.frame.toFront();
    }

    @Override
    public void update(Observable o, Object arg) {
        if (this.isOpened) {
            this.setText();
        }
    }
}
