package yeahx4.command.money

import org.bukkit.ChatColor
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.io.*

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
                overwrite(root, 0.0);
            }
        } catch (ex1: SecurityException) {
            sender.sendMessage("${ChatColor.RED}SecurityException")
            return false
        } catch (ex2: IOException) {
            sender.sendMessage("${ChatColor.RED}SecurityException")
            return false
        }

        return true
    }

    @Throws(SecurityException::class, IOException::class)
    private fun overwrite(root: File, money: Double) {
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