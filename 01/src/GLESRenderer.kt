package com.example.openglstudy
import android.opengl.GLES32
import com.example.openglstudy.Triangle
import com.example.openglstudy.MainActivity
import android.opengl.GLSurfaceView
import javax.microedition.khronos.egl.EGLConfig
import javax.microedition.khronos.opengles.GL10

class GLESRenderer : GLSurfaceView.Renderer{
    val triangle = Triangle()

    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?) {
        triangle.init()
    }

    override fun onDrawFrame(p0: GL10?) {
        triangle.renderer()
    }

    override fun onSurfaceChanged(p0: GL10?, p1: Int, p2: Int) {
        GLES32.glViewport(0,0, p1, p2)
    }
}

