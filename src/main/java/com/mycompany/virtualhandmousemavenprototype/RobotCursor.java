/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.virtualhandmousemavenprototype;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.InputEvent;


/**
 *
 * @author erick
 */
public class RobotCursor extends Robot {

    private Robot r = new Robot();

    public RobotCursor() throws AWTException {

    }

    /*
    potentially automate determination of how long and steps by using a constant factor 
     */
    public void glide(int[] coords, int milliTime, int steps) {
        /*
        due to rounding we may not always get to the exact location but it should be fine considering hand 
        movement translation is already inaccurate
        after making that more precise focus here
         */
        Point p = MouseInfo.getPointerInfo().getLocation();
        int x1 = p.x;
        int y1 = p.y;
        int x2 = coords[0];
        int y2 = coords[1];
        try {
            double deltaX = (x2 - x1) / (double) steps;
            double deltaY = (y2 - y1) / (double) steps;
            double deltaT = (double) milliTime / (double) steps;
            for (int i = 0; i < steps; ++i) {
                Thread.sleep((int) deltaT);
                this.r.mouseMove((int) (x1 + deltaX * i), (int) (y1 + deltaY * i));
            }
        } catch (InterruptedException ie) {
            ie.printStackTrace();
        }

    }

    /*
InputEvent.BUTTON1_DOWN_MASK :  For mouse left -click

InputEvent.BUTTON2_DOWN_MASK  : For mouse middle button click.

InputEvent.BUTTON3_DOWN_MASK : For mouse right-click

InputEvent.BUTTON1_MASK

InputEvent.BUTTON2_MASK

InputEvent.BUTTON3_MASK


     */
    public void click(int i) {
        if (i == 1) {
            this.r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            this.r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
        } else if (i == 2) {
            this.r.mousePress(InputEvent.BUTTON3_DOWN_MASK);
            this.r.mouseRelease(InputEvent.BUTTON3_DOWN_MASK);
        } else {
            this.r.mousePress(InputEvent.BUTTON2_DOWN_MASK);
            this.r.mouseRelease(InputEvent.BUTTON2_DOWN_MASK);

        }
    }
}
