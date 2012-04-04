package com.NightmareApps.NyanCatLive;

import java.util.ArrayList;
import java.util.Random;

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
		public Bitmap frame0, frame1, frame2, frame3, frame4, frame5, frame6,
				frame7, frame8, frame9, frame10, frame11;
		ArrayList<Bitmap> frameArray = new ArrayList<Bitmap>();
		private int backgroundColor;
		public static final int FAST = 50;
		public static final int SLOW = 200;
		public static final int NORMAL = 100;
		public Display display;
		private SharedPreferences mPrefs;

		// Default Pref Variables
		public String stringINTERVAL = "normal";
		public int INTERVAL = 100;
		public String THEME = "nyan_cat";
		public boolean STROBE = false;
		public Random random;

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

			loadFrames();

		}

		public void loadFrames() {
			if (!frameArray.isEmpty()) {
				frameArray.clear();
			}
			if (THEME.contains("nyan_cat")) {
				frame0 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyancat0);

				int minWidth = display.getWidth();
				float ratioHeight = (float) minWidth
						/ (float) frame0.getWidth();
				System.out.println((int) (ratioHeight * frame0.getHeight()));
				int minHeight = (int) (ratioHeight * frame0.getHeight());

				frame0 = Bitmap.createScaledBitmap(frame0, minWidth, minHeight,
						true);
				frameArray.add(frame0);
				frame1 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyancat1);
				frame1 = Bitmap.createScaledBitmap(frame1, minWidth, minHeight,
						true);
				frameArray.add(frame1);

				frame2 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyancat2);
				frame2 = Bitmap.createScaledBitmap(frame2, minWidth, minHeight,
						true);
				frameArray.add(frame2);

				frame3 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyancat3);
				frame3 = Bitmap.createScaledBitmap(frame3, minWidth, minHeight,
						true);
				frameArray.add(frame3);

				frame4 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyancat4);
				frame4 = Bitmap.createScaledBitmap(frame4, minWidth, minHeight,
						true);
				frameArray.add(frame4);

				frame5 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyancat5);
				frame5 = Bitmap.createScaledBitmap(frame5, minWidth, minHeight,
						true);
				frameArray.add(frame5);

				frame6 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyancat6);
				frame6 = Bitmap.createScaledBitmap(frame6, minWidth, minHeight,
						true);
				frameArray.add(frame6);

				frame7 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyancat7);
				frame7 = Bitmap.createScaledBitmap(frame7, minWidth, minHeight,
						true);
				frameArray.add(frame7);

				frame8 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyancat8);
				frame8 = Bitmap.createScaledBitmap(frame8, minWidth, minHeight,
						true);
				frameArray.add(frame8);

				frame9 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyancat9);
				frame9 = Bitmap.createScaledBitmap(frame9, minWidth, minHeight,
						true);
				frameArray.add(frame9);

				frame10 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyancat10);
				frame10 = Bitmap.createScaledBitmap(frame10, minWidth,
						minHeight, true);
				frameArray.add(frame10);
				frame11 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyancat11);
				frame11 = Bitmap.createScaledBitmap(frame11, minWidth,
						minHeight, true);

				frameArray.add(frame11);

				backgroundColor = -16764058;
			} else if (THEME.contains("nyan_skrat")) {

				frame0 = BitmapFactory.decodeResource(getResources(),
						R.drawable.nyanskrat0);

				int minWidth = display.getWidth();
				float ratioHeight = (float) minWidth
						/ (float) frame0.getWidth();
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
				// Printed value from frame0.getPixel(1,1);
				backgroundColor = -13421773;
			}
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
			if (frame0 != null) {
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
//					drawTouchPoint(c);
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
				c.drawColor(backgroundColor);
			}
			c.translate(0, (c.getHeight() / 2) - frame0.getHeight() / 2);
			if (frameArray.size() > 0) {
				c.drawBitmap(frameArray.get(indexnumber), 0, 0, mPaint);
			}
			c.restore();
			if (indexnumber < frameArray.size() - 1) {
				indexnumber++;
			} else {
				indexnumber = 0;
			}
			// if (indexnumber == 11) {
			// indexnumber = 0;
			// } else {
			// indexnumber++;
			// }
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