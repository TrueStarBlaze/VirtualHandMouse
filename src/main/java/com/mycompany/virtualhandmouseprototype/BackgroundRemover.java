/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.virtualhandmouseprototype;

import org.opencv.core.Mat;
//import org.bytedeco.opencv.opencv_core.Mat;
import org.opencv.imgproc.Imgproc;
//import org.bytedeco.opencv.global.opencv_imgproc;
import org.opencv.core.CvType;
//import org.bytedeco.opencv.
//import org.bytedeco.opencv.global.opencv_core;
//import org.bytedeco.javacpp.indexer.UByteBufferIndexer;

/**
 *
 * @author 1100015542
 */
public class BackgroundRemover {
    private final double[] white = {255, 255, 255};
    private final double[] black = {0, 0, 0};
    private Mat background;
    public boolean calibrated;

    public BackgroundRemover() {
        this.calibrated = false;
        this.background = null;
    }

    private void calibrate(Mat input) {
        Imgproc.cvtColor(input, background, Imgproc.COLOR_BGR2GRAY);
        this.calibrated = true;
    }

    public Mat getForeground(Mat input) {
//        Mat foregroundMask =
        return null;
    }

    private Mat getForeGroundMask(Mat input) {
        Mat foregroundMask = null;
        if (!this.calibrated) {
            foregroundMask = Mat.zeros(input.size(), CvType.CV_8SC1);
            return foregroundMask;
        }

        Imgproc.cvtColor(input, foregroundMask, Imgproc.COLOR_BGR2GRAY);

        this.removeBackground(foregroundMask, this.background);

        return foregroundMask;
    }

    private void removeBackground(Mat input, Mat background) {
        int thresholdOffset = 10;
        for (int r = 0; r < input.rows(); ++r) {
            for (int c = 0; c < input.cols(); ++c) {
                double[] inputPixArr = input.get(r, c);
                double[] bgPixArr = background.get(r, c);
                double inputPix = inputPixArr[0] + inputPixArr[1];
                double bgPix = inputPixArr[0] + inputPixArr[1];
                if (inputPix >= bgPix - thresholdOffset && inputPix <= bgPix + thresholdOffset) {
                    input.put(r, c, black);
                }else {
                    input.put(r, c, white);
                }
            }
        }
    }
}
