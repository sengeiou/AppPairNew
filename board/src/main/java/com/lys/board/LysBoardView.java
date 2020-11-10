package com.lys.board;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.lys.base.utils.FsUtils;
import com.lys.base.utils.ImageLoader;
import com.lys.base.utils.LOG;
import com.lys.board.dawing.LysBoardDrawing;
import com.lys.board.dawing.LysBoardDrawingBrush;
import com.lys.board.dawing.LysBoardDrawingEraser;
import com.lys.board.dawing.LysBoardDrawingStress;
import com.lys.board.dawing.LysBoardDrawingStroke;
import com.lys.board.utils.LysBoardMenu;
import com.lys.board.utils.LysBoardMenuDefault;
import com.lys.board.utils.LysBoardPoint;
import com.lys.board.utils.LysBoardUtils;
import com.lys.protobuf.SDrawing;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

// mBitmap被回收时的情况没有处理====
// 在滚动容器中的情况没有处理====
// 没有处理触摸CANCEL的情况====
// 没有处理Pen的问题====
// 没有增加橡皮擦等其它图形====
// 没有存储和加载（目标是图像）====
// 考虑一下整体缩放的问题====
// 讨论组模式====
// 录制模式====
// 扩展等控制====
// 实时存储的问题====
// 加锁====
public class LysBoardView extends View
{
	private static final boolean debug = false;

	private Bitmap mBitmap = null;
	private Canvas mCanvas = null;

	private boolean mLocked = false;

	public boolean isLocked()
	{
		return mLocked;
	}

	public void lock()
	{
		this.mLocked = true;
	}

	public void unlock()
	{
		this.mLocked = false;
	}

	private LysBoardMenu mMenu = new LysBoardMenuDefault();

	public void setMenu(LysBoardMenu menu)
	{
		this.mMenu = menu;
	}

	private File mSyncFile = null;

