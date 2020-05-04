package yeahx4

import org.bukkit.plugin.java.JavaPlugin
import yeahx4.command.Plugin
import yeahx4.command.trigger.AreaTrigger
import yeahx4.command.trigger.GetTrigger

class Main: JavaPlugin() {
    public override fun onLoad() {
        println("Loading YEAHx4 plugin")
    }

    public override fun onEnable() {
        getCommand("plugin")?.setExecutor(Plugin())
        getCommand("areatrigger")?.setExecutor(AreaTrigger())
        getCommand("gettrigger")?.setExecutor(GetTrigger())

        println("YEAHx4 plugin enabled")
    }

    public override fun onDisable() {
        println("YEAHx4 plugin disabled")
    }
}