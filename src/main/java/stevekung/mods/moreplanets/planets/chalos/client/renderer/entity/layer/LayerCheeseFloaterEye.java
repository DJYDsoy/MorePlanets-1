package stevekung.mods.moreplanets.planets.chalos.client.renderer.entity.layer;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import stevekung.mods.moreplanets.planets.chalos.client.renderer.entity.RenderCheeseFloater;
import stevekung.mods.moreplanets.planets.chalos.entity.EntityCheeseFloater;

@SideOnly(Side.CLIENT)
public class LayerCheeseFloaterEye implements LayerRenderer<EntityCheeseFloater>
{
    private static final ResourceLocation TEXTURE = new ResourceLocation("moreplanets:textures/entity/cheese_floater_eyes.png");
    private final RenderCheeseFloater render;

    public LayerCheeseFloaterEye(RenderCheeseFloater render)
    {
        this.render = render;
    }

    @Override
    public void doRenderLayer(EntityCheeseFloater entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (entity.isMinion())
        {
            this.render.bindTexture(LayerCheeseFloaterEye.TEXTURE);
            GlStateManager.enableBlend();
            GlStateManager.disableAlpha();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE);
            GlStateManager.disableLighting();
            GlStateManager.depthMask(!entity.isInvisible());
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GlStateManager.enableLighting();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(true);
            this.render.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            Minecraft.getMinecraft().entityRenderer.setupFogColor(false);
            this.render.setLightmap(entity);
            GlStateManager.depthMask(true);
            GlStateManager.disableBlend();
            GlStateManager.enableAlpha();
        }
    }

    @Override
    public boolean shouldCombineTextures()
    {
        return false;
    }
}