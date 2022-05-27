/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package frontend_viewcontroller;

/**
 *
 * @author 1100015542
 */
import backend_models.*;
public class BackendModelSetup {

    //TODO
    BackgroundRemover backgroundRemover;
    SkinDetector skinDetector;
    FaceDetector faceDetector;
    FingerCount fingerCount;
    //instantiate model

    public BackendModelSetup() {
        //construct
        BackgroundRemover backgroundRemover = new BackgroundRemover();
        SkinDetector skinDetector = new SkinDetector();
        FaceDetector faceDetector = new FaceDetector();
        FingerCount fingerCount = new FingerCount();
    }
}
