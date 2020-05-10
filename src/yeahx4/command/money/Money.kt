package yeahx4.command.money

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.io.*
import java.lang.NumberFormatException

class Money : CommandExecutor {
    override fun onCommand(sender: CommandSender, command: Command, s: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}Only players can use this.")
            return false
        }

        try {
            val dir = File("./plugins/yeahx4/money")
            val root = File(dir, "${sender.uniqueId}.yeahx4");

            if (!dir.exists()) {
                dir.mkdirs()
            }

            if (!root.exists()) {
                overwrite(root, 100.0);
            }

            if (args.isEmpty()) {
                val money = read(root)
                sender.sendMessage("현재 보유자산 : ${money}눈꽃")
                return true
            } else {
                when (args[0]) {
                    "set" -> {
                        if (!sender.isOp) {
                            sender.sendMessage("${ChatColor.RED}You don't have permissions to do this.")
                            return false
                        }

                        if (args.size < 3) {
                            sender.sendMessage("사용법 : money set <user> <money>")
                            return false
                        }

                        val target = Bukkit.getOfflinePlayers().filter { p -> p.name == args[1] }

                        if (target.isEmpty()) {
                            sender.sendMessage("해당 유저를 찾을 수 없습니다.")
                            return false
                        }

                        overwrite(File(dir, "${target[0].uniqueId}.yeahx4"), args[2].toDouble())
                        sender.sendMessage("${target[0].name} 의 돈을 ${args[2].toDouble()}으로 지정했습니다.")
                        return true
                    }
                }
            }
        } catch (ex1: SecurityException) {
            sender.sendMessage("${ChatColor.RED}SecurityException")
            return false
        } catch (ex2: IOException) {
            sender.sendMessage("${ChatColor.RED}SecurityException")
            return false
        } catch (ex3: NumberFormatException) {
            sender.sendMessage("${ChatColor.RED}NumberFormatException")
            return false
        }
        return false
    }

    @Throws(SecurityException::class, IOException::class)
    private fun overwrite(root: File, money: Double) {
        if (root.exists()) {
            root.delete()
            root.createNewFile()
        }

        val fos = FileOutputStream(root)
        val oos = ObjectOutputStream(fos)

        oos.writeDouble(money);

        oos.close()
        fos.close()
    }

    @Throws(SecurityException::class, IOException::class)
    private fun read(root: File): Double {
        val fis = FileInputStream(root)
        val ois = ObjectInputStream(fis)

        val money = ois.readDouble()

        ois.close()
        fis.close()

        return money
    }
}