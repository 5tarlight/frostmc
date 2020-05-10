package yeahx4

import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import yeahx4.command.Plugin
import yeahx4.command.claim.DailyClaim
import yeahx4.command.monetary.Money
import yeahx4.command.monetary.MoneyTabCompletion
import yeahx4.command.monetary.flea.Flea
import yeahx4.command.monetary.flea.FleaTabCompletion
import yeahx4.command.trigger.*
import yeahx4.event.OnInventoryClick
import yeahx4.event.OnPlayerMove
import yeahx4.event.OnPlayerRegainHealth

class Main: JavaPlugin() {
    public override fun onLoad() {
        println("Loading YEAHx4 plugin")
    }

    public override fun onEnable() {
        getCommand("plugin")?.setExecutor(Plugin())
        getCommand("plugin")?.tabCompleter = Plugin()

        getCommand("areatrigger")?.setExecutor(AreaTrigger())
        getCommand("areatrigger")?.tabCompleter = AreaTriggerTabCompletion()

        getCommand("gettrigger")?.setExecutor(GetTrigger())
        getCommand("gettrigger")?.tabCompleter = GetTriggerTabCompletion()

        getCommand("reloadtrigger")?.setExecutor(ReloadTrigger())
        getCommand("reloadtrigger")?.tabCompleter = ReloadTrigger()

        getCommand("claim")?.setExecutor(DailyClaim())
        getCommand("claim")?.tabCompleter = DailyClaim()

        getCommand("money")?.setExecutor(Money())
        getCommand("money")?.tabCompleter = MoneyTabCompletion()

        getCommand("flea")?.setExecutor(Flea())
        getCommand("flea")?.tabCompleter = FleaTabCompletion()

        server.pluginManager.registerEvents(OnPlayerMove(), this)
        server.pluginManager.registerEvents(OnPlayerRegainHealth(), this)
        server.pluginManager.registerEvents(OnInventoryClick(), this)

        reload(Bukkit.getConsoleSender())
        println("YEAHx4 plugin enabled")
    }

    public override fun onDisable() {
        println("YEAHx4 plugin disabled")
    }
}