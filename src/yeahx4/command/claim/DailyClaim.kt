package yeahx4.command.claim

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class DailyClaim : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, s: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}Only players can use this.")
            return false
        }

        val inv = Bukkit.createInventory(sender, 9, "${ChatColor.GOLD}Daily Claim")

        val bread = ItemStack(Material.BREAD, 3)
        val breadMeta = bread.itemMeta

        breadMeta?.lore = listOf("${ChatColor.AQUA}일일 보상")
        breadMeta?.setDisplayName("일일 보상 빵")
        bread.itemMeta = breadMeta

        inv.contents = arrayOf(bread)

        sender.openInventory(inv)
        return true
    }
}