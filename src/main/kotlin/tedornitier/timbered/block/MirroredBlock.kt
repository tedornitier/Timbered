package tedornitier.timbered.block

import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty

abstract class MirroredBlock : TimberedBlock() {
    private lateinit var facingYAxis: BooleanProperty // TODO by lazy?

    init {
        registerDefaultState(
            stateDefinition.any().setValue(facingYAxis, true)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        facingYAxis = BooleanProperty.create("facing_y_axis")
        builder.add(facingYAxis)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val facingYAxis = context.horizontalDirection.opposite.let { it == Direction.NORTH || it == Direction.SOUTH }
        return this.defaultBlockState().setValue(this.facingYAxis, facingYAxis)
    }
}
