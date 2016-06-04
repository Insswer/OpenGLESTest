package com.example.insswer.myapplication;

import android.content.Context;
import android.opengl.GLSurfaceView;
import android.opengl.GLU;
import android.util.Log;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

/**
 * Created by insswer on 2016/4/4.
 */
public class GLRenderer implements GLSurfaceView.Renderer {
    private Square mSquare = null;
    private Context mContext;

    public GLRenderer(Context mContext) {
        this.mContext = mContext;
        mSquare = new Square();
    }


    @Override
    public void onSurfaceCreated(GL10 gl10, EGLConfig eglConfig) {
        // Set the background color to black ( rgba ).
        gl10.glClearColor(0.0f, 0.0f, 0.0f, 0.5f);  // OpenGL docs.
        // Enable Smooth Shading, default not really needed.
        gl10.glShadeModel(GL10.GL_SMOOTH);// OpenGL docs.
        // Depth buffer setup.
        gl10.glClearDepthf(1.0f);// OpenGL docs.
        // Enables depth testing.
        gl10.glEnable(GL10.GL_DEPTH_TEST);// OpenGL docs.
        // The type of depth testing to do.
        gl10.glDepthFunc(GL10.GL_LEQUAL);// OpenGL docs.
        // Really nice perspective calculations.
        gl10.glHint(GL10.GL_PERSPECTIVE_CORRECTION_HINT, // OpenGL docs.
                GL10.GL_NICEST);

        mSquare.createTexture(gl10, mContext, R.drawable.test);
    }

    @Override
    public void onSurfaceChanged(GL10 gl, int width, int height) {
        // Sets the current view port to the new size.
        gl.glViewport(0, 0, width, height);// OpenGL docs.
        // Select the projection matrix
        gl.glMatrixMode(GL10.GL_PROJECTION);// OpenGL docs.
        // Reset the projection matrix
        gl.glLoadIdentity();// OpenGL docs.
        // Calculate the aspect ratio of the window
        GLU.gluPerspective(gl, 45.0f,
                (float) width / (float) height,
                0.1f, 100.0f);
        // Select the modelview matrix
        gl.glMatrixMode(GL10.GL_MODELVIEW);// OpenGL docs.
        // Reset the modelview matrix
        gl.glLoadIdentity();// OpenGL docs.
    }

    private float mTransY = 0.0f;

    @Override
    public void onDrawFrame(GL10 gl10) {
        gl10.glClearColor(0.7f, 0.7f, 0.7f, 1.0f);
        gl10.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);

        gl10.glMatrixMode(GL11.GL_MODELVIEW);
        gl10.glEnableClientState(GL11.GL_VERTEX_ARRAY);

        gl10.glLoadIdentity();
        gl10.glTranslatef(0, 2.0f * (float)Math.sin(mTransY), -10);
        gl10.glColor4f(0.0f, 0.0f, 1.0f, 1.0f);
        mSquare.draw(gl10);

        gl10.glLoadIdentity();
        gl10.glTranslatef((float)(Math.sin(mTransY)/2.0f), 0.0f, -8);
        gl10.glColor4f(1.0f, 0.0f, 0.0f, 1.0f);
        mSquare.draw(gl10);

        mTransY += 0.035f;
        //testTranslate(gl10);
    }
}
