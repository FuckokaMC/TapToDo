package mc.fuckoka.taptodo.infrastructure

import mc.fuckoka.dbconnector.Database
import mc.fuckoka.taptodo.domain.Macro
import mc.fuckoka.taptodo.domain.MacroRepository
import mc.fuckoka.taptodo.domain.Position
import java.sql.SQLException

class MacroRepositoryImpl : MacroRepository {
    override fun find(id: Int): Macro? {
        val connection = Database.getConnection() ?: throw SQLException()
        val stmt = connection.prepareStatement(
            """
                |SELECT
                |    macros.id,
                |    macros.x,
                |    macros.y,
                |    macros.z,
                |    macros.world,
                |    macros.command
                |FROM macros
                |WHERE id = ?;
            """.trimMargin()
        )
        stmt.use {
            stmt.setInt(1, id)
            val resultSet = stmt.executeQuery()
            resultSet.use {
                return if (resultSet.next()) Macro(
                    resultSet.getInt("id"), Position(
                        resultSet.getInt("x"),
                        resultSet.getInt("y"),
                        resultSet.getInt("z"),
                        resultSet.getString("world")
                    ), resultSet.getString("command")
                ) else null
            }
        }
    }

    override fun findByPosition(position: Position): List<Macro> {
        val list = mutableListOf<Macro>()
        val connection = Database.getConnection() ?: throw SQLException()
        val stmt = connection.prepareStatement(
            """
                |SELECT
                |    macros.id,
                |    macros.x,
                |    macros.y,
                |    macros.z,
                |    macros.world,
                |    macros.command
                |FROM macros
                |WHERE x = ? AND y = ? AND z = ? AND world = ?
                |ORDER BY id;
            """.trimMargin()
        )
        stmt.use {
            stmt.setInt(1, position.x)
            stmt.setInt(2, position.y)
            stmt.setInt(3, position.z)
            stmt.setString(4, position.world)
            val resultSet = stmt.executeQuery()
            resultSet.use {
                while (resultSet.next()) {
                    list.add(
                        Macro(
                            resultSet.getInt("id"),
                            Position(
                                resultSet.getInt("x"),
                                resultSet.getInt("y"),
                                resultSet.getInt("z"),
                                resultSet.getString("world")
                            ),
                            resultSet.getString("command")
                        )
                    )
                }
            }
        }
        return list
    }

    override fun store(macro: Macro) {
        val connection = Database.getConnection() ?: throw SQLException()
        val stmt = connection.prepareStatement(
            """
                |INSERT INTO macros (x, y, z, world, command) VALUES
                |(?, ?, ?, ?, ?);
            """.trimMargin()
        )
        stmt.use {
            stmt.setInt(1,  macro.position.x)
            stmt.setInt(2, macro.position.y)
            stmt.setInt(3, macro.position.z)
            stmt.setString(4, macro.position.world)
            stmt.setString(5, macro.command)
            stmt.executeUpdate()
        }
    }

    override fun delete(id: Int) {
        val connection = Database.getConnection() ?: throw SQLException()
        val stmt = connection.prepareStatement("DELETE FROM macros WHERE id = ?;")
        stmt.use {
            stmt.setInt(1, id)
            stmt.executeUpdate()
        }
    }
}
