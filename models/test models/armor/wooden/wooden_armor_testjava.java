// Made with Blockbench 4.12.4
// Exported for Minecraft version 1.17 or later with Mojang mappings
// Paste this class into your mod and generate all required imports


public class wooden_armor_testjava<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation("modid", "wooden_armor_testjava"), "main");
	private final ModelPart armorHead;
	private final ModelPart armorBody;
	private final ModelPart armorRightArm;
	private final ModelPart armorLeftArm;
	private final ModelPart armorLeftLeg;
	private final ModelPart armorLeftBoot;
	private final ModelPart armorRightLeg;
	private final ModelPart armorRightBoot;

	public wooden_armor_testjava(ModelPart root) {
		this.armorHead = root.getChild("armorHead");
		this.armorBody = root.getChild("armorBody");
		this.armorRightArm = root.getChild("armorRightArm");
		this.armorLeftArm = root.getChild("armorLeftArm");
		this.armorLeftLeg = root.getChild("armorLeftLeg");
		this.armorLeftBoot = root.getChild("armorLeftBoot");
		this.armorRightLeg = root.getChild("armorRightLeg");
		this.armorRightBoot = root.getChild("armorRightBoot");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition armorHead = partdefinition.addOrReplaceChild("armorHead", CubeListBuilder.create().texOffs(0, 11).addBox(-5.0F, -8.0F, 4.0F, 10.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(23, 65).addBox(-4.0F, -11.0F, -5.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(76, 81).addBox(-5.0F, -11.0F, -5.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(11, 38).addBox(-3.0F, -12.0F, -5.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(81, 54).addBox(-2.0F, -11.0F, -5.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(57, 39).addBox(-1.0F, -12.0F, -5.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(32, 68).addBox(0.0F, -12.0F, -5.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(62, 81).addBox(1.0F, -11.0F, -5.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(73, 80).addBox(2.0F, -12.0F, -5.0F, 1.0F, 3.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(65, 81).addBox(4.0F, -11.0F, -5.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(0, 82).addBox(3.0F, -11.0F, -5.0F, 1.0F, 2.0F, 0.0F, new CubeDeformation(0.0F))
		.texOffs(72, 61).addBox(5.0F, -12.0F, -4.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(82, 0).addBox(5.0F, -10.0F, -5.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(74, 36).addBox(-5.0F, -11.0F, -2.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(15, 76).addBox(5.0F, -11.0F, -2.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(74, 17).addBox(-5.0F, -10.0F, 0.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(76, 51).addBox(5.0F, -10.0F, 0.0F, 0.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(74, 41).addBox(-5.0F, -11.0F, 2.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(76, 46).addBox(5.0F, -11.0F, 2.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(67, 72).addBox(-5.0F, -12.0F, -4.0F, 0.0F, 3.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(79, 81).addBox(-5.0F, -10.0F, -5.0F, 0.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 0).addBox(-5.0F, -9.0F, -5.0F, 10.0F, 1.0F, 9.0F, new CubeDeformation(0.0F))
		.texOffs(36, 59).addBox(-5.0F, -8.0F, 0.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(22, 74).addBox(-5.0F, -6.0F, 2.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(60, 39).addBox(5.0F, -8.0F, 0.0F, 0.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(27, 74).addBox(5.0F, -6.0F, 2.0F, 0.0F, 2.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition armorBody = partdefinition.addOrReplaceChild("armorBody", CubeListBuilder.create().texOffs(22, 43).addBox(-1.0F, 2.0F, 2.0F, 2.0F, 10.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 71).addBox(1.0F, 1.0F, 2.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(10, 71).addBox(-2.0F, 1.0F, 2.0F, 1.0F, 6.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(5, 69).addBox(-3.0F, 0.0F, 2.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(77, 3).addBox(-4.0F, 2.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(77, 6).addBox(-4.0F, 5.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(77, 9).addBox(3.0F, 5.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 77).addBox(3.0F, 2.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(48, 34).addBox(-4.0F, 2.0F, -2.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(49, 17).addBox(4.0F, 2.0F, -2.0F, 0.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(68, 28).addBox(2.0F, 0.0F, 2.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 28).addBox(-4.0F, 8.0F, 2.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(37, 77).addBox(-2.0F, 8.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(42, 77).addBox(1.0F, 8.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(47, 77).addBox(1.0F, 8.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(77, 61).addBox(-2.0F, 8.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(70, 0).addBox(-4.0F, 8.0F, -3.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(15, 71).addBox(2.0F, 8.0F, -3.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 51).addBox(-4.0F, 8.0F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(51, 43).addBox(4.0F, 8.0F, -2.0F, 0.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(60, 46).addBox(2.0F, 8.0F, 2.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(29, 51).addBox(-1.0F, 2.0F, -3.0F, 2.0F, 9.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(69, 12).addBox(-2.0F, 1.0F, -3.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(69, 37).addBox(1.0F, 1.0F, -3.0F, 1.0F, 7.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(22, 55).addBox(2.0F, 0.0F, -3.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 61).addBox(-3.0F, 0.0F, -3.0F, 1.0F, 8.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(53, 76).addBox(-4.0F, 2.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(76, 55).addBox(-4.0F, 5.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(58, 76).addBox(3.0F, 5.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(77, 0).addBox(3.0F, 2.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(0.0F, 0.0F, 0.0F));

		PartDefinition armorRightArm = partdefinition.addOrReplaceChild("armorRightArm", CubeListBuilder.create().texOffs(49, 26).addBox(-1.0F, -4.0F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(15, 28).addBox(-5.0F, -3.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(67, 58).addBox(-5.0F, -2.0F, 2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(45, 69).addBox(-5.0F, -2.0F, -3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(15, 34).addBox(-5.0F, 0.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(37, 34).addBox(-5.0F, 0.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(5, 63).addBox(0.0F, 0.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(45, 63).addBox(0.0F, 3.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(77, 64).addBox(-1.0F, 0.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(72, 77).addBox(-1.0F, 0.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(61, 0).addBox(-4.0F, 0.0F, -3.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(27, 62).addBox(-4.0F, 0.0F, 2.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(71, 21).addBox(-3.0F, 6.0F, 2.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(78, 30).addBox(-1.0F, 3.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(78, 33).addBox(-4.0F, 6.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(78, 24).addBox(-1.0F, 3.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(78, 27).addBox(-1.0F, 8.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(30, 80).addBox(-1.0F, 6.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(78, 21).addBox(-1.0F, 8.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(35, 80).addBox(-1.0F, 6.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(54, 63).addBox(0.0F, 8.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(63, 63).addBox(0.0F, 6.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(77, 72).addBox(-4.0F, 8.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(77, 75).addBox(-4.0F, 8.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(5, 78).addBox(-4.0F, 6.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 72).addBox(-3.0F, 6.0F, -3.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(53, 72).addBox(-5.0F, 9.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(29, 43).addBox(-5.0F, 6.0F, -2.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-4.0F, 2.0F, 0.0F));

		PartDefinition armorLeftArm = partdefinition.addOrReplaceChild("armorLeftArm", CubeListBuilder.create().texOffs(26, 34).addBox(4.0F, 0.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(60, 72).addBox(4.0F, 9.0F, -1.0F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F))
		.texOffs(40, 43).addBox(4.0F, 6.0F, -2.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(39, 72).addBox(1.0F, 6.0F, -3.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(78, 58).addBox(0.0F, 3.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(40, 80).addBox(0.0F, 6.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(77, 78).addBox(0.0F, 3.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(45, 80).addBox(0.0F, 6.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(79, 18).addBox(0.0F, 0.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(20, 79).addBox(0.0F, 0.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(63, 78).addBox(3.0F, 8.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(10, 79).addBox(0.0F, 8.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(67, 46).addBox(0.0F, 8.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(67, 52).addBox(0.0F, 6.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(68, 6).addBox(0.0F, 3.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(23, 68).addBox(0.0F, 0.0F, -2.0F, 0.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(79, 12).addBox(0.0F, 8.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(79, 15).addBox(3.0F, 8.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 79).addBox(3.0F, 6.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(78, 67).addBox(3.0F, 6.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(46, 72).addBox(1.0F, 6.0F, 2.0F, 2.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(14, 65).addBox(1.0F, 0.0F, -3.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(36, 66).addBox(1.0F, 0.0F, 2.0F, 3.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(56, 69).addBox(1.0F, -2.0F, -3.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(67, 69).addBox(1.0F, -2.0F, 2.0F, 4.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(32, 28).addBox(1.0F, -3.0F, -2.0F, 4.0F, 1.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(50, 0).addBox(0.0F, -4.0F, -2.0F, 1.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(4.0F, 2.0F, 0.0F));

		PartDefinition armorLeftLeg = partdefinition.addOrReplaceChild("armorLeftLeg", CubeListBuilder.create().texOffs(45, 51).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(25, 79).addBox(2.0F, 3.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(52, 79).addBox(2.0F, 7.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(57, 79).addBox(2.0F, 7.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(79, 42).addBox(2.0F, 3.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(79, 36).addBox(2.0F, 0.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(79, 39).addBox(2.0F, 0.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(11, 52).addBox(-2.0F, 6.0F, -3.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 55).addBox(-2.0F, 6.0F, 2.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(56, 51).addBox(-2.0F, 0.0F, 2.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 38).addBox(2.0F, 0.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(38, 18).addBox(2.0F, 6.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition armorLeftBoot = partdefinition.addOrReplaceChild("armorLeftBoot", CubeListBuilder.create().texOffs(0, 21).addBox(-2.0F, 12.0F, -3.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(46, 9).addBox(3.0F, 9.0F, -2.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(23, 18).addBox(-3.0F, 9.0F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(72, 72).addBox(2.0F, 9.0F, -3.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(73, 26).addBox(2.0F, 9.0F, 2.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(11, 58).addBox(-2.0F, 9.0F, -4.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(58, 13).addBox(-2.0F, 9.0F, 3.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offset(2.0F, 12.0F, 0.0F));

		PartDefinition armorRightLeg = partdefinition.addOrReplaceChild("armorRightLeg", CubeListBuilder.create().texOffs(57, 7).addBox(-2.0F, 0.0F, 2.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(56, 57).addBox(-2.0F, 0.0F, -3.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(57, 33).addBox(-2.0F, 6.0F, -3.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(68, 80).addBox(-3.0F, 7.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(5, 81).addBox(-3.0F, 3.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(15, 81).addBox(-3.0F, 0.0F, -3.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(81, 45).addBox(-3.0F, 0.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(81, 48).addBox(-3.0F, 3.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(81, 51).addBox(-3.0F, 7.0F, 2.0F, 1.0F, 1.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(45, 57).addBox(-2.0F, 6.0F, 2.0F, 4.0F, 4.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(39, 0).addBox(-3.0F, 6.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(11, 43).addBox(-3.0F, 0.0F, -2.0F, 1.0F, 4.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		PartDefinition armorRightBoot = partdefinition.addOrReplaceChild("armorRightBoot", CubeListBuilder.create().texOffs(58, 18).addBox(-2.0F, 9.0F, -4.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(60, 23).addBox(-2.0F, 9.0F, 3.0F, 4.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 47).addBox(-4.0F, 9.0F, -2.0F, 1.0F, 3.0F, 4.0F, new CubeDeformation(0.0F))
		.texOffs(23, 11).addBox(-3.0F, 12.0F, -3.0F, 5.0F, 0.0F, 6.0F, new CubeDeformation(0.0F))
		.texOffs(73, 31).addBox(-3.0F, 9.0F, 2.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(74, 12).addBox(-3.0F, 9.0F, -3.0F, 1.0F, 3.0F, 1.0F, new CubeDeformation(0.0F))
		.texOffs(0, 28).addBox(2.0F, 9.0F, -3.0F, 1.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.0F, 12.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 128, 128);
	}

	@Override
	public void setupAnim(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		armorHead.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		armorBody.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		armorRightArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		armorLeftArm.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		armorLeftLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		armorLeftBoot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		armorRightLeg.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		armorRightBoot.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}