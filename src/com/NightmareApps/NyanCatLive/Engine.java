package com.NightmareApps.NyanCatLive;

import java.util.ArrayList;
import java.util.Random;
import java.util.Vector;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.os.Handler;
import android.os.SystemClock;
import android.service.wallpaper.WallpaperService;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.WindowManager;

public class Engine extends WallpaperService {

	public static final String SHARED_PREFS_NAME = "settings";

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

	class RenderEngine extends Engine implements
			SharedPreferences.OnSharedPreferenceChangeListener {
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
		private int indexnumber = 0;
		public Bitmap current, sparkle;
		private int backgroundColor;
		public static final int FAST = 25;
		public static final int SLOW = 300;
		public static final int NORMAL = 100;
		public Display display;
		private SharedPreferences mPrefs;

		// Default Pref Variables
		public String stringINTERVAL = "normal";
		public int INTERVAL = 100;
		public String THEME = "nyan_cat";
		public boolean STROBE = false;
		public Random random;
		public int minWidth, minHeight;

		RenderEngine() {
			// Create a Paint to draw the lines for our cube
			final Paint paint = mPaint;
			paint.setColor(0xffffffff);
			paint.setAntiAlias(true);
			paint.setStrokeWidth(2);
			paint.setStrokeCap(Paint.Cap.ROUND);
			paint.setStyle(Paint.Style.STROKE);
			mPrefs = getSharedPreferences(SHARED_PREFS_NAME, 0);
			mPrefs.registerOnSharedPreferenceChangeListener(this);
			onSharedPreferenceChanged(mPrefs, null);
			random = new Random();

			mStartTime = SystemClock.elapsedRealtime();
		}

		@Override
		public void onCreate(SurfaceHolder surfaceHolder) {
			super.onCreate(surfaceHolder);

			// By default we don't get touch events, so enable them.
			setTouchEventsEnabled(true);

			// //
			DisplayMetrics metrics = new DisplayMetrics();
			display = ((WindowManager) getSystemService(WINDOW_SERVICE))
					.getDefaultDisplay();
			display.getMetrics(metrics);

			// TODO if (Theme==1) check and init

			current = BitmapFactory.decodeResource(getResources(),
					R.drawable.nyancat0);
			minWidth = display.getWidth();
			float ratioHeight = (float) minWidth / (float) current.getWidth();
			System.out.println((int) (ratioHeight * current.getHeight()));
			minHeight = (int) (ratioHeight * current.getHeight());

			current = Bitmap.createScaledBitmap(current, minWidth, minHeight,
					true);
			sparkle = BitmapFactory.decodeResource(getResources(), R.drawable.sparkles);

			loadFrames();

		}

		public void loadFrames() {
//			if (THEME.contains("nyan_cat")) {
//				if (indexnumber == 0) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyancat1);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 1) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyancat2);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 2) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyancat3);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 3) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyancat4);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 4) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyancat5);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 5) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyancat6);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 6) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyancat7);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 7) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyancat8);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 8) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyancat9);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 9) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyancat10);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 10) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyancat11);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 11) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyancat0);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				}
//			} else if (THEME.contains("nyan_skrat")) {
//				if (indexnumber == 0) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyanskrat1);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 1) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyanskrat2);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 2) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyanskrat3);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 3) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyanskrat4);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 4) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyanskrat5);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 5) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyanskrat6);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 6) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyanskrat7);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 7) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyanskrat8);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 8) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyanskrat9);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 9) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyanskrat10);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 10) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyanskrat11);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				} else if (indexnumber == 11) {
//					current = BitmapFactory.decodeResource(getResources(),
//							R.drawable.nyanskrat0);
//					current = Bitmap.createScaledBitmap(current, minWidth,
//							minHeight, true);
//				}
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
			stringINTERVAL = prefs.getString("speed_key", "normal");
			if (stringINTERVAL.contains("normal")) {
				INTERVAL = NORMAL;
			} else if (stringINTERVAL.contains("slow")) {
				INTERVAL = SLOW;
			} else if (stringINTERVAL.contains("fast")) {
				INTERVAL = FAST;
			}
			System.out.println("Interval Speed: " + INTERVAL + " aka: "
					+ stringINTERVAL);
			THEME = prefs.getString("theme_key", "nyan_cat");
			System.out.println("Theme: " + THEME);
			STROBE = prefs.getBoolean("strobe_key", false);
			System.out.println("Strobe: " + STROBE);
			if (current != null) {
				loadFrames();
			}
		}

		/*
		 * Draw one frame of the animation. This method gets called repeatedly
		 * by posting a delayed Runnable. You can do any drawing you want in
		 * here.
		 */
		void drawFrame() {
			final SurfaceHolder holder = getSurfaceHolder();

			Canvas c = null;
			try {
				c = holder.lockCanvas();
				if (c != null) {
					// draw something
					drawImage(c);
					// drawTouchPoint(c);
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

		//
		void drawImage(Canvas c) {
			c.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
			c.save();
			c.translate(mCenterX, mCenterY);
			if (STROBE) {
				backgroundColor = random.nextInt();
				c.drawColor(backgroundColor);
			} else {
				if (THEME.contains("nyan_cat")) {
					backgroundColor = -16764058;
				} else if (THEME.contains("nyan_skrat")) {
					backgroundColor = -13421773;
				}
				c.drawColor(backgroundColor);
			}
			c.translate(0, (c.getHeight() / 2) - current.getHeight() / 2);
			c.drawBitmap(current, 0, 0, mPaint);
			c.restore();
			loadFrames();
			c.drawBitmap(sparkle, 0, 0, mPaint);
			if (indexnumber < 11) {
				indexnumber++;
			} else {
				indexnumber = 0;
			}
			System.out.println(indexnumber);
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