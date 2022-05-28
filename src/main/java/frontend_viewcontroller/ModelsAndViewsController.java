/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frontend_viewcontroller;

/**
 *
 * @author 1100015542
 */
public class ModelsAndViewsController {
    
    /*
     *
     * ModelsAndViewsController needs to have an instance variable to reference
     * the backend's models because the frontend's ModelsAndViewsController is
     * responsible for asking the backend to modify its data.
     *
     * Since the backend models is initially set up by an instance of the
     * BackendModelSetup class, we just need this one instance variable here:
     */
    BackendModelSetup theBackendModel;
    /*
     *
     * This class also needs to have an instance variable to reference the
     * frontend's MainViewDisplay because the ModelsAndViewsController object is
     * responsible for asking the MainViewDisplay object to update itself.
     */
    MainViewDisplay theMainViewDisplay;

    /*
     *
     * Step 1 of 2 to provide user controls: write user action as private class
     * ------------------------------------------------------------------------
     *
     * User actions are written as private inner classes that implement
     * ActionListener, and override the actionPerformed method.
     *
     * Use the following as a template for writting more user actions.
     */
    private class OpenSourceFileAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String pathToFile = theMainViewDisplay.showOpenDialog();
            try {
                if (pathToFile == null) {
                    return;
                }
                theBackendModel.theTextFile = new TextFile(pathToFile);
            } catch (IOException ex) {
                return;
            }
            theMainViewDisplay.updateTextContentField();
        }
    }

    private class SaveResultToFileAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            String pathToFile = theMainViewDisplay.showSaveDialog();
            try {
                if (pathToFile == null) {
                    return;
                }
                theBackendModel.theTextFile.saveToDisk(pathToFile);
            } catch (IOException ex) {
            }
            theMainViewDisplay.updateTextContentField();

        }

    }

    private class EncryptSourceAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                if (theBackendModel.theTextFile == null) {
                    return;
                }
                theBackendModel.theTextFile.encrypt();
            } catch (IOException ex) {
            }
            theMainViewDisplay.updateTextContentField();

        }
    }

    private class DecryptSourceAction implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent ae) {
            try {
                if (theBackendModel.theTextFile == null) {
                    return;
                }
                theBackendModel.theTextFile.decrypt();
            } catch (IOException ex) {
            }
            theMainViewDisplay.updateTextContentField();

        }
    }

    /*
     *
     * Constructor. Probably nothing for students to change.
     */
    public ModelsAndViewsController(BackendModelSetup aBackend, MainViewDisplay aMainViewDisplay) {
        this.theBackendModel = aBackend;
        this.theMainViewDisplay = aMainViewDisplay;
        this.initController();
    }

    /*
     *
     * Step 2 of 2 to provide user controls: wire user action to GUI widgets
     * ----------------------------------------------------------------------
     *
     * Once a private inner class has been written for a user action, you can
     * wire it to a GUI widget (i.e. one of the GUI widgets you created in the
     * MainViewDisplay class and which you gave a memorable variable name!).
     *
     * Use the following as templates for wiring in more user actions.
     */
    private void initController() {
        this.theMainViewDisplay.decryptSourceButton.addActionListener(new DecryptSourceAction());
        this.theMainViewDisplay.encryptSourceButton.addActionListener(new EncryptSourceAction());
        this.theMainViewDisplay.saveResultToFileButton.addActionListener(new SaveResultToFileAction());
        this.theMainViewDisplay.openSourceFileButton.addActionListener(new OpenSourceFileAction());
    }
}
