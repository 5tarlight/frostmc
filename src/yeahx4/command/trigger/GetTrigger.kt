package yeahx4.command.trigger

import org.apache.commons.lang.exception.ExceptionUtils
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import java.io.*

class GetTrigger : CommandExecutor {
    override fun onCommand(sender: CommandSender, cmd: Command, s: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            sender.sendMessage("/gettrigger <name>")
            return false
        }

        try {
            val dir = File("./plugins/yeahx4/trigger")
            val root = File(dir, "${args[0]}.yeahx4")

            if(!dir.exists() || !root.exists()) {
                sender.sendMessage("파일이 존재 하지 않습니다.")
                return false
            }

            val fis = FileInputStream(root)
            val ois = ObjectInputStream(fis)

            val trigger = ois.readObject()

            if(trigger !is AreaTrigger.TriggerInfo) {
                sender.sendMessage("${ChatColor.RED}유효하지 않은 파일입니다.")

                return false
            }

            val info: AreaTrigger.TriggerInfo = trigger
            sender.sendMessage(info.toString())
            
            return true
        } catch (ex1: SecurityException) {
            sender.sendMessage("SecurityException")
            return false
        } catch (ex2: StreamCorruptedException) {
            sender.sendMessage("StreamCorruptedException")
            return false
        } catch (ex3: ClassNotFoundException) {
            sender.sendMessage("ClassNotFoundException")
            return false
        } catch (ex4: IOException) {
            println(ExceptionUtils.getCause(ex4))
            sender.sendMessage("IOExcepion")
            return false
        }
    }
}