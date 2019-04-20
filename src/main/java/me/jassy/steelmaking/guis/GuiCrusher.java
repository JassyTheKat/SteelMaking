package me.jassy.steelmaking.guis;

import me.jassy.steelmaking.machines.TileEntities.TileEntityCrusher;
import me.jassy.steelmaking.machines.containers.ContainerCrusher;
import me.jassy.steelmaking.util.Reference;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiCrusher extends GuiContainer {

	private static final ResourceLocation TEXTURES = new ResourceLocation(Reference.MOD_ID + ":textures/gui/crusher_gui" + ".png");
	private final InventoryPlayer player;
	private final TileEntityCrusher tileentity;
	
	public GuiCrusher(InventoryPlayer player, TileEntityCrusher tileentity) 
	{
		super(new ContainerCrusher(player, tileentity));
		this.player = player;
		this.tileentity = tileentity;
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) 
	{
		String tileName = tileentity.getDisplayName().getUnformattedText();
		fontRenderer.drawString(tileName, (xSize / 2 - fontRenderer.getStringWidth(tileName) / 2) + 3, 8, 4210752);
		fontRenderer.drawString(player.getDisplayName().getUnformattedText(), 122, ySize - 96 + 2, 4210752);
	}
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY)
	{
		GlStateManager.color(1.0f, 1.0f, 1.0f, 1.0f);
		mc.getTextureManager().bindTexture(TEXTURES);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		if(TileEntityCrusher.isCrushing(tileentity))
		{
			int k = getBurnLeftScaled(13);
			drawTexturedModalRect(guiLeft + 8, guiTop + 54 + 12 - k, 176, 12 - k, 14, k + 1);
		}
		
		int l = getCookProgressScaled(24);
		drawTexturedModalRect(guiLeft + 44, guiTop + 36, 176, 14, l + 1, 16);
	}
	
	private int getBurnLeftScaled(int pixels)
	{
		int i = tileentity.getField(1);
		if(i == 0) i = 200;
		return tileentity.getField(0) * pixels / i;
	}
	
	private int getCookProgressScaled(int pixels)
	{
		int i = tileentity.getField(2);
		int j = tileentity.getField(3);
		return j != 0 && i != 0 ? i * pixels / j : 0;
	}
}
