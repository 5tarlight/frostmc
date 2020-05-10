package yeahx4.command.monetary.flea

import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class FleaTabCompletion : TabCompleter {
    override fun onTabComplete(p0: CommandSender, p1: Command, p2: String, args: Array<out String>): MutableList<String> {
        return mutableListOf()
    }
}