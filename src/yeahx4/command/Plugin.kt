package yeahx4.command

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter

class Plugin : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, cmd: Command, s: String, args: Array<out String>): Boolean {
        sender.sendMessage("=====================")
        sender.sendMessage(arrayOf("${ChatColor.GREEN}", "YEAHx4 플러그인"))
        sender.sendMessage("\n")
        sender.sendMessage("개발 : YEAHx4")
        sender.sendMessage("디스코드 : YEAHx4#9662")
        sender.sendMessage("=====================")

        return true
    }

    override fun onTabComplete(sender: CommandSender, command: Command, s: String, args: Array<out String>): MutableList<String> {
        return mutableListOf()
    }
}