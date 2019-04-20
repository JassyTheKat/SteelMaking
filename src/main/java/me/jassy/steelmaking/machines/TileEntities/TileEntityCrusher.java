package me.jassy.steelmaking.machines.TileEntities;

import me.jassy.steelmaking.machines.Crusher;
import me.jassy.steelmaking.machines.recipes.CrusherRecipes;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityCrusher extends TileEntity implements ITickable
{
	private ItemStackHandler handler = new ItemStackHandler(4);
	private String customName;
	private ItemStack crushing = ItemStack.EMPTY;
	
	private int crushTime;
	private int currentCrushTime;
	private int CrushingTime;
	private int totalCrushingTime = 200;

	@Override
	public boolean hasCapability(Capability<?> capability, EnumFacing facing) 
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return true;
		else return false;
	}
	
	@Override
	public <T> T getCapability(Capability<T> capability, EnumFacing facing) 
	{
		if(capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) return (T) handler;
		return super.getCapability(capability, facing);
	}
	
	public boolean hasCustomName() 
	{
		return customName != null && !customName.isEmpty();
	}
	
	public void setCustomName(String customName) 
	{
		this.customName = customName;
	}
	
	@Override
	public ITextComponent getDisplayName() 
	{
		return hasCustomName() ? new TextComponentString(customName) : new TextComponentTranslation("container.crusher");
	}
	
	@Override
	public void readFromNBT(NBTTagCompound compound)
	{
		super.readFromNBT(compound);
		handler.deserializeNBT(compound.getCompoundTag("Inventory"));
		crushTime = compound.getInteger("CrushTime");
		CrushingTime = compound.getInteger("CrushingTime");
		totalCrushingTime = compound.getInteger("CrushingTimeTotal");
		currentCrushTime = getItemCrushTime((ItemStack)handler.getStackInSlot(2));
		
		if(compound.hasKey("CustomName", 8)) setCustomName(compound.getString("CustomName"));
	}
	
	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) 
	{
		super.writeToNBT(compound);
		compound.setInteger("CrushTime", (short)crushTime);
		compound.setInteger("CrushingTime", (short)CrushingTime);
		compound.setInteger("CrushingTimeTotal", (short)totalCrushingTime);
		compound.setTag("Inventory", handler.serializeNBT());
		
		if(hasCustomName()) compound.setString("CustomName", customName);
		return compound;
	}
	
	public boolean isCrushing() 
	{
		return crushTime > 0;
	}
	
	@SideOnly(Side.CLIENT)
	public static boolean isCrushing(TileEntityCrusher steelmaking) 
	{
		return steelmaking.getField(0) > 0;
	}
	
	@Override
	public void update() 
	{	
		if(isCrushing())
		{
			--crushTime;
			Crusher.setState(true, world, pos);
		}
		
		ItemStack[] inputs = new ItemStack[] {handler.getStackInSlot(0), handler.getStackInSlot(1)};
		ItemStack fuel = handler.getStackInSlot(2);
		
		if(isCrushing() || !fuel.isEmpty() && !handler.getStackInSlot(0).isEmpty() || handler.getStackInSlot(1).isEmpty())
		{
			if(!isCrushing() && canCrush())
			{
				crushTime = getItemCrushTime(fuel);
				currentCrushTime = crushTime;
				
				if(isCrushing() && !fuel.isEmpty())
				{
					Item item = fuel.getItem();
					fuel.shrink(1);
					
					if(fuel.isEmpty())
					{
						ItemStack item1 = item.getContainerItem(fuel);
						handler.setStackInSlot(2, item1);
					}
				}
			}
		}
		
		if(isCrushing() && canCrush() && CrushingTime > 0)
		{
			CrushingTime++;
			if(CrushingTime == totalCrushingTime)
			{
				if(handler.getStackInSlot(3).getCount() > 0)
				{
					handler.getStackInSlot(3).grow(1);
				}
				else
				{
					handler.insertItem(3, crushing, false);
				}
				
				crushing = ItemStack.EMPTY;
				CrushingTime = 0;
				return;
			}
		}
		else
		{
			if(canCrush() && isCrushing())
			{
				ItemStack output = CrusherRecipes.getInstance().getCrusherResult(inputs[0], inputs[1]);
				if(!output.isEmpty())
				{
					crushing = output;
					CrushingTime++;
					inputs[0].shrink(1);
					inputs[1].shrink(1);
					handler.setStackInSlot(0, inputs[0]);
					handler.setStackInSlot(1, inputs[1]);
				}
			}
		}
	}
	
	private boolean canCrush() 
	{
		if(((ItemStack)handler.getStackInSlot(0)).isEmpty() || ((ItemStack)handler.getStackInSlot(1)).isEmpty()) return false;
		else 
		{
			ItemStack result = CrusherRecipes.getInstance().getCrusherResult((ItemStack)handler.getStackInSlot(0), (ItemStack)handler.getStackInSlot(1));	
			if(result.isEmpty()) return false;
			else
			{
				ItemStack output = (ItemStack)handler.getStackInSlot(3);
				if(output.isEmpty()) return true;
				if(!output.isItemEqual(result)) return false;
				int res = output.getCount() + result.getCount();
				return res <= 64 && res <= output.getMaxStackSize();
			}
		}
	}
	
	public static int getItemCrushTime(ItemStack fuel) 
	{
		if(fuel.isEmpty()) return 0;
		else 
		{
			Item item = fuel.getItem();

			if (item instanceof ItemBlock && Block.getBlockFromItem(item) != Blocks.AIR) 
			{
				Block block = Block.getBlockFromItem(item);

				if (block == Blocks.WOODEN_SLAB) return 150;
				if (block.getDefaultState().getMaterial() == Material.WOOD) return 300;
				if (block == Blocks.COAL_BLOCK) return 16000;
			}

			if (item instanceof ItemTool && "WOOD".equals(((ItemTool)item).getToolMaterialName())) return 200;
			if (item instanceof ItemSword && "WOOD".equals(((ItemSword)item).getToolMaterialName())) return 200;
			if (item instanceof ItemHoe && "WOOD".equals(((ItemHoe)item).getMaterialName())) return 200;
			if (item == Items.STICK) return 100;
			if (item == Items.COAL) return 1600;
			if (item == Items.LAVA_BUCKET) return 20000;
			if (item == Item.getItemFromBlock(Blocks.SAPLING)) return 100;
			if (item == Items.BLAZE_ROD) return 2400;

			return GameRegistry.getFuelValue(fuel);
		}
	}
		
	public static boolean isItemFuel(ItemStack fuel)
	{
		return getItemCrushTime(fuel) > 0;
	}
	
	public boolean isUsableByPlayer(EntityPlayer player) 
	{
		return world.getTileEntity(pos) != this ? false : player.getDistanceSq((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D) <= 64.0D;
	}

	public int getField(int id) 
	{
		switch(id) 
		{
		case 0:
			return crushTime;
		case 1:
			return currentCrushTime;
		case 2:
			return CrushingTime;
		case 3:
			return totalCrushingTime;
		default:
			return 0;
		}
	}

	public void setField(int id, int value) 
	{
		switch(id) 
		{
		case 0:
			crushTime = value;
			break;
		case 1:
			currentCrushTime = value;
			break;
		case 2:
			CrushingTime = value;
			break;
		case 3:
			totalCrushingTime = value;
		}
	}
}