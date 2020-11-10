package com.lys.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;

import com.lys.App;
import com.lys.activity.ActivityTaskBook;
import com.lys.app.R;
import com.lys.base.activity.BaseActivity;
import com.lys.base.utils.CommonUtils;
import com.lys.base.utils.ImageLoader;
import com.lys.base.utils.LOG;
import com.lys.base.utils.SysUtils;
import com.lys.config.AppConfig;
import com.lys.dialog.DialogSelectFriend;
import com.lys.dialog.DialogSelectFriendToShare;
import com.lys.fragment.LysConversationFragment;
import com.lys.kit.activity.KitActivity;
import com.lys.kit.dialog.DialogAlert;
import com.lys.kit.dialog.DialogTimeWait;
import com.lys.kit.utils.ImageLoad;
import com.lys.kit.view.BoardView;
import com.lys.message.BoardMessage;
import com.lys.message.TaskMessage;
import com.lys.message.TransMessage;
import com.lys.protobuf.SImageMessageExtra;
import com.lys.protobuf.SPTask;
import com.lys.protobuf.SUser;
import com.lys.provider.LysFileMessageItemProvider;
import com.lys.provider.LysImageMessageItemProvider;
import com.lys.provider.LysSightMessageItemProvider;
import com.lys.provider.LysStickerMessageItemProvider;
import com.lys.provider.LysTaskMessageItemProvider;
import com.lys.provider.LysTextMessageItemProvider;
import com.lys.provider.LysVoiceMessageItemProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import io.rong.common.SystemUtils;
import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.IExtensionModule;
import io.rong.imkit.RongExtensionManager;
import io.rong.imkit.RongIM;
import io.rong.imkit.RongMessageItemLongClickActionManager;
import io.rong.imkit.manager.IUnReadMessageObserver;
import io.rong.imkit.manager.SendImageManager;
import io.rong.imkit.model.UIConversation;
import io.rong.imkit.model.UIMessage;
import io.rong.imkit.widget.provider.MessageItemLongClickAction;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.imlib.model.UserInfo;
import io.rong.message.ImageMessage;
import io.rong.message.SightMessage;
import io.rong.message.TextMessage;

