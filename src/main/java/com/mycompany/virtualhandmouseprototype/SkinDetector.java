/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.virtualhandmouseprototype;

//import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.global.opencv_core;
import org.opencv.core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Core;
//import org.bytedeco.opencv.opencv_core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.CvType;

/**
 *
 * @author 1100015542
 */
public class SkinDetector {

    private int hLowThreshold = 0;
    private int hHighThreshold = 0;
    private int sLowThreshold = 0;
    private int sHighThreshold = 0;
    private int vLowThreshold = 0;
    private int vHighThreshold = 0;
    private boolean calibrated = false;
    private Rect skinColorSamplerRectangle1, skinColorSamplerRectangle2;

    private void calculateThresholds(Mat sample1, Mat sample2) {
        int offsetLowThreshold = 80;
	int offsetHighThreshold = 30;
        
        Scalar hsvMeansSample1 = Core.mean(sample1);
        Scalar hsvMeansSample2 = Core.mean(sample2);
        this.hLowThreshold = (int) (Math.min(hsvMeansSample1.val[0], hsvMeansSample2.val[0]) - offsetLowThreshold);
	this.hHighThreshold = (int) (Math.max(hsvMeansSample1.val[0], hsvMeansSample2.val[0]) + offsetHighThreshold);

	this.sLowThreshold = (int) (Math.min(hsvMeansSample1.val[1], hsvMeansSample2.val[1]) - offsetLowThreshold);
	this.sHighThreshold = (int) (Math.max(hsvMeansSample1.val[1], hsvMeansSample2.val[1]) + offsetHighThreshold);
        
        
        //redundant but secure to make future scalars consistent with hsv
        this.vLowThreshold = (int) (Math.min(hsvMeansSample1.val[2], hsvMeansSample2.val[2]) - offsetLowThreshold);
	this.vHighThreshold = (int) (Math.max(hsvMeansSample1.val[2], hsvMeansSample2.val[2]) + offsetHighThreshold);
    }

    private void performOpening(Mat binaryImage, int structuralElementShapde, Point structuralElementSize) {

    }

    public void drawSkinColorSampler(Mat input) {
        int frameWidth = (int) input.size().width;
        int frameHeight = (int) input.size().height;

        int rectSize = 20; //rectangle of roughly this size to cover face
        Scalar rectangleColour = new Scalar(255, 0, 255);

        this.skinColorSamplerRectangle1 = new Rect(frameWidth / 5, frameHeight / 2, rectSize, rectSize); //Sampling two potential regions
        this.skinColorSamplerRectangle2 = new Rect(frameWidth / 5, frameHeight / 3, rectSize, rectSize);

        //covering up the face face
        Imgproc.rectangle(input,
                this.skinColorSamplerRectangle1,
                rectangleColour);

        Imgproc.rectangle(input,
                this.skinColorSamplerRectangle2,
                rectangleColour);

    }

    public void calibrate(Mat input) {
        //converting to HSV to increase resistance against shadows and influence
        Mat hsvInput = null;
        Imgproc.cvtColor(input, hsvInput, Imgproc.COLOR_BGR2HSV);
        Mat sample1 = new Mat(hsvInput, skinColorSamplerRectangle1);
        Mat sample2 = new Mat(hsvInput, skinColorSamplerRectangle2);
        this.calculateThresholds(sample1, sample2);

	this.calibrated = true;
    }

    
    
    
    public Mat getSkinMask(Mat input) {
        Mat skinMask = null;
        if (!this.calibrated) {
            skinMask = Mat.zeros(input.size(), CvType.CV_8UC1);
            return skinMask;
        }
        Mat hsvInput = null;
        Imgproc.cvtColor(input, hsvInput, Imgproc.COLOR_BGR2HSV);
        
        Core.inRange(hsvInput, 
                new Scalar(this.hLowThreshold, this.sLowThreshold, this.vLowThreshold), 
                new Scalar(this.hHighThreshold, this.sHighThreshold, this.vHighThreshold), 
                skinMask);
        return skinMask;
    }
}
