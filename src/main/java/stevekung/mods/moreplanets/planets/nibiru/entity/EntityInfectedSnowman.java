package stevekung.mods.moreplanets.planets.nibiru.entity;

import javax.annotation.Nullable;

import micdoodle8.mods.galacticraft.api.entity.IEntityBreathable;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.monster.EntityGolem;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import stevekung.mods.moreplanets.init.MPBlocks;
import stevekung.mods.moreplanets.init.MPLootTables;
import stevekung.mods.moreplanets.init.MPPotions;
import stevekung.mods.moreplanets.planets.nibiru.entity.projectile.EntityInfectedSnowball;
import stevekung.mods.moreplanets.utils.entity.ISpaceMob;
import stevekung.mods.moreplanets.utils.entity.ai.PathNavigateGroundMP;

public class EntityInfectedSnowman extends EntityGolem implements IRangedAttackMob, IEntityBreathable, ISpaceMob
{
    public EntityInfectedSnowman(World world)
    {
        super(world);
        this.setSize(0.7F, 1.9F);
        this.experienceValue = 5;
    }

    @Override
    protected void initEntityAI()
    {
        this.tasks.addTask(1, new EntityAIAttackRanged(this, 1.25D, 20, 10.0F));
        this.tasks.addTask(2, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 6.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAINearestAttackableTarget<>(this, EntityLiving.class, 10, true, false, IMob.MOB_SELECTOR));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, true));
    }

    @Override
    protected PathNavigate createNavigator(World world)
    {
        return new PathNavigateGroundMP(this, world);
    }

    @Override
    public boolean isPotionApplicable(PotionEffect potion)
    {
        return potion.getPotion() == MPPotions.INFECTED_SPORE ? false : super.isPotionApplicable(potion);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(15.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.20000000298023224D);
    }

    @Override
    public void onLivingUpdate()
    {
        super.onLivingUpdate();

        if (!this.world.isRemote)
        {
            int i = MathHelper.floor(this.posX);
            int j = MathHelper.floor(this.posY);
            int k = MathHelper.floor(this.posZ);

            if (this.isWet())
            {
                this.attackEntityFrom(DamageSource.DROWN, 1.0F);
            }
            if (this.world.getBiome(new BlockPos(i, 0, k)).getTemperature(new BlockPos(i, j, k)) > 1.0F)
            {
                this.attackEntityFrom(DamageSource.ON_FIRE, 1.0F);
            }

            for (int l = 0; l < 4; ++l)
            {
                i = MathHelper.floor(this.posX + (l % 2 * 2 - 1) * 0.25F);
                j = MathHelper.floor(this.posY);
                k = MathHelper.floor(this.posZ + (l / 2 % 2 * 2 - 1) * 0.25F);
                BlockPos blockpos = new BlockPos(i, j, k);

                if (this.world.getBlockState(blockpos).getMaterial() == Material.AIR && this.world.getBiome(new BlockPos(i, 0, k)).getTemperature(blockpos) < 0.8F && MPBlocks.INFECTED_SNOW_LAYER.canPlaceBlockAt(this.world, blockpos))
                {
                    this.world.setBlockState(blockpos, MPBlocks.INFECTED_SNOW_LAYER.getDefaultState());
                }
            }
        }
    }

    @Override
    public boolean getCanSpawnHere()
    {
        return this.world.getDifficulty() != EnumDifficulty.PEACEFUL && this.world.checkNoEntityCollision(this.getEntityBoundingBox()) && this.world.getCollisionBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.world.containsAnyLiquid(this.getEntityBoundingBox()) && this.world.canSeeSky(this.getPosition()) && this.world.getLightBrightness(this.getPosition()) >= 0.0F;
    }

    @Override
    @Nullable
    public ResourceLocation getLootTable()
    {
        return MPLootTables.INFECTED_SNOWMAN;
    }

    @Override
    public void attackEntityWithRangedAttack(EntityLivingBase target, float distance)
    {
        EntityInfectedSnowball entitysnowball = new EntityInfectedSnowball(this.world, this);
        double d0 = target.posY + target.getEyeHeight() - 1.100000023841858D;
        double d1 = target.posX - this.posX;
        double d2 = d0 - entitysnowball.posY;
        double d3 = target.posZ - this.posZ;
        float f = MathHelper.sqrt(d1 * d1 + d3 * d3) * 0.2F;
        entitysnowball.shoot(d1, d2 + f, d3, 1.6F, 12.0F);
        this.playSound(SoundEvents.ENTITY_SNOWMAN_SHOOT, 1.0F, 1.0F / (this.getRNG().nextFloat() * 0.4F + 0.8F));
        this.world.spawnEntity(entitysnowball);
    }

    @Override
    public float getEyeHeight()
    {
        return 1.7F;
    }

    @Override
    public boolean canBreath()
    {
        return true;
    }

    @Override
    public EnumMobType getMobType()
    {
        return EnumMobType.NIBIRU;
    }

    @Override
    protected boolean canDespawn()
    {
        return true;
    }

    @Override
    public void setSwingingArms(boolean swingingArms) {}
}