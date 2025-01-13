package ag.sokolov.telepager.core.domain.recipientregistration

import ag.sokolov.telepager.core.concurrency.CoroutineDispatchers.IO
import ag.sokolov.telepager.core.concurrency.Dispatcher
import ag.sokolov.telepager.core.data.BotRepository
import ag.sokolov.telepager.core.data.RecipientRepository
import ag.sokolov.telepager.core.data.RepositoryError
import ag.sokolov.telepager.core.domain.recipientregistration.RecipientRegistrationState.RegistrationFailure
import ag.sokolov.telepager.core.domain.recipientregistration.RecipientRegistrationState.RegistrationStarted
import ag.sokolov.telepager.core.domain.recipientregistration.RecipientRegistrationState.RegistrationSuccess
import ag.sokolov.telepager.core.model.Bot
import ag.sokolov.telepager.core.result.Result
import ag.sokolov.telepager.core.result.Result.Failure
import ag.sokolov.telepager.core.result.Result.Success
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject


class RecipientRegistrationManagerImpl @Inject constructor(
    private val botRepository: BotRepository,
    private val recipientRepository: RecipientRepository,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : RecipientRegistrationManager {
    override fun registerRecipient(): Flow<RecipientRegistrationState> = flow {
        val bot: Bot? = getBot()

        if (bot == null) {
            emit(RegistrationFailure)
            return@flow
        }

        val verificationCode = generateVerificationCode()
        val verificationTgUrl = getTgVerificationUrl(bot, verificationCode)

        emit(RegistrationStarted(verificationTgUrl))

        val recipientRegistrationResult = withContext(ioDispatcher) {
            recipientRepository.registerRecipient(verificationCode).asRegistrationState()
        }

        emit(recipientRegistrationResult)
    }

    private suspend fun getBot(): Bot? = botRepository.getBot().first()

    private fun getTgVerificationUrl(bot: Bot, verificationCode: String): String =
        "tg://resolve?domain=${bot.username}&start=$verificationCode"

    private fun generateVerificationCode(): String =
        "%06d".format((0..999999).random())

    private fun Result<Nothing, RepositoryError>.asRegistrationState(): RecipientRegistrationState =
        when (this) {
            is Success -> RegistrationSuccess
            is Failure -> RegistrationFailure
        }
}
