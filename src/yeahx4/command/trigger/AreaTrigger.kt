package yeahx4.command.trigger

import org.apache.commons.lang.exception.ExceptionUtils
import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.io.*
import java.lang.NumberFormatException

class AreaTrigger : CommandExecutor {
    data class TriggerInfo (
            val loc1: Triple<Double, Double, Double>,
            val loc2: Triple<Double, Double, Double>,
            val action: TriggerAction
    ) : Serializable

    @SuppressWarnings("unused")
    open inner class TriggerAction (var actionType: String): Serializable {
        open fun execute(trigger : Player): Boolean {
            return false
        }
    }

    inner class TpAction (private var args: Array<out String> = arrayOf()): TriggerAction("tp"), Serializable {
        override fun execute(trigger: Player): Boolean {
            val loc = Location(Bukkit.getWorld("default"), args[8].toDouble(), args[9].toDouble(), args[10].toDouble())
            trigger.teleport(loc)
            return true
        }
    }

    override fun onCommand(sender: CommandSender, cmd: Command, s: String, args: Array<out String>): Boolean {
        if (!sender.isOp) {
            sender.sendMessage("${ChatColor.RED} You don't have permission to do this.")
            return false
        }

        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}플레이어만 사용 할 수 있는 명령어입니다.")
            return false
        }

        if (args.size < 8) {
            sender.sendMessage("")
            sender.sendMessage("=======사용법=======")
            sender.sendMessage("/areatrigger <name> <x1> <y1> <z1> <x2> <y2> <z2> <actionType>")
            sender.sendMessage("(x1, y1, z1) ~ (x2, y2, z2) 사이의 진입을 감시합니다.")
            sender.sendMessage("==================")
            sender.sendMessage("")

            return false
        }

        val action: TriggerAction? = when (args[7]) {
            "tp" -> TpAction(args)
            else -> null
        }

        if(action == null) {
            sender.sendMessage("${ChatColor.RED}invalid action type or action syntax")

            return false
        }

        var result: TriggerInfo
        try {
            result = TriggerInfo(
                    Triple(args[1].toDouble(), args[2].toDouble(), args[3].toDouble()),
                    Triple(args[4].toDouble(), args[5].toDouble(), args[6].toDouble()),
                    action
            )
        } catch (e: NumberFormatException) {
            sender.sendMessage("${ChatColor.RED}Invalid Number")
            return false
        }

        val dir = File("./plugins/yeahx4/trigger")
        val root = File(dir, "${args[0]}.yeahx4")

        if(!dir.exists()) {
            dir.mkdirs()
        }

        try {
            if(!root.exists()) {
                root.createNewFile()
            } else {
                root.delete()
                root.createNewFile()
            }
        } catch (ex: IOException) {
            sender.sendMessage("${ChatColor.RED}Error occurred while creating trigger file. To more details, please check log")
            return false
        }



        val fos: FileOutputStream
        val oos: ObjectOutputStream

        try {
            fos = FileOutputStream(root)
            oos = ObjectOutputStream(fos)
        } catch (ex: IOException) {
            sender.sendMessage("${ChatColor.RED}Error occured while creating stream . To more details, please check log")
            return false
        }

        try {
            oos.writeObject(result)
        } catch (ex1: NotSerializableException) {
            println(ExceptionUtils.getStackTrace(ex1))
            sender.sendMessage("${ChatColor.RED}NotSerializableException - writing")
            return false
        } catch (ex2: InvalidClassException) {
            sender.sendMessage("${ChatColor.RED}InvalidClassException - writing")
            return false
        } catch (ex3: IOException) {
            sender.sendMessage("${ChatColor.RED}IOException - writing")
            return false
        } finally {
            oos.close()
        }

        sender.sendMessage("트리거가 저장되었습니다.")

        return true
    }
}