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
public class Tester {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws AWTException, FrameGrabber.Exception, InterruptedException {
                            RobotCursor rc = new RobotCursor();
//                            int[] test = {0, 0};
//                            rc.glide(test, 10000, 5000);
        String startQ = "Test which part: Webcam (1), Robot Cursor (2), ModifiedLinkedList (3), or exit (exit)";
        System.out.println(startQ);
        Scanner sc = new Scanner(System.in);
        String inputString = sc.nextLine();
        boolean initialized = false;
        int input = (int) Double.parseDouble(inputString);
        Frame f;
        NLinkedList<int[]> nll = new NLinkedList();;
        while (!inputString.toLowerCase().contains("exit")) {
            switch (Integer.parseInt(inputString)) {
                case 1:
                    System.out.println("Getting frame from default device camera...");
                    CanvasFrame canvas = new CanvasFrame("Default Cam");
                    if (!initialized) {
                        VideoAccessor.init();
                        initialized = true;
                    }
                    f = VideoAccessor.getFrame();
                    canvas.showImage(f);
                    System.out.println(startQ);
                    inputString = sc.nextLine();
                    canvas.dispose();
                    break;
                case 2:
                    System.out.println("Enter x coord to send mouse");
                    inputString = sc.nextLine();
                    int x = Integer.parseInt(inputString);
                    System.out.println("Enter y coord to send mouse");
                    inputString = sc.nextLine();
                    int y = Integer.parseInt(inputString);
                    System.out.println("Enter duration of mouse movement in milliseconds");
                    inputString = sc.nextLine();
                    int t = 0;
                    t = Integer.parseInt(inputString);
                    System.out.println("Enter amount of steps (higher amount of steps means smoother movement: try 5000)");
                    inputString = sc.nextLine();
                    int steps = Integer.parseInt(inputString);
                    System.out.println("Moving mouse...");
                    int[] coords = {x, y};
                    rc.glide(coords, t, steps);

//                    rc.glide(coords, 10000, 5000);
                    System.out.println("Enter type of click: left click (1), right click (2)");
                    inputString = sc.nextLine();
                    int click = Integer.parseInt(inputString);
                    rc.click(click);
                    System.out.println(startQ);
                    inputString = sc.nextLine();
                    break;

                case 3:
                    while (!inputString.contains("end")) {
                        //TODO
                        System.out.println("Push coords (push), pop coords (pop), or end (end)");
                        inputString = sc.nextLine();
                        switch (inputString.toLowerCase()) {
                            case "push":
                                System.out.println("Enter x coord to push");
                                inputString = sc.nextLine();
                                int xLL = Integer.parseInt(inputString);
                                System.out.println("Enter y coord to push");
                                inputString = sc.nextLine();
                                int yLL = Integer.parseInt(inputString);
                                int[] coordsNll = {xLL, yLL};
                                nll.push(coordsNll);
                                System.out.println("Coords pushed");
                                break;
                            case "pop":
                                System.out.println("Popping coords...");
                                if (nll.isEmpty()) {
                                    System.out.println("Empty...");
                                } else {
                                    int[] popped = nll.pop();
                                    System.out.println("Popped Coords: (" + popped[0] + ", " + popped[1] + ")");
                                }
                                break;
                        }
                    }
                    nll.clear();
                    System.out.println(startQ);
                    inputString = sc.nextLine();
                    break;
            }
        }
        System.exit(0);
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
