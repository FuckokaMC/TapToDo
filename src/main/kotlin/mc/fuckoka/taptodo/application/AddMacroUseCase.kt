package mc.fuckoka.taptodo.application

import mc.fuckoka.dbconnector.Database.transaction
import mc.fuckoka.taptodo.domain.Macro
import mc.fuckoka.taptodo.domain.MacroRepository
import mc.fuckoka.taptodo.domain.Position

class AddMacroUseCase(private val macroRepository: MacroRepository) {
    fun execute(x: Int, y: Int, z: Int, world: String, command: String): Boolean {
        return transaction {
            val macro = Macro.NewMacro(Position(x, y, z, world), command)
            macroRepository.store(macro)

            return@transaction true
        }
    }
}
