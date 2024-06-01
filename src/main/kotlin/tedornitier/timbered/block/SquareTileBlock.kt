package tedornitier.timbered.block

import net.minecraft.core.BlockPos
import net.minecraft.core.Direction
import net.minecraft.world.item.context.BlockPlaceContext
import net.minecraft.world.level.LevelAccessor
import net.minecraft.world.level.block.Block
import net.minecraft.world.level.block.state.BlockState
import net.minecraft.world.level.block.state.StateDefinition
import net.minecraft.world.level.block.state.properties.BooleanProperty

class SquareTileBlock : Block(Properties.of().lightLevel { 15 }.strength(3.0f)) {
// TODO Blocks.OAK_LOG.properties()) {

    companion object {
        val NORTH: BooleanProperty = BooleanProperty.create("north")
        val EAST: BooleanProperty = BooleanProperty.create("east")
        val SOUTH: BooleanProperty = BooleanProperty.create("south")
        val WEST: BooleanProperty = BooleanProperty.create("west")
    }

    init {
        registerDefaultState(
            stateDefinition.any()
                .setValue(NORTH, false)
                .setValue(EAST, false)
                .setValue(SOUTH, false)
                .setValue(WEST, false)
        )
    }

    override fun createBlockStateDefinition(builder: StateDefinition.Builder<Block, BlockState>) {
        builder.add(NORTH, EAST, SOUTH, WEST)
    }

    override fun getStateForPlacement(context: BlockPlaceContext): BlockState {
        val blockPos = context.clickedPos
        val blockGetter = context.level
        return this.defaultBlockState()
            .setValue(NORTH, isConnected(blockGetter.getBlockState(blockPos.north())))
            .setValue(EAST, isConnected(blockGetter.getBlockState(blockPos.east())))
            .setValue(SOUTH, isConnected(blockGetter.getBlockState(blockPos.south())))
            .setValue(WEST, isConnected(blockGetter.getBlockState(blockPos.west())))
    }

    private fun isConnected(state: BlockState): Boolean {
        return state.block is SquareTileBlock
    }

    override fun updateShape(
        state: BlockState,
        direction: Direction,
        facingState: BlockState,
        level: LevelAccessor,
        currentPos: BlockPos,
        facingPos: BlockPos
    ): BlockState {
        return state.setValue(NORTH, isConnected(level.getBlockState(currentPos.north())))
            .setValue(EAST, isConnected(level.getBlockState(currentPos.east())))
            .setValue(SOUTH, isConnected(level.getBlockState(currentPos.south())))
            .setValue(WEST, isConnected(level.getBlockState(currentPos.west())))
    }
}
