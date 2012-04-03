package com.NightmareApps.NyanCatLive;

import java.util.ArrayList;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Handler;
import android.os.SystemClock;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.WindowManager;

public class Engine extends WallpaperService {

	// Default Pref Variables
	public static int INTERVAL = 100;
	public static int THEME = 1;
	public static boolean STROBE = false;

	private final Handler mHandler = new Handler();

	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public Engine onCreateEngine() {
		return new RenderEngine();
	}

	class RenderEngine extends Engine {
		private final Paint mPaint = new Paint();
		private float mOffset;
		private float mTouchX = -1;
		private float mTouchY = -1;
		private long mStartTime;
		private float mCenterX;
		private float mCenterY;

		private final Runnable mDrawWallpaper = new Runnable() {
			public void run() {
				drawFrame();
			}
		};

		private boolean mVisible;
		private int indexnumber;
		private Bitmap frame;
		public Bitmap frame0, frame1, frame2, frame3, frame4, frame5, frame6,
				frame7, frame8, frame9, frame10, frame11;
		ArrayList<Bitmap> frameArray = new ArrayList<Bitmap>();
		private int backgroundColor;
		public static final int FAST = 50;
		public static final int SLOW = 200;
		public static final int NORMAL = 100;

		RenderEngine() {
			// Create a Paint to draw the lines for our cube
			final Paint paint = mPaint;
			paint.setColor(0xffffffff);
			paint.setAntiAlias(true);
			paint.setStrokeWidth(2);
			paint.setStrokeCap(Paint.Cap.ROUND);
			paint.setStyle(Paint.Style.STROKE);

			mStartTime = SystemClock.elapsedRealtime();
		}

		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);

			// By default we don't get touch events, so enable them.
			setTouchEventsEnabled(true);

			// //
			DisplayMetrics metrics = new DisplayMetrics();
			Display display = ((WindowManager) getSystemService(WINDOW_SERVICE))
					.getDefaultDisplay();
			display.getMetrics(metrics);

//			if (THEME == 1) {

				frame0 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyanskrat0);

				int minWidth = display.getWidth();
				float ratioHeight = (float) minWidth / (float) frame0.getWidth();
				System.out.println((int) (ratioHeight * frame0.getHeight()));
				int minHeight = (int) (ratioHeight * frame0.getHeight());
				
				frame0 = Bitmap.createScaledBitmap(frame0, minWidth, minHeight,
						true);
				frameArray.add(frame0);
				frame1 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyanskrat1);
				frame1 = Bitmap.createScaledBitmap(frame1, minWidth, minHeight,
						true);
				frameArray.add(frame1);

				frame2 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyanskrat2);
				frame2 = Bitmap.createScaledBitmap(frame2, minWidth, minHeight,
						true);
				frameArray.add(frame2);

				frame3 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyanskrat3);
				frame3 = Bitmap.createScaledBitmap(frame3, minWidth, minHeight,
						true);
				frameArray.add(frame3);

				frame4 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyanskrat4);
				frame4 = Bitmap.createScaledBitmap(frame4, minWidth, minHeight,
						true);
				frameArray.add(frame4);

				frame5 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyanskrat5);
				frame5 = Bitmap.createScaledBitmap(frame5, minWidth, minHeight,
						true);
				frameArray.add(frame5);

				frame6 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyanskrat6);
				frame6 = Bitmap.createScaledBitmap(frame6, minWidth, minHeight,
						true);
				frameArray.add(frame6);

				frame7 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyanskrat7);
				frame7 = Bitmap.createScaledBitmap(frame7, minWidth, minHeight,
						true);
				frameArray.add(frame7);

				frame8 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyanskrat8);
				frame8 = Bitmap.createScaledBitmap(frame8, minWidth, minHeight,
						true);
				frameArray.add(frame8);

				frame9 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyanskrat9);
				frame9 = Bitmap.createScaledBitmap(frame9, minWidth, minHeight,
						true);
				frameArray.add(frame9);

				frame10 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyanskrat10);
				frame10 = Bitmap.createScaledBitmap(frame10, minWidth,
						minHeight, true);
				frameArray.add(frame10);
				frame11 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyanskrat11);
				frame11 = Bitmap.createScaledBitmap(frame11, minWidth,
						minHeight, true);

				frameArray.add(frame11);

				backgroundColor = frame0.getPixel(1, 1);
