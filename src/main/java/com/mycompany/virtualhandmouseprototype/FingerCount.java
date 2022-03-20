/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.virtualhandmouseprototype;

import org.bytedeco.opencv.opencv_core.Scalar;

/**
 *
 * @author erick
 */
public class FingerCount {
    //                                      B   G   R   A
    private final Scalar blue =  new Scalar(255, 0, 0, 0);
    private final Scalar green =  new Scalar(0, 255, 0, 0);
    private final Scalar red = new Scalar(0, 0, 255, 0);
    private final Scalar black = new Scalar(0, 0, 0, 0);
    private final Scalar white = new Scalar(255, 255, 255, 0);
    private final Scalar yellow = new Scalar(0, 255, 255, 0);
    private final Scalar purple = new Scalar(255, 0, 255, 0); 
    /*
	color_red = Scalar(0, 0, 255);
	color_black = Scalar(0, 0, 0);
	color_white = Scalar(255, 255, 255);
	color_yellow = Scalar(0, 255, 255);
	color_purple = Scalar(255, 0, 255);
    */
}
