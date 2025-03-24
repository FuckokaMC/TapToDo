package mc.fuckoka.taptodo.application

import mc.fuckoka.dbconnector.Database.transaction
import mc.fuckoka.taptodo.domain.MacroRepository
import mc.fuckoka.taptodo.domain.Position

class FindMacroUseCase(private val macroRepository: MacroRepository) {
    fun execute(x: Int, y: Int, z: Int, world: String): Map<Int, String> {
        return transaction {
            val macros = mutableMapOf<Int, String>()

            macroRepository.findByPosition(Position(x, y, z, world)).forEach {
                macros[it.id] = it.command
            }

            return@transaction macros
        }
    }
}