//			}
		}

		@Override
		public void onDestroy() {
			super.onDestroy();
			mHandler.removeCallbacks(mDrawWallpaper);
		}

		@Override
		public void onVisibilityChanged(boolean visible) {
			mVisible = visible;
			if (visible) {
				drawFrame();
			} else {
				mHandler.removeCallbacks(mDrawWallpaper);
			}
		}

		@Override
		public void onSurfaceChanged(SurfaceHolder holder, int format,
				int width, int height) {
			super.onSurfaceChanged(holder, format, width, height);
			// store the center of the surface, so we can draw the cube in the
			// right spot
			// mCenterX = width / 2.0f;
			// mCenterY = height / 2.0f;
			drawFrame();
		}

		@Override
		public void onSurfaceCreated(SurfaceHolder holder) {
			super.onSurfaceCreated(holder);
		}

		@Override
		public void onSurfaceDestroyed(SurfaceHolder holder) {
			super.onSurfaceDestroyed(holder);
			mVisible = false;
			mHandler.removeCallbacks(mDrawWallpaper);
		}

		@Override
		public void onOffsetsChanged(float xOffset, float yOffset, float xStep,
				float yStep, int xPixels, int yPixels) {
			mOffset = xOffset;
			drawFrame();
		}

		/*
		 * Store the position of the touch event so we can use it for drawing
		 * later
		 */
		@Override
		public void onTouchEvent(MotionEvent event) {
			if (event.getAction() == MotionEvent.ACTION_MOVE) {
				mTouchX = event.getX();
				mTouchY = event.getY();
			} else {
				mTouchX = -1;
				mTouchY = -1;
			}
			super.onTouchEvent(event);
		}

		public void onSharedPreferenceChanged(SharedPreferences prefs,
				String key) {
			INTERVAL = prefs.getInt("speed_key", NORMAL);
			System.out.print("Changed" + INTERVAL);
		}

		/*
		 * Draw one frame of the animation. This method gets called repeatedly
		 * by posting a delayed Runnable. You can do any drawing you want in
		 * here. This example draws a wireframe cube.
		 */
		void drawFrame() {
			final SurfaceHolder holder = getSurfaceHolder();

			Canvas c = null;
			try {
				c = holder.lockCanvas();
				if (c != null) {
					// draw something
					drawImage(c);
					drawTouchPoint(c);
				}
			} finally {
				if (c != null)
					holder.unlockCanvasAndPost(c);
			}

			// Reschedule the next redraw
			mHandler.removeCallbacks(mDrawWallpaper);
			if (mVisible) {
				mHandler.postDelayed(mDrawWallpaper, INTERVAL);
			}
		}

		void drawImage(Canvas c) {
			c.save();
			c.translate(mCenterX, mCenterY);
			c.drawColor(backgroundColor);
			// TODO
			if (indexnumber == 0) {
				frame = frame0;
			} else if (indexnumber == 1) {
				frame = frame1;
			} else if (indexnumber == 2) {
				frame = frame2;
			} else if (indexnumber == 3) {
				frame = frame3;
			} else if (indexnumber == 4) {
				frame = frame4;
			} else if (indexnumber == 5) {
				frame = frame5;
			} else if (indexnumber == 6) {
				frame = frame6;
			} else if (indexnumber == 7) {
				frame = frame7;
			} else if (indexnumber == 8) {
				frame = frame8;
			} else if (indexnumber == 9) {
				frame = frame9;
			} else if (indexnumber == 10) {
				frame = frame10;
			} else if (indexnumber == 11) {
				frame = frame11;
			}
			c.translate(0, 250);
			c.drawBitmap(frame, 0, 0, mPaint);
			c.restore();
			if (indexnumber == 11) {
				indexnumber = 0;
			} else {
				indexnumber++;
			}
		}

		/*
		 * Draw a circle around the current touch point, if any.
		 */
		void drawTouchPoint(Canvas c) {
			if (mTouchX >= 0 && mTouchY >= 0) {
				c.drawCircle(mTouchX, mTouchY, 80, mPaint);
			}
		}

	}

}