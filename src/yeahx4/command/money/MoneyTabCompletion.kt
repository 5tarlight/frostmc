package yeahx4.command.money

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class MoneyTabCompletion : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, s: String, args: Array<out String>): MutableList<String> {
        return when (args.size) {
            1 -> {
                mutableListOf("set", "send")
            }
            2 -> {
                val names = Bukkit.getOfflinePlayers().map { p -> p.name!! }
                names.toMutableList()
            }
            else -> {
                mutableListOf()
            }
        }
    }
}