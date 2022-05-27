/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package backend_models;

//import org.bytedeco.opencv.opencv_core.CvMemStorage;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;

/**
 *
 * @author 1100015542
 */
//import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.core.Size;
import org.opencv.objdetect.Objdetect;


public class FaceDetector {
    private static String faceClassifierFileName = "haarcascade_frontalface_alt.xml";
    private static CascadeClassifier faceCC;
    
    public FaceDetector () throws Exception {
        if (!faceCC.load(this.faceClassifierFileName)){
            throw new Exception("Could not load famnce cascade (FaceDetector.java)");
        }
    }
    
    
    public void removeFaces(Mat input, Mat output){
        MatOfRect faces = null;
        Mat frameGrey = null;
        
        Imgproc.cvtColor(input, frameGrey, Imgproc.COLOR_BGR2GRAY); //grey scaled
        Imgproc.equalizeHist(frameGrey, frameGrey); //improving distinction and contrast
                
            faceCC.detectMultiScale(frameGrey, faces, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE/*CV_HAAR_SCALE_IMAGE*/ , new Size(120, 120));
            //next step is to cover rect
            Rect[] rects = faces.toArray();
            for (int i = 0; i < faces.toArray().length; ++i) {
                Imgproc.rectangle(output, new Point(rects[i].x, rects[i].y), new Point(rects[i].x + rects[i].width, rects[i].y + rects[i].height), new Scalar(0, 0, 0), -1);
            }
    }
    
    public Rect getFaceRect(Mat input) {
        MatOfRect faces2 = null;
        Mat inputGrey = null;
        
        Imgproc.cvtColor(input, inputGrey, Imgproc.COLOR_BGR2GRAY);
        Imgproc.equalizeHist(inputGrey, inputGrey);
        
        faceCC.detectMultiScale(inputGrey, faces2, 1.1, 2, 0 | Objdetect.CASCADE_SCALE_IMAGE, new Size(120, 120));
        Rect[] facesRect = faces2.toArray();
        if (facesRect.length> 0)
		return facesRect[0];
	else
		return new Rect(0, 0, 1, 1);
    }
}
