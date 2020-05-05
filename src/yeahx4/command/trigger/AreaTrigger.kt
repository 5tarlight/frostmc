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

class AreaTrigger : CommandExecutor, Serializable {

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
            val loc = Location(Bukkit.getWorld("world"), args[9].toDouble(), args[10].toDouble(), args[11].toDouble())
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

        if (
                args.isEmpty()
                || args[0] != "list"
                && (args[0] == "add" && args.size < 9
                || args[0] == "remove" && args.size < 2
                || args[0] == "list" && args.isEmpty()) // is it necessary?
        ) {
            sender.sendMessage("")
            sender.sendMessage("=======사용법=======")
            sender.sendMessage("/areatrigger add <name> <x1> <y1> <z1> <x2> <y2> <z2> <actionType>")
            sender.sendMessage("/areatrigger remove <name>")
            sender.sendMessage("/areatrigger list")
            sender.sendMessage("(x1, y1, z1) ~ (x2, y2, z2) 사이의 진입을 감시합니다.")
            sender.sendMessage("==================")
            sender.sendMessage("")

            return false
        }

        if (args[0] != "list" && args.size > 1 && args[0] != "add" && args[0] != "remove") {
            sender.sendMessage("${ChatColor.RED}Invalid operation type")
            return false
        }

        if (args[0] == "add") {
            val action: TriggerAction? = when (args[8]) {
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
                        Triple(args[2].toDouble(), args[3].toDouble(), args[4].toDouble()),
                        Triple(args[5].toDouble(), args[6].toDouble(), args[7].toDouble()),
                        action
                )
            } catch (e: NumberFormatException) {
                sender.sendMessage("${ChatColor.RED}Invalid Number")
                return false
            }

            val dir = File("./plugins/yeahx4/trigger")
            val root = File(dir, "${args[1]}.yeahx4")

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
                println(ExceptionUtils.getCause(ex1))
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

            sender.sendMessage("트리거가 추가되었습니다.")
            return true
        } else if (args[0] == "remove"){
            try {
                val dir = File("./plugins/yeahx4/trigger")
                val root = File(dir, "${args[1]}.yeahx4")

                if (!dir.exists() || !root.exists()) {
                    sender.sendMessage("해당 트리거를 찾을 수 없습니다.")
                    return false
                }

                root.delete()

                sender.sendMessage("${args[1]} 트리거 삭제")
                return true
            } catch (ex1: SecurityException) {
                sender.sendMessage("SecurityException")
                return false
            } catch (ex2: IOException) {
                sender.sendMessage("IOException")
                return false
            }

        } else {
            try {
                val dir = File("./plugins/yeahx4/trigger")

                if (!dir.exists()) {
                    dir.mkdirs()
                }

                val files = dir.listFiles()

                if (files == null || files.isEmpty()) {
                    sender.sendMessage("트리거가 없습니다.")
                    return false
                }
                for (file in files) {
                    sender.sendMessage(file.name)
                }
                return true
            } catch (ex1: SecurityException) {
                sender.sendMessage("SecurityException")
                return false
            } catch (ex2: ClassNotFoundException) {
                sender.sendMessage("ClassNotFoundException")
                return false
            } catch (ex3: IOException) {
                sender.sendMessage("IOException")
                return false
            }
        }
    }
}