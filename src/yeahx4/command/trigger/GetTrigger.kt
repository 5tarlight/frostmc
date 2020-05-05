package yeahx4.command.trigger

import org.apache.commons.lang.exception.ExceptionUtils
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import yeahx4.command.trigger.GetTrigger.Companion.triggerList
import java.io.*

fun reload(sender: CommandSender): Boolean {
    try {
        sender.sendMessage("트리거 리로딩")
        val dir = File("./plugins/yeahx4/trigger")

        if (!dir.exists()) {
            dir.mkdirs()
        }

        val files = dir.listFiles()

        if (files == null || files.isEmpty()) {
            sender.sendMessage("트리거가 없습니다.")
            return false
        }

        if (triggerList.isNotEmpty()) {
            triggerList.clear()
        }

        for (file in files) {
            val fis = FileInputStream(file)
            val ois = ObjectInputStream(fis)

            val obj = ois.readObject()
            if (obj !is AreaTrigger.TriggerInfo) {
                println("유효하지 않은 파일 (${file.nameWithoutExtension}) 스킵")
                continue
            }

            triggerList.add(obj)
        }

        sender.sendMessage("트리거 ${triggerList.size} 개가 로드되었습니다.")
        return true
    } catch (ex1: SecurityException) {
        sender.sendMessage("SecurityException")
        return true
    } catch (ex2: ClassNotFoundException) {
        sender.sendMessage("ClassNotFoundException")
        return true
    } catch (ex3: IOException) {
        sender.sendMessage("IOException")
        return true
    }
}

class GetTrigger : CommandExecutor {
    companion object {
        val triggerList = mutableListOf<AreaTrigger.TriggerInfo>()
    }

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

            sender.sendMessage("loc1 : ${info.loc1}")
            sender.sendMessage("loc2 : ${info.loc2}")
            sender.sendMessage("actionType : ${info.action.actionType}")
            
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