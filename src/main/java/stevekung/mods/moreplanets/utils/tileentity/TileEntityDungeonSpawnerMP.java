package stevekung.mods.moreplanets.utils.tileentity;

import java.util.ArrayList;
import java.util.List;

import micdoodle8.mods.galacticraft.core.client.sounds.GCSounds;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;
import micdoodle8.mods.galacticraft.core.tile.TileEntityDungeonSpawner;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import stevekung.mods.moreplanets.core.config.ConfigManagerMP;

public class TileEntityDungeonSpawnerMP<E extends Entity> extends TileEntityDungeonSpawner<E>
{
    public TileEntityDungeonSpawnerMP(Class<E> bossClass)
    {
        super(bossClass);
    }

    @Override
    public List<Class<? extends EntityLiving>> getDisabledCreatures()
    {
        List<Class<? extends EntityLiving>> list = new ArrayList<>();
        list.add(EntityEvolvedSkeleton.class);
        list.add(EntityEvolvedZombie.class);
        list.add(EntityEvolvedSpider.class);
        list.add(EntityEvolvedCreeper.class);
        return list;
    }

    @Override
    public void playSpawnSound(Entity entity)
    {
        this.world.playSound(null, entity.getPosition(), GCSounds.scaryScape, SoundCategory.AMBIENT, 9.0F, 1.4F);
    }

    @Override
    public void update()
    {
        super.update();

        if (this.spawned && ConfigManagerMP.moreplanets_general.enableNightVisionEffect)
        {
            this.world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(this.getPos().getX() - 16.0D, this.getPos().getY() - 16.0D, this.getPos().getZ() - 16.0D, this.getPos().getX() + 16.0D, this.getPos().getY() + 16.0D, this.getPos().getZ() + 16.0D)).forEach(player ->
            {
                player.addPotionEffect(new PotionEffect(MobEffects.NIGHT_VISION, 240));
            });
        }
    }

    @Override
    public NBTTagCompound getUpdateTag()
    {
        return this.writeToNBT(new NBTTagCompound());
    }
}