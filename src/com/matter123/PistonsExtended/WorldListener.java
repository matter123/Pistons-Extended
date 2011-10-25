/*this file is part of PistonsExtended.
 * 
 *this file determines if i should keep a chunk from unloading
 *
 *this file is used for bukkit a minecraft server modification for more details see
 *http://bukkit.org 
 */

/*
 *@author matter123
 */
package com.matter123.PistonsExtended;

import org.bukkit.Chunk;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.world.ChunkUnloadEvent;

public class WorldListener extends org.bukkit.event.world.WorldListener{

	PistonsExtended plugin;
	
	public WorldListener(PistonsExtended plugin) {
		super();
		this.plugin = plugin;
	}
	@Override
	public void onChunkUnload(ChunkUnloadEvent event) {
		if(event.isCancelled())return;
		if (plugin.getconfig().getload()) {
			Chunk c=event.getChunk();
			boolean keeploaded=false;
			exit:
				for(int x=0;x<16;x++) {
					for(int z=0;z<16;z++) {
						for(int y=0;x<128;y++) {
							if(isPiston(c.getBlock(x, y, z))){
								keeploaded=true;
								break exit;
							}
						}
					}
				}
			if(keeploaded){
				event.setCancelled(true);
			}
		}
	}
	private boolean isPiston(Block block) {
		return block.getType()==Material.PISTON_BASE||block.getType()==Material.PISTON_STICKY_BASE;
	}

}
