package winlyps.lavaFishingRod

import org.bukkit.entity.FishHook
import org.bukkit.entity.LivingEntity
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.ProjectileHitEvent
import org.bukkit.event.player.PlayerFishEvent

class FishingListener : Listener {

    private val lavaHooks = HashSet<FishHook>()

    fun addLavaHook(hook: FishHook) {
        lavaHooks.add(hook)
    }

    @EventHandler
    fun onProjectileHit(event: ProjectileHitEvent) {
        if (event.entity is FishHook) {
            val hook = event.entity as FishHook
            if (lavaHooks.contains(hook)) {
                val hitEntity = event.hitEntity
                if (hitEntity is LivingEntity) {
                    // Apply burning effect for 5 seconds (100 ticks)
                    hitEntity.fireTicks = 100
                }
                lavaHooks.remove(hook)
            }
        }
    }

    @EventHandler
    fun onPlayerFish(event: PlayerFishEvent) {
        // Clean up the set to prevent memory leaks
        if (event.state == PlayerFishEvent.State.FAILED_ATTEMPT || event.state == PlayerFishEvent.State.REEL_IN) {
            lavaHooks.remove(event.hook)
        }
    }
}