/*this file is part of PistonsExtended.
 * 
 *this file determines what happens when you make a "dupe" piston for more details see
 *http://forums.bukkit.org/threads/fun-mech-pistonsextended-v1-4-0-add-extra-functionality-to-pistons-1000.25129/
 *
 *this file is used for bukkit a minecraft server modification for more details see
 *http://bukkit.org 
 *
 */
package com.matter123.PistonsExtended;

import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
/*
 * @author matter123
 */
public class Duper extends Module{
	public Duper(PistonsExtended plugin) {
		super(plugin, "dupe");
	}

	Server server;
	@Override
	public boolean Register(PluginManager pm) {
		this.server=pm.getPlugin("PistonsExtended").getServer();
		pm.registerEvent(Type.BLOCK_PISTON_EXTEND, new innerblocklistener(), Priority.Highest, pm.getPlugin("PistonsExtended"));
		return true;
	}
	private class innerblocklistener extends BlockListener {

		@Override
		public void onBlockPistonExtend(BlockPistonExtendEvent event) {
			if((!plugin.getRegistar().isRegisterd(event.getBlock()))&&plugin.getconfig().useSigns()) {
				Sign s = null;
				out:
				for(int x=-1;x<2;x++) {
					for(int y=-1;y<2;y++) {
						for(int z=-1;z<2;z++) {
							if(this.isSign(event.getBlock().getRelative(x, y, z))) {
								s=(Sign) event.getBlock().getRelative(x, y, z).getState();
								break out;
							}
						}
					}
				}
				if(s==null)return;
				int id=getID(s.getLines());
				if(event.getBlock().getRelative(event.getDirection()).getTypeId()==0) {
					if(id<256) {
						event.getBlock().getRelative(event.getDirection()).setTypeId(id);
					}else {
						event.getBlock().getWorld().dropItemNaturally(event.getBlock().getRelative(event.getDirection()).getLocation(), new ItemStack(id,1));
					}
				}
			}else if(plugin.getconfig().useActivation()){//rewrite to follow crush format
				if(!plugin.getRegistar().getSignContents(event.getBlock())[0].equalsIgnoreCase("dupe"))return;//not my problem
				int id=getID(plugin.getRegistar().getSignContents(event.getBlock()));
				if(event.getBlock().getRelative(event.getDirection()).getTypeId()==0) {
					if(id<256) {
						event.getBlock().getRelative(event.getDirection()).setTypeId(id);
					}else {
						event.getBlock().getWorld().dropItemNaturally(event.getBlock().getRelative(event.getDirection()).getLocation(), new ItemStack(id,1));
					}
				}
			}
		}
		private int getID(String[] sign) {
			ArrayList<Integer>ids=new ArrayList<Integer>();
			String[]ids2=sign[1].split(",");
			String[]ids3=sign[2].split(",");
			String[]ids4=sign[3].split(",");
			for(String id:ids2) {
				int id2=getID(id);
				if(!(id2==0)) {
					ids.add(id2);
				}
			}
			for(String id:ids3) {
				int id2=getID(id);
				if(!(id2==0)) {
					ids.add(id2);
				}
			}
			for(String id:ids4) {
				int id2=getID(id);
				if(!(id2==0)) {
					ids.add(id2);
				}
			}
			if(ids.size()==0)return 0;
			return ids.get((int) (Math.random()*ids.size()));
		}
		private int getID(String id) {
			if(id.equals(""))return 0;
			return Integer.parseInt(id);
		}
		private boolean isSign(Block sign) {
			if(sign.getType()==Material.WALL_SIGN||sign.getType()==Material.SIGN_POST) {//am i actually a sign?
				Sign s=(Sign) sign.getState();
				String[] lines=s.getLines();
				if(lines[0].equalsIgnoreCase("[dupe]"))return true;
			}
			return false;
		}
	}
}