	public void waitSyncOver()
	{
		if (mSyncFile != null)
		{
			while (mSyncing)
			{
				LOG.v("wait syncing ...");
				try
				{
					Thread.sleep(10);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
	}

	public void setSyncFile(File syncFile)
	{
		waitSyncOver();

		this.mSyncFile = syncFile;

		clear();
		if (mSyncFile.exists())
		{
			LOG.v("syncFile:" + syncFile);
			loadImage(mSyncFile.getAbsolutePath(), new ImageLoader.OnDisplay()
			{
				@Override
				public void success(Bitmap bitmap, String url)
				{
					drawBitmap(bitmap);
				}
			});
		}
	}

	private void loadImage(String url, final ImageLoader.OnDisplay callback)
	{
		setTag(url);
		ImageLoader.load(getContext(), url, 0, new ImageLoader.OnLoad()
		{
			@Override
			public void over(Bitmap bitmap, String url)
			{
				if (getTag().equals(url))
				{
					if (bitmap != null)
					{
						if (callback != null)
							callback.success(bitmap, url);
					}
				}
			}
		});
	}

	private boolean mNeedSync = false;
	private boolean mSyncing = false;

	private void startSyncFile()
	{
		mSyncing = true;
		new Thread(new Runnable()
		{
			@Override
			public void run()
			{
				FsUtils.createFolder(mSyncFile.getParentFile());
				Bitmap bitmap = getBitmap();
				if (bitmap != null)
				{
					try
					{
						FileOutputStream os = new FileOutputStream(mSyncFile.getAbsolutePath());
						bitmap.compress(Bitmap.CompressFormat.PNG, 100, os);
						os.close();
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}
				}
				LOG.v("sync over");
				if (mNeedSync)
				{
					mNeedSync = false;
					startSyncFile();
				}
				else
					mSyncing = false;
			}
		}).start();
	}

	// 书写完成时自动调用，操作完成时手动调用
	public void syncFile()
	{
		if (mSyncFile != null)
		{
			if (mSyncing)
				mNeedSync = true;
			else
				startSyncFile();
		}
	}

	private LysBoardDrawing mCurrDrawing = null;
	private LysBoardDrawing mDebugDrawing = null;

	public LysBoardView(Context context, @Nullable AttributeSet attrs)
	{
		super(context, attrs);
		if (debug)
		{
			loadDebugRecord("/storage/emulated/0/drawRecord/xxxxxxxxxx.txt");
		}
	}

	private int getBitmapWidth()
	{
		if (mBitmap != null)
			return mBitmap.getWidth();
		return 0;
	}

	private int getBitmapHeight()
	{
		if (mBitmap != null)
			return mBitmap.getHeight();
		return 0;
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		LOG.v(String.format("onSizeChanged (%d, %d) ==> (%d, %d)", oldw, oldh, w, h));
		if (w <= 0 || h <= 0)
			return;
//		LOG.v(String.format("bitmap size (%d, %d)", getBitmapWidth(), getBitmapHeight()));
		if (getBitmapWidth() != w || getBitmapHeight() != h)
		{
			Bitmap bitmapBackup = null;
			if (mBitmap != null && !mBitmap.isRecycled())
			{
				bitmapBackup = Bitmap.createBitmap(mBitmap);
			}

			destroy();
			create(w, h);

			if (bitmapBackup != null)
			{
				mCanvas.drawBitmap(bitmapBackup, 0, 0, null);
				bitmapBackup.recycle();
			}
		}
	}

	public void destroy()
	{
		if (mBitmap != null)
		{
			mBitmap.recycle();
			mBitmap = null;
			mCanvas = null;
		}
	}

	private void create(int width, int height)
	{
		try
		{
			mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);
		}
		catch (OutOfMemoryError e)
		{
			System.gc();
			mBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
			mCanvas = new Canvas(mBitmap);
		}
	}

	private void reRenderAll()
	{
// TODO
	}

	private long lastDrawTime = 0; // 最后一次绘制结束时间
	private boolean hasDrawTask = false; // 是否有绘制任务
	private Handler waitHandler = new Handler();
	private Runnable waitRunnable = new Runnable()
	{
		@Override
		public void run()
		{
			if (!hasDrawTask)
			{
				hasDrawTask = true;
				postInvalidate();
//				LOG.v("-postInvalidate-------Runnable---");
			}
		}
	};

	@Override
	protected void onDraw(Canvas canvas)
	{
		super.onDraw(canvas);
		if (mBitmap != null)
		{
			if (mBitmap.isRecycled())
			{
				LOG.v("mBitmap isRecycled so to recreate");
				create(mBitmap.getWidth(), mBitmap.getHeight());
				reRenderAll();
			}

			if (mCurrDrawing != null && mCurrDrawing instanceof LysBoardDrawingEraser)
			{
				mCurrDrawing.draw(mCanvas);
				canvas.drawBitmap(mBitmap, 0, 0, null);
				mCurrDrawing.drawGizmo(canvas);
			}
			else if (mCurrDrawing != null && mCurrDrawing instanceof LysBoardDrawingStress)
			{
				mCurrDrawing.draw(mCanvas);
				canvas.drawBitmap(mBitmap, 0, 0, null);
				mCurrDrawing.drawGizmo(canvas);
			}
			else
			{
				canvas.drawBitmap(mBitmap, 0, 0, null);
				if (mCurrDrawing != null)
				{
					mCurrDrawing.draw(canvas);
					mCurrDrawing.drawGizmo(canvas);
				}
			}

			if (debug)
			{
				if (mDebugDrawing != null)
				{
					mDebugDrawing.draw(canvas);
				}
			}
		}
		lastDrawTime = System.currentTimeMillis();
		hasDrawTask = false;
//		LOG.v("onDraw:" + this);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
//		LOG.v("onTouchEvent");
//		return super.onTouchEvent(event);

//		StringBuilder sb = new StringBuilder();
//		sb.append(String.format("source = %d, ", event.getSource()));
//		for (int i = 0; i < event.getPointerCount(); i++)
//		{
//			sb.append(String.format("getToolType[%d] = %d, ", i, event.getToolType(i)));
//		}
//		LOG.v(sb.toString());

		if (mMenu.touchWrite() || LysBoardUtils.isPen(event))
		{
			ViewGroup parent = (ViewGroup) getParent();
			parent.requestDisallowInterceptTouchEvent(true);
			processTouchEvent(event);
			return true;
		}
		else
		{
			return false;
		}
	}

//	@Override
//	public boolean onTouch(View view, MotionEvent event)
//	{
//		LOG.v("onTouch");
//		return true;
//	}

	private void processTouchEvent(MotionEvent event)
	{
		if (isLocked())
			return;
		LysBoardPoint point = new LysBoardPoint(event.getX(), event.getY(), event.getPressure(), event.getEventTime());
		if (event.getAction() == MotionEvent.ACTION_DOWN)
		{
			actionDown(point);
		}
		else if (event.getAction() == MotionEvent.ACTION_MOVE)
		{
			if (mCurrDrawing instanceof LysBoardDrawingStroke || mCurrDrawing instanceof LysBoardDrawingBrush || mCurrDrawing instanceof LysBoardDrawingStress)
			{
				for (int i = 0; i < event.getHistorySize(); i++)
				{
					LysBoardPoint historyPoint = new LysBoardPoint(event.getHistoricalX(i), event.getHistoricalY(i), event.getHistoricalPressure(i), event.getHistoricalEventTime(i));
					actionMove(historyPoint);
				}
			}
			actionMove(point);
		}
		else if (event.getAction() == MotionEvent.ACTION_UP)
		{
			actionUp(point);
		}
//		long sleepTime = System.currentTimeMillis() - lastDrawTime;
//		LOG.v("sleepTime:" + sleepTime);
//		if (sleepTime < 10 && mCurrDrawing instanceof LysBoardDrawingAnyFunc)
//		{
//			waitHandler.removeCallbacks(waitRunnable);
//			waitHandler.postDelayed(waitRunnable, 10 - sleepTime);
//		}
//		else
		{
			if (!hasDrawTask)
			{
				hasDrawTask = true;
				postInvalidate();
//				LOG.v("-postInvalidate------------------");
			}
		}
	}

	public void actionDown(LysBoardPoint point)
	{
		if (mOperationListener != null)
			mOperationListener.onDrawBegin();

		mCurrDrawing = LysBoardDrawing.create(mMenu.getDrawingType(), mMenu.getPaintColor(), mMenu.getStrokeWidth(), mMenu.getAnyParam());
		mCurrDrawing.downPoint(point);
	}

	public void actionMove(LysBoardPoint point)
	{
		mCurrDrawing.movePoint(point);
	}

	public void actionUp(LysBoardPoint point)
	{
		mCurrDrawing.upPoint(point);

		if (mBitmap != null)
		{
			if (mBitmap.isRecycled())
			{
				LOG.v("mBitmap isRecycled so to recreate");
				create(mBitmap.getWidth(), mBitmap.getHeight());
				reRenderAll();
			}
			mCurrDrawing.draw(mCanvas);
		}

		syncFile();
		if (mOperationListener != null)
			mOperationListener.onDrawOver(mCurrDrawing);

		if (debug)
		{
			saveDebugRecord();
		}

		mCurrDrawing = null;
	}

	private void saveDebugRecord()
	{
		if (mCurrDrawing instanceof LysBoardDrawingStroke || mCurrDrawing instanceof LysBoardDrawingBrush)
		{
			String drawRecord = String.format("%s/drawRecord", FsUtils.SD_CARD);
			new File(drawRecord).mkdirs();
			String filepath;
			for (int i = 0; ; i++)
			{
				if (i == 0)
					filepath = String.format("%s/%s.txt", drawRecord, new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
				else
					filepath = String.format("%s/%s(%d).txt", drawRecord, new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()), i);
				if (!new File(filepath).exists())
					break;
			}
			SDrawing drawing = mCurrDrawing.saveToProto();
			LOG.v(String.format("save drawRecord : %d %s", drawing.drawingType, filepath));
			FsUtils.writeText(new File(filepath), drawing.saveToStr());
		}
	}

	private void loadDebugRecord(String filepath)
	{
		if (new File(filepath).exists())
		{
			LOG.v("load drawRecord : " + filepath);
			mDebugDrawing = LysBoardDrawing.create(SDrawing.load(FsUtils.readText(new File(filepath))));
		}
		else
		{
			LOG.v("drawRecord not exists : " + filepath);
		}
	}

	public void addOperation(LysBoardDrawing operation)
	{
		if (mBitmap != null)
		{
			if (mBitmap.isRecycled())
			{
				LOG.v("mBitmap isRecycled so to recreate");
				create(mBitmap.getWidth(), mBitmap.getHeight());
				reRenderAll();
			}
			operation.draw(mCanvas);
			postInvalidate();
		}
	}

	public void setHeight(int height)
	{
		LOG.v("setHeight:" + height);
		ViewGroup.LayoutParams layoutParams = getLayoutParams();
		layoutParams.height = height;
		setLayoutParams(layoutParams);
	}

	public void add(int height)
	{
		LOG.v("add:" + height);
		ViewGroup.LayoutParams layoutParams = getLayoutParams();
		layoutParams.height = getHeight() + height;
		setLayoutParams(layoutParams);
	}

	public void reduce(int height)
	{
		if (getHeight() - height > 0)
		{
			LOG.v("reduce:" + height);
			ViewGroup.LayoutParams layoutParams = getLayoutParams();
			layoutParams.height = getHeight() - height;
			setLayoutParams(layoutParams);
		}
	}

	public void clear()
	{
		LOG.v("clear");
		if (mBitmap != null)
		{
			if (mBitmap.isRecycled())
			{
				LOG.v("mBitmap isRecycled so to recreate");
				create(mBitmap.getWidth(), mBitmap.getHeight());
			}
			else
			{
				Paint paint = new Paint();
				paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
				mCanvas.drawPaint(paint);
			}
			postInvalidate();
		}
	}

	public Bitmap getBitmap()
	{
		if (mBitmap != null)
		{
			if (mBitmap.isRecycled())
			{
				LOG.v("mBitmap isRecycled so to recreate");
				create(mBitmap.getWidth(), mBitmap.getHeight());
				reRenderAll();
			}
			return mBitmap;
		}
		return null;
	}

	public void drawBitmap(Bitmap bitmap)
	{
		LOG.v("drawBitmap");
		if (mBitmap != null)
		{
			if (mBitmap.isRecycled())
			{
				LOG.v("mBitmap isRecycled so to recreate");
				create(mBitmap.getWidth(), mBitmap.getHeight());
				reRenderAll();
			}
			mCanvas.drawBitmap(bitmap, 0, 0, null);
			postInvalidate();
		}
	}

	public void drawBitmap(Bitmap bitmap, Rect dst)
	{
		LOG.v("drawBitmap:" + dst);
		if (mBitmap != null)
		{
			if (mBitmap.isRecycled())
			{
				LOG.v("mBitmap isRecycled so to recreate");
				create(mBitmap.getWidth(), mBitmap.getHeight());
				reRenderAll();
			}
			mCanvas.drawBitmap(bitmap, new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()), dst, null);
			postInvalidate();
		}
	}

	private OnOperationListener mOperationListener = null;

	public void setOperationListener(OnOperationListener operationListener)
	{
		this.mOperationListener = operationListener;
	}

	public OnOperationListener getOperationListener()
	{
		return mOperationListener;
	}

	public interface OnOperationListener
	{
		void onDrawBegin();

		void onDrawOver(LysBoardDrawing currDrawing);

//		void onAdd(int height);

//		void onReduce(int height);

//		void onClear();
	}
}
