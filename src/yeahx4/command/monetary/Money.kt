package yeahx4.command.monetary

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
            if (args.isEmpty()) {
                val money = MoneyManager.getMoney(sender)
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

                        MoneyManager.setMoney(target[0] as Player, args[2].toDouble())
                        sender.sendMessage("${target[0].name} 의 돈을 ${args[2].toDouble()}으로 지정했습니다.")

                        if (target[0].isOnline && target[0] is Player) {
                            val p = target[0] as Player
                            p.sendMessage("${sender.name}님 께서 당신의 돈을 ${args[2].toDouble()}눈꽃 으로 지정했습니다.")
                        }

                        return true
                    }
                    "send" -> {
                        if (args.size < 3) {
                            sender.sendMessage("사용법 : money send <user> <money>")
                            return false
                        }

                        val target = Bukkit.getOfflinePlayers().filter { p -> p.name == args[1] }

                        if (target.isEmpty()) {
                            sender.sendMessage("해당 유저를 찾을 수 없습니다.")
                            return false
                        }

                        if (sender.uniqueId.equals(target[0].uniqueId)) {
                            sender.sendMessage("자기자신에게 송금 할 수 없습니다.")
                            return false
                        }

                        val remit = MoneyManager.getMoney(sender)
                        val recip = MoneyManager.getMoney(target[0] as Player)
                        val smoney = args[2].toDouble()

                        if (remit < smoney) {
                            sender.sendMessage("${ChatColor.RED}돈이 모자랍니다.")
                            return false
                        }

                        MoneyManager.setMoney(sender, remit - smoney)
                        MoneyManager.setMoney(target[0] as Player, recip + smoney)

                        sender.sendMessage("${target[0].name}님 에게 ${smoney}눈꽃을 보냈습니다.")

                        if (target[0].isOnline && target[0] is Player) {
                            val p = target[0] as Player
                            p.sendMessage("${sender.name}님 께서 ${smoney}눈꽃을 보냈습니다.")
                        }

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
}