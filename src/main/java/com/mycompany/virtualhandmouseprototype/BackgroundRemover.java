/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.virtualhandmouseprototype;

//import org.opencv.core.Mat;
import org.bytedeco.opencv.opencv_core.Mat;
//import org.opencv.imgproc.Imgproc;
import org.bytedeco.opencv.global.opencv_imgproc;
//import org.opencv.core.CvType;
//import org.bytedeco.opencv.
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.javacpp.indexer.UByteBufferIndexer;

/**
 *
 * @author 1100015542
 */
public class BackgroundRemover {

    private Mat background;
    public boolean calibrated;

    public BackgroundRemover() {
        this.calibrated = false;
        this.background = null;
    }

    private void calibrate(Mat input) {
        opencv_imgproc.cvtColor(input, background, opencv_imgproc.COLOR_BGR2GRAY);
        this.calibrated = true;
    }

    public Mat getForeground(Mat input) {
//        Mat foregroundMask =
        return null;
    }

    private Mat getForeGroundMask(Mat input) {
        Mat foregroundMask = null;
        if (!this.calibrated) {
            foregroundMask = Mat.zeros(input.size(), opencv_core.CV_8SC1).asMat();
            return foregroundMask;
        }

        opencv_imgproc.cvtColor(input, foregroundMask, opencv_imgproc.COLOR_BGR2GRAY);

        this.removeBackground(foregroundMask, this.background);

        return foregroundMask;
    }

    private void removeBackground(Mat input, Mat background) {
        int thresholdOffset = 10;
        UByteBufferIndexer inputIdx = input.createIndexer();
        UByteBufferIndexer bgIdx = background.createIndexer();
        for (int i = 0; i < input.rows(); ++i) {
            for (int j = 0; j < input.cols(); ++j) {
                int inputPix = inputIdx.get(i, j);
                int bgPix = bgIdx.get(i, j);

                if (inputPix >= bgPix - thresholdOffset && inputPix <= bgPix + thresholdOffset) {
                    inputIdx.put(i, j, 0);
                }else {
                    inputIdx.put(i, j, 255);
                }
            }
        }
    }
}
