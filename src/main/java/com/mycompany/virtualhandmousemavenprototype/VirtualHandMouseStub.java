/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.virtualhandmousemavenprototype;

import java.awt.AWTException;
import java.util.Iterator;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.FrameGrabber;
/**
 *
 * @author erick
 */
public class VirtualHandMouseStub {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AWTException, FrameGrabber.Exception {
        long start = System.currentTimeMillis();
        while(System.currentTimeMillis() - start < 10000) {
        CanvasFrame canvas = new CanvasFrame("Web Cam");
        canvas.showImage(VideoAccessor.getFrame());
        }

//        RobotCursor rc = new RobotCursor();
//        int coords[] = {0, 0};
////        rc.glide(coords, 10000, 5000);
//        rc.click(1);
//        NLinkedList<int[]> nll = new NLinkedList<>();
//        int[] coords = {5, 8};
//        nll.add(coords);
////        coords[0] = 4;
////        coords[1] = 6;
////        nll.add(coords);
////        coords[0] = 3;
////        coords[1] = 4;
////        nll.add(coords);
//
//        int[] coords1 = {3, 7};
//        nll.add(coords1);
//        int[] coords2 = {4, 8};
//        nll.add(coords2);
//        //referencing issue must be done with seperate objects
//        int amt = 2;
//        int[][] coordsArr = new int[nll.size()][2];
////        coordsArr[0] = nll.getLast();
////        for (int i = nll.size() - amt; i < nll.size(); ++i) {
////            for (int j = 0; j < coordsArr[i].length; j+=2) {
////                System.out.println(coordsArr[i][j] + " " + coordsArr[i][j + 1]);
////            }
////        }
////        coordsArr[0] = nll.pop();
////        coordsArr[1] = nll.pop();
////        coordsArr[2] = nll.pop();
////        for (int i = 0; i < coordsArr.length; ++i) {
////            for (int j = 0; j < coordsArr[i].length; ++j) {
////                System.out.println(coordsArr[i][j]);
////            }
////        }
//
//        coordsArr = nll.lastAmt(3);
//                for (int i = 0; i < coordsArr.length; ++i) {
//            for (int j = 0; j < coordsArr[i].length; ++j) {
//                System.out.println(coordsArr[i][j]);
//            }
//        }
    }

}
