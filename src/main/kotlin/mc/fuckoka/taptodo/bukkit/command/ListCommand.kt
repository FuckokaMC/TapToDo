package mc.fuckoka.taptodo.bukkit.command

import mc.fuckoka.commandframework.SubCommandBase
import mc.fuckoka.taptodo.TapToDo
import mc.fuckoka.taptodo.bukkit.listener.InteractListener
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class ListCommand(private val plugin: TapToDo) : SubCommandBase("list", "taptodo.commands.taptodo") {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(plugin.messages.getString("only-player")!!)
            return true
        }

        InteractListener.listenList(sender.entityId)
        sender.sendMessage(plugin.messages.getString("interact-block.list")!!)

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        return null
    }
}
