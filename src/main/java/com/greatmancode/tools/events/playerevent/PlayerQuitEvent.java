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
package com.greatmancode.tools.events.playerEvent;

import com.greatmancode.tools.events.Event;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

/**
 * Fired when a player leaves the server.
 *
 * Carries the name and uuid rather than a Player, because by the time a
 * consumer acts on this the player is generally gone and there is nothing
 * left to resolve them from.
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class PlayerQuitEvent extends Event {
    private final String name;
    private final UUID uuid;

    public PlayerQuitEvent(String name, UUID uuid) {
        this.name = name;
        this.uuid = uuid;
    }
}
