/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend_models;

//import org.opencv.Frame;
import org.opencv.videoio.VideoCapture;

//import org.bytedeco.javacv.FrameGrabber;
//import org.bytedeco.javacv.OpenCVFrameConverter;
//import org.bytedeco.javacv.OpenCVFrameGrabber;
//import org.bytedeco.opencv.opencv_core.IplImage;
//import org.bytedeco.javacv.CanvasFrame;
//import org.bytedeco.javacv.VideoInputFrameGrabber;
//import org.bytedeco.opencv.opencv_videoio.VideoCapture;
/**
 *
 * @author erick
 */
public class VideoAccessor {

    private int cam = 0;
    private VideoCapture vc;
    private boolean init = false;

//    public VideoAccessor() {
//       vc = 
//    }
//    
//    public VideoAccessor(int cam) {
//        vc = new VideoCapture(cam);
//        if (!vc.isOpened()) {
//            cam
//    }
//    private  int cam = 0;
//    private  FrameGrabber fg;
//    private VideoCapture vidCap;
//    private boolean initialized = false;
//    private OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage();
//    
//    public VideoAccessor (int camType) {
//        this.cam = camType;
//    }
//
//    public  void init() throws FrameGrabber.Exception {
//                this.vidCap = new VideoCapture(this.cam); 
//                if (!this.vidCap.isOpened())System.out.println("No Webcam");
//                this.fg = new VideoInputFrameGrabber(this.cam);
//                this.fg.start();
//                this.initialized = true;
//    }
//
//    public Frame getFrame() throws FrameGrabber.Exception, InterruptedException {
//                Frame f = this.fg.grabFrame();
//                return f;
//    }
//
//    public IplImage toImage(Frame f) {
//        IplImage image = this.converter.convert(f);
//        return image;
//    }
//    
//    public boolean getInitStatus() {
//        return this.initialized;
//    }
//    
//    public void setInitStatus(boolean b) {
//        this.initialized = b;
}
