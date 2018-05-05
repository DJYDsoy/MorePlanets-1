package stevekung.mods.moreplanets.planets.diona.world.gen.structure;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.MapGenStructure;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraft.world.gen.structure.StructureStart;

public class MapGenDionaMineshaft extends MapGenStructure
{
    private double chance = 0.004D;

    public MapGenDionaMineshaft() {}

    static
    {
        MapGenStructureIO.registerStructure(StructureMineshaftStart.class, "DionaMineshaft");
        StructureDionaMineshaftPieces.registerStructurePieces();
    }

    @Override
    public String getStructureName()
    {
        return "DionaMineshaft";
    }

    @Override
    protected boolean canSpawnStructureAtCoords(int chunkX, int chunkZ)
    {
        return this.rand.nextDouble() < this.chance && this.rand.nextInt(80) < Math.max(Math.abs(chunkX), Math.abs(chunkZ));
    }

    @Override
    protected StructureStart getStructureStart(int chunkX, int chunkZ)
    {
        return new StructureMineshaftStart(this.world, this.rand, chunkX, chunkZ);
    }

    @Override
    public BlockPos getNearestStructurePos(World world, BlockPos pos, boolean findUnexplored)
    {
        int j = pos.getX() >> 4;
        int k = pos.getZ() >> 4;

        for (int l = 0; l <= 1000; ++l)
        {
            for (int i1 = -l; i1 <= l; ++i1)
            {
                boolean flag = i1 == -l || i1 == l;

                for (int j1 = -l; j1 <= l; ++j1)
                {
                    boolean flag1 = j1 == -l || j1 == l;

                    if (flag || flag1)
                    {
                        int k1 = j + i1;
                        int l1 = k + j1;
                        this.rand.setSeed(k1 ^ l1 ^ world.getSeed());
                        this.rand.nextInt();

                        if (this.canSpawnStructureAtCoords(k1, l1) && (!findUnexplored || !world.isChunkGeneratedAt(k1, l1)))
                        {
                            return new BlockPos((k1 << 4) + 8, 64, (l1 << 4) + 8);
                        }
                    }
                }
            }
        }
        return null;
    }

    public static class StructureMineshaftStart extends StructureStart
    {
        public StructureMineshaftStart() {}

        public StructureMineshaftStart(World world, Random rand, int chunkX, int chunkZ)
        {
            super(chunkX, chunkZ);
            StructureDionaMineshaftPieces.Room piece = new StructureDionaMineshaftPieces.Room(0, rand, (chunkX << 4) + 2, (chunkZ << 4) + 2);
            this.components.add(piece);
            piece.buildComponent(piece, this.components, rand);
            this.updateBoundingBox();
            this.markAvailableHeight(world, rand, 10);
        }
    }
}