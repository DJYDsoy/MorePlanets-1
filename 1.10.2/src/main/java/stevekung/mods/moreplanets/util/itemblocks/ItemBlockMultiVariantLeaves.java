package stevekung.mods.moreplanets.util.itemblocks;

import net.minecraft.block.Block;
import stevekung.mods.moreplanets.util.blocks.IBlockVariants;

public class ItemBlockMultiVariantLeaves extends ItemBlockBaseMP
{
    public ItemBlockMultiVariantLeaves(Block block)
    {
        super(block);
    }

    @Override
    public int getMetadata(int meta)
    {
        return meta | 4;
    }

    @Override
    protected String[] getBlockVariantsName()
    {
        if (this.block instanceof IBlockVariants)
        {
            return ((IBlockVariants)this.block).getVariantsName().getNameList();
        }
        return new String[] {};
    }
}