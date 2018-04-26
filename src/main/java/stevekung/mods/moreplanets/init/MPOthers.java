package stevekung.mods.moreplanets.init;

import stevekung.mods.moreplanets.module.planets.chalos.blocks.ChalosBlocks;
import stevekung.mods.moreplanets.module.planets.diona.blocks.DionaBlocks;
import stevekung.mods.moreplanets.module.planets.diona.entity.EntityInfectedCrystallizeBomb;
import stevekung.mods.moreplanets.module.planets.diona.entity.projectile.EntityInfectedCrystallizeArrow;
import stevekung.mods.moreplanets.module.planets.diona.items.DionaItems;
import stevekung.mods.moreplanets.module.planets.fronos.blocks.FronosBlocks;
import stevekung.mods.moreplanets.module.planets.nibiru.blocks.NibiruBlocks;
import stevekung.mods.moreplanets.module.planets.nibiru.entity.projectile.EntityInfectedArrow;
import stevekung.mods.moreplanets.module.planets.nibiru.entity.projectile.EntityInfectedEgg;
import stevekung.mods.moreplanets.module.planets.nibiru.entity.projectile.EntityInfectedSnowball;
import stevekung.mods.moreplanets.module.planets.nibiru.items.NibiruItems;
import stevekung.mods.moreplanets.util.helper.CommonRegisterHelper;
import stevekung.mods.stevekunglib.utils.BehaviorProjectileDispenseBase;

public class MPOthers
{
    public static void init()
    {
        MPOthers.registerDispenserObject();
        MPOthers.registerEndermanCarriableBlock();
    }

    private static void registerDispenserObject()
    {
        CommonRegisterHelper.registerProjectileDispense(DionaItems.INFECTED_CRYSTALLIZE_BOMB, new BehaviorProjectileDispenseBase(EntityInfectedCrystallizeBomb.class));
        CommonRegisterHelper.registerProjectileDispense(NibiruItems.INFECTED_SNOWBALL, new BehaviorProjectileDispenseBase(EntityInfectedSnowball.class));
        CommonRegisterHelper.registerProjectileDispense(NibiruItems.INFECTED_EGG, new BehaviorProjectileDispenseBase(EntityInfectedEgg.class));
        CommonRegisterHelper.registerProjectileDispense(DionaItems.INFECTED_CRYSTALLIZE_ARROW, new BehaviorProjectileDispenseBase(EntityInfectedCrystallizeArrow.class, true));
        CommonRegisterHelper.registerProjectileDispense(NibiruItems.INFECTED_ARROW, new BehaviorProjectileDispenseBase(EntityInfectedArrow.class, true));
    }

    private static void registerEndermanCarriableBlock()
    {
        CommonRegisterHelper.registerCarriable(DionaBlocks.INFECTED_CRYSTALLIZE_SLIME_BLOCK);
        CommonRegisterHelper.registerCarriable(DionaBlocks.INFECTED_CRYSTALLIZE_PART);
        CommonRegisterHelper.registerCarriable(DionaBlocks.ZELIUS_EGG);
        CommonRegisterHelper.registerCarriable(ChalosBlocks.CHEESE_GRASS);
        CommonRegisterHelper.registerCarriable(ChalosBlocks.CHEESE_DIRT);
        CommonRegisterHelper.registerCarriable(ChalosBlocks.CHEESE_SLIME_BLOCK);
        CommonRegisterHelper.registerCarriable(ChalosBlocks.CHEESE_OF_MILK_CAKE);
        CommonRegisterHelper.registerCarriable(NibiruBlocks.INFECTED_GRASS);
        CommonRegisterHelper.registerCarriable(NibiruBlocks.INFECTED_DIRT);
        CommonRegisterHelper.registerCarriable(NibiruBlocks.INFECTED_CLAY);
        CommonRegisterHelper.registerCarriable(NibiruBlocks.INFECTED_GRAVEL);
        CommonRegisterHelper.registerCarriable(NibiruBlocks.INFECTED_SNOW);
        CommonRegisterHelper.registerCarriable(NibiruBlocks.INFECTED_SNOW_LAYER);
        CommonRegisterHelper.registerCarriable(NibiruBlocks.GREEN_VEIN_GRASS);
        CommonRegisterHelper.registerCarriable(NibiruBlocks.INFECTED_MELON_BLOCK);
        CommonRegisterHelper.registerCarriable(NibiruBlocks.INFECTED_SAND);
        CommonRegisterHelper.registerCarriable(NibiruBlocks.INFECTED_CACTUS);
        CommonRegisterHelper.registerCarriable(NibiruBlocks.NIBIRU_FLOWER);
        CommonRegisterHelper.registerCarriable(NibiruBlocks.INFECTED_VINES_DIRT);
        CommonRegisterHelper.registerCarriable(NibiruBlocks.PURIFY_GRAVEL);
        CommonRegisterHelper.registerCarriable(NibiruBlocks.NIBIRU_GRASS_PATH);
        CommonRegisterHelper.registerCarriable(FronosBlocks.FRONOS_GRASS);
        CommonRegisterHelper.registerCarriable(FronosBlocks.FRONOS_DIRT);
        CommonRegisterHelper.registerCarriable(FronosBlocks.CANDY_CANE_1);
        CommonRegisterHelper.registerCarriable(FronosBlocks.CANDY_CANE_2);
    }
}