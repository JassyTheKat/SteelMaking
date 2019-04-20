package me.jassy.steelmaking.items.tools;

import me.jassy.steelmaking.SteelMain;
import me.jassy.steelmaking.init.ModItems;
import me.jassy.steelmaking.items.ItemBase;
import me.jassy.steelmaking.util.IHasModel;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraftforge.common.util.EnumHelper;
import net.minecraft.item.ItemAxe;

public class ToolAxe extends ItemAxe implements IHasModel {

	public ToolAxe(String name) {
		super(ModItems.MATERIAL_STEEL, 7.0F, -3.2F);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(ItemBase.tabSteelMaking);
		
		ModItems.ITEMS.add(this);
	}
	@Override
	public void registerModels() {
		SteelMain.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
