
package com.schokobaer.battleofgods.block;

import com.schokobaer.battleofgods.world.inventory.WorkbenchMenu;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.core.BlockPos;

import javax.xml.stream.events.Attribute;

public class WoodenWorkbenchBlock extends Block {
	public WoodenWorkbenchBlock() {
		super(BlockBehaviour.Properties.of().ignitedByLava().instrument(NoteBlockInstrument.BASS).sound(SoundType.WOOD).strength(1f, 10f).noOcclusion().isRedstoneConductor((bs, br, bp) -> false));
	}

	@Override
	public boolean propagatesSkylightDown(BlockState state, BlockGetter reader, BlockPos pos) {
		return true;
	}

	@Override
	public int getLightBlock(BlockState state, BlockGetter worldIn, BlockPos pos) {
		return 0;
	}

	@Override
	public VoxelShape getVisualShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return Shapes.empty();
	}
	private VoxelShape makeShape(){
		VoxelShape shape = Shapes.empty();
		shape = Shapes.join(shape, Shapes.box(0.625, 0, 0.1875, 0.8125, 0.5, 0.375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.625, 0, 0.625, 0.8125, 0.5, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0.625, 0.375, 0.5, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0, 0.1875, 0.375, 0.5, 0.375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.6875, 0.125, 0.375, 0.8125, 0.25, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.1875, 0.125, 0.375, 0.3125, 0.25, 0.625), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.125, 0.6875, 0.625, 0.25, 0.8125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.375, 0.125, 0.1875, 0.625, 0.25, 0.3125), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.0625, 0.4375, 0.0625, 0.9375, 0.5625, 0.9375), BooleanOp.OR);
		shape = Shapes.join(shape, Shapes.box(0.125, 0.375, 0.125, 0.875, 0.5, 0.875), BooleanOp.OR);

		return shape;
	}
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
		return makeShape();
	}

	@Override
	public InteractionResult use(BlockState state, Level level, BlockPos pos,
								 Player player, InteractionHand hand, BlockHitResult hit) {
		if (!level.isClientSide) {
			// 👇 Menu mit Gruppe erstellen
			player.openMenu(new SimpleMenuProvider(
					(containerId, inventory, _player) ->
							new WorkbenchMenu(containerId, inventory),
					Component.translatable("container.battleofgods.workbench").withStyle(Style.EMPTY.withColor(0xD3D3D3))
			));
		}
		return InteractionResult.sidedSuccess(level.isClientSide);
	}
}
