package mc.fuckoka.taptodo.bukkit.command

import mc.fuckoka.commandframework.CommandBase
import mc.fuckoka.taptodo.TapToDo
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class TapToDoCommand(private val plugin: TapToDo) : CommandBase() {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: List<String>): Boolean {
        arrayOf("help.header", "help.add", "help.delete", "help.delete-all", "help.list").forEach {
            sender.sendMessage(plugin.messages.getString(it)!!)
        }
        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: List<String>
    ): MutableList<String>? {
        return null
    }
}
