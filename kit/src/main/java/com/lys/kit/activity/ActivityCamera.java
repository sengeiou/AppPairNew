package com.lys.kit.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.SurfaceTexture;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraDevice;
import android.hardware.camera2.CameraManager;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.hardware.camera2.TotalCaptureResult;
import android.hardware.camera2.params.StreamConfigurationMap;
import android.media.Image;
import android.media.ImageReader;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Size;
import android.util.SparseIntArray;
import android.view.Surface;
import android.view.TextureView;
import android.view.View;
import android.widget.Toast;

import com.lys.base.utils.FsUtils;
import com.lys.base.utils.LOG;
import com.lys.kit.R;

import java.io.File;
import java.io.FileOutputStream;
import java.nio.ByteBuffer;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public abstract class ActivityCamera extends KitActivity implements View.OnClickListener
{
	public static final int MODE_DEFAULT = 0; // 默认
	public static final int MODE_BITMAP = 1; // 图像

	private static final SparseIntArray ORIENTATIONS = new SparseIntArray();

	static
	{
		ORIENTATIONS.append(Surface.ROTATION_0, 90);
		ORIENTATIONS.append(Surface.ROTATION_90, 0);
		ORIENTATIONS.append(Surface.ROTATION_180, 270);
		ORIENTATIONS.append(Surface.ROTATION_270, 180);
	}

	private String mCameraId = "0"; // 摄像头ID（通常0代表后置摄像头，1代表前置摄像头）
	private ImageReader imageReader;
	private Size previewSize; // 预览尺寸
	private CameraDevice mCameraDevice;
	private CameraCaptureSession captureSession;
	private CaptureRequest previewRequest;

	private class Holder
	{
		private TextureView texture;
	}

	private Holder holder = new Holder();

	private void initHolder()
	{
		holder.texture = findViewById(R.id.texture);
	}

	protected abstract int getLayoutId();

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(getLayoutId());

		initHolder();

		holder.texture.setSurfaceTextureListener(new TextureView.SurfaceTextureListener()
		{
			@Override
			public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height)
			{
				openCamera(width, height);
			}

			@Override
			public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) { }

			@Override
			public boolean onSurfaceTextureDestroyed(SurfaceTexture texture)
			{
				return true;
			}

			@Override
			public void onSurfaceTextureUpdated(SurfaceTexture texture) {}
		});

		findViewById(R.id.camera).setOnClickListener(this);

	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		if (mCameraDevice != null)
		{
			mCameraDevice.close();
			mCameraDevice = null;
		}
	}

	@Override
	public void onClick(View view)
	{
		if (view.getId() == R.id.camera)
		{
			captureStillPicture();
		}
	}

	private void openCamera(int width, int height)
	{
		setupCameraOutputs(width, height);
		CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
		try
		{
			if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
			{
				return;
			}
			manager.openCamera(mCameraId, new CameraDevice.StateCallback()
			{
				//  摄像头被打开时激发该方法
				@Override
				public void onOpened(CameraDevice cameraDevice)
				{
					mCameraDevice = cameraDevice;
					createCameraPreviewSession(); // 开始预览
				}

				// 摄像头断开连接时激发该方法
				@Override
				public void onDisconnected(CameraDevice cameraDevice)
				{
					cameraDevice.close();
					mCameraDevice = null;
				}

				// 打开摄像头出现错误时激发该方法
				@Override
				public void onError(CameraDevice cameraDevice, int error)
				{
					cameraDevice.close();
					mCameraDevice = null;
					finish();
				}
			}, null);
		}
		catch (CameraAccessException e)
		{
			e.printStackTrace();
		}
	}

	private void setupCameraOutputs(int width, int height)
	{
		CameraManager manager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);
		try
		{
			String[] ids = manager.getCameraIdList();

			// 获取指定摄像头的特性
			CameraCharacteristics characteristics = manager.getCameraCharacteristics(mCameraId);
			// 获取摄像头支持的配置属性
			StreamConfigurationMap map = characteristics.get(CameraCharacteristics.SCALER_STREAM_CONFIGURATION_MAP);

//			for (Size option : map.getOutputSizes(ImageFormat.JPEG))
//			{
//				LOG.v("JPEG:" + option + "---" + 1.0 * option.getWidth() / option.getHeight());
//			}
//			for (Size option : map.getOutputSizes(SurfaceTexture.class))
//			{
//				LOG.v("Surface:" + option + "---" + 1.0 * option.getWidth() / option.getHeight());
//			}

			// 获取摄像头支持的最大尺寸
			Size captureSize = Collections.max(Arrays.asList(map.getOutputSizes(ImageFormat.JPEG)), new CompareSizesByArea());
			captureSize = new Size(1600, 1200);
			LOG.v("captureSize:" + captureSize);

			// 创建一个ImageReader对象，用于获取摄像头的图像数据
			imageReader = ImageReader.newInstance(captureSize.getWidth(), captureSize.getHeight(), ImageFormat.JPEG, 2);
			imageReader.setOnImageAvailableListener(new ImageReader.OnImageAvailableListener()
			{
				@Override
				public void onImageAvailable(ImageReader reader)
				{
					Image image = reader.acquireNextImage();
					Image.Plane[] planes = image.getPlanes();
					ByteBuffer buffer = planes[0].getBuffer();
					byte[] bytes = new byte[buffer.remaining()];
					buffer.get(bytes);
					image.close();

					String filepath;
					for (int i = 0; ; i++)
					{
						if (i == 0)
							filepath = String.format("%s/DCIM/Camera/IMAGE_%s.jpg", FsUtils.SD_CARD, new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()));
						else
							filepath = String.format("%s/DCIM/Camera/IMAGE_%s_%d.jpg", FsUtils.SD_CARD, new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date()), i);
						if (!new File(filepath).exists())
							break;
					}

					try
					{
						FileOutputStream os = new FileOutputStream(filepath);
						os.write(bytes);
						os.close();

						int mode = getIntent().getIntExtra("mode", MODE_DEFAULT);
						if (mode == MODE_DEFAULT)
						{
//							Intent intent = new Intent();
//							intent.setComponent(new ComponentName("com.lys.app.note", "com.lys.activity.ActivityEdit"));
//							intent.putExtra("path", filepath);
//							intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//							startActivity(intent);
						}
						else if (mode == MODE_BITMAP)
						{
							Intent intent = new Intent();
							intent.putExtra("path", filepath);
							setResult(RESULT_OK, intent);
						}
					}
					catch (Exception e)
					{
						e.printStackTrace();
					}

					finish();
				}
			}, null);

			// 获取最佳的预览尺寸
			previewSize = choosePreviewSize(map.getOutputSizes(SurfaceTexture.class), captureSize);
			previewSize = new Size(1440, 1080);
			LOG.v("previewSize:" + previewSize);
			// 根据选中的预览尺寸来调整预览组件（TextureView的）的长宽比
