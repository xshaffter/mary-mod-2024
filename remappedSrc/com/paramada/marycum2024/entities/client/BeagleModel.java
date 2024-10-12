package com.paramada.marycum2024.entities.client;

import com.paramada.marycum2024.entities.animations.ModAnimations;
import com.paramada.marycum2024.entities.custom.BeagleEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.MathHelper;

// Made with Blockbench 4.10.4
// Exported for Minecraft version 1.17+ for Yarn
// Paste this class into your mod and generate all required imports
public class BeagleModel<T extends BeagleEntity> extends SinglePartEntityModel<T> {
	private final ModelPart body;
	private final ModelPart backleftleg;
	private final ModelPart backrightleg;
	private final ModelPart frontleftleg;
	private final ModelPart frontrightleg;
	private final ModelPart tail;
	private final ModelPart head;
	private final ModelPart nose;
	private final ModelPart torso;
	public BeagleModel(ModelPart root) {
		this.body = root.getChild("body");
		this.backleftleg = body.getChild("backleftleg");
		this.backrightleg = body.getChild("backrightleg");
		this.frontleftleg = body.getChild("frontleftleg");
		this.frontrightleg = body.getChild("frontrightleg");
		this.tail = body.getChild("tail");
		this.head = body.getChild("head");
		this.nose = head.getChild("nose");
		this.torso = body.getChild("torso");
	}
	public static TexturedModelData getTexturedModelData() {
		ModelData modelData = new ModelData();
		ModelPartData modelPartData = modelData.getRoot();
		ModelPartData body = modelPartData.addChild("body", ModelPartBuilder.create(), ModelTransform.pivot(0.0F, 15.0F, -1.0F));

		ModelPartData backleftleg = body.addChild("backleftleg", ModelPartBuilder.create().uv(40, 0).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, 3.0F, 4.5F));

		ModelPartData backrightleg = body.addChild("backrightleg", ModelPartBuilder.create().uv(40, 0).mirrored().cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-2.5F, 3.0F, 4.5F));

		ModelPartData frontleftleg = body.addChild("frontleftleg", ModelPartBuilder.create().uv(40, 0).cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(2.5F, 3.0F, -5.5F));

		ModelPartData frontrightleg = body.addChild("frontrightleg", ModelPartBuilder.create().uv(40, 0).mirrored().cuboid(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-2.5F, 3.0F, -5.5F));

		ModelPartData tail = body.addChild("tail", ModelPartBuilder.create().uv(52, 0).cuboid(-1.0F, -8.5F, -1.0F, 2.0F, 9.0F, 2.0F, new Dilation(0.0F)), ModelTransform.of(0.0F, -0.5F, 7.0F, -0.7418F, 0.0F, 0.0F));

		ModelPartData head = body.addChild("head", ModelPartBuilder.create().uv(2, 12).cuboid(-3.0F, -7.0F, -5.0F, 7.0F, 7.0F, 7.0F, new Dilation(0.0F))
		.uv(46, 11).cuboid(4.0F, -6.0F, -4.0F, 1.0F, 9.0F, 6.0F, new Dilation(0.0F))
		.uv(46, 11).mirrored().cuboid(-4.0F, -6.0F, -4.0F, 1.0F, 9.0F, 6.0F, new Dilation(0.0F)).mirrored(false), ModelTransform.pivot(-0.5F, -3.0F, -5.0F));

		ModelPartData nose = head.addChild("nose", ModelPartBuilder.create().uv(23, 3).cuboid(-2.0F, -3.0F, -8.0F, 5.0F, 3.0F, 3.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 0.0F));

		ModelPartData torso = body.addChild("torso", ModelPartBuilder.create().uv(16, 12).cuboid(-4.0F, -3.0F, -9.0F, 8.0F, 6.0F, 14.0F, new Dilation(0.0F)), ModelTransform.pivot(0.0F, 0.0F, 2.0F));
		return TexturedModelData.of(modelData, 64, 32);
	}
	@Override
	public void setAngles(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
		this.getPart().traverse().forEach(ModelPart::resetTransform);
		this.setHeadAngles(netHeadYaw, headPitch);

		this.animateMovement(ModAnimations.BEAGLE_WALK, limbSwing, limbSwingAmount, 2f, 2.5f);
		this.updateAnimation(entity.idleAnimationState, ModAnimations.BEAGLE_IDLE, ageInTicks, 1f);
		this.updateAnimation(entity.barkAnimationState, ModAnimations.BEAGLE_BARK, ageInTicks, 1f);
		this.updateAnimation(entity.biteAnimationState, ModAnimations.BEAGLE_BITE, ageInTicks, 1f);
	}

	public void setHeadAngles(float headYaw, float headPitch) {
		headYaw = MathHelper.clamp(headYaw, -30.0f, 30.0f);
		headPitch = MathHelper.clamp(headPitch, -25.0f, 45.0f);

		this.head.yaw = headYaw * 0.017453292F;
		this.head.pitch = headPitch * 0.017453292F;
	}
	@Override
	public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
		body.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
	}

	@Override
	public ModelPart getPart() {
		return body;
	}
}