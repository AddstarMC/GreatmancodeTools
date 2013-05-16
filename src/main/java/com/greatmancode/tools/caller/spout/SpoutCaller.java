/*
 * This file is part of GreatmancodeTools.
 *
 * Copyright (c) 2013-2013, Greatman <http://github.com/greatman/>
 *
 * GreatmancodeTools is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * GreatmancodeTools is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with GreatmancodeTools.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.greatmancode.tools.caller.spout;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

import com.greatmancode.tools.commands.interfaces.CommandReceiver;
import com.greatmancode.tools.commands.spout.SpoutCommandReceiver;
import com.greatmancode.tools.interfaces.Caller;
import com.greatmancode.tools.interfaces.Loader;
import com.greatmancode.tools.interfaces.SpoutLoader;

import org.spout.api.Server;
import org.spout.api.chat.ChatArguments;
import org.spout.api.entity.Player;
import org.spout.api.scheduler.TaskPriority;

/**
 * Server caller for Spout
 * @author greatman
 */
public class SpoutCaller implements Caller {
	private final SpoutLoader loader;

	/**
	 * Load the Spout Caller
	 * @param loader The {@link SpoutLoader}
	 */
	public SpoutCaller(Loader loader) {
		this.loader = (SpoutLoader) loader;
	}

	@Override
	public void disablePlugin() {
		loader.getPluginLoader().disablePlugin(loader);
	}

	@Override
	public boolean checkPermission(String playerName, String perm) {
		boolean result;
		Player p = loader.getEngine().getPlayer(playerName, true);
		if (p != null) {
			result = p.hasPermission(perm);
		} else {
			// It's the console
			result = true;
		}
		return result;
	}

	@Override
	public void sendMessage(String playerName, String message) {
		Player p = loader.getEngine().getPlayer(playerName, true);
		if (p != null) {
			p.sendMessage(ChatArguments.fromFormatString(message));
		} else {
			loader.getEngine().getCommandSource().sendMessage(ChatArguments.fromFormatString(message));
		}
	}

	@Override
	public String getPlayerWorld(String playerName) {
		String worldName = "";
		Player p = loader.getEngine().getPlayer(playerName, true);
		if (p != null) {
			worldName = p.getWorld().getName();
		}
		return worldName;
	}

	@Override
	public boolean isOnline(String playerName) {
		return loader.getEngine().getPlayer(playerName, true) != null;
	}

	@Override
	public String addColor(String str) {
		// Useless with Spout
		return null;
	}

	@Override
	public boolean worldExist(String worldName) {
		return loader.getEngine().getWorld(worldName) != null;
	}

	@Override
	public String getDefaultWorld() {
		return loader.getEngine().getWorlds().iterator().next().getName();
	}

	@Override
	public File getDataFolder() {
		return loader.getDataFolder();
	}

	@Override
	public int schedule(Runnable entry, long firstStart, long repeating) {
		return schedule(entry, firstStart, repeating, false);
	}

	@Override
	public int schedule(Runnable entry, long firstStart, long repeating, boolean async) {
		//TODO: Spout don't have the Async anymore for some reasons..
		//if (!async) {
		return loader.getEngine().getScheduler().scheduleSyncRepeatingTask(loader, entry, TimeUnit.MILLISECONDS.convert(firstStart, TimeUnit.SECONDS), TimeUnit.MILLISECONDS.convert(repeating, TimeUnit.SECONDS), TaskPriority.NORMAL).getTaskId();
		//} else {
		//return loader.getEngine().getScheduler().scheduleAsyncRepeatingTask(loader, entry, TimeUnit.MILLISECONDS.convert(firstStart, TimeUnit.SECONDS), TimeUnit.MILLISECONDS.convert(repeating, TimeUnit.SECONDS), TaskPriority.NORMAL);
		//}
	}

	@Override
	public List<String> getOnlinePlayers() {
		List<String> list = new ArrayList<String>();
		Player[] pList = ((Server) loader.getEngine()).getOnlinePlayers();
		for (Player p : pList) {
			list.add(p.getName());
		}
		return list;
	}

	@Override
	public void cancelSchedule(int id) {
		loader.getEngine().getScheduler().cancelTask(id);
	}

	@Override
	public int delay(Runnable entry, long start) {
		return delay(entry, start, false);
	}

	@Override
	public int delay(Runnable entry, long start, boolean async) {
		if (!async) {
			return loader.getEngine().getScheduler().scheduleSyncDelayedTask(loader, entry, TimeUnit.MILLISECONDS.convert(start, TimeUnit.SECONDS), TaskPriority.NORMAL).getTaskId();
		} else {
			return loader.getEngine().getScheduler().scheduleAsyncDelayedTask(loader, entry, TimeUnit.MILLISECONDS.convert(start, TimeUnit.SECONDS), TaskPriority.NORMAL).getTaskId();
		}
	}

	@Override
	public void addCommand(String name, String help, CommandReceiver manager) {
		if (manager instanceof SpoutCommandReceiver) {
			loader.getEngine().getRootCommand().addSubCommand(loader, name).setHelp(help).setExecutor((SpoutCommandReceiver) manager);
		}
	}

	@Override
	public String getServerVersion() {
		return "SpoutServer " + loader.getEngine().getAPIVersion();
	}

	@Override
	public String getPluginVersion() {
		return loader.getDescription().getVersion();
	}

	@Override
	public boolean isOp(String playerName) {
		// TODO: Hmm... There's not really a OP in Spout. Maybe use a permission flag?
		return false;
	}

	@Override
	public void loadLibrary(String path) {
		loader.loadLibrary(new File(path));
	}

	@Override
	public void registerPermission(String permissionNode) {
		//TODO: Not needed on spout?
	}

	@Override
	public boolean isOnlineMode() {
		return true;
	}

	@Override
	public Logger getLogger() {
		return loader.getLogger();
	}
}
