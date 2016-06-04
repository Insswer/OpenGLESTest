package com.example.insswer.myapplication;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import javax.microedition.khronos.opengles.GL10;

/**
 * Created by insswer on 2016/5/5.
 */
public class Planet {
    FloatBuffer mVertexData;
    FloatBuffer mNormalData;
    FloatBuffer mColorData;

    float mScale;
    float mSquash;
    float mRadius;
    int mStacks;
    int mSlices;

    public Planet (int stacks, int slices, float radius, float squash) {
        this.mStacks = stacks;
        this.mSlices = slices;
        this.mRadius = radius;
        this.mSquash = squash;

        init(mStacks, mSlices, mRadius, mSquash, "dummy");
    }

    private void init(int stacks, int slices, float radius, float squash, String textureFile) {
        float[] vertexData;
        float[] colorData;
        float[] normalData;
        float colorIncrement = 0f;

        float blue = 0f;
        float red = 1.0f;
        int numVertices = 0;
        int vIndex = 0;
        int cIndex = 0;
        int nIndex = 0;

        mScale = radius;
        mSquash = squash;

        colorIncrement = 1.0f/(float)stacks;

        {
            mStacks = stacks;
            mSlices = slices;
            vertexData = new float[3 * ((mSlices * 2 + 2) * mStacks)];
            colorData = new float[(4 * (mSlices * 2 + 2) * mStacks)];
            normalData = new float[(3 * (mSlices * 2 + 2) * mStacks)];
            int phiIdx, thetaIdx;

            //latitude
            for (phiIdx = 0; phiIdx < mStacks; phiIdx++) {
                float phi0 = (float)Math.PI * ((float) (phiIdx + 0) * (1.0f/(float)(mStacks)) - 0.5f);
                float phi1 = (float)Math.PI * ((float) (phiIdx + 1) * (1.0f/(float)(mStacks)) - 0.5f);

                float cosPhi0 = (float)Math.cos(phi0);
                float sinPhi0 = (float)Math.sin(phi0);
                float cosPhi1 = (float)Math.cos(phi1);
                float sinPhi1 = (float)Math.sin(phi1);

                float cosTheta, sinTheta;

                //longitude
                for (thetaIdx = 0; thetaIdx < mSlices; thetaIdx++) {
                    float theta = (float) (-2.0f * (float)Math.PI * ((float)thetaIdx) * (1.0/(float)(mSlices - 1)));
                    cosTheta = (float) Math.cos(theta);
                    sinTheta = (float) Math.sin(theta);

                    vertexData[vIndex + 0] = mScale * cosPhi0 * cosTheta;
                    vertexData[vIndex + 1] = mScale * (sinPhi0 * mSquash);
                    vertexData[vIndex + 2] = mScale * (cosPhi0 * sinTheta);

                    vertexData[vIndex + 3] = mScale * cosPhi1 * cosTheta;
                    vertexData[vIndex + 4] = mScale * (sinPhi1 * mSquash);
                    vertexData[vIndex + 5] = mScale * (cosPhi1 * sinTheta);

                    colorData[cIndex + 0] = (float)red;
                    colorData[cIndex + 1] = (float)0f;
                    colorData[cIndex + 2] = (float)blue;
                    colorData[cIndex + 4] = (float)red;
                    colorData[cIndex + 5] = (float)0f;
                    colorData[cIndex + 6] = (float)blue;
                    colorData[cIndex + 3] = (float)1.0f;
                    colorData[cIndex + 7] = (float)1.0f;

                    normalData[nIndex + 0] = cosPhi0 * cosTheta;
                    normalData[nIndex + 1] = sinPhi0;
                    normalData[nIndex + 2] = cosPhi0 * sinTheta;

                    normalData[nIndex + 3] = cosPhi1 * cosTheta;
                    normalData[nIndex + 4] = sinPhi1;
                    normalData[nIndex + 5] = cosPhi1 * sinTheta;

                    cIndex += 2 * 4;
                    vIndex += 2 * 3;
                    nIndex += 2 * 3;
                }

                //blue += colorIncrement;
                red -= colorIncrement;

                vertexData[vIndex + 0] = vertexData[vIndex + 3] = vertexData[vIndex - 3];
                vertexData[vIndex + 1] = vertexData[vIndex + 4] = vertexData[vIndex - 2];
                vertexData[vIndex + 2] = vertexData[vIndex + 5] = vertexData[vIndex - 1];
            }
        }

        mVertexData = makeFloatBuffer(vertexData);
        mColorData = makeFloatBuffer(colorData);
        mNormalData = makeFloatBuffer(normalData);
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


    public void draw (GL10 gl) {
        gl.glMatrixMode(GL10.GL_MODELVIEW);
        gl.glEnable(GL10.GL_CULL_FACE);
        gl.glCullFace(GL10.GL_BACK);

        gl.glNormalPointer(GL10.GL_FLOAT, 0, mNormalData);
        gl.glEnableClientState(GL10.GL_NORMAL_ARRAY);

        gl.glVertexPointer(3, GL10.GL_FLOAT, 0, mVertexData);
        gl.glEnableClientState(GL10.GL_VERTEX_ARRAY);

        gl.glColorPointer(4, GL10.GL_FLOAT, 0, mColorData);
        gl.glEnableClientState(GL10.GL_COLOR_ARRAY);

        gl.glDrawArrays(GL10.GL_TRIANGLE_STRIP, 0, (mSlices + 1) * 2 * (mStacks - 1) + 2);
    }


}
