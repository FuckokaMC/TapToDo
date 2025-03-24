package mc.fuckoka.taptodo.domain

open class Macro(val id: Int, val position: Position, val command: String) {
    init {
        require(id >= 0)
    }

    class NewMacro(position: Position, command: String) : Macro(0, position, command)
}
