package mc.fuckoka.taptodo

import mc.fuckoka.taptodo.bukkit.command.AddCommand
import mc.fuckoka.taptodo.bukkit.command.DeleteCommand
import mc.fuckoka.taptodo.bukkit.command.ListCommand
import mc.fuckoka.taptodo.bukkit.command.TapToDoCommand
import mc.fuckoka.taptodo.bukkit.listener.InteractListener
import mc.fuckoka.taptodo.infrastructure.MacroRepositoryImpl
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.plugin.java.JavaPlugin

class TapToDo : JavaPlugin() {
    lateinit var messages: FileConfiguration
        private set

    override fun onEnable() {
        messages = YamlConfiguration()
        messages.setDefaults(YamlConfiguration.loadConfiguration(getResource("messages.yml")!!.reader()))

        val command = TapToDoCommand(this)
        command.registerSubCommands(AddCommand(this), DeleteCommand(this), ListCommand(this))
        getCommand("taptodo")?.setExecutor(command)

        server.pluginManager.registerEvents(InteractListener(this, MacroRepositoryImpl()), this)
    }
}
