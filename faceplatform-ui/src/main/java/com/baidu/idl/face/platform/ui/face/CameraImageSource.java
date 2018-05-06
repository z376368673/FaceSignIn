/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.baidu.idl.face.platform.ui.face;

import android.content.Context;

import com.baidu.idl.face.platform.ui.face.camera.Camera1Control;
import com.baidu.idl.face.platform.ui.face.camera.ICameraControl;
import com.baidu.idl.facesdk.FaceSDK;

import java.util.ArrayList;

/**
 * 封装了系统做机做为输入源。
 */
public class CameraImageSource extends ImageSource {

    //    private int[] argb = null;

    private ICameraControl cameraControl;
    private Context context;

    public ICameraControl getCameraControl() {
        return cameraControl;
    }

    private ArgbPool argbPool = new ArgbPool();

    public CameraImageSource(Context context) {
        this.context = context;
        cameraControl = new Camera1Control(getContext());
        cameraControl.setCameraFacing(ICameraControl.CAMERA_FACING_FRONT);
        cameraControl.setOnFrameListener(new ICameraControl.OnFrameListener<byte[]>() {
            @Override
            public void onPreviewFrame(byte[] data, int rotation, int width, int height) {
                int[] argb = argbPool.acquire(width, height);

                if (argb == null || argb.length != width * height) {
                    argb = new int[width * height];
                }
                rotation = rotation < 0 ? 360 + rotation : rotation;
                FaceSDK.getARGBFromYUVimg(data, argb,
                        width, height,
                        rotation, 0);

                // 旋转了90或270度。高宽需要替换
                if (rotation % 180 == 90) {
                    int temp = width;
                    width = height;
                    height = temp;
                }
                ImageFrame frame = new ImageFrame();
                frame.setArgb(argb);
                frame.setWidth(width);
                frame.setHeight(height);
                frame.setPool(argbPool);
                ArrayList<OnFrameAvailableListener> listeners = getListeners();
                for (OnFrameAvailableListener listener : listeners) {
                    listener.onFrameAvailable(frame);
                }
            }
        });
    }

    @Override
    public void start() {
        super.start();
        cameraControl.start();
    }

    @Override
    public void stop() {
        super.stop();
        cameraControl.stop();
    }

    private Context getContext() {
        return context;
    }

    @Override
    public void setPreviewView(PreviewView previewView) {
        cameraControl.setPreviewView(previewView);
    }
}
