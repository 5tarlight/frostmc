package yeahx4.command.monetary

import org.bukkit.entity.Player
import java.io.*

class MoneyManager {
    companion object {
        @Throws(SecurityException::class, IOException::class)
        fun getMoney(target: Player): Double {
            val dir = File("./plugins/yeahx4/money")
            val root = File(dir, "${target.uniqueId}.yeahx4");

            if (!root.exists()) {
                setMoney(target, 100.0)
                return 100.0
            }

            val fis = FileInputStream(root)
            val ois = ObjectInputStream(fis)

            val money = ois.readDouble()

            ois.close()
            fis.close()

            return money
        }

        @Throws(SecurityException::class, IOException::class)
        fun setMoney(target: Player, money: Double): Boolean {
            val dir = File("./plugins/yeahx4/money")
            val root = File(dir, "${target.uniqueId}.yeahx4");

            if (!dir.exists()) {
                dir.mkdirs()
            }

            if (root.exists()) {
                root.delete()
            }
            root.createNewFile()

            val fos = FileOutputStream(root)
            val oos = ObjectOutputStream(fos)

            oos.writeDouble(money);

            oos.close()
            fos.close()

            return true
        }
    }
}