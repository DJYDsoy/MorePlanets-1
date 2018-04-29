package stevekung.mods.moreplanets.module.moons.koentus.itemblocks;

import micdoodle8.mods.galacticraft.core.TransformerHooks;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import stevekung.mods.moreplanets.utils.itemblocks.ItemBlockMultiVariant;

public class ItemBlockKoentus extends ItemBlockMultiVariant
{
    public ItemBlockKoentus(Block block)
    {
        super(block);
    }

    @Override
    public boolean onEntityItemUpdate(EntityItem entityItem)
    {
        if (entityItem.getItem().getItemDamage() == 4 || entityItem.getItem().getItemDamage() == 10)
        {
            entityItem.motionY += TransformerHooks.getItemGravity(entityItem);
        }
        return false;
    }
}