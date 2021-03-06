package stevekung.mods.moreplanets.module.planets.nibiru.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stevekung.mods.moreplanets.util.DamageSourceMP;
import stevekung.mods.moreplanets.util.blocks.BlockBushMP;
import stevekung.mods.moreplanets.util.blocks.EnumSortCategoryBlock;
import stevekung.mods.moreplanets.util.helper.BlockEventHelper;
import stevekung.mods.moreplanets.util.helper.BlockStateHelper;

public class BlockInfectedCactus extends BlockBushMP
{
    protected static AxisAlignedBB CACTUS_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 0.9375D, 0.9375D);
    protected static AxisAlignedBB CACTUS_COLLISION_AABB = new AxisAlignedBB(0.0625D, 0.0D, 0.0625D, 0.9375D, 1.0D, 0.9375D);

    public BlockInfectedCactus(String name)
    {
        super(Material.CACTUS);
        this.setUnlocalizedName(name);
        this.setHardness(0.4F);
        this.setSoundType(SoundType.CLOTH);
        this.setTickRandomly(true);
    }

    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand)
    {
        BlockPos blockpos = pos.up();

        if (world.isAirBlock(blockpos))
        {
            int i;

            for (i = 1; world.getBlockState(pos.down(i)).getBlock() == this; ++i) {}

            if (i < 3)
            {
                int j = state.getValue(BlockStateHelper.AGE).intValue();

                if (j == 15)
                {
                    world.setBlockState(blockpos, this.getDefaultState());
                    IBlockState iblockstate = state.withProperty(BlockStateHelper.AGE, Integer.valueOf(0));
                    world.setBlockState(pos, iblockstate, 4);
                    this.neighborChanged(iblockstate, world, blockpos, this);
                }
                else
                {
                    world.setBlockState(pos, state.withProperty(BlockStateHelper.AGE, Integer.valueOf(j + 1)), 4);
                }
            }
        }
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState state, World world, BlockPos pos)
    {
        return CACTUS_AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public AxisAlignedBB getSelectedBoundingBox(IBlockState state, World world, BlockPos pos)
    {
        return CACTUS_COLLISION_AABB.offset(pos);
    }

    @Override
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @Override
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block)
    {
        if (!this.canBlockStay(world, pos, state))
        {
            world.destroyBlock(pos, true);
        }
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state)
    {
        for (EnumFacing enumfacing : EnumFacing.Plane.HORIZONTAL)
        {
            if (world.getBlockState(pos.offset(enumfacing)).getMaterial().isSolid())
            {
                return false;
            }
        }
        if (BlockEventHelper.isLiquidBlock(world, pos))
        {
            return false;
        }
        return world.getBlockState(pos.down()).getBlock() == NibiruBlocks.INFECTED_SAND || world.getBlockState(pos.down()).getBlock() == this;
    }

    @Override
    public void onEntityCollidedWithBlock(World world, BlockPos pos, IBlockState state, Entity entity)
    {
        entity.attackEntityFrom(DamageSourceMP.INFECTED_GAS, (int) (4.0D * 0.1D + 1.0D));
        entity.attackEntityFrom(DamageSource.cactus, 1.0F);
    }

    @Override
    public EnumSortCategoryBlock getBlockCategory(int meta)
    {
        return EnumSortCategoryBlock.DECORATION_NON_BLOCK;
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(BlockStateHelper.AGE, Integer.valueOf(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(BlockStateHelper.AGE).intValue();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {BlockStateHelper.AGE});
    }

    @Override
    public String getName()
    {
        return "infected_cactus";
    }
}