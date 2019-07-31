package stevekung.mods.moreplanets.module.planets.fronos.blocks;

import micdoodle8.mods.galacticraft.api.block.ITerraformableBlock;
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
import net.minecraft.world.World;
import stevekung.mods.moreplanets.util.VariantsName;
import stevekung.mods.moreplanets.util.blocks.BlockBaseMP;
import stevekung.mods.moreplanets.util.blocks.IBlockVariants;

public class BlockFronosDirt extends BlockBaseMP implements ITerraformableBlock, IBlockVariants
{
    public static PropertyEnum<BlockType> VARIANT = PropertyEnum.create("variant", BlockType.class);

    public BlockFronosDirt(String name)
    {
        super(Material.GROUND);
        this.setSoundType(SoundType.GROUND);
        this.setHardness(0.55F);
        this.setDefaultState(this.getDefaultState().withProperty(VARIANT, BlockType.FRONOS_DIRT));
        this.setUnlocalizedName(name);
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        return this.getMetaFromState(state);
    }

    @Override
    public boolean isTerraformable(World world, BlockPos pos)
    {
        return !world.getBlockState(pos.up()).isOpaqueCube();
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
    public VariantsName getVariantsName()
    {
        return new VariantsName("dirt", "coarse");
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

    public static enum BlockType implements IStringSerializable
    {
        FRONOS_DIRT,
        FRONOS_COARSE_DIRT;

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