package stevekung.mods.moreplanets.planets.chalos.world.gen.dungeon;

import java.util.Random;

import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.structure.StructureBoundingBox;
import stevekung.mods.moreplanets.planets.chalos.blocks.ChalosBlocks;
import stevekung.mods.moreplanets.planets.chalos.tileentity.TileEntityChalosTreasureChest;
import stevekung.mods.moreplanets.utils.world.gen.dungeon.DungeonConfigurationMP;
import stevekung.mods.moreplanets.utils.world.gen.dungeon.RoomTreasureMP;
import stevekung.mods.stevekunglib.utils.BlockStateProperty;

public class RoomTreasureChalos extends RoomTreasureMP
{
    public RoomTreasureChalos() {}

    public RoomTreasureChalos(DungeonConfigurationMP configuration, Random rand, int blockPosX, int blockPosZ, EnumFacing entranceDir)
    {
        super(configuration, rand, blockPosX, blockPosZ, rand.nextInt(4) + 6, configuration.getRoomHeight(), rand.nextInt(4) + 6, entranceDir);
    }

    public RoomTreasureChalos(DungeonConfigurationMP configuration, Random rand, int blockPosX, int blockPosZ, int sizeX, int sizeY, int sizeZ, EnumFacing entranceDir)
    {
        super(configuration, rand, blockPosX, blockPosZ, sizeX, sizeY, sizeZ, entranceDir);
    }

    @Override
    public boolean addComponentParts(World world, Random rand, StructureBoundingBox boundingBox)
    {
        for (int i = 0; i <= this.sizeX; i++)
        {
            for (int j = 0; j <= this.sizeY; j++)
            {
                for (int k = 0; k <= this.sizeZ; k++)
                {
                    if (i == 0 || i == this.sizeX || j == 0 || j == this.sizeY || k == 0 || k == this.sizeZ)
                    {
                        boolean placeBlock = true;

                        if (this.getDirection().getAxis() == EnumFacing.Axis.Z)
                        {
                            int start = (this.boundingBox.maxX - this.boundingBox.minX) / 2 - 1;
                            int end = (this.boundingBox.maxX - this.boundingBox.minX) / 2 + 1;

                            if (i > start && i <= end && j < 3 && j > 0)
                            {
                                if (this.getDirection() == EnumFacing.SOUTH && k == 0)
                                {
                                    placeBlock = false;
                                }
                                else if (this.getDirection() == EnumFacing.NORTH && k == this.sizeZ)
                                {
                                    placeBlock = false;
                                }
                            }
                        }
                        else
                        {
                            int start = (this.boundingBox.maxZ - this.boundingBox.minZ) / 2 - 1;
                            int end = (this.boundingBox.maxZ - this.boundingBox.minZ) / 2 + 1;

                            if (k > start && k <= end && j < 3 && j > 0)
                            {
                                if (this.getDirection() == EnumFacing.EAST && i == 0)
                                {
                                    placeBlock = false;
                                }
                                else if (this.getDirection() == EnumFacing.WEST && i == this.sizeX)
                                {
                                    placeBlock = false;
                                }
                            }
                        }
                        if (placeBlock)
                        {
                            this.setBlockState(world, this.configuration.getBrickBlock(), i, j, k, boundingBox);
                        }
                        else
                        {
                            this.setBlockState(world, Blocks.AIR.getDefaultState(), i, j, k, boundingBox);
                        }
                    }
                    else if (i == 1 && k == 1 || i == 1 && k == this.sizeZ - 1 || i == this.sizeX - 1 && k == 1 || i == this.sizeX - 1 && k == this.sizeZ - 1)
                    {
                        this.setBlockState(world, this.configuration.getGlowstoneBlock(), i, j, k, boundingBox);
                    }
                    else if (i == this.sizeX / 2 && j == 1 && k == this.sizeZ / 2)
                    {
                        this.setBlockState(world, ChalosBlocks.CHALOS_TREASURE_CHEST.getDefaultState().withProperty(BlockStateProperty.FACING_HORIZON, this.getDirection().getOpposite()), i, j, k, boundingBox);
                        BlockPos blockpos = new BlockPos(this.getXWithOffset(i, k), this.getYWithOffset(j), this.getZWithOffset(i, k));

                        if (world.getTileEntity(blockpos) == null)
                        {
                            world.setTileEntity(blockpos, new TileEntityChalosTreasureChest());
                        }
                    }
                    else
                    {
                        this.setBlockState(world, Blocks.AIR.getDefaultState(), i, j, k, boundingBox);
                    }
                }
            }
        }
        return true;
    }
}