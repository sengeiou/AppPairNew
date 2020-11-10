package com.lys.player;

import tv.danmaku.ijk.media.player.IjkTimedText;

public abstract class VideoPlayerListener
{
	public void onBufferingUpdate(VideoPlayer videoPlayer, int percent)
	{

	}

	public void onCompletion(VideoPlayer videoPlayer)
	{

	}

	public boolean onError(VideoPlayer videoPlayer, int what, int extra)
	{
		return false;
	}

	public boolean onInfo(VideoPlayer videoPlayer, int what, int extra)
	{
		return false;
	}

	public void onPrepared(VideoPlayer videoPlayer)
	{

	}

	public void onSeekComplete(VideoPlayer videoPlayer)
	{

	}

	public void onVideoSizeChanged(VideoPlayer videoPlayer, int width, int height, int sar_num, int sar_den)
	{

	}

	public void onTimedText(VideoPlayer videoPlayer, IjkTimedText timedText)
	{

	}
}