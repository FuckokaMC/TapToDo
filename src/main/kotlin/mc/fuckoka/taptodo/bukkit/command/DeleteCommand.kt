package mc.fuckoka.taptodo.bukkit.command

import mc.fuckoka.commandframework.SubCommandBase
import mc.fuckoka.taptodo.TapToDo
import mc.fuckoka.taptodo.bukkit.listener.InteractListener
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class DeleteCommand(private val plugin: TapToDo) : SubCommandBase("del", "taptodo.commands.taptodo") {
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(plugin.messages.getString("only-player")!!)
            return true
        }

        InteractListener.listenDelete(sender.entityId, if (args.isEmpty()) null else args.joinToString(" "))
        sender.sendMessage(plugin.messages.getString("interact-block.delete")!!)

        return true
    }

    override fun onTabComplete(
        sender: CommandSender, command: Command, label: String, args: Array<out String>
    ): MutableList<String>? {
        return null
    }
}
