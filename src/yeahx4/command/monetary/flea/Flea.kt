package yeahx4.command.monetary.flea

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import yeahx4.util.GUIUtil

class Flea : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, s: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}Only players can use this.")
            return false
        }

        if (args.isEmpty()) {
            sender.sendMessage("사용법 : /flea <item> [tag]")
            return false
        }

        val mat = Material.getMaterial(args[0].toUpperCase())

        if (mat == null) {
            sender.sendMessage("해당 아이템을 찾을 수 없습니다.")
            return false
        }

        var tag = 0

        if (args.size > 1) {
            tag = args[1].toInt()
        }

        val inv = Bukkit.createInventory(sender, 9 * 6, "Flea Market")

        GUIUtil.createItemByte(inv, mat, tag, 1, 5, null, mutableListOf())

        sender.openInventory(inv)

        return true
    }
}