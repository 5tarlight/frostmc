package yeahx4.command.trigger

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class ReloadTrigger : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, s: String, args: Array<out String>): Boolean {
        if (!sender.isOp) {
            sender.sendMessage("${ChatColor.RED}You don't have permissions to do this.")
            return false
        }

        return reload(sender)
    }

    override fun onTabComplete(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): MutableList<String> {
        return mutableListOf()
    }
}