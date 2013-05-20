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
package com.greatmancode.tools.interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.greatmancode.tools.ServerType;
import com.greatmancode.tools.caller.canary.CanaryCaller;

import net.canarymod.plugin.Plugin;

public class CanaryLoader extends Plugin implements Loader {
	private Common common;
	@Override
	public void onEnable() {
		//Not used
	}

	@Override
	public void onDisable() {
		//Not used
	}

	@Override
	public ServerType getServerType() {
		return ServerType.CANARY;
	}

	@Override
	public boolean enable() {
		CanaryCaller canaryCaller = new CanaryCaller(this);
		InputStream is = this.getClass().getResourceAsStream("/loader.yml");
		BufferedReader br = new BufferedReader(new InputStreamReader(is));
		try {
			String mainClass = br.readLine();
			System.out.println("LINE:" + mainClass);
			mainClass = mainClass.split("main-class:")[1].trim();

			System.out.println("result:" + mainClass);
			Class<?> clazz = Class.forName(mainClass);

			if (Common.class.isAssignableFrom(clazz)) {
				common = (Common) clazz.newInstance();
				common.onEnable(canaryCaller, this.getLogman().getParent());
				return true;
			} else {
				this.getLogman().severe("The class " + mainClass + " is invalid!");
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return false;
		} catch (InstantiationException e) {
			e.printStackTrace();
			return false;
		} catch (IllegalAccessException e) {
			return false;
		}
		return false;
	}

	@Override
	public void disable() {

	}
}
