package stevekung.mods.moreplanets.planets.chalos.world.gen.dungeon;

import java.util.Random;

import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedCreeper;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSkeleton;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedSpider;
import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedZombie;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import stevekung.mods.moreplanets.planets.chalos.entity.EntityCheeseFloater;
import stevekung.mods.moreplanets.utils.world.gen.dungeon.DungeonConfigurationMP;
import stevekung.mods.moreplanets.utils.world.gen.dungeon.RoomEmptyMP;

public class RoomSpawnerChalos extends RoomEmptyMP
{
    public RoomSpawnerChalos() {}

    public RoomSpawnerChalos(DungeonConfigurationMP configuration, Random rand, int blockPosX, int blockPosZ, int sizeX, int sizeY, int sizeZ, EnumFacing entranceDir)
    {
        super(configuration, rand, blockPosX, blockPosZ, sizeX, sizeY, sizeZ, entranceDir);
    }

    public RoomSpawnerChalos(DungeonConfigurationMP configuration, Random rand, int blockPosX, int blockPosZ, EnumFacing entranceDir)
    {
        super(configuration, rand, blockPosX, blockPosZ, rand.nextInt(4) + 6, configuration.getRoomHeight(), rand.nextInt(4) + 6, entranceDir);
    }

    @Override
    public boolean addComponentParts(World world, Random rand, StructureBoundingBox boundingBox)
    {
        if (super.addComponentParts(world, rand, boundingBox))
        {
            for (int i = 1; i <= this.sizeX - 1; ++i)
            {
                for (int j = 1; j <= this.sizeY - 1; ++j)
                {
                    for (int k = 1; k <= this.sizeZ - 1; ++k)
                    {
                        if (rand.nextFloat() < 0.025F)
                        {
                            this.setBlockState(world, this.configuration.getWebBlock(), i, j, k, boundingBox);
                        }
                    }
                }
            }

            this.setBlockState(world, Blocks.MOB_SPAWNER.getDefaultState(), 1, 0, 1, boundingBox);
            this.setBlockState(world, Blocks.MOB_SPAWNER.getDefaultState(), this.sizeX - 1, 0, this.sizeZ - 1, boundingBox);
            BlockPos blockpos = new BlockPos(this.getXWithOffset(1, 1), this.getYWithOffset(0), this.getZWithOffset(1, 1));
            TileEntityMobSpawner spawner = (TileEntityMobSpawner) world.getTileEntity(blockpos);

            if (spawner != null)
            {
                spawner.getSpawnerBaseLogic().setEntityId(EntityList.getKey(this.getRandomMob(rand)));
            }

            blockpos = new BlockPos(this.getXWithOffset(this.sizeX - 1, this.sizeZ - 1), this.getYWithOffset(0), this.getZWithOffset(this.sizeX - 1, this.sizeZ - 1));
            spawner = (TileEntityMobSpawner) world.getTileEntity(blockpos);

            if (spawner != null)
            {
                spawner.getSpawnerBaseLogic().setEntityId(EntityList.getKey(this.getRandomMob(rand)));
            }
            return true;
        }
        return false;
    }

    private Class<? extends Entity> getRandomMob(Random rand)
    {
        switch (rand.nextInt(5))
        {
        case 0:
        default:
            return EntityCheeseFloater.class;
        case 2:
            return EntityEvolvedSpider.class;
        case 3:
            return EntityEvolvedCreeper.class;
        case 4:
            return EntityEvolvedSkeleton.class;
        case 5:
            return EntityEvolvedZombie.class;
        }
    }
}