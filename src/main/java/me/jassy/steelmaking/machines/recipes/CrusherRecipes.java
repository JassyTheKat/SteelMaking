package me.jassy.steelmaking.machines.recipes;

import java.util.Map;
import java.util.Map.Entry;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;

import net.minecraft.item.ItemStack;

public class CrusherRecipes {

	private static final CrusherRecipes INSTANCE = new CrusherRecipes();
	private final Table<ItemStack, ItemStack, ItemStack> crushingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
	private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();
	
	public static CrusherRecipes getInstance() {
		return INSTANCE;
	}
	private CrusherRecipes() {
		
	}
	public void addCrusherRecipe(ItemStack input1, ItemStack input2, ItemStack result, float experience) {
		if(getCrusherResult(input1, input2) != ItemStack.EMPTY) return;
		crushingList.put(input1, input2, result);
		experienceList.put(result, Float.valueOf(experience));
	}
	public ItemStack getCrusherResult(ItemStack input1, ItemStack input2) {
		for(Entry<ItemStack, Map<ItemStack, ItemStack>> entry : crushingList.columnMap().entrySet()) {
			if(compareItemStacks(input1, (ItemStack)entry.getKey())) {
				for(Entry<ItemStack, ItemStack> ent : entry.getValue().entrySet()) {
					if(compareItemStacks(input2, (ItemStack)ent.getKey())) {
						return (ItemStack)ent.getValue();
					}
				}
			}
		}
		return ItemStack.EMPTY;
	}
	private boolean compareItemStacks(ItemStack stack1, ItemStack stack2) {
		return stack2.getItem() == stack1.getItem() && (stack2.getMetadata() == 32767 || stack2.getMetadata() == stack1.getMetadata());
	}
	public Table<ItemStack, ItemStack, ItemStack> getCrushingList() {
		return crushingList;
	}
	public float getCrusherExperience(ItemStack stack) {
		for (Entry<ItemStack, Float> entry : experienceList.entrySet()) {
			if(compareItemStacks(stack, (ItemStack)entry.getKey())) {
				return ((Float)entry.getValue().floatValue());
			}
		}
		return 0.0F;
	}
}
