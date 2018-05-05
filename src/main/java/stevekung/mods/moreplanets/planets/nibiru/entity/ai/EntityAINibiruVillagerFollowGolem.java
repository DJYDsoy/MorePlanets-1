package stevekung.mods.moreplanets.planets.nibiru.entity.ai;

import java.util.List;

import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityIronGolem;
import stevekung.mods.moreplanets.planets.nibiru.entity.EntityNibiruVillager;

public class EntityAINibiruVillagerFollowGolem extends EntityAIBase
{
    private EntityNibiruVillager theVillager;
    private EntityIronGolem theGolem;
    private int takeGolemRoseTick;
    private boolean tookGolemRose;

    public EntityAINibiruVillagerFollowGolem(EntityNibiruVillager theVillager)
    {
        this.theVillager = theVillager;
        this.setMutexBits(3);
    }

    @Override
    public boolean shouldExecute()
    {
        if (this.theVillager.getGrowingAge() >= 0)
        {
            return false;
        }
        else if (!this.theVillager.world.isDaytime())
        {
            return false;
        }
        else
        {
            List<EntityIronGolem> list = this.theVillager.world.<EntityIronGolem>getEntitiesWithinAABB(EntityIronGolem.class, this.theVillager.getEntityBoundingBox().expand(6.0D, 2.0D, 6.0D));

            if (list.isEmpty())
            {
                return false;
            }
            else
            {
                for (EntityIronGolem entityirongolem : list)
                {
                    if (entityirongolem.getHoldRoseTick() > 0)
                    {
                        this.theGolem = entityirongolem;
                        break;
                    }
                }
                return this.theGolem != null;
            }
        }
    }

    @Override
    public boolean shouldContinueExecuting()
    {
        return this.theGolem.getHoldRoseTick() > 0;
    }

    @Override
    public void startExecuting()
    {
        this.takeGolemRoseTick = this.theVillager.getRNG().nextInt(320);
        this.tookGolemRose = false;
        this.theGolem.getNavigator().clearPath();
    }

    @Override
    public void resetTask()
    {
        this.theGolem = null;
        this.theVillager.getNavigator().clearPath();
    }

    @Override
    public void updateTask()
    {
        this.theVillager.getLookHelper().setLookPositionWithEntity(this.theGolem, 30.0F, 30.0F);

        if (this.theGolem.getHoldRoseTick() == this.takeGolemRoseTick)
        {
            this.theVillager.getNavigator().tryMoveToEntityLiving(this.theGolem, 0.5D);
            this.tookGolemRose = true;
        }
        if (this.tookGolemRose && this.theVillager.getDistanceSq(this.theGolem) < 4.0D)
        {
            this.theGolem.setHoldingRose(false);
            this.theVillager.getNavigator().clearPath();
        }
    }
}