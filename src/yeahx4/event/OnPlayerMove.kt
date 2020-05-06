package yeahx4.event

import org.bukkit.Location
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerMoveEvent
import yeahx4.command.trigger.GetTrigger

class OnPlayerMove : Listener {
    @EventHandler
    @SuppressWarnings("unused")
    fun onPlayerMove(e: PlayerMoveEvent) {
        val loc = e.to ?: return

        for (trigger in GetTrigger.Companion.triggerList) {
            val x1 = if (trigger.loc1.first <= trigger.loc2.first) trigger.loc1.first else trigger.loc2.first
            val x2 = if (trigger.loc1.first <= trigger.loc2.first) trigger.loc2.first else trigger.loc1.first

            val y1 = if (trigger.loc1.second <= trigger.loc2.second) trigger.loc1.second else trigger.loc2.second
            val y2 = if (trigger.loc1.second <= trigger.loc2.second) trigger.loc2.second else trigger.loc1.second

            val z1 = if (trigger.loc1.third <= trigger.loc2.third) trigger.loc1.third else trigger.loc2.third
            val z2 = if (trigger.loc1.third <= trigger.loc2.third) trigger.loc2.third else trigger.loc1.third

            if (loc.x in (x1 - 0.5)..(x2 + 0.5) && loc.y in (y1 - 0.5)..(y2 + 0.5) && loc.z in (z1 - 0.5)..(z2 + 0.5)) {
                trigger.action.execute(e.player)
            }
        }
    }
}