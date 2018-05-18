package stevekung.mods.moreplanets.planets.diona.entity;

import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import stevekung.mods.moreplanets.core.MorePlanetsMod;
import stevekung.mods.moreplanets.init.MPItems;
import stevekung.mods.moreplanets.init.MPLootTables;
import stevekung.mods.moreplanets.init.MPPotions;
import stevekung.mods.moreplanets.utils.EnumParticleTypesMP;
import stevekung.mods.moreplanets.utils.entity.EntitySlimeBaseMP;

public class EntityInfectedCrystallizedSlimeMinion extends EntitySlimeBaseMP
{
    public EntityInfectedCrystallizedSlimeMinion(World world)
    {
        super(world);
    }

    @Override
    @Nullable
    public ResourceLocation getLootTable()
    {
        return this.getSlimeSize() == 1 ? MPLootTables.INFECTED_CRYSTALLIZED_SLIME : null;
    }

    @Override
    protected boolean canDamagePlayer()
    {
        return !this.isAIDisabled();
    }

    @Override
    public float getSizeBased()
    {
        return 1.01000005F;
    }

    @Override
    public void fall(float distance, float damageMultiplier) {}

    @Override
    public void setDead()
    {
        int i = this.getSlimeSize();

        if (!this.world.isRemote && i > 1 && this.getHealth() <= 0.0F)
        {
            int j = 8 + this.rand.nextInt(8);

            for (int k = 0; k < j; ++k)
            {
                float f = (k % 2 - 0.5F) * i / 4.0F;
                float f1 = (k / 2 - 0.5F) * i / 4.0F;
                EntitySlimeBaseMP entityslime = this.createInstance();

                if (this.hasCustomName())
                {
                    entityslime.setCustomNameTag(this.getCustomNameTag());
                }
                if (this.isNoDespawnRequired())
                {
                    entityslime.enablePersistence();
                }
                entityslime.setSlimeSize(i / 2, true);
                entityslime.setLocationAndAngles(this.posX + f, this.posY + 0.5D, this.posZ + f1, this.rand.nextFloat() * 360.0F, 0.0F);
                this.world.spawnEntity(entityslime);
            }
        }
        this.isDead = true;
    }

    @Override
    protected void dealDamage(EntityLivingBase entity)
    {
        if (this.canEntityBeSeen(entity) && this.getDistanceSq(entity) < this.getDetectRange() && entity.attackEntityFrom(DamageSource.causeMobDamage(this), this.getAttackStrength()))
        {
            this.applyEnchantments(this, entity);
            entity.addPotionEffect(new PotionEffect(MPPotions.INFECTED_CRYSTALLIZED, 200, 1));
        }
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, IEntityLivingData livingdata)
    {
        int i = this.rand.nextInt(2);

        if (i < 2 && this.rand.nextFloat() < 0.5F * difficulty.getClampedAdditionalDifficulty())
        {
            ++i;
        }
        int j = 1 << i;
        this.setSlimeSize(j, true);
        return super.onInitialSpawn(difficulty, livingdata);
    }

    @Override
    public boolean isPotionApplicable(PotionEffect potion)
    {
        return potion.getPotion() == MPPotions.INFECTED_CRYSTALLIZED ? false : super.isPotionApplicable(potion);
    }

    @Override
    protected EntitySlimeBaseMP createInstance()
    {
        return new EntityInfectedCrystallizedSlimeMinion(this.world);
    }

    @Override
    public int getJumpDelay()
    {
        return this.rand.nextInt(2) + 3;
    }

    @Override
    protected double getDetectRange()
    {
        return 1.0D;
    }

    @Override
    protected void createParticles()
    {
        int i = this.getSlimeSize();

        for (int j = 0; j < i * 8; ++j)
        {
            float f = this.rand.nextFloat() * (float)Math.PI * 2.0F;
            float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
            float f2 = MathHelper.sin(f) * i * 0.5F * f1;
            float f3 = MathHelper.cos(f) * i * 0.5F * f1;
            double d0 = this.posX + f2;
            double d1 = this.posZ + f3;
            MorePlanetsMod.PROXY.spawnParticle(EnumParticleTypesMP.CUSTOM_BREAKING, d0, this.getEntityBoundingBox().minY, d1, new Object[] { MPItems.INFECTED_CRYSTALLIZED_SLIMEBALL });
        }
    }

    @Override
    protected void overrideHealth()
    {
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.getSlimeSize() * this.getSlimeSize());
    }
}