package tedornitier.timbered.block

import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.Blocks
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty

abstract class MirroredBlock : TimberedBlock() {
    lateinit var FACING_Y_AXIS: BooleanProperty // TODO by lazy?

    init {
        registerDefaultState(
            stateDefinition.any().setValue(FACING_Y_AXIS, true)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        FACING_Y_AXIS = BooleanProperty.create("facing_y_axis")
        builder.add(FACING_Y_AXIS)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val facingYAxis = context.horizontalDirection.opposite.let { it == Direction.NORTH || it == Direction.SOUTH }
        return this.defaultBlockState().setValue(FACING_Y_AXIS, facingYAxis)
    }
}
