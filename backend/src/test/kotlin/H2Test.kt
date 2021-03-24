import com.uramnoil.serverist.application.usecases.server.commands.CreateServerCommand
import com.uramnoil.serverist.application.usecases.server.commands.CreateServerDto
import com.uramnoil.serverist.application.usecases.user.commands.CreateUserCommand
import com.uramnoil.serverist.application.usecases.user.commands.CreateUserDto
import com.uramnoil.serverist.infrastracture.Servers
import com.uramnoil.serverist.infrastracture.Users
import com.uramnoil.serverist.infrastracture.buildDi
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.kodein.di.instance
import kotlin.test.Test

class H2Test {
    val database = Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
    val di = buildDi(database, Dispatchers.Default)
    val scope = CoroutineScope(Dispatchers.Default)

    @Test
    fun `ユーザーを作成`() {
        val command: CreateUserCommand by di.instance()

        runBlocking {
            newSuspendedTransaction(db = database) {
                SchemaUtils.create(Users)
                delay(50)
                command.execute(CreateUserDto("UramnOIL", "うんちぶり"))
                delay(50)
                Users.selectAll().forEach {
                    println("ID: ${it[Users.id]}, Name: ${it[Users.name]}, Description: ${it[Users.description]}")
                }
            }
        }
    }

    @Test
    fun `サーバーを作成`() {
        val createUserCommand: CreateUserCommand by di.instance()
        val createServerCommand: CreateServerCommand by di.instance()

        runBlocking {
            newSuspendedTransaction(db = database) {
                SchemaUtils.create(Users)
                SchemaUtils.create(Servers)

                createUserCommand.execute(CreateUserDto("UramnOIL", "うんちぶり"))
                commit()

                delay(10)

                val user = Users.selectAll().first()

                Users.selectAll().forEach {
                    println("ID: ${it[Users.id]}, Name: ${it[Users.name]}, Description: ${it[Users.description]}")
                }

                createServerCommand.execute(
                    CreateServerDto(
                        user[Users.id].value,
                        "HogeServer",
                        "uramnoil.com",
                        19132,
                        "うんちぶりぶり"
                    )
                )
                commit()

                delay(10)

                Servers.selectAll().forEach {
                    println("ID: ${it[Servers.id]}, OwnerID: ${it[Servers.owner]}, Name: ${it[Servers.name]}, Address: ${it[Servers.address]}, Port: ${it[Servers.port]}, Description: ${it[Servers.description]}")
                }
            }
        }
    }
}