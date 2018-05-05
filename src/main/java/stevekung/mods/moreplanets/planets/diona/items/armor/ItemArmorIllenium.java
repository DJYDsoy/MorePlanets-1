package stevekung.mods.moreplanets.planets.diona.items.armor;

import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import stevekung.mods.moreplanets.planets.diona.items.DionaItems;
import stevekung.mods.moreplanets.utils.items.armor.ItemArmorMP;

public class ItemArmorIllenium extends ItemArmorMP
{
    public ItemArmorIllenium(String name, ArmorMaterial material, EntityEquipmentSlot type)
    {
        super(material, type);
        this.setUnlocalizedName(name);
    }

    @Override
    public String getArmorTexture(ItemStack itemStack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        if (itemStack.getItem() == DionaItems.ILLENIUM_HELMET || itemStack.getItem() == DionaItems.ILLENIUM_CHESTPLATE || itemStack.getItem() == DionaItems.ILLENIUM_BOOTS)
        {
            return "moreplanets:textures/model/armor/illenium_1.png";
        }
        else if (itemStack.getItem() == DionaItems.ILLENIUM_LEGGINGS)
        {
            return "moreplanets:textures/model/armor/illenium_2.png";
        }
        return null;
    }

    @Override
    public Item getRepairItem()
    {
        return DionaItems.COMPRESSED_ILLENIUM;
    }
}