/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.virtualhandmouseprototype;

import java.nio.ByteBuffer;
import org.bytedeco.opencv.opencv_core.Mat;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.javacpp.presets.javacpp;
//import org.opencv.core.Mat;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.global.opencv_imgproc;
//import org.opencv.core.Point;
//import org.opencv.core.Rect;
import org.bytedeco.opencv.opencv_core.Rect;
//import org.opencv.core.Scalar;
import org.bytedeco.opencv.opencv_core.Scalar;
//import org.opencv.imgproc.Imgproc;
//TODO
//import org.opencv.core.CvType;
//import org.bytedeco.opencv.opencv_core.CvScalar;
//import org.opencv.core.Size;
import org.bytedeco.opencv.opencv_core.Size;
import org.bytedeco.javacpp.indexer.DoubleIndexer;
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
//        Scalar hsvMeansSample1 = opencv_core.mean(sample1);
        Scalar hsvMeansSample1 = opencv_core.mean(sample1);
        Mat hsvMeansMat1 = new Mat(hsvMeansSample1);
        DoubleIndexer dI1 = hsvMeansMat1.createIndexer();

//        Scalar hsvMeansSample2 = opencv_core.mean(sample2);
Scalar hsvMeansSample2 = opencv_core.mean(sample2);
        Mat hsvMeansMat2 = new Mat(hsvMeansSample2);
        DoubleIndexer dI2 = hsvMeansMat2.createIndexer();
        
        //h channel
        this.hLowThreshold = (int) (Math.min(dI1.get(0), dI1.get(0)) - offsetLowThreshold);
	this.hHighThreshold = (int) (Math.max(dI2.get(0), dI2.get(0)) + offsetHighThreshold);
        //s channel
        this.hLowThreshold = (int) (Math.min(dI1.get(1), dI1.get(1)) - offsetLowThreshold);
	this.hHighThreshold = (int) (Math.max(dI2.get(1), dI2.get(1)) + offsetHighThreshold);
        
        //v channel
        //redundant but secure to make future scalars consistent with hsv
        this.hLowThreshold = (int) (Math.min(dI1.get(2), dI1.get(2)) - offsetLowThreshold);
	this.hHighThreshold = (int) (Math.max(dI2.get(2), dI2.get(2)) + offsetHighThreshold);
    }

    private void performOpening(Mat binaryImage, int structuralElementShape, Point structuralElementSize) {
        Mat structuringElement = opencv_imgproc.getStructuringElement(structuralElementShape, new Size(structuralElementSize));
        opencv_imgproc.morphologyEx(binaryImage, binaryImage, opencv_imgproc.MORPH_OPEN, structuringElement);
            
        //binary img is src and dst
    }

    public void drawSkinColorSampler(Mat input) {
        int frameWidth = (int) input.size().width();
        int frameHeight = (int) input.size().height();

        int rectSize = 20; //rectangle of roughly this size to cover face
//        Scalar rectangleColour = new Scalar(255, 0, 255);
        Scalar rectangleColour = new Scalar(255, 0, 255, 0);

        this.skinColorSamplerRectangle1 = new Rect(frameWidth / 5, frameHeight / 2, rectSize, rectSize); //Sampling two potential regions
        this.skinColorSamplerRectangle2 = new Rect(frameWidth / 5, frameHeight / 3, rectSize, rectSize);

        //covering up the face face
        opencv_imgproc.rectangle(input,
                this.skinColorSamplerRectangle1,
                rectangleColour);

        opencv_imgproc.rectangle(input,
                this.skinColorSamplerRectangle2,
                rectangleColour);

    }

    public void calibrate(Mat input) {
        //converting to HSV to increase resistance against shadows and influence
        Mat hsvInput = null;
        opencv_imgproc.cvtColor(input, hsvInput, opencv_imgproc.COLOR_BGR2HSV);
        Mat sample1 = new Mat(hsvInput, skinColorSamplerRectangle1);
        Mat sample2 = new Mat(hsvInput, skinColorSamplerRectangle2);
        this.calculateThresholds(sample1, sample2);

	this.calibrated = true;
    }

    
    
    
    public Mat getSkinMask(Mat input) {
        Mat skinMask = null;
        if (!this.calibrated) {
            skinMask = Mat.zeros(input.size(), opencv_core.CV_8UC1);
            
            return skinMask;
        }
        Mat hsvInput = null;
        opencv_imgproc.cvtColor(input, hsvInput, opencv_imgproc.COLOR_BGR2HSV);
        
        opencv_core.inRange(hsvInput, 
                new Scalar(this.hLowThreshold, this.sLowThreshold, this.vLowThreshold), 
                new Scalar(this.hHighThreshold, this.sHighThreshold, this.vHighThreshold), 
                skinMask);
        return skinMask;
    }
}
