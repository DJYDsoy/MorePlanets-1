package stevekung.mods.moreplanets.module.planets.nibiru.blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stevekung.mods.moreplanets.core.MorePlanetsMod;
import stevekung.mods.moreplanets.init.MPPotions;
import stevekung.mods.moreplanets.module.planets.nibiru.items.NibiruItems;
import stevekung.mods.moreplanets.utils.EnumParticleTypesMP;
import stevekung.mods.moreplanets.utils.blocks.BlockFluidBaseMP;
import stevekung.mods.moreplanets.utils.blocks.IFishableLiquidBlock;
import stevekung.mods.moreplanets.utils.entity.ISpaceMob;
import stevekung.mods.moreplanets.utils.entity.ISpaceMob.EnumMobType;
import stevekung.mods.moreplanets.utils.helper.EntityEffectHelper;

public class BlockFluidInfectedWater extends BlockFluidBaseMP implements IFishableLiquidBlock
{
    public BlockFluidInfectedWater(String name)
    {
        super(NibiruBlocks.INFECTED_WATER_FLUID);
        this.setRenderLayer(BlockRenderLayer.TRANSLUCENT);
        this.setLightOpacity(3);
        this.setUnlocalizedName(name);
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        if (entity instanceof EntityLivingBase && !EntityEffectHelper.isGalacticraftMob(entity) && !(entity instanceof ISpaceMob && ((ISpaceMob)entity).getMobType() == EnumMobType.NIBIRU))
        {
            EntityLivingBase living = (EntityLivingBase) entity;

            if (living instanceof EntityPlayer)
            {
                EntityPlayer player = (EntityPlayer) living;

                if (!player.capabilities.isCreativeMode && !player.isPotionActive(MPPotions.INFECTED_SPORE_PROTECTION))
                {
                    player.addPotionEffect(new PotionEffect(MPPotions.INFECTED_SPORE, 80));
                }
            }
            if (!(living instanceof EntityPlayer))
            {
                living.addPotionEffect(new PotionEffect(MPPotions.INFECTED_SPORE, 80, 0));
            }
        }
        if (entity instanceof EntityItem)
        {
            EntityItem item = (EntityItem) entity;

            if (item.getItem().getItem() == Items.ARROW)
            {
                item.setItem(new ItemStack(NibiruItems.INFECTED_ARROW, item.getItem().getCount()));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random rand)
    {
        int meta = this.getMetaFromState(state);

        if (rand.nextInt(64) == 0)
        {
            if (meta > 0 && meta < 8)
            {
                world.playSound(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, SoundEvents.BLOCK_WATER_AMBIENT, SoundCategory.BLOCKS, rand.nextFloat() * 0.25F + 0.75F, rand.nextFloat() + 0.5F, false);
            }
            else if (rand.nextInt(10) == 0)
            {
                world.spawnParticle(EnumParticleTypes.SUSPENDED, pos.getX() + (double)rand.nextFloat(), pos.getY() + (double)rand.nextFloat(), pos.getZ() + (double)rand.nextFloat(), 0.0D, 0.0D, 0.0D);
            }
        }
        if (rand.nextInt(10) == 0 && world.getBlockState(pos.down()).isSideSolid(world, pos.down(), EnumFacing.UP))
        {
            Material material = world.getBlockState(pos.down(2)).getMaterial();

            if (!material.blocksMovement() && !material.isLiquid())
            {
                double d5 = pos.getX() + rand.nextFloat();
                double d6 = pos.getY() - 1.05D;
                double d7 = pos.getZ() + rand.nextFloat();
                MorePlanetsMod.PROXY.spawnParticle(EnumParticleTypesMP.INFECTED_WATER_DRIP, d5, d6, d7);
            }
        }
    }
}