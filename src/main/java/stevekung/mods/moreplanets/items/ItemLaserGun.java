package stevekung.mods.moreplanets.items;

import javax.annotation.Nullable;

import micdoodle8.mods.galacticraft.api.item.ElectricItemHelper;
import micdoodle8.mods.galacticraft.core.energy.item.ItemElectricBase;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stevekung.mods.moreplanets.core.MorePlanetsMod;
import stevekung.mods.moreplanets.entity.projectile.EntityLaserBullet;
import stevekung.mods.moreplanets.entity.projectile.EntityLaserBullet.EnumLaserType;
import stevekung.mods.moreplanets.init.MPItems;
import stevekung.mods.moreplanets.init.MPSounds;
import stevekung.mods.moreplanets.util.helper.CommonRegisterHelper;
import stevekung.mods.moreplanets.util.items.EnumSortCategoryItem;
import stevekung.mods.moreplanets.util.items.ISingleItemRender;
import stevekung.mods.moreplanets.util.items.ISortableItem;

public class ItemLaserGun extends ItemElectricBase implements ISortableItem, ISingleItemRender
{
    public ItemLaserGun(String name)
    {
        super();
        this.setMaxStackSize(1);
        this.setUnlocalizedName(name);

        this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter()
        {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack itemStack, @Nullable World world, @Nullable EntityLivingBase living)
            {
                if (living == null)
                {
                    return 0.0F;
                }
                else
                {
                    ItemStack gun = living.getActiveItemStack();

                    if (living instanceof EntityPlayer)
                    {
                        EntityPlayer player = (EntityPlayer) living;

                        if (!gun.isEmpty() && gun.getItem() == MPItems.LASER_GUN)
                        {
                            int i = itemStack.getMaxItemUseDuration() - player.getItemInUseCount();

                            if (i > 12)
                            {
                                return 0.9F;
                            }
                            if (i > 0)
                            {
                                return 0.65F;
                            }
                        }
                    }
                    return 0.0F;
                }
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
        {
            @Override
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack itemStack, @Nullable World world, @Nullable EntityLivingBase living)
            {
                return living != null && living.isHandActive() && living.getActiveItemStack() == itemStack ? 1.0F : 0.0F;
            }
        });
    }

    @Override
    public EnumAction getItemUseAction(ItemStack itemStack)
    {
        return EnumAction.BOW;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack itemStack)
    {
        return 16;
    }

    @Override
    public CreativeTabs getCreativeTab()
    {
        return MorePlanetsMod.ITEM_TAB;
    }

    @Override
    public void getSubItems(CreativeTabs creativeTabs, NonNullList<ItemStack> list)
    {
        if (CommonRegisterHelper.isItemTab(creativeTabs))
        {
            list.add(ElectricItemHelper.getUncharged(new ItemStack(this)));
            list.add(ElectricItemHelper.getWithCharge(new ItemStack(this), this.getMaxElectricityStored(new ItemStack(this))));
        }
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack itemStack, World world, EntityLivingBase living)
    {
        if (living instanceof EntityPlayer)
        {
            EntityPlayer player = (EntityPlayer) living;
            boolean flag = player.capabilities.isCreativeMode;
            ItemStack bulletStack = this.findBullet(player);

            if (this.getElectricityStored(itemStack) > 0.0F && (flag || !bulletStack.isEmpty()))
            {
                EntityLaserBullet laser = new EntityLaserBullet(world, player, 1.0F);
                world.playSound(player, player.getPosition(), MPSounds.LASER_SHOOTED, SoundCategory.PLAYERS, 1.0F, 2.0F / (1.0F * 0.4F + 1.2F) + 1.0F * 0.5F);
                int slot = -1;

                if (bulletStack.isEmpty())
                {
                    bulletStack = new ItemStack(MPItems.LASER_BULLET);
                }
                for (int i = 0; i < player.inventory.mainInventory.size(); ++i)
                {
                    if (!player.inventory.mainInventory.get(i).isEmpty() && player.inventory.mainInventory.get(i).getItem() == bulletStack.getItem())
                    {
                        int meta = player.inventory.mainInventory.get(i).getItemDamage();

                        if (meta == 0)
                        {
                            laser.setLaserType(EnumLaserType.NORMAL);
                        }
                        if (meta == 1)
                        {
                            laser.setLaserType(EnumLaserType.INFECTED_CRYSTALLIZE);
                        }
                        slot = i;
                        break;
                    }
                }
                if (!world.isRemote)
                {
                    world.spawnEntity(laser);
                }
                if (!flag && slot >= 0)
                {
                    this.setElectricity(itemStack, this.getElectricityStored(itemStack) - 50.0F);
                    player.inventory.decrStackSize(slot, 1);
                }
            }
        }
        return itemStack;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand)
    {
        boolean flag = !this.findBullet(player).isEmpty();
        ItemStack itemStack = player.getHeldItem(hand);

        if (this.getElectricityStored(itemStack) > 0.0F)
        {
            if (!player.capabilities.isCreativeMode && !flag)
            {
                return !flag ? new ActionResult<>(EnumActionResult.FAIL, itemStack) : new ActionResult<>(EnumActionResult.PASS, itemStack);
            }
            else
            {
                player.setActiveHand(hand);
                return new ActionResult<>(EnumActionResult.SUCCESS, itemStack);
            }
        }
        return new ActionResult<>(EnumActionResult.PASS, itemStack);
    }

    @Override
    public float getMaxElectricityStored(ItemStack itemStack)
    {
        return 100000.0F;
    }

    @Override
    public EnumSortCategoryItem getItemCategory(int meta)
    {
        return EnumSortCategoryItem.OTHER_TOOL;
    }

    @Override
    public String getName()
    {
        return "laser_gun";
    }

    private ItemStack findBullet(EntityPlayer player)
    {
        if (this.isBullet(player.getHeldItem(EnumHand.OFF_HAND)))
        {
            return player.getHeldItem(EnumHand.OFF_HAND);
        }
        else if (this.isBullet(player.getHeldItem(EnumHand.MAIN_HAND)))
        {
            return player.getHeldItem(EnumHand.MAIN_HAND);
        }
        else
        {
            for (int i = 0; i < player.inventory.getSizeInventory(); ++i)
            {
                ItemStack itemStack = player.inventory.getStackInSlot(i);

                if (this.isBullet(itemStack))
                {
                    return itemStack;
                }
            }
            return ItemStack.EMPTY;
        }
    }

    protected boolean isBullet(ItemStack itemStack)
    {
        return !itemStack.isEmpty() && itemStack.getItem() == MPItems.LASER_BULLET;
    }
}