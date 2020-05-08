package yeahx4.command.claim

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.command.TabCompleter
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.io.*
import java.util.*

class DailyClaim : CommandExecutor, TabCompleter {
    override fun onCommand(sender: CommandSender, command: Command, s: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}Only players can use this.")
            return false
        }

        val dir = File("./plugins/yeahx4/dailyClaim")
        val root = File(dir, "${sender.uniqueId}.yeahx4")
        val now = Date().time
        val distant = 1000 * 60

        try {
            if (!dir.exists()) {
                dir.mkdirs()
            }

            if (root.exists()) {
                val fis = FileInputStream(root)
                val dis = DataInputStream(fis)

                val r = dis.readLong()

                dis.close()
                fis.close()

                if (r > now) {
                    sender.sendMessage("${ChatColor.RED}유효하지 않은 시간입니다(미래의 시간입니다).")
                    return false
                }

                if (r + distant > now) {
                    val dist = r + distant - now
                    val h = ((dist / (1000*60*60)) % 24)
                    val m = ((dist / (1000*60)) % 60)
                    val s = (dist / 1000) % 60

                    sender.sendMessage("시간이 다 지나지 않았습니다. ${h}시간 ${m}분 ${s}초 남았습니다.")
                    return false
                }

                writeFile(sender, root, now)
                claim(sender)
                return true
            } else {
                root.createNewFile()
                writeFile(sender, root, now)
                claim(sender)

                return true
            }
        } catch (ex1: SecurityException) {
            sender.sendMessage("${ChatColor.RED}SecurityException")
            return false
        } catch (ex2: EOFException) {
            claim(sender)
            writeFile(sender, root, now)
            return true
        } catch (ex3: IOException) {
            sender.sendMessage("${ChatColor.RED}IOException")
            return false
        }
    }

    private fun claim (sender: Player) {
        val inv = Bukkit.createInventory(sender, 9, "${ChatColor.GOLD}Daily Claim")

        val bread = ItemStack(Material.BREAD, 3)
        val breadMeta = bread.itemMeta

        breadMeta?.lore = listOf("${ChatColor.AQUA}일일 보상")
        breadMeta?.setDisplayName("${ChatColor.RESET}일일 보상 빵")
        bread.itemMeta = breadMeta

        inv.contents = arrayOf(bread)

        sender.openInventory(inv)
    }

    private fun writeFile (sender: Player, root: File, now: Long): Boolean {
        try {
            val fos = FileOutputStream(root)
            val dos = DataOutputStream(fos)

            dos.writeLong(now)

            dos.close()
            fos.close()

            return true
        } catch (ex1: FileNotFoundException) {
            sender.sendMessage("${ChatColor.RED}FileNotFoundException")
            return false
        } catch (ex2: SecurityException) {
            sender.sendMessage("${ChatColor.RED}SecurityException")
            return false
        } catch (ex3: IOException) {
            sender.sendMessage("${ChatColor.RED}IOException")
            return false
        }
    }

    override fun onTabComplete(p0: CommandSender, p1: Command, p2: String, p3: Array<out String>): MutableList<String> {
        return mutableListOf()
    }
}