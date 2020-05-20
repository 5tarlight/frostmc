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
            sender.sendMessage("/flea <search|sell|buy> [item]")
            return false
        } else {
            when (args[0]) {
                "search" -> {
                    if (args.size < 2) {
                        sender.sendMessage("/flea search <item> [tag]")
                        return false
                    }

                    val mat = Material.getMaterial(args[1].toUpperCase())

                    if (mat == null || mat.isAir) {
                        sender.sendMessage("해당 아이템을 찾을 수 없습니다.")
                        return false
                    }

                    val tag = if (args.size > 3) {
                        args[2].toInt()
                    } else {
                        0
                    }

                    searchFlea(sender, mat, tag)
                    return true
                }
                else -> {
                    sender.sendMessage("잘못된 사용입니다.")
                    sender.sendMessage("/flea <search|sell|buy> [item]")
                    return false
                }
            }
        }
    }

    fun searchFlea (sender: Player, mat: Material, tag: Int): Boolean {
        if (mat == null) {
            sender.sendMessage("해당 아이템을 찾을 수 없습니다.")
            return false
        }

        val inv = Bukkit.createInventory(sender, 9 * 6, "Flea Market")

        GUIUtil.createItemByte(inv, mat, tag, 1, 5, null, mutableListOf())
        sender.openInventory(inv)

        return true
    }
}