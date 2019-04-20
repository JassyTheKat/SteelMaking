package me.jassy.steelmaking.util.handlers;

import me.jassy.steelmaking.machines.TileEntities.TileEntityCrusher;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class TileEntityHandler {

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntityCrusher.class, "crusher");
	}
	
}
