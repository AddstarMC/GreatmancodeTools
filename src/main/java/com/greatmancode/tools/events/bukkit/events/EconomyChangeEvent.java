/**
 * This file is part of GreatmancodeTools.
 *
 * Copyright (c) 2013-2016, Greatman <http://github.com/greatman/>
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
package com.greatmancode.tools.events.bukkit.events;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class EconomyChangeEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private final String account;
    private final double amount;

    public EconomyChangeEvent(String account, double amount) {
        // Balance changes reach us from whatever thread the caller used. Vault
        // has no main-thread requirement, so plugins that batch payments fire
        // these from async tasks.
        super(!Bukkit.isPrimaryThread());
        this.account = account;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public String getAccount() {
        return account;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
