package com.example.insswer.myapplication;

import android.opengl.GLSurfaceView;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

/**
 * Created by insswer on 2016/4/29.
 */
public class GLRender2 implements GLSurfaceView.Renderer {
    private Square mSquare;
    private Square mSquare2;
    private Cube mCube;
    private Planet mPlanet;
    private boolean mTranslucentBackground = false;
    private float mTransY;
    private float mAngle;

    public final static int SS_SUNLIGHT= GL10.GL_LIGHT0;
    public final static int SS_SUNLIGHT1 = GL10.GL_LIGHT1;
    public final static int SS_SUNLIGHT2 = GL10.GL_LIGHT2;


    public GLRender2(boolean useTranslucentBackground) {
        this.mTranslucentBackground = useTranslucentBackground;
        mSquare = new SmoothColorSquare();
        mSquare2 = new SmoothColorSquare();
        mCube = new Cube();
        mPlanet = new Planet(100, 100, 1.0f, 1.0f);
    }

    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        gl10.glDisable(GL10.GL_DITHER);
        gl10.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, GL10.GL_FASTEST);

        gl10.glEnable(GL10.GL_CULL_FACE);
        gl10.glCullFace(GL10.GL_BACK);
        gl10.glShadeModel(GL10.GL_SMOOTH);
        gl10.glEnable(GL10.GL_DEPTH_TEST);
        gl10.glDepthMask(false);

//        if (mTranslucentBackground) {
//            gl10.glClearColor(0,0,0,0);
//        } else {
//            gl10.glClearColor(1,1,1,1);
//        }
        initLighting(gl10);
    }

    protected static FloatBuffer makeFloatBuffer(float[] arr)
    {
        ByteBuffer bb = ByteBuffer.allocateDirect(arr.length*4);
        bb.order(ByteOrder.nativeOrder());
        FloatBuffer fb = bb.asFloatBuffer();
        fb.put(arr);
        fb.position(0);
        return fb;
    }

    private void initLighting(GL10 gl) {
        //RGBA
        float[] posMain = {5.0f, 4.0f, -20.0f, 1.0f};
        float[] posFill1 = {-8.0f, 6.0f, -15.0f, 1.0f};
        float[] posFill2 = {-10.0f, -7.0f, -21.0f, 1.0f};


        float[] white = {1.0f, 1.0f, 1.0f, 1.0f};
        float[] red = {1.0f, 0.0f, 0.0f, 1.0f};
        float[] dimred = {0.5f, 0.0f, 0.0f, 1.0f};

        float[] green={0.0f, 1.0f, 0.0f, 0.0f};
        float[] dimgreen = {0.0f, .5f, 0.0f, 0.0f};
        float[] blue={0.0f, 0.0f, 1.0f, 1.0f};
        float[] dimblue={0.0f, 0.0f, 0.2f, 1.0f};

        float[] cyan={0.0f, 1.0f, 1.0f, 1.0f};
        float[] yellow={1.0f, 1.0f, 0.0f, 1.0f};
        float[] megenta= {1.0f, 0.0f, 1.0f, 1.0f};
        float[] dimmagenta = {0.75f, 0.0f, 0.25f, 1.0f};

        float[] dimcyan = {0.0f, 0.5f, 0.5f, 1.0f};

        //x y z
        gl.glLightfv(SS_SUNLIGHT, GL10.GL_POSITION, makeFloatBuffer(posMain));
        gl.glLightfv(SS_SUNLIGHT, GL10.GL_DIFFUSE, makeFloatBuffer(white));
        gl.glLightfv(SS_SUNLIGHT, GL10.GL_SPECULAR, makeFloatBuffer(yellow));

        gl.glLightfv(SS_SUNLIGHT1, GL10.GL_POSITION, makeFloatBuffer(posFill1));
        gl.glLightfv(SS_SUNLIGHT1, GL10.GL_DIFFUSE, makeFloatBuffer(dimblue));
        gl.glLightfv(SS_SUNLIGHT1, GL10.GL_SPECULAR, makeFloatBuffer(dimcyan));

        gl.glLightfv(SS_SUNLIGHT2, GL10.GL_POSITION, makeFloatBuffer(posFill2));
        gl.glLightfv(SS_SUNLIGHT2, GL10.GL_DIFFUSE, makeFloatBuffer(dimmagenta));
        gl.glLightfv(SS_SUNLIGHT2, GL10.GL_SPECULAR, makeFloatBuffer(dimblue));

        gl.glLightf(SS_SUNLIGHT, GL10.GL_QUADRATIC_ATTENUATION, .005f);

        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_DIFFUSE, makeFloatBuffer(cyan));
        gl.glMaterialfv(GL10.GL_FRONT_AND_BACK, GL10.GL_SPECULAR, makeFloatBuffer(white));
        gl.glMaterialf(GL10.GL_FRONT_AND_BACK, GL10.GL_SHININESS, 25.0f);

        gl.glShadeModel(GL10.GL_SMOOTH);
        gl.glLightModelf(GL10.GL_LIGHT_MODEL_TWO_SIDE, 1.0f);
        gl.glEnable(GL10.GL_LIGHTING);
        gl.glEnable(SS_SUNLIGHT);
        gl.glEnable(SS_SUNLIGHT1);
        gl.glEnable(SS_SUNLIGHT2);

        gl.glLoadIdentity();
    }

    @Override
    public void onSurfaceChanged(GL10 gl10, int i, int i1) {
        //change viewport
        gl10.glViewport(0, 0, i, i1);

        float aspectRatio;
        float zNear = 0.1f;
        float zFar = 1000;
        float fieldOfView = 50.0f/57.3f;
        //float fieldOfView = 30.0f/80.0f;
        float size;

        gl10.glEnable(GL10.GL_NORMALIZE);
        aspectRatio = (float)i/(float)i1;
        gl10.glMatrixMode(GL10.GL_PROJECTION);
        size = zNear * (float) (Math.tan((double)(fieldOfView / 2.0f)));
        gl10.glFrustumf(-size, size, -size /aspectRatio,
                size /aspectRatio, zNear, zFar);
        gl10.glMatrixMode(GL10.GL_MODELVIEW);
    }

    @Override
    public void onDrawFrame(GL10 gl10) {
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
        gl10.glClearColor(0.2f, 0.2f, 0.5f, 1.0f);
        gl10.glMatrixMode(GL10.GL_MODELVIEW);
        gl10.glLoadIdentity();

        gl10.glTranslatef(0, 0, -15.0f);
        //gl10.glTranslatef((float) Math.cos(mTransY), (float) Math.sin(mTransY), -7.0f);
        gl10.glRotatef(mAngle, 0.0f, 1.0f, 0.0f);
        gl10.glRotatef(mAngle, 1.0f, 0.0f, 0.0f);

        gl10.glEnableClientState(GL10.GL_VERTEX_ARRAY);
        gl10.glEnableClientState(GL10.GL_COLOR_ARRAY);

        mPlanet.draw(gl10);

        mTransY += .075f;
        mAngle += .4f;
    }
}
