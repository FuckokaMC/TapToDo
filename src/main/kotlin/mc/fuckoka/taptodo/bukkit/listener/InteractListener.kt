package mc.fuckoka.taptodo.bukkit.listener

import mc.fuckoka.taptodo.TapToDo
import mc.fuckoka.taptodo.application.AddMacroUseCase
import mc.fuckoka.taptodo.application.DeleteMacroUseCase
import mc.fuckoka.taptodo.application.FindMacroUseCase
import mc.fuckoka.taptodo.domain.MacroRepository
import org.bukkit.event.EventHandler
import org.bukkit.event.EventPriority
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerInteractEvent

class InteractListener(private val plugin: TapToDo, private val repository: MacroRepository) : Listener {
    companion object {
        private val listenAdd = mutableMapOf<Int, String>()
        private val listenDelete = mutableMapOf<Int, String?>()
        private val listenList = mutableListOf<Int>()

        fun listenAdd(entityId: Int, command: String) {
            listenAdd[entityId] = command
            listenDelete.remove(entityId)
            listenList.remove(entityId)
        }

        fun listenDelete(entityId: Int, command: String?) {
            listenDelete[entityId] = command
            listenAdd.remove(entityId)
            listenList.remove(entityId)
        }

        fun listenList(entityId: Int) {
            if (!listenList.contains(entityId)) listenList.add(entityId)
            listenAdd.remove(entityId)
            listenDelete.remove(entityId)
        }
    }

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    fun onInteract(event: PlayerInteractEvent) {
        val block = event.clickedBlock ?: return
        val player = event.player
        val entityId = player.entityId
        when {
            listenAdd.contains(entityId) -> {
                AddMacroUseCase(repository).execute(
                    block.x,
                    block.y,
                    block.z,
                    block.world.name,
                    listenAdd[entityId]!!
                )
                listenAdd.remove(entityId)
                player.sendMessage(plugin.messages.getString("added")!!)
            }

            listenDelete.contains(entityId) -> {
                var macros = FindMacroUseCase(repository).execute(block.x, block.y, block.z, block.world.name)
                if (listenDelete[entityId] != null) macros = macros.filter { it.value == listenDelete[entityId] }
                listenDelete.remove(entityId)
                macros.forEach {
                    DeleteMacroUseCase(repository).execute(it.key)
                }
                player.sendMessage(plugin.messages.getString("deleted")!!)
            }

            listenList.contains(entityId) -> {
                listenList.remove(entityId)
                val macros = FindMacroUseCase(repository).execute(block.x, block.y, block.z, block.world.name)
                macros.values.forEachIndexed { index, command ->
                    player.sendMessage(plugin.messages.getString("list")!!.format(index, command))
                }
            }

            else -> {
                val macros = FindMacroUseCase(repository).execute(block.x, block.y, block.z, block.world.name)
                macros.values.forEach {
                    player.server.dispatchCommand(player, it)
                }
            }
        }
    }
}
