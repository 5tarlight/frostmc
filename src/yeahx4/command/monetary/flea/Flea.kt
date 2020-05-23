package yeahx4.command.monetary.flea

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.lang.NumberFormatException

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

                    val mat = Material.getMaterial(args[1].toUpperCase(), true)

                    if (mat == null || mat.isAir) {
                        sender.sendMessage("해당 아이템을 찾을 수 없습니다.")
                        return false
                    }

                    val tag = if (args.size > 3) {
                        args[2].toInt()
                    } else {
                        0
                    }

                    FleaManager.searchFlea(sender, mat, tag)
                    return true
                }
                "sell" -> {
                    if (args.size < 4) {
                        sender.sendMessage("/flea sell <item> <tag> <amount> <price>")
                        return false
                    }

                    try {
                        val mat = Material.getMaterial(args[1].toUpperCase(), true)
                        val tag = args[2].toInt()
                        val amount = args[3].toInt()
                        val price = args[4].toDouble()

                        if (mat == null || mat.isAir) {
                            sender.sendMessage("해당 아이템을 찾을 수 없습니다.")
                            return false
                        }

                        FleaManager.sellItem(sender, mat, tag, amount, price)
                        return true
                    } catch (ex1: NumberFormatException) {
                        sender.sendMessage("${ChatColor.RED}numberFormatException")
                        return false
                    }

                }
                else -> {
                    sender.sendMessage("잘못된 사용입니다.")
                    sender.sendMessage("/flea <search|sell|buy> [item]")
                    return false
                }
            }
        }
    }


}