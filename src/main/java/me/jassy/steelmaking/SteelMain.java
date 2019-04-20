package me.jassy.steelmaking;

import me.jassy.steelmaking.init.ModRecipes;
import me.jassy.steelmaking.proxy.CommonProxy;
import me.jassy.steelmaking.util.Reference;
import me.jassy.steelmaking.util.compat.OreDictionaryCompat;
import me.jassy.steelmaking.util.handlers.RegistryHandler;
import net.minecraft.client.main.Main;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Reference.MOD_ID, name = Reference.NAME, version = Reference.VERSION)
public class SteelMain {

	@Instance
	public static SteelMain instance;
	
	@SidedProxy(clientSide = Reference.CLIENT_PROXY_CLASS, serverSide = Reference.COMMON_PROXY_CLASS)
	public static CommonProxy proxy;
	
	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event) {
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event) {
		RegistryHandler.initRegistries();
		OreDictionaryCompat.registerOres();
		ModRecipes.init();
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event) {
	}
}
