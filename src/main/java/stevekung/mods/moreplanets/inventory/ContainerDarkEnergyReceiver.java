package stevekung.mods.moreplanets.inventory;

import micdoodle8.mods.galacticraft.api.item.IItemElectric;
import micdoodle8.mods.galacticraft.core.energy.EnergyUtil;
import micdoodle8.mods.galacticraft.core.inventory.SlotSpecific;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import stevekung.mods.moreplanets.tileentity.TileEntityDarkEnergyReceiver;

public class ContainerDarkEnergyReceiver extends Container
{
    private TileEntityDarkEnergyReceiver tileEntity;

    public ContainerDarkEnergyReceiver(InventoryPlayer invPlayer, TileEntityDarkEnergyReceiver tile)
    {
        this.tileEntity = tile;
        this.addSlotToContainer(new SlotSpecific(tile, 0, 32, 27, IItemElectric.class));

        int i;
        int j;

        for (i = 0; i < 3; ++i)
        {
            for (j = 0; j < 9; ++j)
            {
                this.addSlotToContainer(new Slot(invPlayer, j + i * 9 + 9, 8 + j * 18, 46 + 58 + i * 18));
            }
        }

        for (i = 0; i < 9; ++i)
        {
            this.addSlotToContainer(new Slot(invPlayer, i, 8 + i * 18, 46 + 116));
        }
    }

    @Override
    public boolean canInteractWith(EntityPlayer player)
    {
        return this.tileEntity.isUsableByPlayer(player);
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slot)
    {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot invSlot = this.inventorySlots.get(slot);
        int slotSize = this.inventorySlots.size();

        if (invSlot != null && invSlot.getHasStack())
        {
            ItemStack stack = invSlot.getStack();
            itemStack = stack.copy();
            boolean movedToMachineSlot = false;

            if (slot == 0)
            {
                if (!this.mergeItemStack(stack, slotSize - 36, slotSize, true))
                {
                    return ItemStack.EMPTY;
                }
            }
            else
            {
                if (EnergyUtil.isElectricItem(stack.getItem()))
                {
                    if (!this.mergeItemStack(stack, 0, 1, false))
                    {
                        return ItemStack.EMPTY;
                    }
                    movedToMachineSlot = true;
                }
                else
                {
                    if (slot < slotSize - 9)
                    {
                        if (!this.mergeItemStack(stack, slotSize - 9, slotSize, false))
                        {
                            return ItemStack.EMPTY;
                        }
                    }
                    else if (!this.mergeItemStack(stack, slotSize - 36, slotSize - 9, false))
                    {
                        return ItemStack.EMPTY;
                    }
                }
            }

            if (stack.getCount() == 0)
            {
                if (movedToMachineSlot && itemStack.getCount() > 1)
                {
                    ItemStack remainder = itemStack.copy();
                    remainder.shrink(1);
                    invSlot.putStack(remainder);
                }
                else
                {
                    invSlot.putStack(ItemStack.EMPTY);
                }
            }
            else
            {
                invSlot.onSlotChanged();
            }

            if (stack.getCount() == itemStack.getCount())
            {
                return ItemStack.EMPTY;
            }
            invSlot.onTake(player, stack);
        }
        return itemStack;
    }
}