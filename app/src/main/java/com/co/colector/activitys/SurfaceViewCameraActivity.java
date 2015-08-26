package com.co.colector.activitys;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.co.colector.R;

public class SurfaceViewCameraActivity extends Activity implements SurfaceHolder.Callback {

	private TextView testView;
	private Camera camera;
	private SurfaceView surfaceView;
	private SurfaceHolder surfaceHolder;
	private PictureCallback rawCallback;
	private ShutterCallback shutterCallback;
	private PictureCallback jpegCallback;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
		surfaceHolder = surfaceView.getHolder();

		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed.
		surfaceHolder.addCallback(this);

		// deprecated setting, but required on Android versions prior to 3.0
		surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

		jpegCallback = new PictureCallback() {
			public void onPictureTaken(byte[] data, Camera camera) {
				FileOutputStream outStream = null;
				try {
					outStream = new FileOutputStream(String.format("/sdcard/%d.jpg", System.currentTimeMillis()));
					outStream.write(data);
					outStream.close();

					byte[] visorImagen = resizeImage(data, 200, 200, 50);

					ImageView imagenMax = new ImageView(SurfaceViewCameraActivity.this);

					imagenMax.setImageBitmap(
							BitmapFactory.decodeByteArray(visorImagen, 0, visorImagen.length)
					);

					((LinearLayout) findViewById(R.id.tiraImagenes)).addView(imagenMax);

				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally {

				}

				refreshCamera();
			}
		};
	}

	private byte[] resizeImage(byte[] input,int PHOTO_WIDTH, int PHOTO_HEIGHT, int quality) {
		Bitmap original = BitmapFactory.decodeByteArray(input , 0, input.length);
		Bitmap resized = Bitmap.createScaledBitmap(original, PHOTO_WIDTH, PHOTO_HEIGHT, true);

		ByteArrayOutputStream blob = new ByteArrayOutputStream();
		resized.compress(Bitmap.CompressFormat.JPEG, quality, blob);
		return blob.toByteArray();
	}

	public void captureImage(View v) throws IOException {
		//take the picture
		camera.takePicture(null, null, jpegCallback);
	}

	public void refreshCamera() {
		if (surfaceHolder.getSurface() == null) {
			// preview surface does not exist
			return;
		}

		// stop preview before making changes
		try {
			camera.stopPreview();
		} catch (Exception e) {
			// ignore: tried to stop a non-existent preview
		}

		// set preview size and make any resize, rotate or
		// reformatting changes here
		// start preview with new settings
		try {
			camera.setPreviewDisplay(surfaceHolder);
			camera.startPreview();
		} catch (Exception e) {

		}
	}

	public void surfaceChanged(SurfaceHolder holder, int format, int w, int h) {
		refreshCamera();
	}

	public void surfaceCreated(SurfaceHolder holder) {
		try {
			// open the camera
			camera = Camera.open();
		} catch (RuntimeException e) {
			// check for exceptions
			System.err.println(e);
			return;
		}
		Camera.Parameters param;
		param = camera.getParameters();

		// modify parameter
		param.setPreviewSize(352, 288);
		camera.setParameters(param);
		try {
			// The Surface has been created, now tell the camera where to draw
			// the preview.
			camera.setPreviewDisplay(surfaceHolder);
			camera.startPreview();
		} catch (Exception e) {
			// check for exceptions
			System.err.println(e);
			return;
		}
	}

	public void surfaceDestroyed(SurfaceHolder holder) {
		// stop preview and release camera
		camera.stopPreview();
		camera.release();
		camera = null;
	}

	@Override
	public void onBackPressed() {
		super.onBackPressed();
		finish();
	}
}