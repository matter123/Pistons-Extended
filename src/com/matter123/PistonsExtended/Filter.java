package com.matter123.PistonsExtended;

import java.util.List;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockPistonExtendEvent;

public class Filter extends Module{

	public Filter(PistonsExtended plugin) {
		super(plugin, "filter");
		// TODO Auto-generated constructor stub
	}
	public class InnerBlockListener extends BlockListener {

		@Override
		public void onBlockPistonExtend(BlockPistonExtendEvent event) {
			if(plugin.getRegistar().isRegisterd(event.getBlock())) {
				String[]lines=plugin.getRegistar().getSignContents(event.getBlock());
				if(!lines[0].equalsIgnoreCase("[filter]"))return;
				try {
					event.setCancelled(!check(event.getBlocks(),lines));
				} catch (FormatException e) {
					System.out.println("check the sign near the piston at "+event.getBlock().toString());
				}
			}else if(plugin.getconfig().useSigns()){
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
				try {
					event.setCancelled(!check(event.getBlocks(),s.getLines()));
				} catch (FormatException e) {
					System.out.println("check the sign near the piston at "+event.getBlock().toString());
				}
			}
		} 

		private boolean isSign(Block sign) {
			if(sign.getType()==Material.WALL_SIGN||sign.getType()==Material.SIGN_POST) {//am i actually a sign?
				Sign s=(Sign) sign.getState();
				String[] lines=s.getLines();
				if(lines[0].equalsIgnoreCase("[filter]"))return true;
			}
			return false;
		}			// TODO Auto-generated method stub		
	}
	public boolean check(List<Block> blocks, String[] lines) throws FormatException {
		boolean ret=true;
		for(Block b:blocks) {
			if(!plugin.crusher.canCrush(b, lines)) {
				System.out.println("found a bad one"+b);
				ret=false;
				break;
			}
		}
		return ret;
	}


}
