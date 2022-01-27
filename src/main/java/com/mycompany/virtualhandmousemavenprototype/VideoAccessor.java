/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.virtualhandmousemavenprototype;

import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.FrameGrabber;
import org.bytedeco.javacv.OpenCVFrameConverter;
import org.bytedeco.javacv.OpenCVFrameGrabber;
import org.bytedeco.opencv.opencv_core.IplImage;
import org.bytedeco.javacv.CanvasFrame;
/**
 *
 * @author erick
 */
public class VideoAccessor {

    public static Frame getFrame() throws FrameGrabber.Exception {
        FrameGrabber fg = new OpenCVFrameGrabber(0);
        fg.start();
        return fg.grab();
    }

    public static IplImage toImage(Frame f) {
        OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
        IplImage image = (IplImage) converter.convert(f);
        return image;
    }
}
