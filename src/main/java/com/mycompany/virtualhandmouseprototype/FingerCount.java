/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.localschoolvhm;

/**
 *
 * @author 1100015542
 */
import java.util.List;
import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Size;
import org.opencv.core.Mat;
import org.opencv.core.MatOfInt;
import org.opencv.core.MatOfInt4;
import org.opencv.core.MatOfPoint;

import org.opencv.core.Scalar;

import org.opencv.imgproc.Imgproc;
import org.opencv.core.Rect;
import org.opencv.core.Point;

public class FingerCount {

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

        MatOfPoint biggestContour = contours.get(biggestContourIdx);
        MatOfInt hullPoints = null;
        MatOfInt hullIndxs = null;
        /*TODO should hullpoints be MatOfPoint?
        
        
        ???
                
                
         */
        Imgproc.convexHull(biggestContour, hullPoints, true);
        Imgproc.convexHull(biggestContour, hullIndxs, false);

        MatOfInt4 defects = null;
        /*
        check later whether this is the equivalent of the size from before
         */
        if (3 < hullIndxs.size().area()) {//TODO could be hullIndxs.toList().size();
            Imgproc.convexityDefects(biggestContour, hullIndxs, defects);
        } else {
            return contours_image;
        }

        Rect boundingRect = Imgproc.boundingRect(hullPoints);
        Point centerOfRect = new Point((boundingRect.tl().x + boundingRect.br().x) / 2, (boundingRect.tl().y + boundingRect.br().y) / 2);

        MatOfPoint startPoints = new MatOfPoint(), farPoints = new MatOfPoint();

        for (int i = 0; i < defects.size().area(); ++i) {//TODO could be defects.toList().size();
            int[] rc0 = null, rc2 = null;
            defects.get(i, 0, rc0);
            defects.get(i, 2, rc2);
            Point p0 = new Point(biggestContour.get(rc0));
            Point p2 = new Point(biggestContour.get(rc2));
            startPoints.push_back(new MatOfPoint(p0));
            //filter based on distance from center of bounding rect
            if (findPointsDistance(p2, centerOfRect) < boundingRect.height * BOUNDING_RECT_FINGER_SIZE_SCALING) {
                farPoints.push_back(new MatOfPoint(p2));
            }
        }

        MatOfPoint filteredSP = compactOnNeighbourhoodMedian(startPoints, boundingRect.height * BOUNDING_RECT_FINGER_SIZE_SCALING);
        MatOfPoint filteredFP = compactOnNeighbourhoodMedian(farPoints, boundingRect.height * BOUNDING_RECT_FINGER_SIZE_SCALING);

        MatOfPoint filteredFingerPoints;

        if (filteredFP.toList().size() > 1) {
            MatOfPoint fingerPoints;

            for (int i = 0; i < filteredSP.toList().size(); ++i) {
                //MatOfPoint closestPoints 
            }
        }

    }

    private static double findPointsDistance(Point a, Point b) {
        return Math.sqrt(Math.pow(a.x - b.x, 2) + Math.pow(a.y - b.y, 2));

    }

    private static MatOfPoint compactOnNeighbourhoodMedian(MatOfPoint points, double maxNeighDist) {
        if (points.size().area() == 0) {
            return null;
        }
        if (maxNeighDist <= 0) {
            return null;
        }

        MatOfPoint medianPoints = new MatOfPoint();

        List<Point> pointsList = points.toList();
        Point ref = pointsList.get(0);
        Point median = pointsList.get(0);//dont cross reference here!

        for (int i = 1; i < pointsList.size(); ++i) {
            if (findPointsDistance(ref, pointsList.get(i)) > maxNeighDist) {
                medianPoints.push_back(new MatOfPoint(median));

                ref = pointsList.get(i);
                median = pointsList.get(i);//dont cross reference here!
                //swap
            } else {
                median.x += pointsList.get(i).x;
                median.x /= 2;
            }
        }

        //last
        medianPoints.push_back(new MatOfPoint(median));
        return medianPoints;
    }

    private static double findAngle(Point a, Point b, Point c) {
        double ab = findPointsDistance(a, b);
        double bc = findPointsDistance(b, c);
        double ac = findPointsDistance(a, c);
        // cos C = (a^2 + b^2 - c^2)/ 2ab
        //also converted to degrees
        double radAng = Math.acos((ab * ab + bc * bc - ac * ac) / (2 * ab * bc));
        double deg =  radAng * 180 / Math.PI;
        return deg;
    }
}
