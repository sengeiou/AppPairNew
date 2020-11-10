package com.lys.utils;

import com.lys.App;
import com.lys.base.utils.LOG;
import com.lys.plugin.LysBoardPlugin;
import com.lys.plugin.LysCameraPlugin;
import com.lys.plugin.LysImagePlugin;
import com.lys.plugin.LysRecordPlugin;
import com.lys.plugin.LysTaskPlugin;
import com.lys.plugin.LysTopicPlugin;
import com.lys.plugin.LysVideoPlugin;

import java.util.ArrayList;
import java.util.List;

import io.rong.imkit.DefaultExtensionModule;
import io.rong.imkit.emoticon.IEmoticonTab;
import io.rong.imkit.plugin.IPluginModule;
import io.rong.imlib.model.Conversation;

public class LysExtensionModule extends DefaultExtensionModule
{
	@Override
	public List<IPluginModule> getPluginModules(Conversation.ConversationType conversationType)
	{
//		List<IPluginModule> pluginModules = super.getPluginModules(conversationType);
//		for (IPluginModule module : pluginModules)
//		{
//			if (module instanceof ImagePlugin)
//			{
//				pluginModules.remove(module);
//				break;
//			}
//		}
		List<IPluginModule> pluginModules = new ArrayList();
		pluginModules.add(new LysImagePlugin());
		pluginModules.add(new LysCameraPlugin());
		pluginModules.add(new LysVideoPlugin());
		pluginModules.add(new LysBoardPlugin());
		pluginModules.add(new LysRecordPlugin());
		if (!App.isStudent())
		{
			pluginModules.add(new LysTopicPlugin());
			pluginModules.add(new LysTaskPlugin());
		}
		pluginModules.add(super.getPluginModules(conversationType).get(1));
		LOG.v("pluginModules.size = " + pluginModules.size());
		for (IPluginModule module : pluginModules)
		{
			LOG.v(String.format("        %s", module.getClass().getName()));
		}
		return pluginModules;
	}

	@Override
	public List<IEmoticonTab> getEmoticonTabs()
	{
		List<IEmoticonTab> emoticonTabs = super.getEmoticonTabs();
		LOG.v("emoticonTabs.size = " + emoticonTabs.size());
		for (IEmoticonTab module : emoticonTabs)
		{
			LOG.v(String.format("        %s", module.getClass().getName()));
		}
		return emoticonTabs;
	}
}
