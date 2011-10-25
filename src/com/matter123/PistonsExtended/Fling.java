package com.matter123.PistonsExtended;

import java.util.List;

import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.plugin.PluginManager;

public class Fling extends Module{

	@Override
	public boolean Register(PluginManager pm) {
		pm.registerEvent(Type.BLOCK_PISTON_EXTEND, new InnerBlockListener(), Priority.Highest, plugin);
		return false;
	}
	public Fling(PistonsExtended plugin) {
		super(plugin, null);
		// TODO Auto-generated constructor stub
	}
	private class InnerBlockListener extends BlockListener {//try to see if i can loop through all directions

		@Override
		public void onBlockPistonExtend(BlockPistonExtendEvent event) {
			if(event.getDirection()==BlockFace.UP) {
				List<Entity> entities=event.getBlock().getWorld().getEntities();
				for(final Entity e:entities) {
					if(e.getLocation().getBlock().equals(event.getBlock().getRelative(BlockFace.UP))) {
						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

							@Override
							public void run() {
								e.setVelocity(plugin.getconfig().getUP());
							}
							
						}, 3);
					}
				}
			}else if(event.getDirection()==BlockFace.DOWN) {
				List<Entity> entities=event.getBlock().getWorld().getEntities();
				for(final Entity e:entities) {
					if(e.getLocation().getBlock().equals(event.getBlock().getRelative(BlockFace.DOWN))) {
						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

							@Override
							public void run() {
								e.setVelocity(plugin.getconfig().getDOWN());
							}
							
						}, 3);
					}
				}
			}else if(event.getDirection()==BlockFace.NORTH) {
				List<Entity> entities=event.getBlock().getWorld().getEntities();
				for(final Entity e:entities) {
					if(e.getLocation().getBlock().equals(event.getBlock().getRelative(BlockFace.NORTH))) {
						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

							@Override
							public void run() {
								e.setVelocity(plugin.getconfig().getNORTH());
							}
							
						}, 3);
					}
				}
			}else if(event.getDirection()==BlockFace.EAST) {
				List<Entity> entities=event.getBlock().getWorld().getEntities();
				for(final Entity e:entities) {
					if(e.getLocation().getBlock().equals(event.getBlock().getRelative(BlockFace.EAST))) {
						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

							@Override
							public void run() {
								e.setVelocity(plugin.getconfig().getEAST());
							}
							
						}, 3);
					}
				}
			}else if(event.getDirection()==BlockFace.SOUTH) {
				List<Entity> entities=event.getBlock().getWorld().getEntities();
				for(final Entity e:entities) {
					if(e.getLocation().getBlock().equals(event.getBlock().getRelative(BlockFace.SOUTH))) {
						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

							@Override
							public void run() {
								e.setVelocity(plugin.getconfig().getSOUTH());
							}
							
						}, 3);
					}
				}
			}else if(event.getDirection()==BlockFace.WEST) {
				List<Entity> entities=event.getBlock().getWorld().getEntities();
				for(final Entity e:entities) {
					if(e.getLocation().getBlock().equals(event.getBlock().getRelative(BlockFace.WEST))) {
						plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {

							@Override
							public void run() {
								e.setVelocity(plugin.getconfig().getWEST());
							}
							
						}, 3);
					}
				}
			}
		}
		
	}

}
