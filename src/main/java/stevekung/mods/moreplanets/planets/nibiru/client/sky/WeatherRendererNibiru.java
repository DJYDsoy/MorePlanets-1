package stevekung.mods.moreplanets.planets.nibiru.client.sky;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.client.IRenderHandler;
import stevekung.mods.moreplanets.init.MPBiomes;
import stevekung.mods.stevekunglib.client.event.ClientEventHandler;

public class WeatherRendererNibiru extends IRenderHandler
{
    private static final ResourceLocation RAIN = new ResourceLocation("moreplanets:textures/environment/infected_rain.png");
    private static final ResourceLocation PURIFIED_RAIN = new ResourceLocation("moreplanets:textures/environment/purified_rain.png");
    private static final ResourceLocation SNOW = new ResourceLocation("moreplanets:textures/environment/infected_snow.png");
    private static final ResourceLocation PURIFIED_SNOW = new ResourceLocation("moreplanets:textures/environment/purified_snow.png");
    private final float[] rainXCoords = new float[1024];
    private final float[] rainYCoords = new float[1024];
    private int rainSoundCounter;
    private final Random rand = new Random();

    public WeatherRendererNibiru()
    {
        for (int i = 0; i < 32; ++i)
        {
            for (int j = 0; j < 32; ++j)
            {
                float f = j - 16;
                float f1 = i - 16;
                float f2 = MathHelper.sqrt(f * f + f1 * f1);
                this.rainXCoords[i << 5 | j] = -f1 / f2;
                this.rainYCoords[i << 5 | j] = f / f2;
            }
        }
    }

    @Override
    public void render(float partialTicks, WorldClient worldClient, Minecraft mc)
    {
        int rendererUpdateCount = ClientEventHandler.ticksPaused;

        if (mc.player.posY > 256)
        {
            return;
        }
        if (rendererUpdateCount == -1)//TODO Fix vanilla particles
        {
            this.addRainParticles(mc, rendererUpdateCount);
        }

        float rainStrength = mc.world.getRainStrength(partialTicks);

        if (rainStrength > 0.0F)
        {
            this.renderDefaultWeather(mc, rainStrength, rendererUpdateCount, partialTicks);
            this.renderNibiruWeather(mc, rainStrength, rendererUpdateCount, partialTicks);
        }
    }

