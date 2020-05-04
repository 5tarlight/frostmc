package yeahx4

import org.bukkit.plugin.java.JavaPlugin

class Main: JavaPlugin() {
    public override fun onEnable() {
        println("YEAHx4 플러그인 활성화")
    }

    public override fun onDisable() {
        println("YEAHx4 플러그인 비활성화")
    }
}