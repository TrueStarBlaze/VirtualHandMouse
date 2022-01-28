/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.virtualhandmousemavenprototype;

import java.awt.AWTException;
import java.util.Iterator;
import java.util.Scanner;
import org.bytedeco.javacv.CanvasFrame;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;

/**
 *
 * @author erick
 */
public class VirtualHandMouseStub {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AWTException, FrameGrabber.Exception, InterruptedException {
        String startQ = "Test which part: Webcam (1), Robot Cursor (2), ModifiedLinkedList (3), or exit (end)";
        System.out.println(startQ);
        Scanner sc = new Scanner(System.in);
        String inputString = sc.nextLine();
        boolean initialized = false;
        int input = (int) Double.parseDouble(inputString);
        Frame f;
        CanvasFrame canvas = new CanvasFrame("Default Cam");
        RobotCursor rc = new RobotCursor();
        while (!inputString.toLowerCase().contains("end")) {
            switch (Integer.parseInt(inputString)) {
                case 1:
                    if (!initialized) {
                        VideoAccessor.init();
                        initialized = true;
                    }
                    System.out.println("Getting frame from default device camera...");
                    f = VideoAccessor.getFrame();
                    canvas.showImage(f);
                    System.out.println(startQ);
                    inputString = sc.nextLine();
                    break;
                case 2:
                    System.out.println("Enter coords to send mouse (x, y)");
                    int x = sc.nextInt();
                    int y = sc.nextInt();
                    System.out.println("Enter duration of mouse movement in milliseconds");
                    int t = sc.nextInt();
                    System.out.println("Enter amount of steps (higher amount of steps means smoother movement: try 5000");
                    int steps = sc.nextInt();
                    System.out.println("Moving mouse...");
                    int[] coords = {x, y};
                    rc.glide(coords, t, steps);
                    System.out.println("Enter type of click: left click (1), right click (2)");
                    int click = sc.nextInt();
                    rc.click(click);
                    System.out.println(startQ);
                    inputString = sc.nextLine();
                    break;
                case 3:
                    while (!inputString.contains("exit")) {
                        //TODO
                    }
                    break;
            }
            System.exit(0);
        }
//                CanvasFrame canvas = new CanvasFrame("Web Cam");
//                int frames = 0;
//                VideoAccessor.init();
//                long start = System.currentTimeMillis();
//        while(System.currentTimeMillis() - start < 10000) {
//        Frame f = VideoAccessor.getFrame();
//        System.out.println(++frames);
//        }
//        System.out.println("FPS: " + frames/ 10.0);
//        System.exit(0);

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
