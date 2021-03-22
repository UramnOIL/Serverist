import com.uramnoil.serverist.application.usecases.server.queries.FindServerByIdDto
import com.uramnoil.serverist.application.usecases.server.queries.FindServerByIdOutputPort
import com.uramnoil.serverist.application.usecases.server.queries.FindServerByIdOutputPortDto
import com.uramnoil.serverist.application.usecases.server.queries.FindServerByIdQuery
import org.junit.Test
import org.kodein.di.*

class DiTest : DIAware {
    override val di = DI {
        bind<FindServerByIdQuery>() with factory { outputPort: FindServerByIdOutputPort ->
            FindServerByIdQuery {
                outputPort.handle(FindServerByIdOutputPortDto(it.id, "hoge", "hoge", "hoge", 19132, "うんちぶり"))
            }
        }
    }

    @Test
    fun `DIできてるかどうか確認しまーす！ｗ`() {
        val query: FindServerByIdQuery by di.instance(arg = FindServerByIdOutputPort { println(it) })
        query.execute(FindServerByIdDto("buri"))
    }
}