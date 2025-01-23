package ag.sokolov.telepager.core.data

import ag.sokolov.telepager.core.model.Bot
import ag.sokolov.telepager.core.model.BotState
import ag.sokolov.telepager.core.model.BotToken
import kotlinx.coroutines.flow.Flow

interface BotRepository {
    fun getBot(): Flow<Bot?>
    suspend fun setBot(bot: Bot)
    suspend fun updateBot(bot: Bot)
    suspend fun deleteBot()

    fun getBotState(): Flow<BotState?>

    fun getBotToken(): Flow<BotToken?>
    suspend fun setBotToken(token: String)

    suspend fun invalidateBotToken()
}
