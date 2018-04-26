package stevekung.mods.moreplanets.module.planets.nibiru.blocks;

import net.minecraft.block.BlockSlab;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import stevekung.mods.moreplanets.util.VariantsName;
import stevekung.mods.moreplanets.util.blocks.BlockSlabMP;
import stevekung.mods.moreplanets.util.blocks.EnumSortCategoryBlock;

public class BlockHalfNibiruSandstoneSlab extends BlockSlabMP
{
    public static PropertyEnum<BlockType> VARIANT = PropertyEnum.create("variant", BlockType.class);

    public BlockHalfNibiruSandstoneSlab(String name)
    {
        super(Material.ROCK);
        this.setUnlocalizedName(name);
        this.useNeighborBrightness = true;
        this.setHardness(0.8F);
        IBlockState state = this.blockState.getBaseState();

        if (!this.isDouble())
        {
            state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);
        }
        this.setDefaultState(state.withProperty(VARIANT, BlockType.NIBIRU_SANDSTONE_SLAB));
    }

    public BlockHalfNibiruSandstoneSlab(Material material)
    {
        super(material);
        this.useNeighborBrightness = true;
        this.setHardness(0.8F);
        IBlockState state = this.blockState.getBaseState();

        if (!this.isDouble())
        {
            state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);
        }
        this.setDefaultState(state.withProperty(VARIANT, BlockType.NIBIRU_SANDSTONE_SLAB));
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
    public IProperty<?> getVariantProperty()
    {
        return VARIANT;
    }

    @Override
    public Comparable<?> getTypeForItem(ItemStack itemStack)
    {
        return BlockType.valuesCached()[itemStack.getMetadata() & 7];
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        IBlockState state = this.getDefaultState().withProperty(VARIANT, BlockType.valuesCached()[meta & 7]);

        if (!this.isDouble())
        {
            state = state.withProperty(HALF, (meta & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
        }
        return state;
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        byte b0 = 0;
        int i = b0 | state.getValue(VARIANT).ordinal();

        if (!this.isDouble() && state.getValue(HALF) == EnumBlockHalf.TOP)
        {
            i |= 8;
        }
        return i;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return this.isDouble() ? new BlockStateContainer(this, VARIANT) : new BlockStateContainer(this, HALF, VARIANT);
    }

    @Override
    public EnumSortCategoryBlock getBlockCategory()
    {
        return EnumSortCategoryBlock.SLAB_STONE;
    }

    @Override
    public BlockSlabMP getHalf()
    {
        return NibiruBlocks.HALF_NIBIRU_SANDSTONE_SLAB;
    }

    @Override
    public BlockSlabMP getDouble()
    {
        return NibiruBlocks.DOUBLE_NIBIRU_SANDSTONE_SLAB;
    }

    @Override
    public VariantsName getVariantsName()
    {
        return new VariantsName("default", "smooth");
    }

    public static enum BlockType implements IStringSerializable
    {
        NIBIRU_SANDSTONE_SLAB,
        NIBIRU_SMOOTH_SANDSTONE_SLAB;

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