//			int orientation = getResources().getConfiguration().orientation;
//			if (orientation == Configuration.ORIENTATION_LANDSCAPE)
//			{
//				holder.texture.setAspectRatio(previewSize.getWidth(), previewSize.getHeight());
//			}
//			else
//			{
//				holder.texture.setAspectRatio(previewSize.getHeight(), previewSize.getWidth());
//			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private Size choosePreviewSize(Size[] previewSizes, Size captureSize)
	{
		List<Size> chooses = new ArrayList<>();
		int captureWidth = captureSize.getWidth();
		int captureHeight = captureSize.getHeight();
		for (Size previewSize : previewSizes)
		{
			if (previewSize.getHeight() == previewSize.getWidth() * captureHeight / captureWidth)
			{
				chooses.add(previewSize);
			}
		}
		// 如果找到多个预览尺寸，获取其中面积最大的。
		if (chooses.size() > 0)
		{
			return Collections.max(chooses, new CompareSizesByArea());
		}
		else
		{
			LOG.v("找不到合适的预览尺寸！！！");
			return previewSizes[0];
		}
	}

	private void createCameraPreviewSession()
	{
		try
		{
			SurfaceTexture texture = holder.texture.getSurfaceTexture();
			texture.setDefaultBufferSize(previewSize.getWidth(), previewSize.getHeight());
			Surface surface = new Surface(texture);
			// 创建作为预览的CaptureRequest.Builder
			final CaptureRequest.Builder previewRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_PREVIEW);
			// 将textureView的surface作为CaptureRequest.Builder的目标
			previewRequestBuilder.addTarget(new Surface(texture));
			// 创建CameraCaptureSession，该对象负责管理处理预览请求和拍照请求
			mCameraDevice.createCaptureSession(Arrays.asList(surface, imageReader.getSurface()), new CameraCaptureSession.StateCallback()
			{
				@Override
				public void onConfigured(CameraCaptureSession cameraCaptureSession)
				{
					if (null == mCameraDevice)
						return;

					// 当摄像头已经准备好时，开始显示预览
					captureSession = cameraCaptureSession;
					try
					{
						previewRequestBuilder.set(CaptureRequest.CONTROL_AF_TRIGGER, CameraMetadata.CONTROL_AF_TRIGGER_CANCEL);
						previewRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
						previewRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
						previewRequest = previewRequestBuilder.build();
						// 设置预览时连续捕获图像数据
						captureSession.setRepeatingRequest(previewRequest, null, null);
					}
					catch (CameraAccessException e)
					{
						e.printStackTrace();
					}
				}

				@Override
				public void onConfigureFailed(CameraCaptureSession cameraCaptureSession)
				{
					Toast.makeText(ActivityCamera.this, "配置失败！", Toast.LENGTH_SHORT).show();
				}
			}, null);
		}
		catch (CameraAccessException e)
		{
			e.printStackTrace();
		}
	}

	private void captureStillPicture()
	{
		try
		{
			if (mCameraDevice == null)
				return;
			// 创建作为拍照的CaptureRequest.Builder
			CaptureRequest.Builder captureRequestBuilder = mCameraDevice.createCaptureRequest(CameraDevice.TEMPLATE_STILL_CAPTURE);
			// 将imageReader的surface作为CaptureRequest.Builder的目标
			captureRequestBuilder.addTarget(imageReader.getSurface());
			captureRequestBuilder.set(CaptureRequest.CONTROL_AF_MODE, CaptureRequest.CONTROL_AF_MODE_CONTINUOUS_PICTURE);
			captureRequestBuilder.set(CaptureRequest.CONTROL_AE_MODE, CaptureRequest.CONTROL_AE_MODE_ON_AUTO_FLASH);
			// 获取设备方向
			int rotation = getWindowManager().getDefaultDisplay().getRotation();
			// 根据设备方向计算设置照片的方向
			captureRequestBuilder.set(CaptureRequest.JPEG_ORIENTATION, ORIENTATIONS.get(rotation));
			// 停止连续取景
			captureSession.stopRepeating();
			// 捕获静态图像
			captureSession.capture(captureRequestBuilder.build(), new CameraCaptureSession.CaptureCallback()
			{
				// 拍照完成时激发该方法
				@Override
				public void onCaptureCompleted(CameraCaptureSession session, CaptureRequest request, TotalCaptureResult result)
				{
//					try
//					{
//						// 打开连续取景模式
//						captureSession.setRepeatingRequest(previewRequest, null, null);
//					}
//					catch (CameraAccessException e)
//					{
//						e.printStackTrace();
//					}
				}
			}, null);
		}
		catch (CameraAccessException e)
		{
			e.printStackTrace();
		}
	}

	private class CompareSizesByArea implements Comparator<Size>
	{
		@Override
		public int compare(Size lhs, Size rhs)
		{
			return Long.signum((long) lhs.getWidth() * lhs.getHeight() - (long) rhs.getWidth() * rhs.getHeight());
		}
	}
}
