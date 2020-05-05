package yeahx4.command.trigger

import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import java.io.File
import java.io.IOException

class AreaTriggerTabCompletion : TabCompleter {
    override fun onTabComplete(sender: CommandSender, command: Command, alias: String, args: Array<out String>): MutableList<String> {
        when (args.size) {
            1 -> return mutableListOf("add", "remove", "list")
            2 -> {
                try {
                    val dir = File("./plugins/yeahx4/trigger")

                    if (!dir.exists()) {
                        dir.mkdirs()
                    }

                    val files = dir.listFiles()

                    if (files == null || files.isEmpty()) {
                        return mutableListOf()
                    }

                    val list = mutableListOf<String>()
                    for (file in files) {
                        list.add(file.name.split(".")[0])
                    }
                    return mutableListOf()
                } catch (ex1: SecurityException) {
                    sender.sendMessage("SecurityException")
                    return mutableListOf()
                } catch (ex2: ClassNotFoundException) {
                    sender.sendMessage("ClassNotFoundException")
                    return mutableListOf()
                } catch (ex3: IOException) {
                    sender.sendMessage("IOException")
                    return mutableListOf()
                }
            }
            9 -> return mutableListOf("tp")
        }

        return mutableListOf()
    }
}