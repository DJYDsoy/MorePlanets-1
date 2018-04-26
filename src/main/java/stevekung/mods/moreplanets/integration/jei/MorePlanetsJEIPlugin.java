package stevekung.mods.moreplanets.integration.jei;

import mezz.jei.api.IModPlugin;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.JEIPlugin;
import mezz.jei.api.recipe.IRecipeCategoryRegistration;
import micdoodle8.mods.galacticraft.api.recipe.INasaWorkbenchRecipe;
import micdoodle8.mods.galacticraft.core.GCBlocks;
import net.minecraft.item.ItemStack;
import stevekung.mods.moreplanets.client.gui.GuiRocketCrusher;
import stevekung.mods.moreplanets.init.MPBlocks;
import stevekung.mods.moreplanets.integration.jei.black_hole_storage.BlackHoleStorageRecipeCategory;
import stevekung.mods.moreplanets.integration.jei.black_hole_storage.BlackHoleStorageRecipeWrapper;
import stevekung.mods.moreplanets.integration.jei.dark_energy_transform.DarkEnergyTransformRecipeCategory;
import stevekung.mods.moreplanets.integration.jei.dark_energy_transform.DarkEnergyTransformRecipeWrapper;
import stevekung.mods.moreplanets.integration.jei.rocket_crusher.RocketCrusherRecipeCategory;
import stevekung.mods.moreplanets.integration.jei.rocket_crusher.RocketCrusherRecipesWrapper;
import stevekung.mods.moreplanets.integration.jei.rockett4.Tier4RocketRecipeCategory;
import stevekung.mods.moreplanets.integration.jei.rockett4.Tier4RocketRecipeWrapper;
import stevekung.mods.moreplanets.integration.jei.rockett5.Tier5RocketRecipeCategory;
import stevekung.mods.moreplanets.integration.jei.rockett5.Tier5RocketRecipeWrapper;
import stevekung.mods.moreplanets.integration.jei.rockett6.Tier6RocketRecipeCategory;
import stevekung.mods.moreplanets.integration.jei.rockett6.Tier6RocketRecipeWrapper;
import stevekung.mods.moreplanets.module.planets.diona.blocks.DionaBlocks;
import stevekung.mods.moreplanets.recipe.BlackHoleStorageRecipes;
import stevekung.mods.moreplanets.recipe.DarkEnergyRecipeData;
import stevekung.mods.moreplanets.recipe.RocketCrusherRecipes;
import stevekung.mods.moreplanets.util.recipes.ShapedRecipesMP;

@JEIPlugin
public class MorePlanetsJEIPlugin implements IModPlugin
{
    @Override
    public void register(IModRegistry registry)
    {
        JEIRegistryHelper.registry = registry;
        JEIRegistryHelper.guiHelper = registry.getJeiHelpers().getGuiHelper();

        ItemDescription.init();

        registry.addRecipeClickArea(GuiRocketCrusher.class, 80, 30, 52, 25, MPJEIRecipes.ROCKET_CRUSHER);
        JEIRegistryHelper.registerRecipeHandlers(DarkEnergyRecipeData.class, DarkEnergyTransformRecipeWrapper::new, MPJEIRecipes.DARK_ENERGY_TRANSFORM);
        JEIRegistryHelper.registerRecipeHandlers(ShapedRecipesMP.class, RocketCrusherRecipesWrapper::new, MPJEIRecipes.ROCKET_CRUSHER);
        JEIRegistryHelper.registerRecipeHandlers(INasaWorkbenchRecipe.class, Tier4RocketRecipeWrapper::new, MPJEIRecipes.TIER_4_ROCKET);
        JEIRegistryHelper.registerRecipeHandlers(INasaWorkbenchRecipe.class, Tier5RocketRecipeWrapper::new, MPJEIRecipes.TIER_5_ROCKET);
        JEIRegistryHelper.registerRecipeHandlers(INasaWorkbenchRecipe.class, Tier6RocketRecipeWrapper::new, MPJEIRecipes.TIER_6_ROCKET);
        JEIRegistryHelper.registerRecipeHandlers(INasaWorkbenchRecipe.class, BlackHoleStorageRecipeWrapper::new, MPJEIRecipes.BLACK_HOLE_STORAGE);
        JEIRegistryHelper.registerRecipe(DarkEnergyRecipeData.getRecipeList(), MPJEIRecipes.DARK_ENERGY_TRANSFORM);
        JEIRegistryHelper.registerRecipe(RocketCrusherRecipes.getRecipeList(), MPJEIRecipes.ROCKET_CRUSHER);
        JEIRegistryHelper.registerRecipe(BlackHoleStorageRecipes.getRecipesList(), MPJEIRecipes.BLACK_HOLE_STORAGE);
        JEIRegistryHelper.registerStackDisplayRecipe(new ItemStack(MPBlocks.ROCKET_CRUSHER), MPJEIRecipes.ROCKET_CRUSHER);
        JEIRegistryHelper.registerStackDisplayRecipe(new ItemStack(DionaBlocks.DARK_ENERGY_CORE), MPJEIRecipes.DARK_ENERGY_TRANSFORM);
        JEIRegistryHelper.registerStackDisplayRecipe(new ItemStack(MPBlocks.BLACK_HOLE_STORAGE), MPJEIRecipes.BLACK_HOLE_STORAGE);
        JEIRegistryHelper.registerStackDisplayRecipe(new ItemStack(GCBlocks.nasaWorkbench), MPJEIRecipes.TIER_4_ROCKET, MPJEIRecipes.TIER_5_ROCKET, MPJEIRecipes.TIER_6_ROCKET);
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registry)
    {
        JEIRegistryHelper.categoryRegistration = registry;
        JEIRegistryHelper.guiHelper = registry.getJeiHelpers().getGuiHelper();

        JEIRegistryHelper.registerRecipeCategories(new DarkEnergyTransformRecipeCategory());
        JEIRegistryHelper.registerRecipeCategories(new RocketCrusherRecipeCategory());
        JEIRegistryHelper.registerRecipeCategories(new Tier4RocketRecipeCategory());
        JEIRegistryHelper.registerRecipeCategories(new Tier5RocketRecipeCategory());
        JEIRegistryHelper.registerRecipeCategories(new Tier6RocketRecipeCategory());
        JEIRegistryHelper.registerRecipeCategories(new BlackHoleStorageRecipeCategory());
    }
}