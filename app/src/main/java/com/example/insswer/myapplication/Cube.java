package com.example.insswer.myapplication;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;
import javax.microedition.khronos.opengles.GL11;

/**
 * Created by insswer on 2016/5/5.
 */
public class Cube {
    private float vertices[] = {
        -1.0f, 1.0f, 1.0f,
        1.0f, 1.0f, 1.0f,
        1.0f, -1.0f, 1.0f,
        -1.0f, -1.0f, 1.0f,
        -1.0f, 1.0f, -1.0f,
        1.0f, 1.0f, -1.0f,
        1.0f, -1.0f, -1.0f,
        -1.0f, -1.0f, -1.0f
    };


    private byte maxColor = (byte)255;
    private byte colors[] = {
        maxColor, maxColor, 0, maxColor,
        0, maxColor, maxColor, maxColor,
        0, 0, 0, maxColor,
        maxColor, 0, maxColor, maxColor,
        maxColor, 0, 0, maxColor,
        0, maxColor, 0, maxColor,
        0, 0, maxColor, maxColor,
        0, 0, 0, maxColor
    };

    private byte tfan1[] = {
            1, 0, 3,
            1, 3, 2,
            1, 2, 6,
            1, 6, 5,
            1, 5, 4,
            1, 4, 0
    };

    private byte tfan2[] = {
            7, 4, 5,
            7, 5, 6,
            7, 6, 2,
            7, 2, 3,
            7, 3, 0,
            7, 0, 4
    };

    private FloatBuffer mFVertexBuffer = null;
    private ByteBuffer mBColorBuffer = null;
    private ByteBuffer mTfan1 = null;
    private ByteBuffer mTfan2 = null;

    public Cube() {
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        mFVertexBuffer = vbb.asFloatBuffer();
        mFVertexBuffer.put(vertices);

        mFVertexBuffer.position(0);
        mBColorBuffer = ByteBuffer.allocateDirect(colors.length);
        mBColorBuffer.put(colors);
        mBColorBuffer.position(0);

        mTfan1 = ByteBuffer.allocateDirect(tfan1.length);
        mTfan1.put(tfan1);
        mTfan1.position(0);
        mTfan2 = ByteBuffer.allocateDirect(tfan2.length);
        mTfan2.put(tfan2);
        mTfan2.position(0);
    }

    public void draw(GL10 gl) {
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mFVertexBuffer);
        gl.glColorPointer(4, GL10.GL_UNSIGNED_BYTE, 0, mBColorBuffer);

        gl.glDrawElements(gl.GL_LINE_LOOP, 6 * 3, GL10.GL_UNSIGNED_BYTE, mTfan1);
        gl.glDrawElements(gl.GL_LINE_LOOP, 6 * 3, GL10.GL_UNSIGNED_BYTE, mTfan2);
    }


}
