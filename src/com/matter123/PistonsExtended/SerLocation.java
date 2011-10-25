package com.matter123.PistonsExtended;

import java.io.Serializable;

import org.bukkit.Location;
import org.bukkit.Server;

public final class SerLocation implements Serializable{
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((world == null) ? 0 : world.hashCode());
		result = prime * result + x;
		result = prime * result + y;
		result = prime * result + z;
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SerLocation other = (SerLocation) obj;
		if (world == null) {
			if (other.world != null)
				return false;
		} else if (!world.equals(other.world))
			return false;
		if (x != other.x)
			return false;
		if (y != other.y)
			return false;
		if (z != other.z)
			return false;
		return true;
	}
	/**
	 * 
	 */
	private static final long serialVersionUID = 7042362701366464561L;
	private final int x;
	private final int y;
	private final int z;
	private final String world;
	public SerLocation(Location l) {
		this.x=l.getBlockX();this.y=l.getBlockY();this.z=l.getBlockZ();this.world=l.getWorld().getName();
	}
	public Location toLocation(Server s) {
		return new Location(s.getWorld(world),x,y,z);
	}
}
