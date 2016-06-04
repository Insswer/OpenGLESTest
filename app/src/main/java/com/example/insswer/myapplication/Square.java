package com.example.insswer.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.opengl.GLUtils;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by insswer on 2016/4/4.
 */
public class Square {
    private float vertices[] = {
            -1.0f, -1.0f,
            1.0f, -1.0f,
            -1.0f, 1.0f,
            1.0f, 1.0f,
    };
    private short[] indices = {0, 3, 1, 0, 2, 3};
    private int[] textures = new int[4];

    protected  FloatBuffer mTextureBuffer;
    protected FloatBuffer vertexBuffer;
    protected ShortBuffer indexBuffer;

    private float[] textureCoords = {
            0.0f, 0.0f,
            1.0f, 0.0f,
            0.0f, 1.0f,
            1.0f, 1.0f,
    };

    public Square() {
        ByteBuffer vbb = ByteBuffer.allocateDirect(vertices.length * 4);
        vbb.order(ByteOrder.nativeOrder());
        vertexBuffer = vbb.asFloatBuffer();
        vertexBuffer.put(vertices);
        vertexBuffer.position(0);

        ByteBuffer sbb = ByteBuffer.allocateDirect(vertices.length * 2);
        sbb.order(ByteOrder.nativeOrder());
        indexBuffer = sbb.asShortBuffer();
        indexBuffer.put(indices);
        indexBuffer.position(0);

        ByteBuffer tbb = ByteBuffer.allocateDirect(textureCoords.length * 4);
        tbb.order(ByteOrder.nativeOrder());
        mTextureBuffer = tbb.asFloatBuffer();
        mTextureBuffer.put(textureCoords);
        mTextureBuffer.position(0);
    }

    public int createTexture(GL10 gl, Context mContext, int res) {
        Bitmap image = BitmapFactory.decodeResource(mContext.getResources(), res);
        //generate a unique number for texture
        gl.glGenTextures(1, textures, 0);
        gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);
        //bind image
        GLUtils.texImage2D(GL10.GL_TEXTURE_2D, 0, image, 0);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MIN_FILTER, GL10.GL_LINEAR);
        gl.glTexParameterf(GL10.GL_TEXTURE_2D, GL10.GL_TEXTURE_MAG_FILTER, GL10.GL_LINEAR);

        image.recycle();
        return res;
    }

    public void draw(GL10 gl) {
        gl.glVertexPointer(2, GL10.GL_FLOAT, 0, vertexBuffer);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

       // gl.glEnable(GL10.GL_TEXTURE_2D);
       // gl.glEnable(GL10.GL_BLEND);

       // gl.glBlendFunc(GL10.GL_ONE, GL10.GL_SRC_COLOR);
       // gl.glBindTexture(GL10.GL_TEXTURE_2D, textures[0]);

       // gl.glTexCoordPointer(2, GL10.GL_FLOAT, 0, mTextureBuffer);
       // gl.glEnableClientState(GL10.GL_TEXTURE_COORD_ARRAY);

        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, 4);

        gl.glDisableClientState(GL10.GL_VERTEX_ARRAY);
       // gl.glDisableClientState(GL10.GL_TEXTURE_COORD_ARRAY);
    }

}
