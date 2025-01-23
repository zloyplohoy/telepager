package ag.sokolov.telepager.core.data

import ag.sokolov.telepager.core.concurrency.CoroutineDispatchers.IO
import ag.sokolov.telepager.core.concurrency.Dispatcher
import ag.sokolov.telepager.core.database.dao.BotDao
import ag.sokolov.telepager.core.database.dao.BotTokenDao
import ag.sokolov.telepager.core.database.entity.BotTokenEntity
import ag.sokolov.telepager.core.database.entity.asEntity
import ag.sokolov.telepager.core.database.entity.asExternalModel
import ag.sokolov.telepager.core.model.Bot
import ag.sokolov.telepager.core.model.BotState
import ag.sokolov.telepager.core.model.BotToken
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BotRepositoryImpl @Inject constructor(
    private val botDao: BotDao,
    private val botTokenDao: BotTokenDao,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : BotRepository {
    override fun getBot(): Flow<Bot?> =
        botDao.getBot()
            .distinctUntilChanged()
            .map { it?.asExternalModel() }

    override suspend fun setBot(bot: Bot) =
        withContext(ioDispatcher) { botDao.insertBot(bot.asEntity()) }

    override suspend fun updateBot(bot: Bot) =
        withContext(ioDispatcher) { botDao.updateBot(bot.asEntity()) }

    override suspend fun deleteBot() =
        withContext(ioDispatcher) { botTokenDao.deleteBotToken() }

    override fun getBotState(): Flow<BotState?> =
        combine(
            botTokenDao.getBotToken().distinctUntilChanged(),
            botDao.getBot().distinctUntilChanged()
        )
        { botToken, bot ->
            if (botToken == null || bot == null) {
                null
            } else {
                BotState(
                    bot = bot.asExternalModel(),
                    isTokenValid = botToken.isValid
                )
            }
        }

    override fun getBotToken(): Flow<BotToken?> =
        botTokenDao.getBotToken().map { it?.asExternalModel() }

    override suspend fun setBotToken(token: String) =
        withContext(ioDispatcher) { botTokenDao.insertBotToken(BotTokenEntity(token = token)) }

    override suspend fun invalidateBotToken() =
        withContext(ioDispatcher) { botTokenDao.updateIsBotTokenValid(false) }
}
