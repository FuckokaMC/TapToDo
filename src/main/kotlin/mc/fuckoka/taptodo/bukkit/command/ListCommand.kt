package mc.fuckoka.taptodo.bukkit.command

import mc.fuckoka.commandframework.SubCommandBase
import mc.fuckoka.taptodo.TapToDo
import mc.fuckoka.taptodo.application.FindMacroUseCase
import mc.fuckoka.taptodo.domain.MacroRepository
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class ListCommand(private val plugin: TapToDo, private val repository: MacroRepository) :
    SubCommandBase("list", "taptodo.commands.taptodo"), Listener {
    private val listenInteractEvent = mutableListOf<Int>()
    override fun onCommand(sender: CommandSender, command: Command, label: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(plugin.messages.getString("only-player")!!)
            return true
        }

        listenInteractEvent.add(sender.entityId)

        sender.sendMessage(plugin.messages.getString("interact-block.list")!!)

        return true
    }

    override fun onTabComplete(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>
    ): MutableList<String>? {
        TODO("Not yet implemented")
    }

    @EventHandler
    fun onInteract(event: PlayerInteractEvent) {
        val player = event.player
        val entityId = player.entityId
        if (!listenInteractEvent.contains(entityId)) return

        val block = event.clickedBlock ?: return

        val macros = FindMacroUseCase(repository).execute(block.x, block.y, block.z, block.world.name)
        listenInteractEvent.remove(entityId)
        macros.values.forEachIndexed { index, macro ->
            player.sendMessage(plugin.messages.getString("list")!!.format(index, macro))
        }
    }
}
