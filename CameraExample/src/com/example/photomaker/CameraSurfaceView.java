package com.example.photomaker;

import java.io.IOException;

import android.content.Context;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

class CameraSurfaceView extends SurfaceView implements SurfaceHolder.Callback {

	public Camera camera;

	CameraSurfaceView(Context context) {
		super(context);

		SurfaceHolder holder = this.getHolder();
		holder.addCallback(this);
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

		camera.startPreview();
	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		try {
			// Open the Camera in preview mode
			this.camera = Camera.open();

			Camera.Parameters p = camera.getParameters();
			p.setZoom(0);
			p.setFlashMode(Parameters.FLASH_MODE_AUTO);
			camera.setParameters(p);

			this.camera.setPreviewDisplay(holder);
		} catch (IOException ioe) {
			ioe.printStackTrace(System.out);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		// Surface will be destroyed when replaced with a new screen
		// Always make sure to release the Camera instance
		camera.stopPreview();
		camera.release();
		camera = null;
	}

	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
	}

	public void takePicture(PictureCallback imageCallback) {
		camera.takePicture(null, null, imageCallback);
	}

	public int changeZoom(int zoom) {
		Camera.Parameters p = camera.getParameters();
		int maxZoom = p.getMaxZoom();

		if (p.isZoomSupported()) {
			if (zoom > maxZoom) {
				zoom -= 10;
			}
			p.setZoom(zoom);
		}

		camera.setParameters(p);

		return zoom;

	}

	public boolean toggleFlash(boolean flashStatus) {
		Camera.Parameters p = camera.getParameters();
		p.setFlashMode(flashStatus ? Parameters.FLASH_MODE_OFF
				: Parameters.FLASH_MODE_ON);
		camera.setParameters(p);

		return !flashStatus;
	}

}