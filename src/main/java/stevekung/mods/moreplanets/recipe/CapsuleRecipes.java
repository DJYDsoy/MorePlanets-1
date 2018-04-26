package stevekung.mods.moreplanets.recipe;

import micdoodle8.mods.galacticraft.core.GCItems;
import micdoodle8.mods.galacticraft.planets.mars.items.MarsItems;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import stevekung.mods.moreplanets.init.MPItems;
import stevekung.mods.moreplanets.module.planets.chalos.blocks.ChalosBlocks;
import stevekung.mods.moreplanets.module.planets.chalos.items.ChalosItems;
import stevekung.mods.moreplanets.module.planets.diona.items.DionaItems;
import stevekung.mods.moreplanets.module.planets.nibiru.blocks.NibiruBlocks;
import stevekung.mods.moreplanets.module.planets.nibiru.items.NibiruItems;
import stevekung.mods.moreplanets.util.helper.RecipeHelper;

public class CapsuleRecipes
{
    public static void init()
    {
        RecipeHelper.addRecipe(new ItemStack(MPItems.EMPTY_CAPSULE), new Object[] { " C", "X ", 'C', new ItemStack(GCItems.canister, 1, 0), 'X', Items.IRON_INGOT });
        RecipeHelper.addShapelessRecipe(new ItemStack(MPItems.INFECTED_SPORE_PROTECTION_CAPSULE), MPItems.EMPTY_CAPSULE, new ItemStack(ChalosItems.CHEESE_FOOD, 1, 3), ChalosBlocks.CHEESE_SPORE_FLOWER, Blocks.BROWN_MUSHROOM);
        RecipeHelper.addShapelessRecipe(new ItemStack(MPItems.INFECTED_SPORE_PROTECTION_CAPSULE), MPItems.EMPTY_CAPSULE, new ItemStack(NibiruBlocks.NIBIRU_FLOWER, 1, 0), new ItemStack(NibiruItems.NIBIRU_ITEM, 1, 0));
        RecipeHelper.addShapelessRecipe(new ItemStack(MPItems.DARK_ENERGY_PROTECTION_CAPSULE), MPItems.EMPTY_CAPSULE, new ItemStack(DionaItems.DIONA_ITEM, 1, 4), new ItemStack(MarsItems.marsItemBasic, 1, 0));
        RecipeHelper.addShapelessRecipe(new ItemStack(MPItems.DARK_ENERGY_PROTECTION_CAPSULE), MPItems.EMPTY_CAPSULE, new ItemStack(NibiruBlocks.NIBIRU_FLOWER, 1, 1), new ItemStack(NibiruItems.NIBIRU_ITEM, 1, 0), new ItemStack(MarsItems.marsItemBasic, 1, 0));
    }
}