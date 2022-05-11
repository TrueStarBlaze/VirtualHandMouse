/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.virtualhandmouseprototype;

//import java.util.ArrayList;
import org.bytedeco.opencv.global.opencv_core;
import org.bytedeco.opencv.opencv_core.Mat;
//import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Scalar;
import org.bytedeco.opencv.global.opencv_imgproc;
//import org.bytedeco.opencv.presets.opencv_imgproc
import org.bytedeco.opencv.opencv_core.MatVector;
import org.bytedeco.opencv.opencv_core.Point;
import org.bytedeco.opencv.opencv_core.Rect;
//import org.bytedeco.opencv.opencv_core.MatVectorVector;

/**
 *
 * @author erick
 */
public class FingerCount {
    //                                      B   G   R   A
    private final Scalar blue = new Scalar(255, 0, 0, 0);
    private final Scalar green = new Scalar(0, 255, 0, 0);
    private final Scalar red = new Scalar(0, 0, 255, 0);
    private final Scalar black = new Scalar(0, 0, 0, 0);
    private final Scalar white = new Scalar(255, 255, 255, 0);
    private final Scalar yellow = new Scalar(0, 255, 255, 0);
    private final Scalar purple = new Scalar(255, 0, 255, 0);

    public Mat findFingersCount(Mat input, Mat frame) {
        Mat contoursMat = Mat.zeros(input.size(), opencv_core.CV_8UC3).asMat();

        if (input.empty() || input.channels() != 1) {
            return contoursMat;
        }
        MatVector contours = null;
        Mat hierarchy = null;
        opencv_imgproc.findContours(input, contours, hierarchy, opencv_imgproc.CV_RETR_EXTERNAL, opencv_imgproc.CHAIN_APPROX_NONE);

        if (contours.size() <= 0) {
            return contoursMat;
        }
        int biggestContourIdx = -1;
        double biggestArea = 0;
        Mat[] contoursMatArr = contours.get();
        for (int i = 0; i < contours.size(); ++i) {
            double area = opencv_imgproc.contourArea(contoursMatArr[i], true);
            if (area > biggestArea) {
                biggestArea = area;
                biggestContourIdx = i;
            }
        }

        if (biggestContourIdx < 0) {
            return contoursMat;
        }

        Mat hullPoints = null, hullIndxs = null;
        opencv_imgproc.convexHull(contoursMatArr[biggestContourIdx], hullPoints, true, true);
        opencv_imgproc.convexHull(contoursMatArr[biggestContourIdx], hullIndxs, true, false);
        
        Mat defects = null;
        if (3 < hullIndxs.rows()){//TODO
            opencv_imgproc.convexityDefects(contoursMatArr[biggestContourIdx], hullIndxs, defects);
        }else return contoursMat;
        
        Rect boundingRect = opencv_imgproc.boundingRect(hullPoints);
        Point centerOfRect = new Point(boundingRect.tl().x() + boundingRect.br().x() / 2, boundingRect.tl().y() + boundingRect.br().y() / 2);
        
        Mat startPoints = null, farPoints;
        MatVector temp = new MatVector(defects);
        for (int i = 0; i < temp.size(); ++i) {//TODO
//            startPoints.push_back(contoursMatArr[biggestContourIdx].val(0));   
        }
        
      return null;  
    }

}
