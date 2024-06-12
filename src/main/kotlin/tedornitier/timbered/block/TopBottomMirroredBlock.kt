package tedornitier.timbered.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty

abstract class TopBottomMirroredBlock : TimberedBlock() { // TODO extend MirroredBlock

    val DEFAULT_BOTTOM_STATE = true
    lateinit var FACING_Y_AXIS: BooleanProperty
    lateinit var BOTTOM: BooleanProperty

    init {
        registerDefaultState(
            stateDefinition.any().setValue(FACING_Y_AXIS, true)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        FACING_Y_AXIS = BooleanProperty.create("facing_y_axis")
        BOTTOM = BooleanProperty.create("bottom")
        builder.add(FACING_Y_AXIS).add(BOTTOM)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val facingYAxis = context.horizontalDirection.opposite.let {
            it == Direction.NORTH || it == Direction.SOUTH
        }
        val belowBlockState = context.level.getBlockState(context.clickedPos.below())
        return this.defaultBlockState().setValue(FACING_Y_AXIS, facingYAxis)
            .setValue(BOTTOM, getOppositeState(belowBlockState))
    }

    override fun updateShape(
        state: BlockState,
        direction: Direction,
        neighborState: BlockState,
        level: LevelAccessor,
        pos: BlockPos,
        neighborPos: BlockPos
    ): BlockState {
        val belowBlockState = level.getBlockState(pos.below())
        return state.setValue(BOTTOM, getOppositeState(belowBlockState))
    }

    private fun getOppositeState(blockState: BlockState): Boolean {
        return if (blockState.block is TopBottomMirroredBlock) !blockState.getValue(BOTTOM)
        else DEFAULT_BOTTOM_STATE
    }
}