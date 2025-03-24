package mc.fuckoka.taptodo.domain

interface MacroRepository {
    fun find(id: Int): Macro?

    fun findByPosition(position: Position): List<Macro>

    fun store(macro: Macro)

    fun delete(id: Int)
}
