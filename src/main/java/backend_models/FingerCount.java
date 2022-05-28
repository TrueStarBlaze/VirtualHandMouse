/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend_models;

/**
 *
 * @author 1100015542
 */
import java.util.ArrayList;
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

    private final double LIMIT_ANGLE_SUP = 60, LIMIT_ANGLE_INF = 5,
            BOUNDING_RECT_NEIGHBOR_DISTANCE_SCALING = 0.05,
            BOUNDING_RECT_FINGER_SIZE_SCALING = 0.3;
    private final static Scalar blue = new Scalar(255, 0, 0, 0),
            green = new Scalar(0, 255, 0, 0),
            red = new Scalar(0, 0, 255, 0),
            black = new Scalar(0, 0, 0, 0),
            white = new Scalar(255, 255, 255, 0),
            yellow = new Scalar(0, 255, 255, 0),
            purple = new Scalar(255, 0, 255, 0);

    public Mat findFingersCount(Mat input, Mat frame) {
        Mat contoursImage = Mat.zeros(input.size(), CvType.CV_8UC3);

        if (input.empty() || input.channels() != 1) {
            return contoursImage;
        }
        List<MatOfPoint> contours = new ArrayList<>();
        Mat hierarchy = new Mat();
        //CV_RETR_EXTERNAL = 0
        Imgproc.findContours(input, contours, hierarchy, 0, Imgproc.CHAIN_APPROX_NONE);

        if (contours.size() <= 0) {
            return contoursImage;
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
            return contoursImage;
        }
/*
        TODO 
        optimize for just one contour: the biggest one
        */
        MatOfPoint biggestContour = contours.get(biggestContourIdx);
        
        List<MatOfPoint> hullList = new ArrayList<>();
        MatOfInt hullIndxs = new MatOfInt();
        
        Imgproc.convexHull(biggestContour, hullIndxs);
        
        Point[] biggestContourArray = biggestContour.toArray();
        Point[] hullPoints = new Point[hullIndxs.rows()];
        List<Integer> hullContourIdxList = hullIndxs.toList();
        
        for (int i = 0; i < hullContourIdxList.size(); ++i) {
            hullPoints[i] = biggestContourArray[hullContourIdxList.get(i)];
        }
        hullList.add(new MatOfPoint(hullPoints));
        
                
////////        MatOfInt hullPoints = null;
////////        MatOfInt hullIndxs = null;
////////        /*
////////        TODO should hullpoints be MatOfPoint?
////////         */
////////        Imgproc.convexHull(biggestContour, hullPoints, true);
////////        Imgproc.convexHull(biggestContour, hullIndxs, false);

        MatOfInt4 defects = new MatOfInt4();
        /*
        check later whether this is the equivalent of the size from before
         */
        if (3 < hullIndxs.size().area()) {//TODO could be hullIndxs.toList().size();
            Imgproc.convexityDefects(biggestContour, hullIndxs, defects);
        } else {
            return contoursImage;
        }
        
        MatOfPoint hullPointsMat = new MatOfPoint();
        hullPointsMat.fromArray(hullPoints);
        Rect boundingRect = Imgproc.boundingRect(hullPointsMat);
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

        Point[] fSPArr = filteredSP.toArray();
        Point[] fFPArr = filteredFP.toArray();

        MatOfPoint filteredFingerPoints = null;

        int filteredFPSize = filteredFP.toArray().length;
        int filteredSPSize = filteredSP.toArray().length;

        if (filteredFPSize > 1) {
            MatOfPoint fingerPoints = new MatOfPoint();

            for (int i = 0; i < filteredSPSize; ++i) {
                MatOfPoint closestPoints = FingerCount.findClosestOnX(filteredFP, fFPArr[i]);

                if (FingerCount.isFinger(closestPoints.toArray()[0],
                        fSPArr[i],
                        closestPoints.toArray()[1],
                        LIMIT_ANGLE_INF,
                        LIMIT_ANGLE_SUP,
                        centerOfRect,
                        boundingRect.height * BOUNDING_RECT_FINGER_SIZE_SCALING)) 
                    fingerPoints.push_back(new MatOfPoint(fSPArr[i]));
            }
            int fingerPointsSize = fingerPoints.toArray().length;
            if (fingerPointsSize > 0) {
                /*
                TODO donut know if opencv pops from the front or back of list maybe try both ways after implementation of gui
                also maybe optimize
                */
                while (fingerPointsSize > 5){
                        List<Point> fpl = fingerPoints.toList();
                        fpl.remove(fpl.size() - 1);
                        fingerPoints.fromList(fpl);
                }
                //filter based on proximity
                
                Point[] fingerPointsArr = fingerPoints.toArray();
                for (int i = 0; i < fingerPointsArr.length; ++i) {
                    if (FingerCount.findPointsDistanceOnX(fingerPointsArr[i], fingerPointsArr[i + 1]) > boundingRect.height *BOUNDING_RECT_NEIGHBOR_DISTANCE_SCALING * 1.5)
                        filteredFingerPoints.push_back(new MatOfPoint(fingerPointsArr[i]));
                }
                /*
                TODO wth is this if statement for seems to do it either way??? ROFL
                */
                if (fingerPointsArr.length > 2) {
                    if (FingerCount.findPointsDistanceOnX(fingerPointsArr[0], fingerPointsArr[fingerPointsArr.length - 1]) > boundingRect.height *BOUNDING_RECT_NEIGHBOR_DISTANCE_SCALING * 1.5)
                        filteredFingerPoints.push_back(new MatOfPoint(fingerPointsArr[fingerPointsArr.length - 1]));
                }else {
                    filteredFingerPoints.push_back(new MatOfPoint(fingerPointsArr[fingerPointsArr.length - 1]));
                }
                        
            }
            
        }
        
        Imgproc.drawContours(contoursImage, contours, biggestContourIdx, green, 2, 8, hierarchy);
        /*
        TODO how to proceed with hullPoints
        */
        List<MatOfPoint> listOfPoints = new ArrayList<>();
	Imgproc.polylines(contoursImage, hullList, true, blue);
	Imgproc.rectangle(contoursImage, boundingRect.tl(), boundingRect.br(), red, 2, 8, 0);
	Imgproc.circle(contoursImage, centerOfRect, 5, purple, 2, 8);
	FingerCount.drawVectorPoints(contoursImage, filteredSP, blue, true);
	FingerCount.drawVectorPoints(contoursImage, filteredFP, red, true);
	FingerCount.drawVectorPoints(contoursImage, filteredFingerPoints, yellow, false);
	Imgproc.putText(contoursImage, filteredFingerPoints.size().toString(), centerOfRect, Imgproc.FONT_HERSHEY_PLAIN, 3, purple);

	// and on the starting frame
	Imgproc.drawContours(frame, contours, biggestContourIdx, green, 2, 8, hierarchy);
	Imgproc.circle(frame, centerOfRect, 5, purple, 2, 8);
	FingerCount.drawVectorPoints(frame, filteredFingerPoints, yellow, false);
	Imgproc.putText(frame, filteredFingerPoints.size().toString(), centerOfRect, Imgproc.FONT_HERSHEY_PLAIN, 3, purple);
        
        return contoursImage;
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
        double deg = radAng * 180 / Math.PI;
        return deg;
    }

    private static boolean isFinger(Point a, Point b, Point c, double limitAngleInf, double limitAngleSup,
            Point palmCenter, double minDistanceFromPalm) {

        double angle = FingerCount.findAngle(a, b, c);
        if (angle > limitAngleSup || angle < limitAngleInf) {
            return false;
        }

        double deltaY1 = b.y - a.y;
        double deltaY2 = b.y - c.y;
        if (deltaY1 > 0 && deltaY2 > 0) {
            return false;
        }

        double deltaY3 = palmCenter.y - a.y;
        double deltaY4 = palmCenter.y - c.y;
        if (deltaY3 < 0 && deltaY4 < 0) {
            return false;
        }

        double distanceFromPalm = FingerCount.findPointsDistance(b, palmCenter);
        if (distanceFromPalm < minDistanceFromPalm) {
            return false;
        }

        double distanceFromPalmFar1 = FingerCount.findPointsDistance(a, palmCenter);
        double distanceFromPalmFar2 = FingerCount.findPointsDistance(a, palmCenter);
        if (distanceFromPalmFar1 < minDistanceFromPalm / 4 || distanceFromPalmFar2 < minDistanceFromPalm / 4) {
            return false;
        }
        return true;
    }

    private static MatOfPoint findClosestOnX(MatOfPoint points, Point pivot) {
        MatOfPoint ret = new MatOfPoint();
        /*
        TODO
        what MatOfPoint constructor takes an int 2 param???
        originally vector<Point>
         */
        int pointsSize = points.toArray().length;
        if (pointsSize == 0) {
            return ret;
        }

        double distanceX1 = Double.MAX_VALUE;
        double distance1 = Double.MAX_VALUE;
        double distanceX2 = Double.MAX_VALUE;
        double distance2 = Double.MAX_VALUE;
        int indexFound = 0;
        Point[] pointsArr = points.toArray();
        for (int i = 0; i < pointsSize; ++i) {
            double distanceX = findPointsDistanceOnX(pivot, pointsArr[i]);
            double distance = findPointsDistance(pivot, pointsArr[i]);

            if (distanceX < distanceX2 && distanceX != 0 && distance <= distance2 && distanceX != distanceX1) {
                distanceX2 = distanceX;
                distance2 = distance;
                indexFound = i;
            }
        }

        points.toList().add(1, pointsArr[indexFound]);
        return ret;
    }

    private static double findPointsDistanceOnX(Point a, Point b) {
        double ret = 0.0;

        if (a.x > b.x) {
            ret = a.x - b.x;
        } else {
            ret = b.x - a.x;
        }

        return ret;
    }

    private static void drawVectorPoints(Mat image, MatOfPoint points,
            Scalar colour, boolean wNums) {
        for (int i = 0; i < points.toArray().length; i++) {
            Imgproc.circle(image, points.toArray()[i], 5, colour, 2, 8);
            if (wNums) {
                Imgproc.putText(image, ("" + i), points.toArray()[i], Imgproc.FONT_HERSHEY_PLAIN, 3, colour);
            }
        }
    }
}
