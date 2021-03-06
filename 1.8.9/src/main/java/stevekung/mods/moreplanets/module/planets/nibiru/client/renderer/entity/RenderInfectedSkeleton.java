package stevekung.mods.moreplanets.module.planets.nibiru.client.renderer.entity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerBipedArmor;
import net.minecraft.client.renderer.entity.layers.LayerHeldItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stevekung.mods.moreplanets.module.planets.nibiru.client.model.ModelInfectedSkeleton;
import stevekung.mods.moreplanets.module.planets.nibiru.entity.EntityInfectedSkeleton;

@SideOnly(Side.CLIENT)
public class RenderInfectedSkeleton extends RenderBiped<EntityInfectedSkeleton>
{
    public RenderInfectedSkeleton(RenderManager manager)
    {
        super(manager, new ModelInfectedSkeleton(), 0.5F);
        this.addLayer(new LayerHeldItem(this));
        this.addLayer(new LayerBipedArmor(this)
        {
            @Override
            protected void initArmor()
            {
                this.field_177189_c = new ModelInfectedSkeleton(0.5F, true);
                this.field_177186_d = new ModelInfectedSkeleton(1.0F, true);
            }
        });
    }

    @Override
    public void transformHeldFull3DItemLayer()
    {
        GlStateManager.translate(0.09375F, 0.1875F, 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityInfectedSkeleton entity)
    {
        return new ResourceLocation("moreplanets:textures/entity/infected_skeleton.png");
    }
}