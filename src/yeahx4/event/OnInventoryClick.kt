package yeahx4.event

import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent

class OnInventoryClick : Listener {
    @EventHandler
    fun onInventoryClick (e: InventoryClickEvent) {
        when (e.view.title) {
            "Flea Market" -> e.isCancelled = true
        }
    }
}