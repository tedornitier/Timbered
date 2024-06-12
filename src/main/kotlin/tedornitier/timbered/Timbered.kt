package tedornitier.timbered

import net.minecraft.client.Minecraft
import net.minecraft.world.item.CreativeModeTabs
import net.neoforged.bus.api.SubscribeEvent
import net.neoforged.fml.common.EventBusSubscriber
import net.neoforged.fml.common.Mod
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent
import net.neoforged.fml.event.lifecycle.FMLDedicatedServerSetupEvent
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent
import org.apache.logging.log4j.Level
import org.apache.logging.log4j.LogManager
import org.apache.logging.log4j.Logger
import tedornitier.timbered.block.ModBlocks
import tedornitier.timbered.block.ModBlocks.blockItems
import thedarkcolour.kotlinforforge.neoforge.forge.MOD_BUS
import thedarkcolour.kotlinforforge.neoforge.forge.runForDist

@Mod(Timbered.ID)
@EventBusSubscriber(bus = EventBusSubscriber.Bus.MOD)
object Timbered {
    const val ID = "timbered"

    val logger: Logger = LogManager.getLogger(ID)

    init {
        logger.log(Level.INFO, "Hello world!")

        ModBlocks.registry.register(MOD_BUS)
        ModBlocks.blockItems.register(MOD_BUS)

        val obj = runForDist(
            clientTarget = {
                MOD_BUS.addListener(::onClientSetup)
                Minecraft.getInstance()
            },
            serverTarget = {
                MOD_BUS.addListener(::onServerSetup)
                "test"
            })

        println(obj)
    }

    private fun onClientSetup(event: FMLClientSetupEvent) {
        logger.log(Level.INFO, "Initializing client...")
    }

    private fun onServerSetup(event: FMLDedicatedServerSetupEvent) {
        logger.log(Level.INFO, "Server starting...")
    }

    @SubscribeEvent
    fun onCommonSetup(event: FMLCommonSetupEvent) {
        logger.log(Level.INFO, "Hello! This is working!")
    }

    @SubscribeEvent
    fun buildContents(event: BuildCreativeModeTabContentsEvent) {
        if (event.tabKey === CreativeModeTabs.BUILDING_BLOCKS) {
            blockItems.entries.forEach { blockItem ->
                event.accept(blockItem.get())
            }
        }
    }
}