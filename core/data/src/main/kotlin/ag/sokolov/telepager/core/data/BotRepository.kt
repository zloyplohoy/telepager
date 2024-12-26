package ag.sokolov.telepager.core.data

import ag.sokolov.telepager.core.result.Result
import ag.sokolov.telepager.core.model.Bot
import kotlinx.coroutines.flow.Flow

interface BotRepository {
    fun getBot() : Flow<Bot?>
    suspend fun addBot(token: String): Result<Nothing, BotRepositoryError>
    suspend fun deleteBot(): Result<Nothing, BotRepositoryError>
}
