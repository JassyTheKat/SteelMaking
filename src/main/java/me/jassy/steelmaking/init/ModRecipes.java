package me.jassy.steelmaking.init;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModRecipes {

	public static void init() {
		GameRegistry.addSmelting(ModItems.STEEL_DUST, new ItemStack(ModItems.STEEL_INGOT, 1), 1.5f);
	}
}
