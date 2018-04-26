package stevekung.mods.moreplanets.module.planets.nibiru.blocks;

import javax.annotation.Nullable;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import stevekung.mods.moreplanets.util.VariantsName;
import stevekung.mods.moreplanets.util.blocks.BlockBaseMP;
import stevekung.mods.moreplanets.util.blocks.EnumSortCategoryBlock;
import stevekung.mods.moreplanets.util.blocks.IBlockVariants;

public class BlockNibiru extends BlockBaseMP implements IBlockVariants
{
    public static PropertyEnum<BlockType> VARIANT = PropertyEnum.create("variant", BlockType.class);

    public BlockNibiru(String name)
    {
        super(Material.ROCK);
        this.setDefaultState(this.getDefaultState().withProperty(VARIANT, BlockType.NIBIRU_ROCK));
        this.setUnlocalizedName(name);
    }

    @Override
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player)
    {
        return new ItemStack(this, 1, this.getMetaFromState(state));
    }

    @Override
    public boolean isBeaconBase(IBlockAccess world, BlockPos pos, BlockPos beacon)
    {
        return this.getMetaFromState(world.getBlockState(pos)) == 7;
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
    public float getBlockHardness(IBlockState state, World world, BlockPos pos)
    {
        int meta = this.getMetaFromState(state);

        if (meta == 0 || meta >= 3 && meta <= 6)
        {
            return 1.5F;
        }
        if (meta == 1 || meta == 2)
        {
            return 2.0F;
        }
        if (meta == 7)
        {
            return 5.0F;
        }
        return 4.0F;
    }

    @Override
    public float getExplosionResistance(World world, BlockPos pos, Entity exploder, Explosion explosion)
    {
        if (this.getMetaFromState(world.getBlockState(pos)) == 8 || this.getMetaFromState(world.getBlockState(pos)) == 9)
        {
            return 40.0F;
        }
        return super.getExplosionResistance(world, pos, exploder, explosion);
    }

    @Override
    public SoundType getSoundType(IBlockState state, World world, BlockPos pos, @Nullable Entity entity)
    {
        return this.getMetaFromState(world.getBlockState(pos)) == 7 ? SoundType.METAL : SoundType.STONE;
    }

    @Override
    public int damageDropped(IBlockState state)
    {
        if (this.getMetaFromState(state) == 0)
        {
            return 1;
        }
        return this.getMetaFromState(state);
    }

    @Override
    public EnumSortCategoryBlock getBlockCategory()
    {
        //        switch (meta)
        //        {
        //        case 7:
        //            return EnumSortCategoryBlock.INGOT_BLOCK;
        //        case 8:
        //        case 9:
        //            return EnumSortCategoryBlock.DUNGEON_BRICK;
        //        default:
        return EnumSortCategoryBlock.BUILDING_BLOCK;
        //        }
    }

    @Override
    public VariantsName getVariantsName()
    {
        return new VariantsName("nibiru_rock", "nibiru_cobblestone", "nibiru_vein_cobblestone", "infected_stone_bricks", "infected_vein_stone_bricks", "infected_cracked_stone_bricks", "infected_chiseled_stone_bricks", "inferumite_block", "nibiru_dungeon_brick", "mossy_nibiru_dungeon_brick");
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, BlockType.valuesCached()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return state.getValue(VARIANT).ordinal();
    }

    public static enum BlockType implements IStringSerializable
    {
        NIBIRU_ROCK,
        NIBIRU_COBBLESTONE,
        NIBIRU_VEIN_COBBLESTONE,
        INFECTED_STONE_BRICKS,
        INFECTED_VEIN_STONE_BRICKS,
        INFECTED_CRACKED_STONE_BRICKS,
        INFECTED_CHISELED_STONE_BRICKS,
        INFERUMITE_BLOCK,
        NIBIRU_DUNGEON_BRICK,
        MOSSY_NIBIRU_DUNGEON_BRICK;

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