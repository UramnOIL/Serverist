import com.uramnoil.serverist.application.usecases.user.commands.CreateUserCommand
import com.uramnoil.serverist.application.usecases.user.commands.CreateUserDto
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
import org.jetbrains.exposed.sql.transactions.transaction
import org.kodein.di.instance
import kotlin.test.BeforeTest
import kotlin.test.Test

class H2Test {
    val database = Database.connect("jdbc:h2:mem:test", driver = "org.h2.Driver")
    val di = buildDi(database, Dispatchers.Default)
    val scope = CoroutineScope(Dispatchers.Default)

    @BeforeTest
    fun `テーブル初期化`() {
        transaction {
            commit()
        }
    }

    @Test
    fun `ユーザーを作成`() {
        val command: CreateUserCommand by di.instance()

        runBlocking {
            newSuspendedTransaction(db = database) {
                SchemaUtils.create(Users)
                command.execute(CreateUserDto("UramnOIL", "うんちぶり"))

                delay(500)

                Users.selectAll().forEach {
                    println("id: ${it[Users.id]}, name: ${it[Users.name]}, description: ${it[Users.description]}")
                }
            }
        }
    }
}