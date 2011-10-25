/*this file is part of PistonsExtended.
 * 
 *this file determines what happens when you make a "crush" piston for more details see
 *http://forums.bukkit.org/threads/fun-mech-pistonsextended-v1-4-0-add-extra-functionality-to-pistons-1000.25129/
 *
 *this file is used for bukkit a minecraft server modification for more details see
 *http://bukkit.org 
 *
 *@author matter123
 */
package com.matter123.PistonsExtended;

import java.util.ArrayList;
import java.util.Collection;

import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Chest;
import org.bukkit.block.Sign;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.PluginManager;

public class Crusher extends Module{

	public Crusher(PistonsExtended plugin) {
		super(plugin, "crush");
		// TODO Auto-generated constructor stub
	}
	Server server;
	@Override
	public boolean Register(PluginManager pm) {
		this.server=pm.getPlugin("PistonsExtended").getServer();
		pm.registerEvent(Type.BLOCK_PHYSICS, new innerblocklistener(), Priority.Highest, pm.getPlugin("PistonsExtended"));
		pm.registerEvent(Type.PLAYER_INTERACT, new innerplayerlistener(), Priority.Highest, pm.getPlugin("PistonsExtended"));
		return true;

	}
	public class innerblocklistener extends BlockListener {

		@Override
		public void onBlockPhysics(BlockPhysicsEvent event) {
			if(isPowerdPiston(event.getBlock())) {
				BlockFace dir=getDirection(event.getBlock());
				if(plugin.getRegistar().isRegisterd(event.getBlock())) {
					String[] lines=plugin.getRegistar().getSignContents(event.getBlock());
					if(!lines[0].equalsIgnoreCase("[crush]"))return;
					crush(event.getBlock(),dir,lines);
				}else if(plugin.getconfig().useSigns()){
					Sign s = null;
					out:
					for(int x=-1;x<2;x++) {
						for(int y=-1;y<2;y++) {
							for(int z=-1;z<2;z++) {
								if(isSign(event.getBlock().getRelative(x, y, z))) {
									s=(Sign) event.getBlock().getRelative(x, y, z).getState();
									break out;
								}
							}
						}
					}
					if(s==null)return;
					String[] lines=s.getLines();
					if(!lines[0].equalsIgnoreCase("[crush]"))return;
					crush(event.getBlock(),dir,lines);
				}
			}
		}
		
		}
		private boolean isPowerdPiston(Block block) {
			if(block.getTypeId()==33){
				if(block.isBlockPowered())return true;
				if(block.isBlockIndirectlyPowered())return true;
			}
			return false;
		}
		public void crush(Block block, BlockFace dir, String[] lines) {
			if(block.getRelative(dir).getRelative(dir).getTypeId()==7||block.getRelative(dir).getRelative(dir).getTypeId()==49) {
				try {
					if(this.canCrush(block.getRelative(dir), lines)) {
						ItemStack i=new ItemStack(block.getRelative(dir).getType(),1,block.getRelative(dir).getData());
						block.getRelative(dir).setTypeId(0);
						block.getWorld().dropItemNaturally(block.getRelative(dir).getLocation(), i);
					}
				} catch (FormatException e) {
					System.out.println("check the sign near the piston at "+block.getRelative(dir).toString());
				}
			}else if(block.getRelative(dir).getRelative(dir).getTypeId()==54&&plugin.getconfig().canusechestcrush()) {
				Chest c=(Chest) block.getRelative(dir).getRelative(dir).getState();
				try {
					if(this.canCrush(block.getRelative(dir), lines)) {
						ItemStack i=new ItemStack(block.getRelative(dir).getType(),1,block.getRelative(dir).getData());
						block.getRelative(dir).setTypeId(0);
						c.getInventory().addItem(i);
					}
				} catch (FormatException e) {
					System.out.println("check the sign near the piston at "+block.toString());
				}
			}
		}
		private BlockFace getDirection(Block block) {
			if(block.getState().getData().equals(new MaterialData(Material.PISTON_BASE,(byte) 0)))return BlockFace.DOWN;
			if(block.getState().getData().equals(new MaterialData(Material.PISTON_BASE,(byte) 1)))return BlockFace.UP;
			if(block.getState().getData().equals(new MaterialData(Material.PISTON_BASE,(byte) 2)))return BlockFace.EAST;
			if(block.getState().getData().equals(new MaterialData(Material.PISTON_BASE,(byte) 3)))return BlockFace.WEST;
			if(block.getState().getData().equals(new MaterialData(Material.PISTON_BASE,(byte) 4)))return BlockFace.NORTH;
			if(block.getState().getData().equals(new MaterialData(Material.PISTON_BASE,(byte) 5)))return BlockFace.SOUTH;
			if(block.getState().getData().equals(new MaterialData(Material.PISTON_BASE,(byte) 8)))return BlockFace.DOWN;
			if(block.getState().getData().equals(new MaterialData(Material.PISTON_BASE,(byte) 9)))return BlockFace.UP;
			if(block.getState().getData().equals(new MaterialData(Material.PISTON_BASE,(byte) 10)))return BlockFace.EAST;
			if(block.getState().getData().equals(new MaterialData(Material.PISTON_BASE,(byte) 11)))return BlockFace.WEST;
			if(block.getState().getData().equals(new MaterialData(Material.PISTON_BASE,(byte) 12)))return BlockFace.NORTH;
			if(block.getState().getData().equals(new MaterialData(Material.PISTON_BASE,(byte) 13)))return BlockFace.SOUTH;
			System.out.println(block.getState().getData());
			return null;
		}

