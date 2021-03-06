package stevekung.mods.moreplanets.module.planets.nibiru.blocks;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stevekung.mods.moreplanets.module.planets.nibiru.entity.EntityInfectedWorm;
import stevekung.mods.moreplanets.util.VariantsName;
import stevekung.mods.moreplanets.util.blocks.BlockBaseMP;
import stevekung.mods.moreplanets.util.blocks.EnumSortCategoryBlock;
import stevekung.mods.moreplanets.util.blocks.IBlockVariants;

public class BlockNibiruSilverfish extends BlockBaseMP implements IBlockVariants
{
    public static PropertyEnum VARIANT = PropertyEnum.create("variant", BlockType.class);

    public BlockNibiruSilverfish(String name)
    {
        super(Material.clay);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockType.NIBIRU_ROCK));
        this.setHardness(0.75F);
        this.setUnlocalizedName(name);
    }

    @Override
    public int quantityDropped(Random rand)
    {
        return 0;
    }

    @Override
    protected ItemStack createStackedBlock(IBlockState state)
    {
        if (state == state.withProperty(VARIANT, BlockType.NIBIRU_COBBLESTONE))
        {
            return new ItemStack(NibiruBlocks.NIBIRU_BLOCK, 1, 1);
        }
        else if (state == state.withProperty(VARIANT, BlockType.INFECTED_STONE_BRICKS))
        {
            return new ItemStack(NibiruBlocks.NIBIRU_BLOCK, 1, 3);
        }
        else if (state == state.withProperty(VARIANT, BlockType.INFECTED_VEIN_STONE_BRICKS))
        {
            return new ItemStack(NibiruBlocks.NIBIRU_BLOCK, 1, 4);
        }
        else if (state == state.withProperty(VARIANT, BlockType.INFECTED_CRACKED_STONE_BRICKS))
        {
            return new ItemStack(NibiruBlocks.NIBIRU_BLOCK, 1, 5);
        }
        else if (state == state.withProperty(VARIANT, BlockType.INFECTED_CHISELED_STONE_BRICKS))
        {
            return new ItemStack(NibiruBlocks.NIBIRU_BLOCK, 1, 6);
        }
        else
        {
            return new ItemStack(NibiruBlocks.NIBIRU_BLOCK, 1, 0);
        }
    }

    @Override
    public void dropBlockAsItemWithChance(World world, BlockPos pos, IBlockState state, float chance, int fortune)
    {
        if (!world.isRemote && world.getGameRules().getBoolean("doTileDrops"))
        {
            EntityInfectedWorm entitysilverfish = new EntityInfectedWorm(world);
            entitysilverfish.setLocationAndAngles(pos.getX() + 0.5D, pos.getY(), pos.getZ() + 0.5D, 0.0F, 0.0F);
            world.spawnEntityInWorld(entitysilverfish);
            entitysilverfish.spawnExplosionParticle();
        }
    }

    @Override
    public int getDamageValue(World world, BlockPos pos)
    {
        IBlockState iblockstate = world.getBlockState(pos);
        return iblockstate.getBlock().getMetaFromState(iblockstate);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubBlocks(Item item, CreativeTabs creativeTabs, List list)
    {
        for (int i = 0; i < BlockType.valuesCached().length; ++i)
        {
            list.add(new ItemStack(item, 1, i));
        }
    }

    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] { VARIANT });
    }

    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        return this.getDefaultState().withProperty(VARIANT, BlockType.valuesCached()[meta]);
    }

    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((BlockType)state.getValue(VARIANT)).ordinal();
    }

    @Override
    public VariantsName getVariantsName()
    {
        return new VariantsName("nibiru_rock", "nibiru_cobblestone", "infected_stone_bricks", "infected_vein_stone_bricks", "infected_cracked_stone_bricks", "infected_chiseled_stone_bricks");
    }

    @Override
    public EnumSortCategoryBlock getBlockCategory(int meta)
    {
        return EnumSortCategoryBlock.DECORATION_BLOCK;
    }

    public static enum BlockType implements IStringSerializable
    {
        NIBIRU_ROCK(NibiruBlocks.NIBIRU_BLOCK.getDefaultState().withProperty(BlockNibiru.VARIANT, BlockNibiru.BlockType.NIBIRU_ROCK)),
        NIBIRU_COBBLESTONE(NibiruBlocks.NIBIRU_BLOCK.getDefaultState().withProperty(BlockNibiru.VARIANT, BlockNibiru.BlockType.NIBIRU_COBBLESTONE)),
        INFECTED_STONE_BRICKS(NibiruBlocks.NIBIRU_BLOCK.getDefaultState().withProperty(BlockNibiru.VARIANT, BlockNibiru.BlockType.INFECTED_STONE_BRICKS)),
        INFECTED_VEIN_STONE_BRICKS(NibiruBlocks.NIBIRU_BLOCK.getDefaultState().withProperty(BlockNibiru.VARIANT, BlockNibiru.BlockType.INFECTED_VEIN_STONE_BRICKS)),
        INFECTED_CRACKED_STONE_BRICKS(NibiruBlocks.NIBIRU_BLOCK.getDefaultState().withProperty(BlockNibiru.VARIANT, BlockNibiru.BlockType.INFECTED_CRACKED_STONE_BRICKS)),
        INFECTED_CHISELED_STONE_BRICKS(NibiruBlocks.NIBIRU_BLOCK.getDefaultState().withProperty(BlockNibiru.VARIANT, BlockNibiru.BlockType.INFECTED_CHISELED_STONE_BRICKS));

        IBlockState parent;
        private static BlockType[] values = BlockType.values();

        private BlockType(IBlockState parent)
        {
            this.parent = parent;
        }

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

        public static BlockType getParentBlock(IBlockState parent)
        {
            for (BlockType type : BlockType.values())
            {
                if (parent == type.parent)
                {
                    return type;
                }
            }
            return NIBIRU_ROCK;
        }
    }
}