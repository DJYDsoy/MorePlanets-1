package stevekung.mods.moreplanets.module.planets.chalos.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.core.recipe.NasaWorkbenchRecipe;
import net.minecraft.item.ItemStack;
import stevekung.mods.moreplanets.integration.jei.MPJEIRecipes;
import stevekung.mods.moreplanets.integration.jei.rockett5.Tier5RocketRecipeWrapper;
import stevekung.mods.moreplanets.inventory.InventorySchematicRocket;

public class Tier5RocketRecipes
{
    private static List<INasaWorkbenchRecipe> ROCKET_RECIPE = new ArrayList<>();

    public static ItemStack findMatchingRocketRecipe(InventorySchematicRocket inv)
    {
        for (INasaWorkbenchRecipe recipe : Tier5RocketRecipes.getRocketRecipes())
        {
            if (recipe.matches(inv))
            {
                return recipe.getRecipeOutput();
            }
        }
        return null;
    }

    public static void addRocketRecipe(ItemStack result, HashMap<Integer, ItemStack> input)
    {
        Tier5RocketRecipes.addRocketRecipe(new NasaWorkbenchRecipe(result, input));
    }

    public static void addRocketRecipe(INasaWorkbenchRecipe recipe)
    {
        Tier5RocketRecipes.ROCKET_RECIPE.add(recipe);
    }

    public static List<INasaWorkbenchRecipe> getRocketRecipes()
    {
        return Tier5RocketRecipes.ROCKET_RECIPE;
    }

    public static List<Tier5RocketRecipeWrapper> getRecipesList()
    {
        List<Tier5RocketRecipeWrapper> recipes = new ArrayList<>();
        int chestCount = -1;

        for (INasaWorkbenchRecipe recipe : Tier5RocketRecipes.getRocketRecipes())
        {
            int chests = MPJEIRecipes.countChests(recipe);

            if (chests == chestCount)
            {
                continue;
            }
            chestCount = chests;
            Tier5RocketRecipeWrapper wrapper = new Tier5RocketRecipeWrapper(recipe);
            recipes.add(wrapper);
        }
        return recipes;
    }
}