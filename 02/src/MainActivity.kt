package com.example.openglstudy

import android.opengl.GLSurfaceView
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        val glView = GLSurfaceView(this)
        glView.setEGLContextClientVersion(3)
        glView.setRenderer(GLESRenderer());
        setContentView(glView)
    }

}