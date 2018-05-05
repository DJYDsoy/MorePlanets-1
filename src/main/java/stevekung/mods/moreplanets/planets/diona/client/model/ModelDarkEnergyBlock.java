package stevekung.mods.moreplanets.planets.diona.client.model;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;

public class ModelDarkEnergyBlock extends ModelBase
{
    private ModelRenderer base1;
    private ModelRenderer base2;
    private ModelRenderer base3;
    private ModelRenderer base4;
    private ModelRenderer rod1;
    private ModelRenderer rod2;
    private ModelRenderer rod3;
    private ModelRenderer rod4;
    private ModelRenderer rod5;
    private ModelRenderer rod6;
    private ModelRenderer rod7;
    private ModelRenderer rod8;
    private ModelRenderer energyBall;

    public ModelDarkEnergyBlock()
    {
        this.textureWidth = 128;
        this.textureHeight = 64;

        this.base1 = new ModelRenderer(this, 0, 50);
        this.base1.addBox(-6F, 0F, -6F, 12, 2, 12);
        this.base1.setRotationPoint(0F, 22F, 0F);
        this.base2 = new ModelRenderer(this, 0, 33);
        this.base2.addBox(-7F, 1F, -7F, 14, 3, 14);
        this.base2.setRotationPoint(0F, 18F, 0F);
        this.base3 = new ModelRenderer(this, 0, 15);
        this.base3.addBox(-8F, 0F, -8F, 16, 2, 16);
        this.base3.setRotationPoint(0F, 17F, 0F);
        this.base4 = new ModelRenderer(this, 68, 46);
        this.base4.addBox(-7.5F, 0F, -7.5F, 15, 3, 15);
        this.base4.setRotationPoint(0F, 14F, 0F);
        this.rod1 = new ModelRenderer(this, 0, 8);
        this.rod1.addBox(-1F, -4F, -1F, 2, 4, 2);
        this.rod1.setRotationPoint(6F, 14.5F, -6F);
        this.setRotation(this.rod1, -0.2617994F, -0.7853982F, 0F);
        this.rod2 = new ModelRenderer(this, 0, 8);
        this.rod2.addBox(-1F, -4F, -1F, 2, 4, 2);
        this.rod2.setRotationPoint(-6F, 14.5F, -6F);
        this.setRotation(this.rod2, -0.2617994F, 0.7853982F, 0F);
        this.rod3 = new ModelRenderer(this, 0, 8);
        this.rod3.addBox(-1F, -4F, -1F, 2, 4, 2);
        this.rod3.setRotationPoint(-6F, 14.5F, 6F);
        this.setRotation(this.rod3, 0.2617994F, -0.7853982F, 0F);
        this.rod4 = new ModelRenderer(this, 0, 8);
        this.rod4.addBox(-1F, -4F, -1F, 2, 4, 2);
        this.rod4.setRotationPoint(6F, 14.5F, 6F);
        this.setRotation(this.rod4, 0.2617994F, 0.7853982F, 0F);
        this.rod5 = new ModelRenderer(this, 0, 0);
        this.rod5.addBox(-0.5F, -3F, -0.5F, 1, 3, 1);
        this.rod5.setRotationPoint(-5.5F, 11F, -5.5F);
        this.setRotation(this.rod5, -0.6108652F, 0.7853982F, 0F);
        this.rod6 = new ModelRenderer(this, 0, 0);
        this.rod6.addBox(-0.5F, -3F, -0.5F, 1, 3, 1);
        this.rod6.setRotationPoint(-5.5F, 11F, 5.5F);
        this.setRotation(this.rod6, 0.6108652F, -0.7853982F, 0F);
        this.rod7 = new ModelRenderer(this, 0, 0);
        this.rod7.addBox(-0.5F, -3F, -0.5F, 1, 3, 1);
        this.rod7.setRotationPoint(5.5F, 11F, -5.5F);
        this.setRotation(this.rod7, -0.6108652F, -0.7853982F, 0F);
        this.rod8 = new ModelRenderer(this, 0, 0);
        this.rod8.addBox(-0.5F, -3F, -0.5F, 1, 3, 1);
        this.rod8.setRotationPoint(5.5F, 11F, 5.5F);
        this.setRotation(this.rod8, 0.6108652F, 0.7853982F, 0F);
        this.energyBall = new ModelRenderer(this, 104, 24);
        this.energyBall.addBox(-3F, -3F, -3F, 6, 6, 6);
        this.energyBall.setRotationPoint(0F, 1F, 0F);
    }

    public void renderAll(float tick)
    {
        this.base1.render(0.0625F);
        this.base2.render(0.0625F);
        this.base3.render(0.0625F);
        this.base4.render(0.0625F);
        this.rod1.render(0.0625F);
        this.rod2.render(0.0625F);
        this.rod3.render(0.0625F);
        this.rod4.render(0.0625F);
        this.rod5.render(0.0625F);
        this.rod6.render(0.0625F);
        this.rod7.render(0.0625F);
        this.rod8.render(0.0625F);

        GlStateManager.pushMatrix();
        GlStateManager.translate(0.0F, -0.25F + tick, 0.0F);
        this.energyBall.render(0.0625F);
        GlStateManager.popMatrix();
    }

    private void setRotation(ModelRenderer model, float x, float y, float z)
    {
        model.rotateAngleX = x;
        model.rotateAngleY = y;
        model.rotateAngleZ = z;
    }
}