/*this file is part of PistonsExtended.
 * 
 *this file determines what happens when you make a "pusher" piston for more details see
 *http://forums.bukkit.org/threads/fun-mech-pistonsextended-v1-4-0-add-extra-functionality-to-pistons-1000.25129/
 *
 *this file is used for bukkit a minecraft server modification for more details see
 *http://bukkit.org 
 *
 *@author matter123
 */
package com.matter123.PistonsExtended;

import java.io.File;

import org.bukkit.Material;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.util.config.Configuration;

@SuppressWarnings("deprecation")
public class Pusher extends Module {

	Configuration parts;
	public Pusher(PistonsExtended plugin) {
		super(plugin, "thing that is longer then 15 charecters");
		parts=new Configuration(new File("plugins/PistonsExtended/pistons.yml"));
	}
	public class innerblocklistener extends BlockListener {

		@SuppressWarnings("unused")
		@Override
		public void onBlockPhysics(BlockPhysicsEvent event) {
			if(!plugin.getconfig().canpush())return;
			if (event.getBlock().getType().equals(Material.PISTON_BASE)&&(event.getBlock().isBlockPowered()||event.getBlock().isBlockIndirectlyPowered())) {
				int pushsize=parts.getInt(event.getBlock().getWorld().getName()+"."+event.getBlock().getX()+"."+event.getBlock().getY()+"."+event.getBlock().getZ(), -1);
				
			}
		}
		
	}
	public class innerplayerlistener extends PlayerListener {

		@Override
		public void onPlayerInteract(PlayerInteractEvent event) {
			if(event.getAction()==Action.RIGHT_CLICK_BLOCK) {
				if(event.getPlayer().getItemInHand().getType()==Material.IRON_INGOT) {
					event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount()-1);
					int pushsize=parts.getInt(event.getPlayer().getTargetBlock(null, 0).getWorld().getName()+"."+event.getPlayer().getTargetBlock(null, 0).getX()+"."+event.getPlayer().getTargetBlock(null, 0).getY()+"."+event.getPlayer().getTargetBlock(null, 0).getZ(), -1);
					if(pushsize==-1)return;
					if(pushsize==1) {
						parts=new Configuration(new File("plugins/PistonsExtended/pistons.yml"));
						parts.load();
						parts.setProperty(event.getPlayer().getTargetBlock(null, 0).getWorld().getName()+"."+event.getPlayer().getTargetBlock(null, 0).getX()+"."+event.getPlayer().getTargetBlock(null, 0).getY()+"."+event.getPlayer().getTargetBlock(null, 0).getZ(), -1);
						parts.save();
					}else {
						parts=new Configuration(new File("plugins/PistonsExtended/pistons.yml"));
						parts.load();
						parts.setProperty(event.getPlayer().getTargetBlock(null, 0).getWorld().getName()+"."+event.getPlayer().getTargetBlock(null, 0).getX()+"."+event.getPlayer().getTargetBlock(null, 0).getY()+"."+event.getPlayer().getTargetBlock(null, 0).getZ(), pushsize-1);
						parts.save();
					}
				}
			}
			if(event.getAction()==Action.LEFT_CLICK_BLOCK) {
				if(event.getPlayer().getItemInHand().getType()==Material.IRON_INGOT) {
					event.getPlayer().getItemInHand().setAmount(event.getPlayer().getItemInHand().getAmount()-1);
					int pushsize=parts.getInt(event.getPlayer().getTargetBlock(null, 0).getWorld().getName()+"."+event.getPlayer().getTargetBlock(null, 0).getX()+"."+event.getPlayer().getTargetBlock(null, 0).getY()+"."+event.getPlayer().getTargetBlock(null, 0).getZ(), -1);
					if(pushsize==plugin.getconfig().maxpushlength())return;
					if(pushsize==-1) {
						parts=new Configuration(new File("plugins/PistonsExtended/pistons.yml"));
						parts.load();
						parts.setProperty(event.getPlayer().getTargetBlock(null, 0).getWorld().getName()+"."+event.getPlayer().getTargetBlock(null, 0).getX()+"."+event.getPlayer().getTargetBlock(null, 0).getY()+"."+event.getPlayer().getTargetBlock(null, 0).getZ(), 1);
						parts.save();
					}else {
						parts=new Configuration(new File("plugins/PistonsExtended/pistons.yml"));
						parts.load();
						parts.setProperty(event.getPlayer().getTargetBlock(null, 0).getWorld().getName()+"."+event.getPlayer().getTargetBlock(null, 0).getX()+"."+event.getPlayer().getTargetBlock(null, 0).getY()+"."+event.getPlayer().getTargetBlock(null, 0).getZ(), pushsize+1);
						parts.save();
					}
				}
			}
		}
		
	}

}
