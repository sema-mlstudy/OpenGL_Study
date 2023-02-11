# 01 삼각형 그리기

## GL_SURFACE_VIEW

Andorid 에서 OpenglES 는 GLSurfaceView.Renderer 를 통하여 사용할 수 있다.

GLSurfaceView.Renderer는 인터페이스로, 
기본적으로 onSurfaceCreated, onDrawFrame, onSurfaceChanged 가 있어야 한다.

onSurfaceCreated 는 생성되었을 때 한번만 실행된다
onDrawFrame 은 매 프라임마다 실행된다. 대략 1초에 60번 정도 실행이 된다고 보면 된다.
onSurfaceChanged는 크기가 바뀌거나 화면이 돌아가면 실행된다.

먼저 GLESRenderer.kt 를 생성해서 아래와 같이 입력하자

```kotlin
class GLESRenderer : GLSurfaceView.Renderer{
    override fun onSurfaceCreated(p0: GL10?, p1: EGLConfig?){

    }

    override fun onDrawFrame(p0: GL10?) {

    }


    override fun onSurfaceChanged(p0: GL10?, p1: Int, p2: Int) {
        
    }
}

```

## Graphics Pipeline 

이제 Triangle.kt 를 만들고, android.opengl.GLES32를 import 해주자

```kotlin

import android.opengl.GLES32 

class Triangle{

}
```


Opengl ES 로 삼각형을 그리기 위해서는 그래픽스 파이프라인에 대한 대략적인 이해가 필요하다
![그래픽스 파이프라인](https://learnopengl.com/img/getting-started/pipeline.png)

여기에서 Vertex Shader 와 Fragment Shader 를 삽입하여야 한다

그래픽스 파이프라인에 대한 자세한 설명은 https://velog.io/@jodmsoluth/OpenGL-Graphics-pipeline 을 참고하길 바란다.

먼저 initProgram 이라는 함수를 만들고 
Vertex Shader를 삽입해보자

```kotlin
fun initProgram(){

        val vs = GLES32.glCreateShader(GLES32.GL_VERTEX_SHADER) #Fragment 쉐이더 생성

        GLES32.glShaderSource(vs, """
            #version 320 es
           
           layout (location = 0 ) in vec3 position;
            
            void main(){
                gl_Position = vec4(position, 1.0);
             }
        """.trimIndent()  #vertex shader 코드 삽입

        
        GLES32.glCompileShader(vs) #vertex shader 컴파일
        Log.i("Vertex", GLES32.glGetShaderInfoLog(vs)) #디버깅을 위해 필요 (선택)


        )

}

```

```
#version 320 es
           
layout (location = 0 ) in vec3 position;
            
void main(){
    gl_Position = vec4(position, 1.0);
}

```

vertex shader 의 코드는 C언어와 굉장히 유사한 GLSL 이라는 것을 통하여 작성한다.

#version 320 es 

우리가 opengl es 3.2 를 사용한다고 명시를 해주는 것이다

layout(location = 0) in vec3 position;

잠시 후에 vertexAttribPointer 에 대해 다룰 때 설명하겠다.


void main(){
    gl_Position = vec4(position, 1.0);
}

gl_Position = vec4(position, 1.0);


