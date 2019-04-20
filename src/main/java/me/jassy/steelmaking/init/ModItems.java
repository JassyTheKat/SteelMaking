package me.jassy.steelmaking.init;

import java.util.ArrayList;
import java.util.List;

import me.jassy.steelmaking.items.ItemBase;
import me.jassy.steelmaking.items.armor.ArmorBase;
import me.jassy.steelmaking.items.tools.ToolAxe;
import me.jassy.steelmaking.items.tools.ToolHoe;
import me.jassy.steelmaking.items.tools.ToolPickaxe;
import me.jassy.steelmaking.items.tools.ToolSpade;
import me.jassy.steelmaking.items.tools.ToolSword;
import me.jassy.steelmaking.util.Reference;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemAxe;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemSpade;
import net.minecraft.item.ItemSword;
import net.minecraftforge.common.util.EnumHelper;

public class ModItems {

	public static final List<Item> ITEMS = new ArrayList<Item>();
	
	//Materials
	public static final ToolMaterial MATERIAL_STEEL = EnumHelper.addToolMaterial("material_steel", 3, 750, 7.5F, 3F, 10);
	public static final ArmorMaterial ARMOR_MATERIAL_STEEL = EnumHelper.addArmorMaterial("armor_material_steel", Reference.MOD_ID + ":steel", 14, new int[] { 4, 7, 8, 4}, 10, SoundEvents.ITEM_ARMOR_EQUIP_CHAIN, 0.0F);
	
	//Items
	public static final Item STEEL_INGOT = new ItemBase("steel_ingot");
	public static final Item STEEL_DUST = new ItemBase("steel_dust");
	public static final Item IRON_DUST = new ItemBase("iron_dust");
	public static final Item COAL_DUST = new ItemBase("coal_dust");
	
	//Tools
	public static final ItemSword STEEL_SWORD = new ToolSword("steel_sword", MATERIAL_STEEL);
	public static final ItemPickaxe STEEL_PICKAXE = new ToolPickaxe("steel_pickaxe", MATERIAL_STEEL);
	public static final ItemSpade STEEL_SPADE = new ToolSpade("steel_spade", MATERIAL_STEEL);
	public static final ItemAxe STEEL_AXE = new ToolAxe("steel_axe");
	public static final ItemHoe STEEL_HOE = new ToolHoe("steel_hoe", MATERIAL_STEEL);
	
	//Armor
	public static final Item STEEL_HELMET = new ArmorBase("steel_helmet", ARMOR_MATERIAL_STEEL, 1, EntityEquipmentSlot.HEAD);
	public static final Item STEEL_CHESTPLATE = new ArmorBase("steel_chestplate", ARMOR_MATERIAL_STEEL, 1, EntityEquipmentSlot.CHEST);
	public static final Item STEEL_LEGGINGS = new ArmorBase("steel_leggings", ARMOR_MATERIAL_STEEL, 2, EntityEquipmentSlot.LEGS);
	public static final Item STEEL_BOOTS = new ArmorBase("steel_boots", ARMOR_MATERIAL_STEEL, 1, EntityEquipmentSlot.FEET);
}
