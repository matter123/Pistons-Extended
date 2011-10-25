/*this file is part of PistonsExtended.
 * 
 *this file determines what happens when you make a "auto" piston for more details see
 *http://forums.bukkit.org/threads/fun-mech-pistonsextended-v1-4-0-add-extra-functionality-to-pistons-1000.25129/
 *
 *this file is used for bukkit a minecraft server modification for more details see
 *http://bukkit.org
 *
 *@author matter123
 */
package com.matter123.PistonsExtended;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Sign;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPhysicsEvent;
import org.bukkit.material.MaterialData;
import org.bukkit.plugin.PluginManager;

public class Auto extends Module {

	public Auto(PistonsExtended plugin) {
		super(plugin, "auto");
	}

	public static ArrayList<Block>currentactive=new ArrayList<Block>();
	@Override
	public boolean Register(PluginManager pm) {
		pm.registerEvent(Type.BLOCK_PHYSICS, new autoblock(), Priority.Highest, pm.getPlugin("PistonsExtended"));
		return true;
	}

	public class autoblock extends BlockListener {

		@Override
		public void onBlockPhysics(BlockPhysicsEvent event) {//here comes all the action
			if(currentactive.contains(event.getBlock()))return;
			if(!(event.getBlock().getType().equals(Material.PISTON_BASE)))return;//is the block a piston
			if((event.getBlock().isBlockPowered()||event.getBlock().isBlockIndirectlyPowered()))return;//is it not powered
			BlockFace dir=this.getFaceofPistonHead(event.getBlock());//get direction of pistons line: 113
			if(event.getBlock().getRelative(dir).getTypeId()!=0||event.getBlock().getRelative(dir).getType()!=Material.PISTON_EXTENSION||event.getBlock().getRelative(dir).getType()!=Material.PISTON_MOVING_PIECE) {//is there something to push
				if(!hasSign(event.getBlock()))return;//do i have a sign? line: 70
				Block lever=this.getLever(event.getBlock());//get my lever line: 89
				if(lever==null)return; //is the lever null
				setPowered(lever,true); //set the lever to powered line: 51
				currentactive.add(event.getBlock());
				plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, new inneroff(event.getBlock(),lever),5);//run a task to turn the lever off
			}
		}
		private void setPowered(Block lever, boolean b) {//change a lever's powered state used in lines: 45,66
			lever.setData((byte) (b ? (lever.getData() | 0x8) : (lever.getData() & ~0x8)),true);

		}
		public class inneroff implements Runnable {

			Block lever;
			private Block piston;
			public inneroff(Block piston,Block lever) {
				super();
				this.lever = lever;
				this.piston=piston;
			}

			@Override
			public void run() {
				//System.out.println("8");
				currentactive.remove(piston);
				setPowered(lever,false);//turn the lever off line: 51
			}

		}
		private boolean hasSign(Block block) { //does this piston have a lever used in line: 41
			BlockFace dir=this.getFaceofPistonHead(block);//get direction of piston line: 90
			//check all 6 sides for a sign
			if(dir!=BlockFace.DOWN&&isSign(block.getRelative(BlockFace.DOWN)))return true;
			if(dir!=BlockFace.EAST&&isSign(block.getRelative(BlockFace.EAST)))return true;
			if(dir!=BlockFace.NORTH&&isSign(block.getRelative(BlockFace.NORTH)))return true;
			if(dir!=BlockFace.SOUTH&&isSign(block.getRelative(BlockFace.SOUTH)))return true;
			if(dir!=BlockFace.UP&&isSign(block.getRelative(BlockFace.UP)))return true;
			if(dir!=BlockFace.WEST&&isSign(block.getRelative(BlockFace.WEST)))return true;
			return false;
		}
		private boolean isSign(Block sign) {//is this block a sign used in lines: 73,74,75,76,77,78
			if(sign.getType()==Material.WALL_SIGN||sign.getType()==Material.SIGN_POST) {//am i actually a sign?
				Sign s=(Sign) sign.getState();
				String[] lines=s.getLines();
				if(lines[0].equalsIgnoreCase("[auto]"))return true;
			}
			return false;
		}
		private Block getLever(Block block) {//get lever used in line: 42
			BlockFace dir=this.getFaceofPistonHead(block);//get direction of piston head line: 113
			if(dir!=BlockFace.DOWN&&isLever(block.getRelative(BlockFace.DOWN)))return  block.getRelative(BlockFace.DOWN);
			if(dir!=BlockFace.EAST&&isLever(block.getRelative(BlockFace.EAST)))return  block.getRelative(BlockFace.EAST);
			if(dir!=BlockFace.NORTH&&isLever(block.getRelative(BlockFace.NORTH)))return  block.getRelative(BlockFace.NORTH);
			if(dir!=BlockFace.SOUTH&&isLever(block.getRelative(BlockFace.SOUTH)))return  block.getRelative(BlockFace.SOUTH);
			if(dir!=BlockFace.UP&&isLever(block.getRelative(BlockFace.UP)))return  block.getRelative(BlockFace.UP);
			if(dir!=BlockFace.WEST&&isLever(block.getRelative(BlockFace.WEST)))return  block.getRelative(BlockFace.WEST);
			return null;
		}
		private boolean isLever(Block lever) {//am i a lever used in lines: 91,92,93,94,95,96
			return lever.getType()==Material.LEVER;
		}
		private BlockFace getFaceofPistonHead(Block block) {//get direction of piston head used in lines: 38,90
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
	}

}
