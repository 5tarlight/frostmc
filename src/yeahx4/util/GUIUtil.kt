package yeahx4.util

import org.bukkit.Material
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack

class GUIUtil {
    companion object {
        fun createItem (inv: Inventory, material: Material, amount: Int, invSlot: Int, displayName: String?, lore: MutableList<out String>): ItemStack {
            val item = ItemStack(material, amount)

            val meta = item.itemMeta
            meta?.setDisplayName(displayName)
            meta?.lore = lore
            item.itemMeta = meta

            inv.setItem(invSlot - 1, item)
            return item
        }

        fun createItemByte (inv: Inventory, material: Material, byteId: Int, amount: Int, invSlot: Int, displayName: String?, lore: MutableList<out String>): ItemStack {
            val item = ItemStack(material, amount, byteId.toShort())

            val meta = item.itemMeta
            meta?.setDisplayName(displayName)
            meta?.lore = lore
            item.itemMeta = meta

            inv.setItem(invSlot - 1, item)
            return item
        }
    }
}