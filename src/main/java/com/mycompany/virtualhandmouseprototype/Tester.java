/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.virtualhandmouseprototype;

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
        VideoAccessor va = new VideoAccessor(0);
        va.init();
    }

}
