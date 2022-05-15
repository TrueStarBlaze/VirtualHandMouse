/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.virtualhandmouseprototype;

//import java.util.ArrayList;
//import org.bytedeco.opencv.global.opencv_core;
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.CvType;
//import org.bytedeco.opencv.opencv_core.Mat;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfPoint;
//import org.bytedeco.opencv.opencv_core.Point;
//import org.bytedeco.opencv.opencv_core.Scalar;
import org.opencv.core.Scalar;
//import org.bytedeco.opencv.global.opencv_imgproc;
import org.opencv.imgproc.Imgproc;
//import org.bytedeco.opencv.presets.opencv_imgproc
//import org.bytedeco.opencv.opencv_core.MatVector;
//import org.bytedeco.opencv.opencv_core.Point;
//import org.bytedeco.opencv.opencv_core.Rect;
import org.opencv.core.Rect;
import org.opencv.core.Point;
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

    private final double BOUNDING_RECT_FINGER_SIZE_SCALING = 0.3;

    public Mat findFingersCount(Mat input, Mat frame) {
        Mat contours_image = Mat.zeros(input.size(), CvType.CV_8UC3);

        if (input.empty() || input.channels() != 1) {
            return contours_image;
        }
        List<MatOfPoint> contours = null;
        Mat hierarchy = null;
        //CV_RETR_EXTERNAL = 0
        Imgproc.findContours(input, contours, hierarchy, 0, Imgproc.CHAIN_APPROX_NONE);

        if (contours.size() <= 0) {
            return contours_image;
        }
        
        int biggestContourIdx = -1;
        double biggestArea = 0;
        
        for (int i = 0; i < contours.size(); ++i) {
            double area = Imgproc.contourArea(contours.get(i), true);
            if (area > biggestArea) {
                biggestArea = area;
                biggestContourIdx = i;
            }
        }

        if (biggestContourIdx < 0) {
            return contours_image;
        }

        MatOfInt hullPoints = null;
        MatOfInt hullIndxs = null;
        /*TODO should hullpoints be MatOfPoint?
        
        
        ???
                
                
        */
        Imgproc.convexHull(contours.get(biggestContourIdx), hullPoints, true);
        Imgproc.convexHull(contours.get(biggestContourIdx), hullIndxs, false);

        MatOfInt4 defects = null;
        if (3 < hullIndxs.rows()) {//TODO
            Imgproc.convexityDefects(contours.get(biggestContourIdx), hullIndxs, defects);
        } else {
            return contours_image;
        }

        Rect boundingRect = Imgproc.boundingRect(hullPoints);
        Point centerOfRect = new Point(boundingRect.tl().x() + boundingRect.br().x() / 2, boundingRect.tl().y() + boundingRect.br().y() / 2);

        Mat startPoints = null, farPoints;
        MatVector temp = new MatVector(defects);
        for (int i = 0; i < temp.size(); ++i) {//TODO
            startPoints.push_back(contoursMatArr[biggestContourIdx]);

            if (this.findPointsDistance(new Point(contoursMatArr[biggestContourIdx]), centerOfRect) < boundingRect.height() * BOUNDING_RECT_FINGER_SIZE_SCALING) {//TODO
                  
            }
        }

        return null;//TODO
    }

    public static double findPointsDistance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x() - b.x(), 2) + Math.pow(a.y() - b.y(), 2));

    }

}
