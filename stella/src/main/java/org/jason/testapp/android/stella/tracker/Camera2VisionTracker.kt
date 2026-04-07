package org.jason.testapp.android.stella.tracker

import org.opencv.android.CameraBridgeViewBase
import org.opencv.core.Mat
import org.opencv.core.Size
import org.opencv.imgproc.Imgproc

class Camera2VisionTracker constructor(val targetSize: Size, val drawPoints: Boolean) :
    VSLamTracker<CameraBridgeViewBase.CvCameraViewFrame, Mat>() {

    private val resizedMat = Mat()

    private lateinit var resizeProcessedFrame: Mat

    override fun feedFrame(frame: CameraBridgeViewBase.CvCameraViewFrame): Mat? {
        val grayMat = frame.gray()

        if (drawPoints) {
            if (!this::resizeProcessedFrame.isInitialized) {
                resizeProcessedFrame = Mat(grayMat.size(), grayMat.type())
            }
        }
        
        // Use INTER_NEAREST for faster interpolation compared to the default INTER_LINEAR
        Imgproc.resize(grayMat, resizedMat, targetSize, 0.0, 0.0, Imgproc.INTER_NEAREST)

        processFrame(resizedMat)

        if (!drawPoints) {
            return null
        }

        val processedMat = getProcessedFrame()
        Imgproc.resize(processedMat, resizeProcessedFrame, grayMat.size(), 0.0, 0.0, Imgproc.INTER_NEAREST)
        return resizeProcessedFrame
    }

    override fun destroy() {
        super.destroy()
        resizedMat.release()
    }
}