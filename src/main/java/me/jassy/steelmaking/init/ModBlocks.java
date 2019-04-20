package me.jassy.steelmaking.init;

import java.util.ArrayList;
import java.util.List;

import me.jassy.steelmaking.blocks.BlockBase;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class ModBlocks {

	public static final List<Block> BLOCKS = new ArrayList<Block>();
	
	//Blocks
	public static final Block STEEL_BLOCK = new BlockBase("steel_block", Material.IRON);
	
	//Crusher
	public static final Block CRUSHER = new BlockBase("crusher", Material.IRON);
}
