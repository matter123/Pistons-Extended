package com.matter123.PistonsExtended;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionDefault;
import org.bukkit.plugin.PluginManager;

public class Registrar extends Module{
	public Registrar(PistonsExtended plugin) {
		super(plugin, null);
		// TODO Auto-generated constructor stub
	}

	private HashMap<SerLocation,String[]>registerdblocks=new HashMap<SerLocation,String[]>();
	public boolean isRegisterd(Block block) {
		// TODO Auto-generated method stub
		return this.registerdblocks.containsKey(new SerLocation(block.getLocation()));
	}

	public String[] getSignContents(Block block) {
		// TODO Auto-generated method stub
		return this.registerdblocks.get(new SerLocation(block.getLocation()));
	}
	public void Save() {
		File f=new File("plugins/PistonsExtended/data.dat");
		if(f.exists())f.delete();
		try {
			f.createNewFile();
			ObjectOutputStream os=new ObjectOutputStream(new FileOutputStream(f));
			os.writeObject(registerdblocks);
			os.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public boolean Register(PluginManager pm) {
		pm.registerEvent(Type.PLAYER_INTERACT, new PlayerListener() {

			@Override
			public void onPlayerInteract(PlayerInteractEvent event) {
				if(event.getAction()==Action.RIGHT_CLICK_BLOCK) {
					if(event.getClickedBlock()==null)return;
					if(!(event.getClickedBlock().getTypeId()==33))return;
					Sign s=null;
					out:
						for(int x=-1;x<2;x++) {
							for(int y=-1;y<2;y++) {
								for(int z=-1;z<2;z++) {
									if(isSign(event.getClickedBlock().getRelative(x, y, z))) {
										s=(Sign) event.getClickedBlock().getRelative(x, y, z).getState();
										break out;
									}
								}
							}
						}
					if(s==null) return;
					if(!event.getPlayer().hasPermission(new Permission("PistonsExtended."+s.getLines()[0],PermissionDefault.OP)))return;
					event.getPlayer().sendMessage(ChatColor.YELLOW+"[Pistons Extended] Registering this piston to type \""+s.getLines()[0]+"\"");
					registerdblocks.put(new SerLocation(event.getClickedBlock().getLocation()), s.getLines());
				}
			}

		}, Priority.Highest, plugin);
		return false;
	}

	protected boolean isSign(Block sign) {
		if(sign.getType()==Material.WALL_SIGN||sign.getType()==Material.SIGN_POST) {//am i actually a sign?
			Sign s=(Sign) sign.getState();
			String[] lines=s.getLines();
			for(String str:Module.getNames())
				if(lines[0].equalsIgnoreCase("["+str+"]"))return true;
		}
		return false;
	}

	@SuppressWarnings("unchecked")
	public void Load() {
		File f=new File("/plugins/PistonsExtended/data.dat");
		try {
			ObjectInputStream is=new ObjectInputStream(new FileInputStream(f));
			registerdblocks=(HashMap<SerLocation, String[]>) is.readObject();
			is.close();
		} catch (FileNotFoundException e) {
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