		private boolean isSign(Block sign) {
			if(sign.getType()==Material.WALL_SIGN||sign.getType()==Material.SIGN_POST) {//am i actually a sign?
				Sign s=(Sign) sign.getState();
				String[] lines=s.getLines();
				if(lines[0].equalsIgnoreCase("[Crush]"))return true;
			}
			return false;
		}
	protected boolean canCrush(Block block,String[] lines) throws FormatException {
		if(block.getType()==Material.PISTON_EXTENSION||block.getType()==Material.PISTON_MOVING_PIECE)return false;
		if(lines[1].equalsIgnoreCase("[NOT]")&&lines[2].equalsIgnoreCase("3"))throw new FormatException();
		if(lines[1].equalsIgnoreCase("[NOT]")&&lines[3].equalsIgnoreCase("3"))throw new FormatException();
		if(lines[2].equalsIgnoreCase("[NOT]")&&lines[3].equalsIgnoreCase("3"))throw new FormatException();
		try {
			if((lines[1]=="")&&(lines[2]=="")&&(lines[3]==""))return true;
			if(lines[1].contains("[NOT]")) {
				ArrayList<Integer> badids=new ArrayList<Integer>();
				if(!lines[2].equals(""))
					for(String id:lines[2].split(",")) {
						if(!id.contains("-"))
						badids.add(Integer.parseInt(id));
						else badids.addAll(getfrom(id));
					}
				if(!lines[3].equals(""))
					for(String id:lines[3].split(",")) {
						if(!id.contains("-"))
						badids.add(Integer.parseInt(id));
						else badids.addAll(getfrom(id));
					}
				return !badids.contains(block.getTypeId());
			}else if(lines[2].contains("[NOT]")) {
				ArrayList<Integer> goodids=new ArrayList<Integer>();
				ArrayList<Integer> badids=new ArrayList<Integer>();
				if(!lines[3].equals(""))
					for(String id:lines[3].split(",")) {
						if(!id.contains("-"))
						badids.add(Integer.parseInt(id));
						else badids.addAll(getfrom(id));
					}
				if(!lines[1].equals(""))
					for(String id:lines[1].split(",")) {
						if(!id.contains("-"))
						goodids.add(Integer.parseInt(id));
						else goodids.addAll(getfrom(id));
					}
				return goodids.contains(block.getTypeId())&&!badids.contains(block.getTypeId());
			}else {
				ArrayList<Integer> goodids=new ArrayList<Integer>();
				if(!lines[3].equals(""))
					for(String id:lines[3].split(",")) {
						if(!id.contains("-"))
						goodids.add(Integer.parseInt(id));
						else goodids.addAll(getfrom(id));
					}
				if(!lines[2].equals(""))
					for(String id:lines[2].split(",")) {
						if(!id.contains("-"))
						goodids.add(Integer.parseInt(id));
						else goodids.addAll(getfrom(id));
					}
				if(!lines[1].equals(""))
					for(String id:lines[1].split(",")) {
						if(!id.contains("-"))
						goodids.add(Integer.parseInt(id));
						else goodids.addAll(getfrom(id));
					}
				return goodids.contains(block.getTypeId());
			}
		} catch (Exception e) {
			if(plugin.getconfig().isdebug())e.printStackTrace();
			throw new FormatException();
		}
	}
	private Collection<? extends Integer> getfrom(String id) {
		int start=Integer.parseInt(id.split("-")[0]);
		int end=Integer.parseInt(id.split("-")[1]);
		ArrayList<Integer> ids=new ArrayList<Integer>();
		for(;start<=end;start++) {
			ids.add(start);
		}
		return ids;
	}
	public class innerplayerlistener extends PlayerListener{

		@Override
		public void onPlayerInteract(PlayerInteractEvent event) {
			if(!plugin.getconfig().cantogglesticky());
			if(event.getAction()==Action.LEFT_CLICK_BLOCK&&(event.getPlayer().getTargetBlock(null, 0).getType()==Material.PISTON_BASE||event.getPlayer().getTargetBlock(null, 0).getType()==Material.PISTON_STICKY_BASE)) {
				if(!(event.getPlayer().getItemInHand().getType()==Material.SLIME_BALL)) {
					return;
				}
				if(event.getPlayer().getTargetBlock(null, 0).isBlockPowered()||event.getPlayer().getTargetBlock(null, 0).isBlockIndirectlyPowered()) {
					event.getPlayer().sendMessage("You need to power down the piston to toggling the stickiness");
				}
				Byte Data;
				Data=event.getPlayer().getTargetBlock(null, 0).getData();
				if(event.getPlayer().getTargetBlock(null, 0).getType()==Material.PISTON_BASE) {
					event.getPlayer().getTargetBlock(null, 0).setTypeIdAndData(Material.PISTON_STICKY_BASE.getId(), Data, false);
					return;
				}
				
				if(event.getPlayer().getTargetBlock(null, 0).getType()==Material.PISTON_STICKY_BASE) {
					event.getPlayer().getTargetBlock(null, 0).setTypeIdAndData(Material.PISTON_BASE.getId(), Data, false);
				}
			}
		}
		
	}
}
