package stevekung.mods.moreplanets.init;

import net.minecraft.potion.Potion;
import stevekung.mods.moreplanets.items.capsule_effect.DarkEnergyProtectionEffect;
import stevekung.mods.moreplanets.items.capsule_effect.InfectedSporeProtectionEffect;
import stevekung.mods.moreplanets.module.planets.diona.potion.DarkEnergyEffect;
import stevekung.mods.moreplanets.module.planets.diona.potion.InfectedCrystallizeEffect;
import stevekung.mods.moreplanets.module.planets.nibiru.potion.InfectedSporeEffect;
import stevekung.mods.moreplanets.util.helper.CommonRegisterHelper;

public class MPPotions
{
    public static Potion INFECTED_CRYSTALLIZE;
    public static Potion INFECTED_SPORE;
    public static Potion DARK_ENERGY;
    public static Potion INFECTED_SPORE_PROTECTION;
    public static Potion DARK_ENERGY_PROTECTION;

    public static void init()
    {
        MPPotions.INFECTED_CRYSTALLIZE = new InfectedCrystallizeEffect();
        MPPotions.INFECTED_SPORE = new InfectedSporeEffect();
        MPPotions.DARK_ENERGY = new DarkEnergyEffect();
        MPPotions.INFECTED_SPORE_PROTECTION = new InfectedSporeProtectionEffect();
        MPPotions.DARK_ENERGY_PROTECTION = new DarkEnergyProtectionEffect();

        CommonRegisterHelper.registerPotion(MPPotions.INFECTED_CRYSTALLIZE, "infected_crystallize");
        CommonRegisterHelper.registerPotion(MPPotions.INFECTED_SPORE, "infected_spore");
        CommonRegisterHelper.registerPotion(MPPotions.DARK_ENERGY, "dark_energy");
        CommonRegisterHelper.registerPotion(MPPotions.INFECTED_SPORE_PROTECTION, "infected_spore_protection");
        CommonRegisterHelper.registerPotion(MPPotions.DARK_ENERGY_PROTECTION, "dark_energy_protection");
    }
}