    private void renderDefaultWeather(Minecraft mc, float rainStrength, int rendererUpdateCount, float partialTicks)
    {
        mc.entityRenderer.enableLightmap();
        Entity entity = mc.getRenderViewEntity();
        World world = mc.world;
        int i = MathHelper.floor(entity.posX);
        int j = MathHelper.floor(entity.posY);
        int k = MathHelper.floor(entity.posZ);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        GlStateManager.disableCull();
        GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.alphaFunc(516, 0.1F);
        double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
        double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
        double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
        int l = MathHelper.floor(d1);
        int i1 = 5;

        if (mc.gameSettings.fancyGraphics)
        {
            i1 = 10;
        }

        int j1 = -1;
        float f1 = rendererUpdateCount + partialTicks;
        buffer.setTranslation(-d0, -d1, -d2);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int k1 = k - i1; k1 <= k + i1; ++k1)
        {
            for (int l1 = i - i1; l1 <= i + i1; ++l1)
            {
                int i2 = (k1 - k + 16) * 32 + l1 - i + 16;
                double d3 = this.rainXCoords[i2] * 0.5D;
                double d4 = this.rainYCoords[i2] * 0.5D;
                mutablePos.setPos(l1, 0, k1);
                Biome biome = world.getBiome(mutablePos);

                if (biome.canRain() || biome.getEnableSnow())
                {
                    int j2 = world.getPrecipitationHeight(mutablePos).getY();
                    int k2 = j - i1;
                    int l2 = j + i1;

                    if (k2 < j2)
                    {
                        k2 = j2;
                    }
                    if (l2 < j2)
                    {
                        l2 = j2;
                    }

                    int i3 = j2;

                    if (j2 < l)
                    {
                        i3 = l;
                    }

                    if (k2 != l2)
                    {
                        this.rand.setSeed(l1 * l1 * 3121 + l1 * 45238971 ^ k1 * k1 * 418711 + k1 * 13761);
                        mutablePos.setPos(l1, k2, k1);
                        float f2 = biome.getTemperature(mutablePos);

                        if (world.getBiomeProvider().getTemperatureAtHeight(f2, j2) >= 0.15F)
                        {
                            if (j1 != 0)
                            {
                                if (j1 >= 0)
                                {
                                    tessellator.draw();
                                }
                                j1 = 0;
                                mc.getTextureManager().bindTexture(WeatherRendererNibiru.RAIN);
                                buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                            }
                            double d5 = ((double)(rendererUpdateCount + l1 * l1 * 3121 + l1 * 45238971 + k1 * k1 * 418711 + k1 * 13761 & 31) + (double)partialTicks) / 32.0D * (3.0D + this.rand.nextDouble());
                            double d6 = l1 + 0.5F - entity.posX;
                            double d7 = k1 + 0.5F - entity.posZ;
                            float f3 = MathHelper.sqrt(d6 * d6 + d7 * d7) / i1;
                            float f4 = ((1.0F - f3 * f3) * 0.5F + 0.5F) * rainStrength;
                            mutablePos.setPos(l1, i3, k1);
                            int j3 = world.getCombinedLight(mutablePos, 0);
                            int k3 = j3 >> 16 & 65535;
                    int l3 = j3 & 65535;
                    buffer.pos(l1 - d3 + 0.5D, k2, k1 - d4 + 0.5D).tex(0.0D, k2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
                    buffer.pos(l1 + d3 + 0.5D, k2, k1 + d4 + 0.5D).tex(1.0D, k2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
                    buffer.pos(l1 + d3 + 0.5D, l2, k1 + d4 + 0.5D).tex(1.0D, l2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
                    buffer.pos(l1 - d3 + 0.5D, l2, k1 - d4 + 0.5D).tex(0.0D, l2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
                        }
                        else
                        {
                            if (j1 != 1)
                            {
                                if (j1 >= 0)
                                {
                                    tessellator.draw();
                                }
                                j1 = 1;
                                mc.getTextureManager().bindTexture(WeatherRendererNibiru.SNOW);
                                buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                            }
                            double d8 = ((rendererUpdateCount & 511) + partialTicks) / 512.0F;
                            double d9 = this.rand.nextDouble() + f1 * 0.01D * (float)this.rand.nextGaussian();
                            double d10 = this.rand.nextDouble() + f1 * (float)this.rand.nextGaussian() * 0.001D;
                            double d11 = l1 + 0.5F - entity.posX;
                            double d12 = k1 + 0.5F - entity.posZ;
                            float f6 = MathHelper.sqrt(d11 * d11 + d12 * d12) / i1;
                            float f5 = ((1.0F - f6 * f6) * 0.3F + 0.5F) * rainStrength;
                            mutablePos.setPos(l1, i3, k1);
                            int i4 = (world.getCombinedLight(mutablePos, 0) * 3 + 15728880) / 4;
                            int j4 = i4 >> 16 & 65535;
                int k4 = i4 & 65535;
                buffer.pos(l1 - d3 + 0.5D, k2, k1 - d4 + 0.5D).tex(0.0D + d9, k2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();
                buffer.pos(l1 + d3 + 0.5D, k2, k1 + d4 + 0.5D).tex(1.0D + d9, k2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();
                buffer.pos(l1 + d3 + 0.5D, l2, k1 + d4 + 0.5D).tex(1.0D + d9, l2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();
                buffer.pos(l1 - d3 + 0.5D, l2, k1 - d4 + 0.5D).tex(0.0D + d9, l2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();
                        }
                    }
                }
            }
        }

        if (j1 >= 0)
        {
            tessellator.draw();
        }
        buffer.setTranslation(0.0D, 0.0D, 0.0D);
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(516, 0.1F);
        mc.entityRenderer.disableLightmap();
    }

    private void renderNibiruWeather(Minecraft mc, float rainStrength, int rendererUpdateCount, float partialTicks)
    {
        mc.entityRenderer.enableLightmap();
        float f1 = rendererUpdateCount + partialTicks;
        Entity entity = mc.getRenderViewEntity();
        World world = mc.world;
        int i = MathHelper.floor(entity.posX);
        int j = MathHelper.floor(entity.posY);
        int k = MathHelper.floor(entity.posZ);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder buffer = tessellator.getBuffer();
        GlStateManager.disableCull();
        GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.alphaFunc(516, 0.1F);
        double d0 = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks;
        double d1 = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks;
        double d2 = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks;
        int l = MathHelper.floor(d1);
        int i1 = 5;

        if (mc.gameSettings.fancyGraphics)
        {
            i1 = 10;
        }

        int j1 = -1;
        buffer.setTranslation(-d0, -d1, -d2);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        BlockPos.MutableBlockPos mutablePos = new BlockPos.MutableBlockPos();

        for (int k1 = k - i1; k1 <= k + i1; ++k1)
        {
            for (int l1 = i - i1; l1 <= i + i1; ++l1)
            {
                int i2 = (k1 - k + 16) * 32 + l1 - i + 16;
                double d3 = this.rainXCoords[i2] * 0.5D;
                double d4 = this.rainYCoords[i2] * 0.5D;
                mutablePos.setPos(l1, 0, k1);
                Biome biome = world.getBiome(mutablePos);

                if (biome.canRain() || biome.getEnableSnow())
                {
                    int j2 = world.getPrecipitationHeight(mutablePos).getY();
                    int k2 = j - i1;
                    int l2 = j + i1;

                    if (k2 < j2)
                    {
                        k2 = j2;
                    }
                    if (l2 < j2)
                    {
                        l2 = j2;
                    }

                    int i3 = j2;

                    if (j2 < l)
                    {
                        i3 = l;
                    }

                    if (k2 != l2)
                    {
                        this.rand.setSeed(l1 * l1 * 3121 + l1 * 45238971 ^ k1 * k1 * 418711 + k1 * 13761);
                        mutablePos.setPos(l1, k2, k1);
                        biome.getTemperature(mutablePos);

                        if (biome == MPBiomes.GREEN_VEIN_FIELDS || biome == MPBiomes.GREEN_VEIN_FIELD_SHORE)
                        {
                            if (j1 != 0)
                            {
                                if (j1 >= 0)
                                {
                                    tessellator.draw();
                                }
                                j1 = 0;
                                mc.getTextureManager().bindTexture(WeatherRendererNibiru.PURIFIED_RAIN);
                                buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                            }
                            double d5 = ((double)(rendererUpdateCount + l1 * l1 * 3121 + l1 * 45238971 + k1 * k1 * 418711 + k1 * 13761 & 31) + (double)partialTicks) / 32.0D * (3.0D + this.rand.nextDouble());
                            double d6 = l1 + 0.5F - entity.posX;
                            double d7 = k1 + 0.5F - entity.posZ;
                            float f3 = MathHelper.sqrt(d6 * d6 + d7 * d7) / i1;
                            float f4 = ((1.0F - f3 * f3) * 0.5F + 0.5F) * rainStrength;
                            mutablePos.setPos(l1, i3, k1);
                            int j3 = world.getCombinedLight(mutablePos, 0);
                            int k3 = j3 >> 16 & 65535;
                    int l3 = j3 & 65535;
                    buffer.pos(l1 - d3 + 0.5D, k2, k1 - d4 + 0.5D).tex(0.0D, k2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
                    buffer.pos(l1 + d3 + 0.5D, k2, k1 + d4 + 0.5D).tex(1.0D, k2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
                    buffer.pos(l1 + d3 + 0.5D, l2, k1 + d4 + 0.5D).tex(1.0D, l2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
                    buffer.pos(l1 - d3 + 0.5D, l2, k1 - d4 + 0.5D).tex(0.0D, l2 * 0.25D + d5).color(1.0F, 1.0F, 1.0F, f4).lightmap(k3, l3).endVertex();
                        }
                        else if (biome == MPBiomes.COLD_GREEN_VEIN_MOUTAINS)
                        {
                            if (j1 != 1)
                            {
                                if (j1 >= 0)
                                {
                                    tessellator.draw();
                                }
                                j1 = 1;
                                mc.getTextureManager().bindTexture(WeatherRendererNibiru.PURIFIED_SNOW);
                                buffer.begin(7, DefaultVertexFormats.PARTICLE_POSITION_TEX_COLOR_LMAP);
                            }
                            double d8 = ((rendererUpdateCount & 511) + partialTicks) / 512.0F;
                            double d9 = this.rand.nextDouble() + f1 * 0.01D * (float)this.rand.nextGaussian();
                            double d10 = this.rand.nextDouble() + f1 * (float)this.rand.nextGaussian() * 0.001D;
                            double d11 = l1 + 0.5F - entity.posX;
                            double d12 = k1 + 0.5F - entity.posZ;
                            float f6 = MathHelper.sqrt(d11 * d11 + d12 * d12) / i1;
                            float f5 = ((1.0F - f6 * f6) * 0.3F + 0.5F) * rainStrength;
                            mutablePos.setPos(l1, i3, k1);
                            int i4 = (world.getCombinedLight(mutablePos, 0) * 3 + 15728880) / 4;
                            int j4 = i4 >> 16 & 65535;
        int k4 = i4 & 65535;
        buffer.pos(l1 - d3 + 0.5D, k2, k1 - d4 + 0.5D).tex(0.0D + d9, k2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();
        buffer.pos(l1 + d3 + 0.5D, k2, k1 + d4 + 0.5D).tex(1.0D + d9, k2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();
        buffer.pos(l1 + d3 + 0.5D, l2, k1 + d4 + 0.5D).tex(1.0D + d9, l2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();
        buffer.pos(l1 - d3 + 0.5D, l2, k1 - d4 + 0.5D).tex(0.0D + d9, l2 * 0.25D + d8 + d10).color(1.0F, 1.0F, 1.0F, f5).lightmap(j4, k4).endVertex();
                        }
                    }
                }
            }
        }

        if (j1 >= 0)
        {
            tessellator.draw();
        }
        buffer.setTranslation(0.0D, 0.0D, 0.0D);
        GlStateManager.enableCull();
        GlStateManager.disableBlend();
        GlStateManager.alphaFunc(516, 0.1F);
        mc.entityRenderer.disableLightmap();
    }

    private void addRainParticles(Minecraft mc, int rendererUpdateCount)
    {
        float f = mc.world.getRainStrength(1.0F);

        if (!mc.gameSettings.fancyGraphics)
        {
            f /= 2.0F;
        }

        if (f != 0.0F)
        {
            this.rand.setSeed(rendererUpdateCount * 312987231L);
            Entity entity = mc.getRenderViewEntity();
            World world = mc.world;
            BlockPos blockpos = new BlockPos(entity);
            int i = 10;
            double d0 = 0.0D;
            double d1 = 0.0D;
            double d2 = 0.0D;
            int j = 0;
            int k = (int)(100.0F * f * f);

            if (mc.gameSettings.particleSetting == 1)
            {
                k >>= 1;
            }
            else if (mc.gameSettings.particleSetting == 2)
            {
                k = 0;
            }

            for (int l = 0; l < k; ++l)
            {
                BlockPos blockpos1 = world.getPrecipitationHeight(blockpos.add(this.rand.nextInt(i) - this.rand.nextInt(i), 0, this.rand.nextInt(i) - this.rand.nextInt(i)));
                Biome biomegenbase = world.getBiome(blockpos1);
                BlockPos blockpos2 = blockpos1.down();

                if (blockpos1.getY() <= blockpos.getY() + i && blockpos1.getY() >= blockpos.getY() - i && biomegenbase.canRain() && biomegenbase.getTemperature(blockpos1) >= 0.15F)
                {
                    double d3 = this.rand.nextDouble();
                    double d4 = this.rand.nextDouble();
                    AxisAlignedBB axisalignedbb = world.getBlockState(blockpos2).getBoundingBox(world, blockpos2);

                    if (world.getBlockState(blockpos2).getMaterial() == Material.LAVA)
                    {
                        mc.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, blockpos1.getX() + d3, blockpos1.getY() + 0.1F - axisalignedbb.minY, blockpos1.getZ() + d4, 0.0D, 0.0D, 0.0D, new int[0]);
                    }
                    else if (world.getBlockState(blockpos2).getMaterial() != Material.AIR)
                    {
                        ++j;

                        if (this.rand.nextInt(j) == 0)
                        {
                            d0 = blockpos2.getX() + d3;
                            d1 = blockpos2.getY() + 0.1F + axisalignedbb.maxY - 1.0D;
                            d2 = blockpos2.getZ() + d4;
                        }
                        //TODO mc.effectRenderer.addEffect(new EntityInfectedRainFX(world, blockpos2.getX() + d3, blockpos2.getY() + 0.1F + block.getBlockBoundsMaxY(), blockpos2.getZ() + d4));
                    }
                }
            }

            if (j > 0 && this.rand.nextInt(3) < this.rainSoundCounter++)
            {
                this.rainSoundCounter = 0;

                if (d1 > blockpos.getY() + 1 && world.getPrecipitationHeight(blockpos).getY() > MathHelper.floor(blockpos.getY()))
                {
                    mc.world.playSound(d0, d1, d2, SoundEvents.WEATHER_RAIN_ABOVE, SoundCategory.WEATHER, 0.1F, 0.5F, false);
                }
                else
                {
                    mc.world.playSound(d0, d1, d2, SoundEvents.WEATHER_RAIN, SoundCategory.WEATHER, 0.2F, 1.0F, false);
                }
            }
        }
    }
}