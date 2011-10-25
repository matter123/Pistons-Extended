/*this file is part of PistonsExtended.
 * 
 *this file determines what other files can and can not do for more details see
 *http://forums.bukkit.org/threads/fun-mech-pistonsextended-v1-4-0-add-extra-functionality-to-pistons-1000.25129/
 *
 *this file is used for bukkit a minecraft server modification for more details see
 *http://bukkit.org 
 *
 *@author matter123
 */
package com.matter123.PistonsExtended;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.util.Vector;

public class Config {
	YamlConfiguration c;
	File f=new File("plugins/PistonsExtended/config.yml");
	public Config() {
		c=YamlConfiguration.loadConfiguration(f);
		if(!f.exists()) {
			new File(f.getParent()).mkdirs();
			c.set("crush.enabled",true);
			c.set("crush.chest",true);
			c.set("dupe.enabled",true);
			c.set("toggle.sticky",true);
			c.set("toggle.debug",false);
			c.set("pusher.enabled",true);
			c.set("auto.enabled",true);
			c.set("pusher.maxlength","Not working yet");
			c.set("fling.enabled", true);
			//fling start
			c.set("fling.up.x", 0);
			c.set("fling.up.y", 3);
			c.set("fling.up.z", 0);
			c.set("fling.down.x", 0);
			c.set("fling.down.y", -3);
			c.set("fling.down.z", 0);
			c.set("fling.north.x", -3);
			c.set("fling.north.y", 0);
			c.set("fling.north.z", 0);
			c.set("fling.east.x", 0);
			c.set("fling.east.y", 0);
			c.set("fling.east.z", -3);
			c.set("fling.south.x", 3);
			c.set("fling.south.y", 0);
			c.set("fling.south.z", 0);
			c.set("fling.west.x", 0);
			c.set("fling.west.y", 0);
			c.set("fling.west.z", 3);
			//fling end
			c.set("activation.enabled", false);
			c.set("activation.AlsoUseSigns", true);
			c.set("KeepChunksWithPistonsLoaded",false);
			try {
				c.save(f);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public boolean canusechestcrush() {
		return c.getBoolean("crush.chest", true);
	}
	public boolean canusecrush() {
		return c.getBoolean("crush.enabled", true);
	}
	public boolean cantogglesticky() {
		return c.getBoolean("toggle.sticky", true);
	}
	public boolean candupe() {
		return c.getBoolean("dupe.enabled", true);
	}
	public boolean canpush() {
		return c.getBoolean("pusher.enabled", true);
	}
	public int maxpushlength() {
		return c.getInt("pusher.maxlength", 15);
	}
	public boolean isdebug() {
		return c.getBoolean("toggle.debug", false);
	}
	public boolean canauto() {
		return c.getBoolean("auto.enabled", false);
	}
	public boolean getload() {
		return c.getBoolean("KeepChunksWithPistonsLoaded",false);
	}
	public Vector getUP() {//+y
		double x=c.getDouble("fling.up.x", 0);
		double y=c.getDouble("fling.up.y", 3);
		double z=c.getDouble("fling.up.z", 0);
		return new Vector(x,y,z);
	}
	public boolean useActivation() {
		return c.getBoolean("activation.enabled", true);
	}
	public boolean useSigns() {
		return(!this.useActivation())||c.getBoolean("activation.AlsoUseSigns",false);
	}
	public Vector getDOWN() {//-y
		double x=c.getDouble("fling.down.x", 0);
		double y=c.getDouble("fling.down.y", -3);
		double z=c.getDouble("fling.down.z", 0);
		return new Vector(x,y,z);
	}
	public Vector getNORTH() {//-x
		double x=c.getDouble("fling.north.x", -3);
		double y=c.getDouble("fling.north.y", 0);
		double z=c.getDouble("fling.north.z", 0);
		return new Vector(x,y,z);
	}
	public Vector getSOUTH() {//+x
		double x=c.getDouble("fling.south.x", 3);
		double y=c.getDouble("fling.south.y", 0);
		double z=c.getDouble("fling.south.z", 0);
		return new Vector(x,y,z);
	}
	public Vector getEAST() {//-z
		double x=c.getDouble("fling.east.x", 0);
		double y=c.getDouble("fling.east.y", 0);
		double z=c.getDouble("fling.east.z", -3);
		return new Vector(x,y,z);
	}
	public Vector getWEST() {//+z
		double x=c.getDouble("fling.west.x", 0);
		double y=c.getDouble("fling.west.y", 0);
		double z=c.getDouble("fling.west.z", 3);
		return new Vector(x,y,z);
	}
}
