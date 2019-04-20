package me.jassy.steelmaking.items.armor;

import me.jassy.steelmaking.SteelMain;
import me.jassy.steelmaking.init.ModItems;
import me.jassy.steelmaking.items.ItemBase;
import me.jassy.steelmaking.util.IHasModel;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;

public class ArmorBase extends ItemArmor implements IHasModel {

	public ArmorBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
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
