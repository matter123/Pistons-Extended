/*this file is part of PistonsExtended.
 * 
 *this file is the generic interface that all modules use.
 *
 *this file is used for bukkit a minecraft server modification for more details see
 *http://bukkit.org.
 *
 *@author matter123
 */
package com.matter123.PistonsExtended;

import java.util.HashSet;

import org.bukkit.plugin.PluginManager;

public abstract class Module {
	private final static HashSet<String> names=new HashSet<String>();
	PistonsExtended plugin;
	String name;

	public Module(PistonsExtended plugin,String name) {
		this.name=name;
		names.add(name);
		this.plugin = plugin;
	}

	public boolean Register(PluginManager pm) {
		// TODO Auto-generated method stub
		return false;
	}

	public static HashSet<String> getNames() {
		return names;
	}
}