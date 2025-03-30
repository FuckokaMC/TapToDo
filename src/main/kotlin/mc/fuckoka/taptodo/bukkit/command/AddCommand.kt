package mc.fuckoka.taptodo.bukkit.command

import mc.fuckoka.commandframework.SubCommandBase
import mc.fuckoka.taptodo.TapToDo
import mc.fuckoka.taptodo.bukkit.listener.InteractListener
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class AddCommand(private val plugin: TapToDo) : SubCommandBase("add", "taptodo.commands.taptodo") {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(plugin.messages.getString("only-player")!!)
            return true
        }

        if (args.isEmpty()) return false

        InteractListener.listenAdd(sender.entityId, args.joinToString(" "))
        sender.sendMessage(plugin.messages.getString("interact-block")!!)

        return true
    }

    override fun onTabComplete(
        sender: CommandSender, command: Command, label: String, args: Array<out String>
    ): MutableList<String>? {
        return null
    }
}
