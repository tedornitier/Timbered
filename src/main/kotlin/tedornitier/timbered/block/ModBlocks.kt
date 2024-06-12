package tedornitier.timbered.block

import net.minecraft.world.item.BlockItem
import net.minecraft.world.item.Item
import net.neoforged.neoforge.registries.DeferredRegister
import org.apache.logging.log4j.Level
import tedornitier.timbered.Timbered
import tedornitier.timbered.Timbered.logger
import tedornitier.timbered.block.TimberedBlocks.blocks
import tedornitier.timbered.block.TimberedWoodTypes.woodTypes
import java.util.function.Supplier
import kotlin.reflect.full.companionObjectInstance
import kotlin.reflect.full.createInstance

object ModBlocks {
    val registry: DeferredRegister.Blocks = DeferredRegister.createBlocks(Timbered.ID)
    val blockItems: DeferredRegister.Items = DeferredRegister.createItems(Timbered.ID)

    init {
        woodTypes.forEach { wood ->
            blocks.forEach { block ->
                val blockCompanionObject = block.companionObjectInstance as TimberedObject
                val blockName = blockCompanionObject.name
                logger.log(Level.INFO, "Registering block $blockName")
                registry.register("${blockName}_$wood") { -> block.createInstance() }.also {
                    logger.log(Level.INFO, "Registering block item $blockName")
                    blockItems.register("${blockName}_$wood", Supplier { BlockItem(it.get(), Item.Properties()) })
                }
            }
        }
    }
}
