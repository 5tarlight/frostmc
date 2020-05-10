package yeahx4.event

import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.EntityRegainHealthEvent

class OnPlayerRegainHealth : Listener {
    @EventHandler
    fun onPlayerRegainHealth(e: EntityRegainHealthEvent) {
        val p = e.entity

        if (p is Player) {
            e.isCancelled = true
        }
    }
}