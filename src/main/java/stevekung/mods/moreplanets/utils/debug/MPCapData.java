package stevekung.mods.moreplanets.utils.debug;

import net.minecraft.nbt.NBTTagCompound;

public class MPCapData implements MorePlanetsCapabilityData
{
    private String startedCelestial = "";

    @Override
    public void writeNBT(NBTTagCompound nbt)
    {
        nbt.setString("StartedCelestial", this.startedCelestial);
    }

    @Override
    public void readNBT(NBTTagCompound nbt)
    {
        this.startedCelestial = nbt.getString("StartedCelestial");
    }

    @Override
    public void setStartCelestial(String object)
    {
        this.startedCelestial = object;
    }

    @Override
    public String getStartCelestial()
    {
        return this.startedCelestial;
    }
}