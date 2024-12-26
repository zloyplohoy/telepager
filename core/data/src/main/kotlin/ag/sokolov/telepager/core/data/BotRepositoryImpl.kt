package ag.sokolov.telepager.core.data

import ag.sokolov.telepager.core.data.BotRepositoryError.InvalidToken
import ag.sokolov.telepager.core.data.BotRepositoryError.NetworkError
import ag.sokolov.telepager.core.database.dao.BotDetailsDao
import ag.sokolov.telepager.core.database.dao.BotTokenDao
import ag.sokolov.telepager.core.database.entity.BotTokenEntity
import ag.sokolov.telepager.core.database.entity.asExternalModel
import ag.sokolov.telepager.core.model.Bot
import ag.sokolov.telepager.core.result.Result
import ag.sokolov.telepager.core.result.Result.Failure
import ag.sokolov.telepager.core.result.Result.Success
import ag.sokolov.telepager.core.telegram.TelegramBotApi
import ag.sokolov.telepager.core.telegram.TelegramBotApiError
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class BotRepositoryImpl @Inject constructor(
    private val botTokenDao: BotTokenDao,
    private val botDetailsDao: BotDetailsDao,
    private val telegramBotApi: TelegramBotApi,
) : BotRepository {
    override fun getBot(): Flow<Bot?> =
        botTokenDao.getBotToken()
            .distinctUntilChanged()
            .onEach { it?.let { handleBotTokenEntityUpdate(it) } }
            .combine(botDetailsDao.getBotDetails()) { botTokenEntity, botDetailsEntity ->
                if (botTokenEntity == null) {
                    null
                } else {
                    Bot(
                        isValid = botTokenEntity.isValid,
                        details = botDetailsEntity?.asExternalModel()
                    )
                }
            }

    override suspend fun addBot(token: String): Result<Nothing, BotRepositoryError> {
        val addBotResult = telegramBotApi.getBot(token)
        return when (addBotResult) {
            is Success -> {
                botTokenDao.setBotToken(value = token, isValid = true)
                Success()
            }

            is Failure -> {
                when (addBotResult.error) {
                    is TelegramBotApiError.Unauthorized -> Failure(InvalidToken)
                    is TelegramBotApiError.NetworkError -> Failure(NetworkError)
                    else -> Failure(BotRepositoryError.UnknownError)
                }
            }
        }
    }

    override suspend fun deleteBot(): Result<Nothing, BotRepositoryError> {
        botTokenDao.deleteBotToken()
        return Success()
    }

    private suspend fun handleBotTokenEntityUpdate(botTokenEntity: BotTokenEntity) {
        if (botTokenEntity.isValid) {
            val getBotResult = telegramBotApi.getBot(botTokenEntity.value)
            when (getBotResult) {
                is Success -> {
                    val userDto = getBotResult.data
                    botDetailsDao.setBotDetails(
                        id = userDto!!.id,
                        name = userDto.firstName,
                        username = userDto.username!!
                    )
                }

                is Failure -> {
                    if (getBotResult.error is TelegramBotApiError.Unauthorized) {
                        botTokenDao.setIsBotTokenValid(false)
                    }
                }
            }
        }
    }
}
