package stevekung.mods.moreplanets.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stevekung.mods.moreplanets.core.MorePlanetsMod;
import stevekung.mods.moreplanets.tileentity.TileEntityBlackHoleStorage;
import stevekung.mods.moreplanets.utils.EnumParticleTypesMP;

public class EntityBlackHoleStorage extends Entity
{
    private BlockPos mainTilePos;
    private static final DataParameter<String> COLLECT_MODE = EntityDataManager.createKey(EntityBlackHoleStorage.class, DataSerializers.STRING);
    private static final DataParameter<Boolean> DISABLE = EntityDataManager.createKey(EntityBlackHoleStorage.class, DataSerializers.BOOLEAN);

    public EntityBlackHoleStorage(World world)
    {
        super(world);
        this.preventEntitySpawning = true;
        this.setSize(1.0F, 1.0F);
    }

    @SideOnly(Side.CLIENT)
    public EntityBlackHoleStorage(World world, double x, double y, double z)
    {
        this(world);
        this.setPosition(x, y, z);
    }

    @Override
    public boolean canBeCollidedWith()
    {
        return true;
    }

    @Override
    public void onUpdate()
    {
        super.onUpdate();
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        this.move(MoverType.SELF, 0.0D, 0.0D, 0.0D);

        if (this.mainTilePos != null)
        {
            TileEntity tile = this.world.getTileEntity(this.mainTilePos);

            if (tile == null || !(tile instanceof TileEntityBlackHoleStorage))
            {
                this.setDead();
            }
        }

        if (!this.isDisable())
        {
            boolean collectAll = this.getCollectMode().equals("item_and_xp");

            if (this.getCollectMode().equals("item") || collectAll)
            {
                int range = 12;

                this.world.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(this.posX - range, this.posY - range, this.posZ - range, this.posX + range, this.posY + range, this.posZ + range)).forEach(entity ->
                {
                    double motionX = this.posX - entity.posX;
                    double motionY = this.posY - entity.posY + 0.5D;
                    double motionZ = this.posZ - entity.posZ;
                    entity.motionX = motionX * 0.1F;
                    entity.motionY = motionY * 0.1F;
                    entity.motionZ = motionZ * 0.1F;
                });
            }
            if (this.getCollectMode().equals("xp") || collectAll)
            {
                int range = 12;

                this.world.getEntitiesWithinAABB(EntityXPOrb.class, new AxisAlignedBB(this.posX - range, this.posY - range, this.posZ - range, this.posX + range, this.posY + range, this.posZ + range)).forEach(entity ->
                {
                    double motionX = this.posX - entity.posX;
                    double motionY = this.posY - entity.posY + 0.5D;
                    double motionZ = this.posZ - entity.posZ;
                    entity.motionX = motionX * 0.1F;
                    entity.motionY = motionY * 0.1F;
                    entity.motionZ = motionZ * 0.1F;
                    entity.delayBeforeCanPickup = 5;
                });
            }
            if (this.world.isRemote)
            {
                for (int i = 0; i < 16; ++i)
                {
                    double d0 = this.posX + this.rand.nextFloat();
                    double d1 = this.posY + this.rand.nextFloat();
                    double d2 = this.posZ + this.rand.nextFloat();
                    double d3 = (this.rand.nextFloat() - 0.5D) * 0.5D;
                    double d4 = (this.rand.nextFloat() - 0.5D) * 0.5D;
                    double d5 = (this.rand.nextFloat() - 0.5D) * 0.5D;
                    int j = this.rand.nextInt(2) * 2 - 1;
                    d0 = this.posX + 0.25D * j;
                    d3 = this.rand.nextFloat() * 2.0F * j;
                    d2 = this.posZ + 0.25D * j;
                    d5 = this.rand.nextFloat() * 2.0F * j;
                    MorePlanetsMod.PROXY.spawnParticle(EnumParticleTypesMP.DARK_PORTAL, d0, d1, d2, d3, d4, d5);
                }
            }
        }
    }

    @Override
    protected void entityInit()
    {
        this.dataManager.register(EntityBlackHoleStorage.COLLECT_MODE, "item");
        this.dataManager.register(EntityBlackHoleStorage.DISABLE, false);
    }

    @Override
    protected void readEntityFromNBT(NBTTagCompound nbt)
    {
        this.mainTilePos = new BlockPos(nbt.getInteger("MainTileX"), nbt.getInteger("MainTileY"), nbt.getInteger("MainTileZ"));
        this.setDisable(nbt.getBoolean("DisableBlackHole"));
        this.setCollectMode(nbt.getString("CollectMode"));
    }

    @Override
    protected void writeEntityToNBT(NBTTagCompound nbt)
    {
        if (this.mainTilePos != null)
        {
            nbt.setInteger("MainTileX", this.mainTilePos.getX());
            nbt.setInteger("MainTileY", this.mainTilePos.getY());
            nbt.setInteger("MainTileZ", this.mainTilePos.getZ());
        }
        if (this.isDisable())
        {
            nbt.setBoolean("DisableBlackHole", this.isDisable());
        }
        if (this.getCollectMode() != null)
        {
            nbt.setString("CollectMode", this.getCollectMode());
        }
    }

    public void setTileEntityPos(BlockPos pos)
    {
        this.mainTilePos = pos;
    }

    public boolean isDisable()
    {
        return this.dataManager.get(EntityBlackHoleStorage.DISABLE);
    }

    public void setDisable(boolean disable)
    {
        this.dataManager.set(EntityBlackHoleStorage.DISABLE, disable);
    }

    public String getCollectMode()
    {
        return this.dataManager.get(EntityBlackHoleStorage.COLLECT_MODE);
    }

    public void setCollectMode(String mode)
    {
        this.dataManager.set(EntityBlackHoleStorage.COLLECT_MODE, mode);
    }
}