public class LysIM implements RongIM.UserInfoProvider, //
		RongIM.OnSendMessageListener, //
		RongIMClient.OnReceiveMessageListener, //
		RongIMClient.ConnectionStatusListener, //
		RongIM.ConversationListBehaviorListener, //
		RongIM.ConversationClickListener, //
		IUnReadMessageObserver
{
	private static LysIM mInstance = null;

	public static void init(Context context)
	{
		mInstance = new LysIM(context);
	}

	public static LysIM instance()
	{
		return mInstance;
	}

	//--------------------------

	private Context context = null;

	private LysIM(Context context)
	{
		this.context = context.getApplicationContext();
		initImpl();
	}

	private void initImpl()
	{
		if (SysUtils.isDebug())
			RongIM.init(context, "bmdehs6pba3as");
		else
			RongIM.init(context, "pkfcgjstp8l88");

		RongIM.registerMessageType(BoardMessage.class);
		RongIM.registerMessageType(TransMessage.class);

		RongIM.registerMessageType(TaskMessage.class);
		RongIM.registerMessageTemplate(new LysTaskMessageItemProvider());

		RongIM.registerMessageType(SightMessage.class);
		RongIM.registerMessageTemplate(new LysSightMessageItemProvider());
//		RongExtensionManager.getInstance().registerExtensionModule(new SightExtensionModule());

		RongIM.setUserInfoProvider(this, true);
		RongIM.getInstance().setSendMessageListener(this);
		RongIM.setOnReceiveMessageListener(this);
		RongIM.setConnectionStatusListener(this);
		RongIM.setConversationListBehaviorListener(this);
		RongIM.getInstance().setConversationClickListener(this);

		Conversation.ConversationType[] conversationTypes = {Conversation.ConversationType.PRIVATE};
		RongIM.getInstance().addUnReadMessageCountChangedObserver(this, conversationTypes);

		List<IExtensionModule> moduleList = RongExtensionManager.getInstance().getExtensionModules();
		if (moduleList != null)
		{
			IExtensionModule defaultModule = null;
			for (IExtensionModule module : moduleList)
			{
				if (module instanceof DefaultExtensionModule)
				{
					defaultModule = module;
					break;
				}
			}
			if (defaultModule != null)
			{
				RongExtensionManager.getInstance().unregisterExtensionModule(defaultModule);
				RongExtensionManager.getInstance().registerExtensionModule(0, new LysExtensionModule());
			}
		}

		RongIM.getInstance().registerMessageTemplate(new LysImageMessageItemProvider());
		RongIM.getInstance().registerMessageTemplate(new LysTextMessageItemProvider());
		RongIM.getInstance().registerMessageTemplate(new LysVoiceMessageItemProvider(context));
		RongIM.getInstance().registerMessageTemplate(new LysStickerMessageItemProvider());
		RongIM.getInstance().registerMessageTemplate(new LysFileMessageItemProvider());

		RongMessageItemLongClickActionManager.getInstance().addMessageItemLongClickAction(new MessageItemLongClickAction.Builder().title("录画布").showFilter(new MessageItemLongClickAction.Filter()
		{
			@Override
			public boolean filter(UIMessage uiMessage)
			{
				if (uiMessage.getContent() instanceof ImageMessage)
					return true;
				return false;
			}
		}).actionListener(new MessageItemLongClickAction.MessageItemLongClickListener()
		{
			@Override
			public boolean onMessageItemLongClick(final Context context, UIMessage uiMessage)
			{
				if (context instanceof KitActivity)
				{
					final KitActivity activity = (KitActivity) context;
					ImageMessage imageMessage = (ImageMessage) uiMessage.getContent();
					String url = LysImageMessageItemProvider.getImageMessageUrl(imageMessage);
					if (!TextUtils.isEmpty(url))
					{
						ImageLoader.load(context, url, SysUtils.screenWidth(context), new ImageLoader.OnLoad()
						{
							@Override
							public void over(Bitmap bitmap, String url)
							{
								if (bitmap != null)
								{
									activity.record(bitmap, new BaseActivity.OnImageListener()
									{
										@Override
										public void onResult(String filepath)
										{
											LysConversationFragment fragment = (LysConversationFragment) activity.getSupportFragmentManager().findFragmentById(R.id.conversation);
											boolean sendOrigin = false;
											LinkedHashMap<String, Integer> linkedHashMap = new LinkedHashMap();
											linkedHashMap.put("file://" + filepath, 3);
											fragment.onImageResult(linkedHashMap, sendOrigin);
										}
									});
								}
								else
								{
									LOG.toast(context, "图像下载失败");
								}
							}
						});
					}
				}
				return true;
			}
		}).build());

		RongMessageItemLongClickActionManager.getInstance().addMessageItemLongClickAction(new MessageItemLongClickAction.Builder().title("转发").showFilter(new MessageItemLongClickAction.Filter()
		{
			@Override
			public boolean filter(UIMessage uiMessage)
			{
				if (uiMessage.getContent() instanceof TaskMessage)
					return false;
				return true;
			}
		}).actionListener(new MessageItemLongClickAction.MessageItemLongClickListener()
		{
			@Override
			public boolean onMessageItemLongClick(final Context context, final UIMessage uiMessage)
			{
				if (context instanceof KitActivity)
				{
					final KitActivity activity = (KitActivity) context;
					DialogSelectFriend.show(context, App.userId(), new DialogSelectFriend.OnListener()
					{
						@Override
						public void onSelect(List<SUser> selectedList)
						{
							if (selectedList.size() > 0)
							{
								for (SUser user : selectedList)
								{
									sendMessage(user.id, uiMessage.getContent());
								}
								LOG.toast(context, "已转发");
							}
						}
					});
				}
				return true;
			}
		}).build());

	}

	//--------------------------------------------------------------

	private ConcurrentLinkedQueue<Message> mSendQueue = new ConcurrentLinkedQueue<>();
	private boolean isSending = false;

	private void send()
	{
		final Message message = mSendQueue.peek(); // 获取但不移除
		LOG.v("send : " + message.toString());
		RongIM.getInstance().sendMessage(message, null, null, new IRongCallback.ISendMessageCallback()
		{
			@Override
			public void onAttached(Message msg)
			{
			}

			@Override
			public void onSuccess(Message msg)
			{
				LOG.v("onSuccess");
				mSendQueue.remove(message);
				if (!mSendQueue.isEmpty())
					send();
				else
					isSending = false;
			}

			@Override
			public void onError(Message msg, RongIMClient.ErrorCode errorCode)
			{
				LOG.v("onFailed " + errorCode);
				new Handler().postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						send();
					}
				}, 100);
			}
		});
	}

	private void startSend()
	{
		if (!isSending && !mSendQueue.isEmpty())
		{
			isSending = true;
			send();
		}
	}

	public void sendMessage(String userId, MessageContent content)
	{
		Message message = Message.obtain(userId, Conversation.ConversationType.PRIVATE, content);
		mSendQueue.offer(message);
		startSend();
	}

	//--------------------------

	private boolean alreadyConnected = false;

	public void connect(SUser user, RongIMClient.ConnectCallback callback)
	{
		if (alreadyConnected)
			return;
		if (user.useRong == 1)
		{
			if (TextUtils.isEmpty(user.token))
			{
				LOG.toast(context, "rong token is null");
				return;
			}
			String current = SystemUtils.getCurrentProcessName(context);
			String mainProcessName = context.getPackageName();
			if (mainProcessName.equals(current))
			{
				alreadyConnected = true;
				LOG.v("connect rong im .........");
				RongIM.connect(user.token, callback);
			}
		}
		else
		{
			String current = SystemUtils.getCurrentProcessName(context);
			String mainProcessName = context.getPackageName();
			if (mainProcessName.equals(current))
			{
				alreadyConnected = true;
				LOG.v("not use rong im .........");
				callback.onSuccess(user.id);
				Intent intent = new Intent();
				intent.setAction(Action_CONNECTED(context));
				context.sendBroadcast(intent);
			}
		}
	}

	public void logout()
	{
		if (!alreadyConnected)
			return;
		alreadyConnected = false;
		RongIM.getInstance().logout();
	}

	//--------------------------

	@Override
	public UserInfo getUserInfo(String userId)
	{
		UserCacheManager.instance().getUser(userId, new UserCacheManager.OnResult()
		{
			@Override
			public void result(SUser user)
			{
				if (user != null)
					refreshUserInfoCache(user);
			}
		});
		return null;
	}

	public void refreshUserInfoCache(SUser user)
	{
		Uri portraitUri;
		if (TextUtils.isEmpty(user.head))
			portraitUri = Uri.parse(AppConfig.defaultHead);
		else
			portraitUri = Uri.parse(ImageLoad.checkUrl(user.head));
		UserInfo userInfo = new UserInfo(user.id, user.name, portraitUri);
		RongIM.getInstance().refreshUserInfoCache(userInfo);
	}

	//------------------ 发出消息监听 -------------------

	@Override
	public Message onSend(Message message)
	{
		if (message.getContent() instanceof ImageMessage)
		{
			ImageMessage imageMessage = (ImageMessage) message.getContent();
			if (imageMessage.getLocalUri() != null)
			{
				String path = imageMessage.getLocalUri().getPath();
				if (!TextUtils.isEmpty(path))
				{
					BitmapFactory.Options opts = CommonUtils.readBitmapSize(path);
					int width;
					int height;
					if (opts.outWidth < 128)
					{
						width = 128;
						height = 128 * opts.outHeight / opts.outWidth;
					}
					else if (opts.outWidth > 512)
					{
						width = 512;
						height = 512 * opts.outHeight / opts.outWidth;
					}
					else
					{
						width = opts.outWidth;
						height = opts.outHeight;
					}
					imageMessage.setExtra(SImageMessageExtra.create(width, height).saveToStr());
				}
			}
		}
		return message;
	}

	@Override
	public boolean onSent(Message message, RongIM.SentMessageErrorCode sentMessageErrorCode)
	{
		return false;
	}

	//------------------ 接收消息监听 -------------------

	public static String ActionTrans(Context context, String evt)
	{
		return context.getPackageName() + "." + LysIM.class.getName() + ".trans." + evt;
	}

	@Override
	public boolean onReceived(final Message message, int left)
	{
		LOG.v("onReceived:" + message);
		if (message.getContent() instanceof TransMessage)
		{
			final TransMessage transMessage = (TransMessage) message.getContent();
			if (transMessage.isValid())
			{
				final KitActivity topActivity = KitActivity.getTopActivity();
				if (topActivity != null)
				{
					topActivity.runOnUiThread(new Runnable()
					{
						@Override
						public void run()
						{
							if (!onReceivedTrans(topActivity, message, transMessage))
							{
								Intent intent = new Intent();
								intent.setAction(ActionTrans(context, transMessage.getEvt()));
								intent.putExtra("message", message);
								context.sendBroadcast(intent);
							}
						}
					});
				}
			}
			return true;
		}
		return false;
	}

	private boolean onReceivedTrans(final KitActivity context, final Message message, final TransMessage transMessage)
	{
		if (transMessage.getEvt().equals(AppConfig.TransEvt_TeachCall))
		{
			final long leftTime = transMessage.leftTime() - 5 * 1000; // 注意：这个时间只能取一次，不能放在 tick 里取，因为这个时间是动态的；减 5 秒是为了避免临界情况
			if (leftTime > 5 * 1000)
			{
				final SPTask task = SPTask.load(transMessage.getMsg());
				final String targetId = transMessage.getParam(0);
				String targetName = transMessage.getParam(1);
				final String teachInstanceId = transMessage.getParam(2);

				StringBuilder sb = new StringBuilder();
				sb.append(String.format("接收到上课邀请《%s》%s\r\n", task.name, AppConfig.isSelfTask(task) ? "学生任务" : "老师任务"));
				sb.append(String.format("任务ID：%s\r\n", task.id));
				sb.append(String.format("邀请老师：%s（%s）\r\n", targetName, targetId));
				Helper.addEvent(context, App.userId(), AppConfig.EventAction_TeachCall, teachInstanceId, sb.toString());

				DialogTimeWait.create(context).showWait(false).setMessage(String.format("《%s》在《%s》等你，是否进入？", targetName, task.name)).setTickListener(new DialogTimeWait.OnTickListener()
				{
					@Override
					public void onTick(DialogTimeWait dialog, long timeDt)
					{
						long downTime = leftTime - timeDt;
						if (downTime > 0)
						{
							dialog.setInfo(String.format("（%s）", downTime / 1000));
						}
						else
						{
							dialog.dismiss();
						}
					}
				}).setLeftListener("拒绝", new DialogTimeWait.OnClickListener()
				{
					@Override
					public void onClick(DialogTimeWait dialog)
					{
						dialog.dismiss();
						TransMessage.send(message.getSenderUserId(), TransMessage.obtain(AppConfig.TransEvt_TeachRefuse, teachInstanceId), null);
						Helper.addEvent(context, App.userId(), AppConfig.EventAction_TeachRefuse, teachInstanceId, String.format("我拒绝了"));
					}
				}).setRightListener("进入", new DialogTimeWait.OnClickListener()
				{
					@Override
					public void onClick(DialogTimeWait dialog)
					{
						dialog.dismiss();
						TransMessage.send(message.getSenderUserId(), TransMessage.obtain(AppConfig.TransEvt_TeachAgree, teachInstanceId), null);
						Helper.addEvent(context, App.userId(), AppConfig.EventAction_TeachAgree, teachInstanceId, String.format("我同意了"));
						boolean findIt = false;
						for (KitActivity activity : KitActivity.mActivityList)
						{
							if (activity.getClass().equals(ActivityTaskBook.class))
							{
								findIt = true;
								break;
							}
						}
						if (findIt)
						{
							clearAndGoinClass(task, targetId, teachInstanceId);
						}
						else
						{
							ActivityTaskBook.goinWithSyncGuest(context, task, targetId, teachInstanceId);
						}
					}
				}).startTick().show();
			}
			return true;
		}
		return false;
	}

	private void clearAndGoinClass(final SPTask task, final String targetId, final String teachInstanceId)
	{
		KitActivity topActivity = KitActivity.getTopActivity();
		if (topActivity.getClass().equals(ActivityTaskBook.class))
		{
			ActivityTaskBook bookActivity = (ActivityTaskBook) topActivity;
			bookActivity.setFinishAction(new KitActivity.OnFinishAction()
			{
				@Override
				public void onFinish()
				{
					new Handler().postDelayed(new Runnable()
					{
						@Override
						public void run()
						{
							ActivityTaskBook.goinWithSyncGuest(KitActivity.getTopActivity(), task, targetId, teachInstanceId);
						}
					}, 500);
				}
			});
			bookActivity.close(true);
		}
		else
		{
			topActivity.setFinishAction(new KitActivity.OnFinishAction()
			{
				@Override
				public void onFinish()
				{
					clearAndGoinClass(task, targetId, teachInstanceId);
				}
			});
			topActivity.finish();
		}
	}

	//------------------ 连接状态监听 -------------------

	public static String Action_CONNECTED(Context context)
	{
		return context.getPackageName() + "." + LysIM.class.getName() + ".CONNECTED";
	}

	@Override
	public void onChanged(ConnectionStatus connectionStatus)
	{
		LOG.v("rong ConnectionStatus onChanged : " + connectionStatus);
		switch (connectionStatus)
		{
		case CONNECTED://连接成功。
			Intent intent = new Intent();
			intent.setAction(Action_CONNECTED(context));
			context.sendBroadcast(intent);
			break;
		case DISCONNECTED://断开连接。
			break;
		case CONNECTING://连接中。
			break;
		case NETWORK_UNAVAILABLE://网络不可用。
			break;
		case TOKEN_INCORRECT:
			LOG.toast(context, "token失效，请联系技术人员");
			break;
		case KICKED_OFFLINE_BY_OTHER_CLIENT://用户账户在其他设备登录，本机会被踢掉线
			DialogAlert.show(KitActivity.getTopActivity(), "您的账号在其它设备登录！", null, null, "我知道了");
			break;
		}
	}

	//------------------ 会话列表操作监听 -------------------

	@Override
	public boolean onConversationPortraitClick(Context context, Conversation.ConversationType conversationType, String targetId)
	{
		return false;
	}

	@Override
	public boolean onConversationPortraitLongClick(Context context, Conversation.ConversationType conversationType, String targetId)
	{
		return false;
	}

	@Override
	public boolean onConversationLongClick(Context context, View view, UIConversation uiConversation)
	{
		return false;
	}

	@Override
	public boolean onConversationClick(Context context, View view, UIConversation uiConversation)
	{
		return false;
	}

	//------------------ 会话界面操作监听 -------------------

	@Override
	public boolean onUserPortraitClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String targetId)
	{
		return false;
	}

	@Override
	public boolean onUserPortraitLongClick(Context context, Conversation.ConversationType conversationType, UserInfo userInfo, String targetId)
	{
		return false;
	}

	@Override
	public boolean onMessageClick(final Context context, View view, Message message)
	{
		return false;
	}

	@Override
	public boolean onMessageLinkClick(Context context, String s, Message message)
	{
		if (message.getContent() instanceof TextMessage)
			return true;
		return false;
	}

	@Override
	public boolean onMessageLongClick(Context context, View view, Message message)
	{
		return false;
	}

	//------------------ 未读消息数监听器 -------------------

	public interface OnUnReadMessageObserver
	{
		void onCountChanged(int count);
	}

	private List<OnUnReadMessageObserver> mUnReadMessageObservers = new ArrayList<>();
	private int mUnReadMessageCount = 0;

	public void addUnReadMessageCountChangedObserver(OnUnReadMessageObserver observer)
	{
		mUnReadMessageObservers.add(observer);
		observer.onCountChanged(mUnReadMessageCount);
	}

	public void removeUnReadMessageCountChangedObserver(OnUnReadMessageObserver observer)
	{
		mUnReadMessageObservers.remove(observer);
	}

	@Override
	public void onCountChanged(int count)
	{
		LOG.v("unread count : " + count);
		mUnReadMessageCount = count;
		for (OnUnReadMessageObserver observer : mUnReadMessageObservers)
		{
			observer.onCountChanged(count);
		}
	}

	//------------------ 其它 -------------------

	public static void share(final Context context, final BoardView board, String shareUrl)
	{
		DialogSelectFriendToShare.show(context, App.userId(), shareUrl, new DialogSelectFriendToShare.OnListener()
		{
			@Override
			public void onSelect(List<SUser> selectedList)
			{
				if (selectedList.size() > 0)
				{
					board.genAnswer(Color.WHITE);
					for (SUser user : selectedList)
					{
						SendImageManager.getInstance().sendImages(Conversation.ConversationType.PRIVATE, user.id, Collections.singletonList(Uri.parse("file://" + BoardView.getAnswerFile(board.getDir()).toString())), true);
					}
//					FsUtils.delete(BoardView.getAnswerFile(board.getDir()));
					LOG.toast(context, "已发送");
				}
			}
		});
	}

}
