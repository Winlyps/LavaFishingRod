package winlyps.lavaFishingRod

import org.bukkit.Material
import org.bukkit.entity.FishHook
import org.bukkit.plugin.java.JavaPlugin

class LavaFishingRod : JavaPlugin() {

    private lateinit var fishingListener: FishingListener

    override fun onEnable() {
        fishingListener = FishingListener()
        server.pluginManager.registerEvents(fishingListener, this)

        server.scheduler.runTaskTimer(this, Runnable {
            for (world in server.worlds) {
                for (entity in world.entities) {
                    if (entity is FishHook) {
                        if (entity.location.block.type == Material.LAVA) {
                            fishingListener.addLavaHook(entity)
                        }
                    }
                }
            }
        }, 0L, 1L) // Check every tick
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
