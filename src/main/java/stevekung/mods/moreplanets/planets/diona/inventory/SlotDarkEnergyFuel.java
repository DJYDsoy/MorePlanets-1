package stevekung.mods.moreplanets.planets.diona.inventory;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import stevekung.mods.moreplanets.planets.diona.items.DionaItems;

public class SlotDarkEnergyFuel extends Slot
{
    public SlotDarkEnergyFuel(IInventory inv, int slotIndex, int x, int y)
    {
        super(inv, slotIndex, x, y);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack)
    {
        return itemStack.getItem() == DionaItems.DARK_ENERGY_PEARL;
    }
}