package stevekung.mods.moreplanets.module.planets.nibiru.blocks;

import java.util.Random;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stevekung.mods.moreplanets.util.blocks.BlockBreakableMP;

public class BlockHugeTerrashroom extends BlockBreakableMP
{
    public static PropertyEnum<BlockType> VARIANT = PropertyEnum.create("variant", BlockType.class);

    public BlockHugeTerrashroom(String name)
    {
        super(Material.WOOD);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockType.ALL_OUTSIDE));
        this.setHardness(0.2F);
        this.setLightOpacity(3);
        this.setSoundType(SoundType.WOOD);
        this.setUnlocalizedName(name);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @Override
    public int quantityDropped(Random rand)
    {
        return Math.max(0, rand.nextInt(10) - 7);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune)
    {
        return Item.getItemFromBlock(NibiruBlocks.NIBIRU_FLOWER);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return 7;
    }

    @Override
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
        return this.getDefaultState();
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, BlockType.valuesCached()[meta]);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this, 1, 0);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(VARIANT).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, new IProperty[] {VARIANT});
    }

    @Override
    public boolean rotateBlock(World world, BlockPos pos, EnumFacing axis)
    {
        IBlockState state = world.getBlockState(pos);

        for (IProperty prop : state.getProperties().keySet())
        {
            if (prop.getName().equals("variant"))
            {
                world.setBlockState(pos, state.cycleProperty(prop));
                return true;
            }
        }
        return false;
    }

    @Override
    protected boolean isTranslucent()
    {
        return true;
    }

    @Override
    protected boolean renderSideWithState()
    {
        return false;
    }

    @Override
    public String getName()
    {
        return "huge_terrashroom_block";
    }

    @Override
    public IBlockState withRotation(IBlockState state, Rotation rotation)
    {
        switch (rotation)
        {
        case CLOCKWISE_180:
            switch (state.getValue(VARIANT))
            {
            case NORTH_WEST:
                return state.withProperty(VARIANT, BlockType.SOUTH_EAST);
            case NORTH:
                return state.withProperty(VARIANT, BlockType.SOUTH);
            case NORTH_EAST:
                return state.withProperty(VARIANT, BlockType.SOUTH_WEST);
            case WEST:
                return state.withProperty(VARIANT, BlockType.EAST);
            case EAST:
                return state.withProperty(VARIANT, BlockType.WEST);
            case SOUTH_WEST:
                return state.withProperty(VARIANT, BlockType.NORTH_EAST);
            case SOUTH:
                return state.withProperty(VARIANT, BlockType.NORTH);
            case SOUTH_EAST:
                return state.withProperty(VARIANT, BlockType.NORTH_WEST);
            default:
                return state;
            }
        case COUNTERCLOCKWISE_90:
            switch (state.getValue(VARIANT))
            {
            case NORTH_WEST:
                return state.withProperty(VARIANT, BlockType.SOUTH_WEST);
            case NORTH:
                return state.withProperty(VARIANT, BlockType.WEST);
            case NORTH_EAST:
                return state.withProperty(VARIANT, BlockType.NORTH_WEST);
            case WEST:
                return state.withProperty(VARIANT, BlockType.SOUTH);
            case EAST:
                return state.withProperty(VARIANT, BlockType.NORTH);
            case SOUTH_WEST:
                return state.withProperty(VARIANT, BlockType.SOUTH_EAST);
            case SOUTH:
                return state.withProperty(VARIANT, BlockType.EAST);
            case SOUTH_EAST:
                return state.withProperty(VARIANT, BlockType.NORTH_EAST);
            default:
                return state;
            }
        case CLOCKWISE_90:
            switch (state.getValue(VARIANT))
            {
            case NORTH_WEST:
                return state.withProperty(VARIANT, BlockType.NORTH_EAST);
            case NORTH:
                return state.withProperty(VARIANT, BlockType.EAST);
            case NORTH_EAST:
                return state.withProperty(VARIANT, BlockType.SOUTH_EAST);
            case WEST:
                return state.withProperty(VARIANT, BlockType.NORTH);
            case EAST:
                return state.withProperty(VARIANT, BlockType.SOUTH);
            case SOUTH_WEST:
                return state.withProperty(VARIANT, BlockType.NORTH_WEST);
            case SOUTH:
                return state.withProperty(VARIANT, BlockType.WEST);
            case SOUTH_EAST:
                return state.withProperty(VARIANT, BlockType.SOUTH_WEST);
            default:
                return state;
            }
        default:
            return state;
        }
    }

    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirror)
    {
        BlockType type = state.getValue(VARIANT);

        switch (mirror)
        {
        case LEFT_RIGHT:
            switch (type)
            {
            case NORTH_WEST:
                return state.withProperty(VARIANT, BlockType.SOUTH_WEST);
            case NORTH:
                return state.withProperty(VARIANT, BlockType.SOUTH);
            case NORTH_EAST:
                return state.withProperty(VARIANT, BlockType.SOUTH_EAST);
            case WEST:
            case EAST:
            default:
                return super.withMirror(state, mirror);
            case SOUTH_WEST:
                return state.withProperty(VARIANT, BlockType.NORTH_WEST);
            case SOUTH:
                return state.withProperty(VARIANT, BlockType.NORTH);
            case SOUTH_EAST:
                return state.withProperty(VARIANT, BlockType.NORTH_EAST);
            }
        case FRONT_BACK:
            switch (type)
            {
            case NORTH_WEST:
                return state.withProperty(VARIANT, BlockType.NORTH_EAST);
            case NORTH:
            case SOUTH:
            default:
                return super.withMirror(state, mirror);
            case NORTH_EAST:
                return state.withProperty(VARIANT, BlockType.NORTH_WEST);
            case WEST:
                return state.withProperty(VARIANT, BlockType.EAST);
            case EAST:
                return state.withProperty(VARIANT, BlockType.WEST);
            case SOUTH_WEST:
                return state.withProperty(VARIANT, BlockType.SOUTH_EAST);
            case SOUTH_EAST:
                return state.withProperty(VARIANT, BlockType.SOUTH_WEST);
            }
        default:
            return super.withMirror(state, mirror);
        }
    }

    public static enum BlockType implements IStringSerializable
    {
        ALL_INSIDE,
        NORTH_WEST,
        NORTH,
        NORTH_EAST,
        WEST,
        CENTER,
        EAST,
        SOUTH_WEST,
        SOUTH,
        SOUTH_EAST,
        STEM,
        ALL_OUTSIDE,
        ALL_STEM;

        private static BlockType[] values = BlockType.values();

        public static BlockType[] valuesCached()
        {
            return BlockType.values;
        }

        @Override
        public String toString()
        {
            return this.name().toLowerCase();
        }

        @Override
        public String getName()
        {
            return this.name().toLowerCase();
        }
    }
}