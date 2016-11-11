package com.brandon3055.dragontweaks;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;
import org.apache.logging.log4j.Level;

import java.util.Iterator;

@Mod(modid = DragonTweaks.MODID, name = DragonTweaks.MODNAME,version = DragonTweaks.VERSION)
public class DragonTweaks
{
    public static final String MODID = "dragontweaks";
	public static final String MODNAME = "Dragon Tweaks";
    public static final String VERSION = "${mod_version}";
    public static Configuration configuration;

    public static boolean harderRecipe = true;
    public static boolean dragonEggSpawn = true;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        configuration = new Configuration(event.getSuggestedConfigurationFile());

        try {
            harderRecipe = configuration.get(Configuration.CATEGORY_GENERAL, "harderCrystalRecipe", harderRecipe, "This makes the End Crystal require a nether star.").getBoolean(harderRecipe);
            dragonEggSpawn = configuration.get(Configuration.CATEGORY_GENERAL, "dragonEggSpawn", dragonEggSpawn, "This makes the dragon drop an egg every time it dies.").getBoolean(dragonEggSpawn);
            if (configuration.hasChanged()) {
                configuration.save();
            }
        }
        catch (Exception e) {
            FMLLog.log(Level.ERROR, MODNAME, "En error occurred while loading the configuration file.");
            e.printStackTrace();
        }

        if (dragonEggSpawn) {
            MinecraftForge.EVENT_BUS.register(new ModEventHandler());
        }
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        if (harderRecipe) {
            java.util.List<IRecipe> list = CraftingManager.getInstance().getRecipeList();

            boolean removed = false;
            Iterator<IRecipe> i = list.iterator();
            while (i.hasNext()) {
                IRecipe recipe = i.next();
                if (recipe.getRecipeOutput() != null && recipe.getRecipeOutput().getItem() == Items.END_CRYSTAL) {
                    i.remove();
                    removed = true;
                }
            }

            if (removed) {
                GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(Items.END_CRYSTAL), "AAA", "ABA", "ACA", 'A', "paneGlassColorless", 'B', "netherStar", 'C', Items.GHAST_TEAR));
            }
        }
    }
}
