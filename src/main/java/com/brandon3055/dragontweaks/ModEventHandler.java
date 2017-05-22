package com.brandon3055.dragontweaks;

import net.minecraft.entity.boss.EntityDragon;
import net.minecraft.init.Blocks;
import net.minecraft.world.end.DragonFightManager;
import net.minecraft.world.gen.feature.WorldGenEndPodium;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;



/**
 * Created by brandon3055 on 11/11/2016.
 */
public class ModEventHandler {

    @SubscribeEvent
    public void onDropEvent(LivingDropsEvent event) {
        if (!event.getEntity().world.isRemote && (event.getEntity() instanceof EntityDragon)) {

            DragonFightManager manager = ((EntityDragon) event.getEntity()).getFightManager();
            if (manager != null && manager.hasPreviouslyKilledDragon()) {
                event.getEntity().world.setBlockState(event.getEntity().world.getHeight(WorldGenEndPodium.END_PODIUM_LOCATION).add(0, 0, -4), Blocks.DRAGON_EGG.getDefaultState());
            }
        }
    }
}
