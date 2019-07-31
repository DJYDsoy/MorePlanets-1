package stevekung.mods.moreplanets.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import stevekung.mods.moreplanets.util.VariantsName;
import stevekung.mods.moreplanets.util.blocks.BlockBaseMP;
import stevekung.mods.moreplanets.util.blocks.IBlockVariants;

public class BlockPolishedSpaceDecoration extends BlockBaseMP implements IBlockVariants
{
    public static PropertyEnum<BlockType> VARIANT = PropertyEnum.create("variant", BlockType.class);

    public BlockPolishedSpaceDecoration(String name)
    {
        super(Material.ROCK);
        this.setDefaultState(this.getDefaultState().withProperty(VARIANT, BlockType.POLISHED_TIN_DECORATION_BLOCK));
        this.setHardness(1.5F);
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
    public int damageDropped(IBlockState state)
    {
        return this.getMetaFromState(state);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, BlockType.valuesCached()[meta % BlockType.valuesCached().length]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(VARIANT).ordinal();
    }

    @Override
    public VariantsName getVariantsName()
    {
        return new VariantsName("tin", "aluminum");
    }

    public static enum BlockType implements IStringSerializable
    {
        POLISHED_TIN_DECORATION_BLOCK,
        POLISHED_ALUMINUM_DECORATION_BLOCK;

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