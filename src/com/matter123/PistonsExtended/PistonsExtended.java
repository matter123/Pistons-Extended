/*this file is part of PistonsExtended.
 * 
 *this file is the main file which bukkit (as defined below) creates a new instance of.
 *
 *this file is used for bukkit a minecraft server modification for more details see
 *http://bukkit.org 
 *
 *@author matter123
 */
package com.matter123.PistonsExtended;

import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.java.JavaPlugin;

public class PistonsExtended extends JavaPlugin {

	Crusher crusher;
	Duper duper;
	Auto auto;
	Registrar r;
	private Config config;
	Filter filter;
	@Override
	public void onDisable() {
		r.Save();
		this.getServer().getLogger().info("[PistonsExtended] v"+this.getDescription().getVersion()+" Disabled");
	}

	@Override
	public void onEnable() {
		this.getServer().getLogger().info("[PistonsExtended] v"+this.getDescription().getVersion()+" by matter123 Enabled");
		config=new Config();
		if(config.canusecrush()) {
			crusher=new Crusher(this);
			crusher.Register(this.getServer().getPluginManager());
			this.getServer().getLogger().info("[PistonsExtended] crush Enabled");
		}
		if(config.candupe()) {
		duper=new Duper(this);
		duper.Register(this.getServer().getPluginManager());
		this.getServer().getLogger().info("[PistonsExtended] dupe Enabled");
		}
		if(config.canauto()) {
			auto=new Auto(this);
			auto.Register(this.getServer().getPluginManager());
			this.getServer().getLogger().info("[PistonsExtended] auto Enabled");
		}
		r=new Registrar(this);
		if(config.useActivation()) {
		r.Load();
		r.Register(this.getServer().getPluginManager());
		this.getServer().getLogger().info("[PistonsExtended] activation Enabled");
		if(config.useSigns())this.getServer().getLogger().info("[PistonsExtended] also using signs");
		}
		//filter=new Filter(this);
		//filter.Register(this.getServer().getPluginManager());
		//this.getServer().getLogger().info("[PistonsExtended] filter Enabled");
		Fling fling=new Fling(this);
		fling.Register(this.getServer().getPluginManager());
		
		if(config.getload()) {
			this.getServer().getPluginManager().registerEvent(Type.CHUNK_UNLOAD, new WorldListener(this), Priority.Highest, this);
			this.getServer().getLogger().info("[PistonsExtended] chunks with pistons are now kept loaded");
		}
	}
	public Config getconfig() {
		return config;
	}
	Registrar getRegistar() {
		return r;
	}

}
