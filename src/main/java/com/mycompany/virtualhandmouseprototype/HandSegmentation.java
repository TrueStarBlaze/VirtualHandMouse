/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.virtualhandmouseprototype;

//import org.bytedeco.opencv.opencv_core.Mat;
import org.opencv.core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.opencv.core.Rect;
//import org.bytedeco.opencv.opencv_core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;

/**
 *
 * @author 1100015542
 */
public class HandSegmentation {

    private int hLowThreshold = 0;
    private int hHighThreshold = 0;
    private int sLowThreshold = 0;
    private int sHighThreshold = 0;
    private int vLowThreshold = 0;
    private int vHighThreshold = 0;
    private boolean calibrated = false;
    private Rect skinColorSamplerRectangle1, skinColorSamplerRectangle2;

    private void calculateThresholds(Mat sample1, Mat sample2) {

    }

    private void performOpening(Mat binaryImage, int structuralElementShapde, Point structuralElementSize) {

    }

    public void drawSkinColorSampler(Mat input) {
        int frameWidth  = (int) input.size().width;
        int frameHeight = (int) input.size().height;
        
        int rectSize = 20;
        Scalar rectangleColour = new Scalar(255, 0, 255);
        
        this.skinColorSamplerRectangle1 = new Rect(frameWidth / 5, frameHeight / 2, rectSize, rectSize);
        this.skinColorSamplerRectangle2 = new Rect(frameWidth / 5, frameHeight / 3, rectSize, rectSize);
        
        Imgproc.rectangle(input,
		skinColorSamplerRectangle1,
		rectangleColour);
        
        Imgproc.rectangle(input, skinColorSamplerRectangle2, rectangleColour);
        

    }

    public void calibrate(Mat input) {

    }

    public Mat getSkinMask(Mat input) {

    }
}
