package stevekung.mods.moreplanets.blocks.decoration;

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
import stevekung.mods.moreplanets.init.MPBlocks;
import stevekung.mods.moreplanets.util.VariantsName;
import stevekung.mods.moreplanets.util.blocks.BlockSlabMP;
import stevekung.mods.moreplanets.util.blocks.EnumSortCategoryBlock;

public class BlockHalfDungeonBrickSlab1 extends BlockSlabMP
{
    public static PropertyEnum<BlockType> VARIANT = PropertyEnum.create("variant", BlockType.class);

    public BlockHalfDungeonBrickSlab1(String name)
    {
        super(Material.ROCK);
        this.setUnlocalizedName(name);
        this.useNeighborBrightness = true;
        this.setHardness(4.0F);
        this.setResistance(40.0F);
        IBlockState state = this.blockState.getBaseState();

        if (!this.isDouble())
        {
            state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);
        }
        this.setDefaultState(state.withProperty(VARIANT, BlockType.DIONA_DUNGEON_BRICK_SLAB));
    }

    public BlockHalfDungeonBrickSlab1(Material material)
    {
        super(material);
        this.useNeighborBrightness = true;
        this.setHardness(4.0F);
        this.setResistance(40.0F);
        IBlockState state = this.blockState.getBaseState();

        if (!this.isDouble())
        {
            state = state.withProperty(HALF, EnumBlockHalf.BOTTOM);
        }
        this.setDefaultState(state.withProperty(VARIANT, BlockType.DIONA_DUNGEON_BRICK_SLAB));
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
        return EnumSortCategoryBlock.SLAB_DUNGEON_BRICK;
    }

    @Override
    public BlockSlabMP getHalf()
    {
        return MPBlocks.HALF_DUNGEON_BRICK_SLAB_1;
    }

    @Override
    public BlockSlabMP getDouble()
    {
        return MPBlocks.DOUBLE_DUNGEON_BRICK_SLAB_1;
    }

    @Override
    public VariantsName getVariantsName()
    {
        return new VariantsName("diona", "chalos", "nibiru");
    }

    public static enum BlockType implements IStringSerializable
    {
        DIONA_DUNGEON_BRICK_SLAB,
        CHALOS_DUNGEON_BRICK_SLAB,
        NIBIRU_DUNGEON_BRICK_SLAB;

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