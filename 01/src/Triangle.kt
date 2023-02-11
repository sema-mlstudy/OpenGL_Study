package com.example.openglstudy

import android.opengl.GLES32
import android.util.Log
import java.nio.ByteBuffer
import java.nio.ByteOrder

class Triangle {
    var program= 0
    var vao = 0
    var ebo = 0


    fun initMesh(){
        val vertices = floatArrayOf(
            0.5f, 0.5f, 0.0f,
            0.5f, -0.5f, 0.0f,
            -0.5f, -0.5f, 0.0f,
            -0.5f, 0.5f, 0.0f
        )


        val indices = intArrayOf(
            0, 1 ,3,
            1, 2, 3
        )
        val buffer = ByteBuffer.allocateDirect(vertices.size * 4)
            .order(ByteOrder.nativeOrder()).asFloatBuffer()

        buffer.put(vertices)
        buffer.flip()

        val buffer2 = ByteBuffer.allocateDirect(indices.size * 4)
            .order(ByteOrder.nativeOrder()).asIntBuffer()

        buffer2.put(indices)
        buffer2.flip()


        val tmp = IntArray(1)

        GLES32.glGenVertexArrays(1, tmp, 0)
        vao = tmp[0]

        GLES32.glGenBuffers(1, tmp, 0)
        val vbo = tmp[0]

        GLES32.glGenBuffers(1, tmp, 0)
        ebo = tmp[0]


        GLES32.glBindVertexArray(vao)

        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, vbo)
        GLES32.glBufferData(GLES32.GL_ARRAY_BUFFER, vertices.size * 4, buffer, GLES32.GL_STATIC_DRAW)
        GLES32.glVertexAttribPointer(0, 3, GLES32.GL_FLOAT, false,0, 0)
        GLES32.glEnableVertexAttribArray(0)


        GLES32.glBindBuffer(GLES32.GL_ELEMENT_ARRAY_BUFFER, ebo)
        GLES32.glBufferData(GLES32.GL_ELEMENT_ARRAY_BUFFER, indices.size * 4, buffer2, GLES32.GL_STATIC_DRAW)

        GLES32.glBindBuffer(GLES32.GL_ARRAY_BUFFER, 0)
        GLES32.glBindBuffer(GLES32.GL_ELEMENT_ARRAY_BUFFER, 0)
        GLES32.glBindVertexArray(0)
    }

    fun init(){

        initProgram()
        initMesh()
    }

    fun initProgram(){
        program = GLES32.glCreateProgram()
        val vs = GLES32.glCreateShader(GLES32.GL_VERTEX_SHADER)
        val fs = GLES32.glCreateShader(GLES32.GL_FRAGMENT_SHADER)

        GLES32.glShaderSource(vs, """
            #version 320 es
           
           layout (location = 0 ) in vec3 position;
            
            void main(){
                gl_Position = vec4(position, 1.0);
             }
        """.trimIndent()

        )

        GLES32.glShaderSource(fs, """
            #version 320 es
            
            precision mediump float;
            
            out vec4 FragColor;
            
            void main(){
                FragColor = vec4(0.75, 0.7, 0.5, 1.0);
            }
        """.trimIndent())

        GLES32.glCompileShader(vs)
        Log.i("Vertex", GLES32.glGetShaderInfoLog(vs))


        GLES32.glCompileShader(fs)
        Log.i("Frag", GLES32.glGetShaderInfoLog(fs))


        GLES32.glAttachShader(program, vs)
        GLES32.glAttachShader(program, fs)
        GLES32.glLinkProgram(program)

        GLES32.glDeleteShader(vs)
        GLES32.glDeleteShader(fs)

        Log.i("GL:PROGRAM", GLES32.glGetProgramInfoLog(program))

    }

    fun renderer(){
        GLES32.glClearColor(0.0f, 0.0f, 0.3f, 0.3f)
        GLES32.glClear(GLES32.GL_COLOR_BUFFER_BIT)


        GLES32.glUseProgram(program)
        GLES32.glBindVertexArray(vao)
        GLES32.glBindBuffer(GLES32.GL_ELEMENT_ARRAY_BUFFER, ebo)

        GLES32.glDrawElements(GLES32.GL_TRIANGLES, 6, GLES32.GL_UNSIGNED_INT, 0)

        GLES32.glBindVertexArray(0)
    }
}
