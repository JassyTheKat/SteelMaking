package me.jassy.steelmaking.machines.containers;

import me.jassy.steelmaking.machines.Crusher;
import me.jassy.steelmaking.machines.TileEntities.TileEntityCrusher;
import me.jassy.steelmaking.machines.recipes.CrusherRecipes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;

public class ContainerCrusher extends Container
{
	private final TileEntityCrusher tileentity;
	private int crushTime, totalCrushTime, crushingTime, currentCrushingTime;
	
	public ContainerCrusher(InventoryPlayer player, TileEntityCrusher tileentity) 
	{
		this.tileentity = tileentity;
		IItemHandler handler = tileentity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
		
		addSlotToContainer(new SlotItemHandler(handler, 0, 26, 11));
		addSlotToContainer(new SlotItemHandler(handler, 2, 7, 35));
		addSlotToContainer(new SlotItemHandler(handler, 3, 81, 36));
		
		for(int y = 0; y < 3; y++)
		{
			for(int x = 0; x < 9; x++)
			{
				this.addSlotToContainer(new Slot(player, x + y*9 + 9, 8 + x*18, 84 + y*18));
			}
		}
		
		for(int x = 0; x < 9; x++)
		{
			this.addSlotToContainer(new Slot(player, x, 8 + x * 18, 142));
		}
	}
	
	@Override
	public void detectAndSendChanges() 
	{
		super.detectAndSendChanges();
		
		for(int i = 0; i < listeners.size(); ++i) 
		{
			IContainerListener listener = (IContainerListener)listeners.get(i);
			
			if(crushTime != tileentity.getField(2)) listener.sendWindowProperty(this, 2, tileentity.getField(2));
			if(crushingTime != tileentity.getField(0)) listener.sendWindowProperty(this, 0, tileentity.getField(0));
			if(currentCrushingTime != tileentity.getField(1)) listener.sendWindowProperty(this, 1, tileentity.getField(1));
			if(totalCrushTime != tileentity.getField(3)) listener.sendWindowProperty(this, 3, tileentity.getField(3));
		}
		
		crushTime = tileentity.getField(2);
		crushingTime = tileentity.getField(0);
		currentCrushingTime = tileentity.getField(1);
		totalCrushTime = tileentity.getField(3);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) 
	{
		tileentity.setField(id, data);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) 
	{
		return tileentity.isUsableByPlayer(playerIn);
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) 
	{
		ItemStack stack = ItemStack.EMPTY;
		Slot slot = (Slot)inventorySlots.get(index);
		
		if(slot != null && slot.getHasStack()) 
		{
			ItemStack stack1 = slot.getStack();
			stack = stack1.copy();
			
			if(index == 3) 
			{
				if(!mergeItemStack(stack1, 4, 40, true)) return ItemStack.EMPTY;
				slot.onSlotChange(stack1, stack);
			}
			else if(index != 2 && index != 1 && index != 0) 
			{		
				Slot slot1 = (Slot)inventorySlots.get(index + 1);
				
				if(!CrusherRecipes.getInstance().getCrusherResult(stack1, slot1.getStack()).isEmpty())
				{
					if(!mergeItemStack(stack1, 0, 2, false)) 
					{
						return ItemStack.EMPTY;
					}
					else if(TileEntityCrusher.isItemFuel(stack1))
					{
						if(!mergeItemStack(stack1, 2, 3, false)) return ItemStack.EMPTY;
					}
					else if(TileEntityCrusher.isItemFuel(stack1))
					{
						if(!mergeItemStack(stack1, 2, 3, false)) return ItemStack.EMPTY;
					}
					else if(TileEntityCrusher.isItemFuel(stack1))
					{
						if(!mergeItemStack(stack1, 2, 3, false)) return ItemStack.EMPTY;
					}
					else if(index >= 4 && index < 31)
					{
						if(!mergeItemStack(stack1, 31, 40, false)) return ItemStack.EMPTY;
					}
					else if(index >= 31 && index < 40 && !this.mergeItemStack(stack1, 4, 31, false))
					{
						return ItemStack.EMPTY;
					}
				}
			} 
			else if(!this.mergeItemStack(stack1, 4, 40, false)) 
			{
				return ItemStack.EMPTY;
			}
			if(stack1.isEmpty())
			{
				slot.putStack(ItemStack.EMPTY);
			}
			else
			{
				slot.onSlotChanged();

			}
			if(stack1.getCount() == stack.getCount()) return ItemStack.EMPTY;
			slot.onTake(playerIn, stack1);
		}
		return stack;
	}
}