package stevekung.mods.moreplanets.module.planets.nibiru.tileentity;

import stevekung.mods.moreplanets.module.planets.nibiru.blocks.BlockTerrastoneFurnace;
import stevekung.mods.moreplanets.utils.tileentity.TileEntityFurnaceMP;

public class TileEntityTerrastoneFurnace extends TileEntityFurnaceMP
{
    @Override
    protected void setState()
    {
        BlockTerrastoneFurnace.setState(this.isBurning(), this.world, this.pos);
    }
}