package stevekung.mods.moreplanets.module.planets.chalos.client.renderer.entity;

import micdoodle8.mods.galacticraft.api.vector.Vector3;
import micdoodle8.mods.galacticraft.core.util.ClientUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stevekung.mods.moreplanets.module.planets.chalos.entity.EntityTier5Rocket;
import stevekung.mods.moreplanets.util.client.renderer.item.ItemRendererTieredRocket;

@SideOnly(Side.CLIENT)
public class RenderTier5Rocket extends Render<EntityTier5Rocket>
{
    private ItemRendererTieredRocket rocketModel;

    public RenderTier5Rocket(RenderManager manager)
    {
        super(manager);
        this.shadowSize = 2F;
    }

    @Override
    protected ResourceLocation getEntityTexture(EntityTier5Rocket entity)
    {
        return null;
    }

    @Override
    public void doRender(EntityTier5Rocket entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
        float rotationPitch = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * partialTicks + 180;
        float rollAmplitude = entity.rollAmplitude / 3 - partialTicks;
        GlStateManager.disableRescaleNormal();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y + entity.getRenderOffsetY(), (float)z);
        GlStateManager.rotate(180.0F - entityYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-rotationPitch, 0.0F, 0.0F, 1.0F);

        if (rollAmplitude > 0.0F)
        {
            float i = entity.getLaunched() ? (5 - MathHelper.floor_double(entity.timeUntilLaunch / 85)) / 10F : 0.3F;
            GlStateManager.rotate(MathHelper.sin(rollAmplitude) * rollAmplitude * i * partialTicks, 1.0F, 0.0F, 0.0F);
            GlStateManager.rotate(MathHelper.sin(rollAmplitude) * rollAmplitude * i * partialTicks, 1.0F, 0.0F, 1.0F);
        }

        this.updateModel();
        RenderHelper.disableStandardItemLighting();
        this.bindTexture(TextureMap.locationBlocksTexture);

        if (Minecraft.isAmbientOcclusionEnabled())
        {
            GlStateManager.shadeModel(7425);
        }
        else
        {
            GlStateManager.shadeModel(7424);
        }

        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.scale(0.8F, 0.8F, 0.8F);
        ClientUtil.drawBakedModel(this.rocketModel);
        Vector3 teamColor = ClientUtil.updateTeamColor(Minecraft.getMinecraft().thePlayer.getName(), true);

        if (teamColor != null)
        {
            GlStateManager.color(teamColor.floatX(), teamColor.floatY(), teamColor.floatZ());
        }
        if (Minecraft.getMinecraft().thePlayer.ticksExisted / 10 % 2 < 1)
        {
            GlStateManager.color(1, 0, 0);
        }
        else
        {
            GlStateManager.color(0, 1, 0);
        }
        GlStateManager.disableTexture2D();
        GlStateManager.disableLighting();
        GlStateManager.enableTexture2D();
        GlStateManager.enableLighting();
        GlStateManager.color(1, 1, 1);
        GlStateManager.popMatrix();
        RenderHelper.enableStandardItemLighting();
    }

    @Override
    public boolean shouldRender(EntityTier5Rocket rocket, ICamera camera, double camX, double camY, double camZ)
    {
        AxisAlignedBB axisalignedbb = rocket.getEntityBoundingBox().expand(0.5D, 0, 0.5D);
        return rocket.isInRangeToRender3d(camX, camY, camZ) && camera.isBoundingBoxInFrustum(axisalignedbb);
    }

    private void updateModel()
    {
        if (this.rocketModel == null)
        {
            ModelResourceLocation modelResourceLocation = new ModelResourceLocation("moreplanets:tier_5_rocket", "inventory");
            this.rocketModel = (ItemRendererTieredRocket) Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getModelManager().getModel(modelResourceLocation);
        }
    }
}