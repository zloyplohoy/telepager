package ag.sokolov.telepager.core.data

import ag.sokolov.telepager.core.concurrency.CoroutineDispatchers.IO
import ag.sokolov.telepager.core.concurrency.Dispatcher
import ag.sokolov.telepager.core.data.BotRepositoryError.InvalidToken
import ag.sokolov.telepager.core.data.BotRepositoryError.NetworkError
import ag.sokolov.telepager.core.data.BotRepositoryError.UnknownError
import ag.sokolov.telepager.core.database.dao.BotDao
import ag.sokolov.telepager.core.database.entity.asExternalModel
import ag.sokolov.telepager.core.model.Bot
import ag.sokolov.telepager.core.result.Result
import ag.sokolov.telepager.core.result.Result.Failure
import ag.sokolov.telepager.core.result.Result.Success
import ag.sokolov.telepager.core.telegram.TelegramBotApi
import ag.sokolov.telepager.core.telegram.TelegramBotApiError
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BotRepositoryImpl @Inject constructor(
    private val botDao: BotDao,
    private val telegramBotApi: TelegramBotApi,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : BotRepository {
    override fun getBot(): Flow<Bot?> =
        botDao.getBot()
            .distinctUntilChanged()
            .map { it?.asExternalModel() }
            .onStart { updateBotDetails() }

    override suspend fun addBot(token: String): Result<Nothing, BotRepositoryError> =
        withContext(ioDispatcher) {
            val getBotResult = telegramBotApi.getBot(token)

            return@withContext when (getBotResult) {
                is Success -> {
                    val bot = getBotResult.data!!
                    botDao.setBot(
                        token = token,
                        isValid = true,
                        id = bot.id,
                        name = bot.firstName,
                        username = bot.username!!
                    )
                    Success()
                }

                is Failure -> {
                    when (getBotResult.error) {
                        is TelegramBotApiError.Unauthorized -> Failure(InvalidToken)
                        is TelegramBotApiError.NetworkError -> Failure(NetworkError)
                        else -> Failure(UnknownError)
                    }
                }
            }
        }

    override suspend fun deleteBot(): Result<Nothing, BotRepositoryError> =
        withContext(ioDispatcher) {
            botDao.deleteBot()
            return@withContext Success()
        }

    override suspend fun updateBotDetails(): Result<Nothing, BotRepositoryError> =
        withContext(ioDispatcher) {
            val token = botDao.getBotToken().firstOrNull()

            if (token == null) {
                Success()
            } else {
                when (val getBotResult = telegramBotApi.getBot(token)) {
                    is Success -> {
                        val userDto = getBotResult.data!!
                        botDao.setIsValid(true)
                        botDao.setBotDetails(
                            name = userDto.firstName,
                            username = userDto.username!!
                        )
                        Success()
                    }

                    is Failure -> {
                        if (getBotResult.error is TelegramBotApiError.Unauthorized) {
                            botDao.setIsValid(false)
                            Failure(InvalidToken)
                        } else {
                            Failure(UnknownError)
                        }
                    }
                }
            }
        }
}
