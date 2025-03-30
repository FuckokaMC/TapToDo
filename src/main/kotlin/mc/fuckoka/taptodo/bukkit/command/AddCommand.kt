package mc.fuckoka.taptodo.bukkit.command

import mc.fuckoka.commandframework.SubCommandBase
import mc.fuckoka.taptodo.TapToDo
import mc.fuckoka.taptodo.application.AddMacroUseCase
import mc.fuckoka.taptodo.domain.MacroRepository
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class AddCommand(private val plugin: TapToDo, private val repository: MacroRepository) :
    SubCommandBase("add", "taptodo.commands.taptodo"), Listener {
    private val listenInteractEvent = mutableMapOf<Int, String>()

    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(plugin.messages.getString("only-player")!!)
            return true
        }

        if (args.isEmpty()) return false

        listenInteractEvent[sender.entityId] = args.joinToString(" ")
        sender.sendMessage(plugin.messages.getString("interact-block")!!)

        return true
    }

    override fun onTabComplete(
        sender: CommandSender, command: Command, label: String, args: Array<out String>
    ): MutableList<String>? {
        return null
    }

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        val entityId = player.entityId
        if (!listenInteractEvent.contains(entityId)) return

        val block = event.clickedBlock ?: return

        AddMacroUseCase(repository).execute(
            block.x,
            block.y,
            block.z,
            block.world.name,
            listenInteractEvent[entityId]!!
        )
        listenInteractEvent.remove(entityId)
        player.sendMessage(plugin.messages.getString("added")!!)
    }
}
