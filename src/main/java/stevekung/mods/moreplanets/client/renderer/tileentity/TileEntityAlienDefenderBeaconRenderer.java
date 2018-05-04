package stevekung.mods.moreplanets.client.renderer.tileentity;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stevekung.mods.moreplanets.client.model.ModelAlienDefenderBeacon;
import stevekung.mods.moreplanets.tileentity.TileEntityAlienDefenderBeacon;

@SideOnly(Side.CLIENT)
public class TileEntityAlienDefenderBeaconRenderer extends TileEntitySpecialRenderer<TileEntityAlienDefenderBeacon>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("moreplanets:textures/model/alien_defender_beacon.png");
    private static final ResourceLocation GLOW = new ResourceLocation("moreplanets:textures/model/alien_defender_beacon_glow.png");
    private final ModelAlienDefenderBeacon model = new ModelAlienDefenderBeacon();

    @Override
    public void render(TileEntityAlienDefenderBeacon tile, double x, double y, double z, float partialTicks, int destroyStage, float alpha)
    {
        float lightMapSaveX = OpenGlHelper.lastBrightnessX;
        float lightMapSaveY = OpenGlHelper.lastBrightnessY;
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x + 0.5F, (float)y + 1.5F, (float)z + 0.5F);
        GlStateManager.scale(-1.0F, -1.0F, 1.0F);
        GlStateManager.pushMatrix();
        GlStateManager.enableRescaleNormal();

        if (destroyStage >= 0)
        {
            this.bindTexture(DESTROY_STAGES[destroyStage]);
            GlStateManager.matrixMode(5890);
            GlStateManager.pushMatrix();
            GlStateManager.scale(4.0F, 4.0F, 1.0F);
            GlStateManager.translate(0.0625F, 0.0625F, 0.0625F);
            GlStateManager.matrixMode(5888);
        }
        else
        {
            this.bindTexture(TileEntityAlienDefenderBeaconRenderer.TEXTURE);
        }

        this.model.renderBase();
        GlStateManager.popMatrix();

        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F, 240F);
        GlStateManager.disableLighting();
        this.bindTexture(TileEntityAlienDefenderBeaconRenderer.GLOW);
        this.model.renderBase();
        GlStateManager.enableCull();
        GlStateManager.enableBlend();
        GlStateManager.depthMask(false);
        GlStateManager.blendFunc(770, 771);
        this.model.renderBeam();
        GlStateManager.disableBlend();
        GlStateManager.cullFace(1029);
        GlStateManager.depthMask(true);
        GlStateManager.disableCull();

        GlStateManager.enableCull();
        GlStateManager.enableBlend();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, lightMapSaveX, lightMapSaveY);
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();

        if (destroyStage >= 0)
        {
            GlStateManager.matrixMode(5890);
            GlStateManager.popMatrix();
            GlStateManager.matrixMode(5888);
        }
    }

    @Override
    public boolean isGlobalRenderer(TileEntityAlienDefenderBeacon tile)
    {
        return false;
    }
}