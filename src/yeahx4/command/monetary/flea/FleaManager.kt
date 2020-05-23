package yeahx4.command.monetary.flea

import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import yeahx4.util.GUIUtil

class FleaManager {
    companion object {
        fun searchFlea (sender: Player, mat: Material, tag: Int): Boolean {
            if (mat == null) {
                sender.sendMessage("해당 아이템을 찾을 수 없습니다.")
                return false
            }

            val inv = Bukkit.createInventory(sender, 9 * 6, "Flea Market")

            GUIUtil.createItemByte(inv, mat, tag, 1, 5, null, mutableListOf())
            sender.openInventory(inv)

            return true
        }

        fun sellItem (sender: Player, mat: Material, tag: Int, amount: Int, price: Double): Boolean {
            return false
        }
    }
}