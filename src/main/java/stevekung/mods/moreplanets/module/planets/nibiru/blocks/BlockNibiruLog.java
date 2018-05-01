package stevekung.mods.moreplanets.module.planets.nibiru.blocks;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import stevekung.mods.moreplanets.utils.VariantsName;
import stevekung.mods.moreplanets.utils.blocks.BlockLogMP;
import stevekung.mods.moreplanets.utils.blocks.IBlockVariants;
import stevekung.mods.stevekunglib.utils.BlockStateProperty;

public class BlockNibiruLog extends BlockLogMP implements IBlockVariants
{
    public static PropertyEnum<BlockType> VARIANT = PropertyEnum.create("variant", BlockType.class);

    public BlockNibiruLog(String name)
    {
        super(Material.WOOD);
        this.setDefaultState(this.getDefaultState().withProperty(VARIANT, BlockType.INFECTED_OAK_LOG).withProperty(BlockStateProperty.AXIS, BlockStateProperty.EnumAxis.Y));
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
        return new VariantsName("infected_oak", "infected_dead_oak", "infected_jungle", "alien_berry_oak");
    }

    public static enum BlockType implements IStringSerializable
    {
        INFECTED_OAK_LOG,
        INFECTED_DEAD_OAK_LOG,
        INFECTED_JUNGLE_LOG,
        ALIEN_BERRY_OAK_LOG;

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