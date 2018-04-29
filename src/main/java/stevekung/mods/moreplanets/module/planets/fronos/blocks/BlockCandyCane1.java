package stevekung.mods.moreplanets.module.planets.fronos.blocks;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import stevekung.mods.moreplanets.utils.VariantsName;
import stevekung.mods.moreplanets.utils.blocks.BlockLogMP;
import stevekung.mods.moreplanets.utils.blocks.IBlockVariants;
import stevekung.mods.stevekunglib.utils.BlockStateProperty;

public class BlockCandyCane1 extends BlockLogMP implements IBlockVariants
{
    public static PropertyEnum<BlockType> VARIANT = PropertyEnum.create("variant", BlockType.class);

    public BlockCandyCane1(String name)
    {
        super(Material.CLOTH);
        this.setHardness(0.55F);
        this.setResistance(3.0F);
        this.setSoundType(SoundType.CLOTH);
        this.setDefaultState(this.getDefaultState().withProperty(VARIANT, BlockType.RED_CANDY_CANE).withProperty(BlockStateProperty.AXIS, BlockStateProperty.EnumAxis.Y));
        this.setUnlocalizedName(name);
    }

    @Override
    public void getSubBlocks(CreativeTabs creativeTabs, NonNullList<ItemStack> list)
    {
        for (int i = 0; i < BlockType.valuesCached().length; ++i)
        {
            list.add(new ItemStack(this, 1, i));
        }
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state = this.getDefaultState().withProperty(VARIANT, BlockType.valuesCached()[meta & 3]);

        switch (meta & 12)
        {
        case 0:
            state = state.withProperty(BlockStateProperty.AXIS, BlockStateProperty.EnumAxis.Y);
            break;
        case 4:
            state = state.withProperty(BlockStateProperty.AXIS, BlockStateProperty.EnumAxis.X);
            break;
        case 8:
            state = state.withProperty(BlockStateProperty.AXIS, BlockStateProperty.EnumAxis.Z);
            break;
        default:
            state = state.withProperty(BlockStateProperty.AXIS, BlockStateProperty.EnumAxis.NONE);
        }
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        byte b = 0;
        int i = b | state.getValue(VARIANT).ordinal();

        switch (BlockStateProperty.SwitchEnumAxis.AXIS_LOOKUP[state.getValue(BlockStateProperty.AXIS).ordinal()])
        {
        case 1:
            i |= 4;
            break;
        case 2:
            i |= 8;
            break;
        case 3:
            i |= 12;
        }
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, VARIANT, BlockStateProperty.AXIS);
    }

    @Override
    protected ItemStack getSilkTouchDrop(IBlockState state)
    {
        return new ItemStack(this, 1, state.getValue(VARIANT).ordinal());
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return state.getValue(VARIANT).ordinal();
    }

    @Override
    public VariantsName getVariantsName()
    {
        return new VariantsName("red", "green", "blue", "orange");
    }

    @Override
    public boolean canSustainLeaves(IBlockState state, IBlockAccess world, BlockPos pos)
    {
        return false;
    }

    @Override
    public boolean isWood(IBlockAccess world, BlockPos pos)
    {
        return false;
    }

    public static enum BlockType implements IStringSerializable
    {
        RED_CANDY_CANE,
        GREEN_CANDY_CANE,
        BLUE_CANDY_CANE,
        ORANGE_CANDY_CANE;

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