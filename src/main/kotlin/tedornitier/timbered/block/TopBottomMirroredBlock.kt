package tedornitier.timbered.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty

abstract class TopBottomMirroredBlock : TimberedBlock() { // TODO extend MirroredBlock

    private val defaultBottomState = true
    private lateinit var facingYAxis: BooleanProperty
    private lateinit var bottom: BooleanProperty

    init {
        registerDefaultState(
            stateDefinition.any().setValue(facingYAxis, true)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        facingYAxis = BooleanProperty.create("facing_y_axis")
        bottom = BooleanProperty.create("bottom")
        builder.add(facingYAxis).add(bottom)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val facingYAxis = context.horizontalDirection.opposite.let {
            it == Direction.NORTH || it == Direction.SOUTH
        }
        val belowBlockState = context.level.getBlockState(context.clickedPos.below())
        return this.defaultBlockState().setValue(this.facingYAxis, facingYAxis)
            .setValue(bottom, getOppositeState(belowBlockState))
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
        return state.setValue(bottom, getOppositeState(belowBlockState))
    }

    private fun getOppositeState(blockState: BlockState): Boolean {
        return if (blockState.block is TopBottomMirroredBlock) !blockState.getValue(bottom)
        else defaultBottomState
    }
}