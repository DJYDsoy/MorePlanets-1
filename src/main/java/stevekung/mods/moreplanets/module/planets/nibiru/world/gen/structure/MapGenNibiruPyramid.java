package stevekung.mods.moreplanets.module.planets.nibiru.world.gen.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;

import micdoodle8.mods.galacticraft.core.entities.EntityEvolvedWitch;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureStart;
import stevekung.mods.moreplanets.init.MPBiomes;
import stevekung.mods.moreplanets.utils.MPLog;

public class MapGenNibiruPyramid extends MapGenStructure
{
    private List<SpawnListEntry> entitySpawnList;
    private int maxDistanceBetweenScatteredFeatures;
    private int minDistanceBetweenScatteredFeatures;

    static
    {
        MapGenStructureIO.registerStructure(Start.class, "NibiruPyramid");
        MapGenStructureIO.registerStructureComponent(StructureNibiruPyramidPieces.NibiruPyramid.class, "NibiruPyramid");
    }

    public MapGenNibiruPyramid()
    {
        this.maxDistanceBetweenScatteredFeatures = 32;
        this.minDistanceBetweenScatteredFeatures = 8;
        this.entitySpawnList = new ArrayList<>();
        this.entitySpawnList.add(new SpawnListEntry(EntityEvolvedWitch.class, 1, 1, 1));
    }

    public MapGenNibiruPyramid(Map<String, String> map)
    {
        this();

        for (Entry<String, String> entry : map.entrySet())
        {
            if (entry.getKey().equals("distance"))
            {
                this.maxDistanceBetweenScatteredFeatures = MathHelper.getInt(entry.getValue(), this.maxDistanceBetweenScatteredFeatures, this.minDistanceBetweenScatteredFeatures + 1);
            }
        }
    }

    public List<SpawnListEntry> getSpawnList()
    {
        return this.entitySpawnList;
    }

    public boolean canMobSpawn(BlockPos pos)
    {
        StructureStart structurestart = this.getStructureAt(pos);

        if (structurestart != null && structurestart instanceof Start && !structurestart.getComponents().isEmpty())
        {
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public String getStructureName()
    {
        return "NibiruPyramid";
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        int i = chunkX;
        int j = chunkZ;

        if (chunkX < 0)
        {
            chunkX -= this.maxDistanceBetweenScatteredFeatures - 1;
        }
        if (chunkZ < 0)
        {
            chunkZ -= this.maxDistanceBetweenScatteredFeatures - 1;
        }

        int k = chunkX / this.maxDistanceBetweenScatteredFeatures;
        int l = chunkZ / this.maxDistanceBetweenScatteredFeatures;
        Random random = this.world.setRandomSeed(k, l, 14357617);
        k = k * this.maxDistanceBetweenScatteredFeatures;
        l = l * this.maxDistanceBetweenScatteredFeatures;
        k = k + random.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);
        l = l + random.nextInt(this.maxDistanceBetweenScatteredFeatures - this.minDistanceBetweenScatteredFeatures);

        if (i == k && j == l)
        {
            Biome biomegenbase = this.world.getBiomeProvider().getBiome(new BlockPos(i * 16 + 8, 0, j * 16 + 8));

            if (biomegenbase == null)
            {
                return false;
            }
            if (biomegenbase == MPBiomes.INFECTED_DESERT)
            {
                return true;
            }
        }
        return false;
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new Start(this.world, this.rand, chunkX, chunkZ);
    }

    @Override
    public BlockPos getNearestStructurePos(World world, BlockPos pos, boolean findUnexplored)
    {
        this.world = world;
        return MapGenStructure.findNearestStructurePosBySpacing(world, this, pos, this.maxDistanceBetweenScatteredFeatures, 8, 14357617, false, 100, findUnexplored);
    }

    public static class Start extends StructureStart
    {
        public Start() {}

        public Start(World world, Random rand, int chunkX, int chunkZ)
        {
            super(chunkX, chunkZ);
            MPLog.debug("Generate pyramid at x:{} z:{}", chunkX * 16, chunkZ * 16);
            StructureNibiruPyramidPieces.NibiruPyramid componentscatteredfeaturepieces$desertpyramid = new StructureNibiruPyramidPieces.NibiruPyramid(rand, chunkX * 16, chunkZ * 16);
            this.components.add(componentscatteredfeaturepieces$desertpyramid);
            this.updateBoundingBox();
        }
    }
}