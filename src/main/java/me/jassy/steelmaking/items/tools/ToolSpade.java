package me.jassy.steelmaking.items.tools;

import me.jassy.steelmaking.SteelMain;
import me.jassy.steelmaking.init.ModItems;
import me.jassy.steelmaking.items.ItemBase;
import me.jassy.steelmaking.util.IHasModel;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemSpade;

public class ToolSpade extends ItemSpade implements IHasModel{

	public ToolSpade(String name, ToolMaterial material) {
		super(material);
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
