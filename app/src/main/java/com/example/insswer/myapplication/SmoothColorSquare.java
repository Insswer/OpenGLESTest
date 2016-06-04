package com.example.insswer.myapplication;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by insswer on 2016/4/9.
 */
public class SmoothColorSquare extends Square {
    private float[] colors = {
            1f, 0f, 0f, 1f,
            0f, 1f, 0f, 1f,
            0f, 0f, 1f, 1f,
            1f, 0f, 1f, 1f,
    };
    private FloatBuffer colorBuffer;

    public SmoothColorSquare() {
        super();

        ByteBuffer cbb = ByteBuffer.allocateDirect(colors.length * 4);
        cbb.order(ByteOrder.nativeOrder());
        colorBuffer = cbb.asFloatBuffer();
        colorBuffer.put(colors);
        colorBuffer.position(0);
    }

    @Override
    public void draw(GL10 gl) {
        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);
        gl.glColorPointer(4, GL10.GL_FLOAT, 0, colorBuffer);

        super.draw(gl);
        gl.glDisableClientState(GL10.GL_COLOR_ARRAY);
    }
}
