package me.jassy.steelmaking.items;

import me.jassy.steelmaking.SteelMain;
import me.jassy.steelmaking.init.ModItems;
import me.jassy.steelmaking.util.IHasModel;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class ItemBase extends Item implements IHasModel {

	public ItemBase(String name) {
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(tabSteelMaking);
		
		ModItems.ITEMS.add(this);
	}
	@Override
	public void registerModels() {
		SteelMain.proxy.registerItemRenderer(this, 0, "inventory");
	}
	public static final CreativeTabs tabSteelMaking = new CreativeTabs("tabSteelMaking") {
		@Override
		public ItemStack getTabIconItem() {
			return new ItemStack(ModItems.STEEL_INGOT);
		}
	};
}
