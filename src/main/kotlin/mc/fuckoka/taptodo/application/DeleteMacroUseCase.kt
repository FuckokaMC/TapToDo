package mc.fuckoka.taptodo.application

import mc.fuckoka.dbconnector.Database.transaction
import mc.fuckoka.taptodo.domain.MacroRepository

class DeleteMacroUseCase(private val macroRepository: MacroRepository) {
    fun execute(id: Int): Boolean {
        return transaction {
            macroRepository.delete(id)
            return@transaction true
        }
    }
}
