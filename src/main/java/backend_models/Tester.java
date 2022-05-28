/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend_models;

import java.awt.AWTException;
import org.opencv.core.Mat;

import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

/**
 *
 * @author erick
 */
public class Tester {

//    static {
//        System.load("C:\\Users\\erick\\Documents\\NetBeansProjects\\LocalIndexerTest\\opencv\\build\\java\\x64\\opencv_java455.dll");
//    }
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AWTException, InterruptedException, Exception {

        int cam = 0;
        VideoCapture vc = new VideoCapture(cam);
        vc.set(Videoio.CAP_PROP_SETTINGS, 0);
        ++cam;
        for (; !vc.isOpened() && cam < 3; ++cam) {
            vc = new VideoCapture(cam);
        }
        if (!vc.isOpened()) {
            System.out.println("Couldn't find device camera");
            System.exit(0);
        }

        Mat frame = new Mat(), frameOut = new Mat(), handMask = new Mat(),
                foreground = new Mat(), fingerCountDebug = new Mat();

        //init
        BackgroundRemover backgroundRemover = new BackgroundRemover();
        SkinDetector skinDetector = new SkinDetector();
        FaceDetector faceDetector = new FaceDetector();
        FingerCount fingerCount = new FingerCount();

        while (true) {
            vc.retrieve(frame);
            frameOut = frame.clone();
            skinDetector.drawSkinColorSampler(frameOut);

            foreground = backgroundRemover.getForeground(frame);

            faceDetector.removeFaces(frame, foreground);
            handMask = skinDetector.getSkinMask(foreground);
            fingerCountDebug = fingerCount.findFingersCount(handMask, frameOut);

            HighGui.imshow("output", frameOut);
            HighGui.imshow("foreground", foreground);
            HighGui.imshow("handMask", handMask);
            HighGui.imshow("handDetection", fingerCountDebug);

            int key = HighGui.waitKey(1);

            switch (key) {
                // esc
                case 27:
                    System.exit(0);
                // b
                case 98:
                    backgroundRemover.calibrate(frame);
                    break;
                // s
                case 115:
                    skinDetector.calibrate(frame);
                    break;
                default:
                    ;
            }
        }
    }

}
