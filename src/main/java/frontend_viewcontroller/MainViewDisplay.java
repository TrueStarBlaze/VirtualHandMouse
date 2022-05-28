/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frontend_viewcontroller;

/**
 *
 * @author 1100015542
 */
import java.awt.*;
import java.io.*;
import javax.swing.*;
public class MainViewDisplay extends JFrame{
   
    /*
     *
     * MainViewDisplay needs to have a instance variable to reference the
     * backend's models because the frontend's MainViewDisplay is responsible
     * for displaying data from the backend.
     *
     * Since the backend models is initially set up by an instance of the
     * BackendModelSetup class, we just need this one instance variable here:
     */
    BackendModelSetup theBackendModel;

    /*
     *
     * Step 1 of 4 for creating GUI widgets: declare them
     * --------------------------------------------------
     *
     * GUI widgets to be displayed to the user on the screen is declared here
     * (but will be constructed and initialized in the initComponents method).
     * The user will see data and can later interact with these widgets.
     */
    JLabel textContentLabel;
    JTextArea textContentArea;
    JButton openSourceFileButton;
    JButton saveResultToFileButton;
    JButton encryptSourceButton;
    JButton decryptSourceButton;
    JScrollPane scrollPane;

    /*
     *
     * Constructor. Probably nothing for students to change.
     */
    public MainViewDisplay(BackendModelSetup aBackend) {
        this.theBackendModel = aBackend;
        this.initComponents();
    }

    /*
     *
     * initComponents is all about fulfilling Responsibility #1 of this class:
     * (1) Construct the graphical user interface (GUI) on the screen
     */
    private void initComponents() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        /*
         *
         * Step 2 of 4 for creating GUI widgets: construct them
         * ----------------------------------------------------
         *
         * Construct GUI widget components here, and add them into the
         * mainDisplayPane later
         */
        this.textContentLabel = new JLabel();
        this.textContentLabel.setText("Text content");

        this.decryptSourceButton = new JButton();
        this.decryptSourceButton.setText("Decrypt");

        this.textContentArea = new JTextArea();
        this.textContentArea.setLineWrap(true);
        this.textContentArea.setWrapStyleWord(true);
        this.textContentArea.setEditable(false);
        this.scrollPane = new JScrollPane(this.textContentArea);

        this.encryptSourceButton = new JButton();
        this.encryptSourceButton.setText("Encrypt");

        this.saveResultToFileButton = new JButton();
        this.saveResultToFileButton.setText("Save File");

        this.openSourceFileButton = new JButton();
        this.openSourceFileButton.setText("Open File");

        /*
         * Choose your LayoutManager for the mainDisplayPane here. See:
         * http://docs.oracle.com/javase/tutorial/uiswing/layout/visual.html
         *
         * I suggest GridBagLayout. For more details, see:
         * http://docs.oracle.com/javase/tutorial/uiswing/layout/gridbag.html
         */
        Container mainDisplayPane = this.getContentPane();
        mainDisplayPane.setLayout(new GridBagLayout());


        /*
         * you should construct a new GridBagConstraints object each time you
         * use it, in order to avoid subtle bugs...
         */
        GridBagConstraints c;


        /*
         *
         * Step 3 of 4 for creating GUI widgets: add them to the pane
         * ----------------------------------------------------------
         *
         * For each GUI widget you constructed earlier, you will now specify a
         * GridBagConstraints for it, then add the widget into the
         * mainDisplayPane
         */
        c = new GridBagConstraints(); // construct a new GridBagConstraints each time you use it, to avoid subtle bugs...
        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.gridheight = 1;
        c.anchor = GridBagConstraints.WEST;
//        c.anchor = GridBagConstraints.FIRST_LINE_START;
//        c.ipady = 10;
//        c.ipadx = 10;
//        c.insets = new Insets(0, 50, 0, 0);
        mainDisplayPane.add(this.textContentLabel, c);

        c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 1;
        c.gridwidth = 2;
        c.gridheight = 2;
        c.fill = GridBagConstraints.BOTH;
        c.ipadx = 400;
        c.ipady = 200;
        mainDisplayPane.add(this.scrollPane, c);

        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        mainDisplayPane.add(this.openSourceFileButton);

        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 1;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 0.3;
//        c.ipady = -2;
//        c.insets = new Insets(50, 0, 0, 0);
        mainDisplayPane.add(this.encryptSourceButton, c);

//        c.gridx = 1;
//        c.gridy = 0;
//        c.weightx = 0.5;
//        mainDisplayPane.add(this.saveResultToFileButton, c);
        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 2;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.weighty = 0.3;

//        c.ipady = -2;
//        c.insets = new Insets(150, 0, 0, 0);
        mainDisplayPane.add(this.decryptSourceButton, c);

        c = new GridBagConstraints();
        c.gridx = 2;
        c.gridy = 3;
        c.gridwidth = 1;
        c.gridheight = 1;
        mainDisplayPane.add(this.saveResultToFileButton, c);

        this.pack(); // leave this line last in this method.
        // must pack this JFrame before it can be displayed on screen
    }

    /*
     *
     * Step 4 of 4 for creating GUI widgets: write methods to update them
     * -------------------------------------------------------------------
     *
     * The methods below are all about fulfilling Responsibility #2 of this
     * class: (2) Pull data from the backend to display in the GUI
     *
     * Write below all the methods for displaying data into the GUI using this
     * MainViewDisplay object
     */
    void updateTextContentField() {
        if (this.theBackendModel.theTextFile != null) {
            this.textContentArea.setText(this.theBackendModel.theTextFile.fileContent);
        } else {
            this.textContentArea.setText("");
        }

    }

    String showSaveDialog() {
        JFileChooser jfc = new JFileChooser();
        int status = jfc.showSaveDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            File theFile = jfc.getSelectedFile();
            String thePath = theFile.getAbsolutePath();
            return thePath;
        }

        return null;
    }

    String showOpenDialog() {
        JFileChooser jfc = new JFileChooser();
        int status = jfc.showOpenDialog(this);
        if (status == JFileChooser.APPROVE_OPTION) {
            File theFile = jfc.getSelectedFile();
            String thePath = theFile.getAbsolutePath();
            return thePath;
        }

        return null;
    } 
}
