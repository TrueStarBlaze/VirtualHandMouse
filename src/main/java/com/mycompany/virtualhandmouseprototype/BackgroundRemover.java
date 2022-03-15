/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.virtualhandmouseprototype;

import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;
import org.opencv.core.CvType;
import java.lang.Character;

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
        Imgproc.cvtColor(input, background, Imgproc.COLOR_BGR2GRAY);
        this.calibrated = true;
    }
    
    public Mat getForeground(Mat input) {
//        Mat foregroundMask =
return null;
    }
    
    private Mat getForeGroundMask(Mat input) {
        Mat foregroundMask = null;
        if (!this.calibrated){
            foregroundMask = Mat.zeros(input.size(), CvType.CV_8SC1);
            return foregroundMask;
        }
        
        Imgproc.cvtColor(input, foregroundMask, Imgproc.COLOR_BGR2GRAY);
        
        this.removeBackground(foregroundMask, this.background);
        
        return foregroundMask;
    }

    private void removeBackground(Mat input, Mat background) {
        int offset = 10;
        byte[] buffer = new byte[(int) input.total() * input.channels()];
        for (int i = 0; i < input.rows(); ++i)  {
            for (int j = 0; j < input.cols(); ++j) {
                int pixel = input.get(i, j, buffer);
            }
        }
    }
}
