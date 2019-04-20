package me.jassy.steelmaking.util.compat;

import me.jassy.steelmaking.init.ModItems;
import me.jassy.steelmaking.items.ItemBase;
import net.minecraftforge.oredict.OreDictionary;

public class OreDictionaryCompat {
	
	public static void registerOres() {
		OreDictionary.registerOre("dustIron", ModItems.IRON_DUST);
		OreDictionary.registerOre("ingotSteel", ModItems.STEEL_INGOT);
		OreDictionary.registerOre("dustSteel", ModItems.STEEL_DUST);
		OreDictionary.registerOre("dustCoal", ModItems.COAL_DUST);
	}

}
