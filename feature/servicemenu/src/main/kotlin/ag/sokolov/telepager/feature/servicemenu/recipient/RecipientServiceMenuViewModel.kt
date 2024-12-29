package ag.sokolov.telepager.feature.servicemenu.recipient

import ag.sokolov.telepager.core.concurrency.CoroutineDispatchers.IO
import ag.sokolov.telepager.core.concurrency.Dispatcher
import ag.sokolov.telepager.core.data.RecipientRepository
import ag.sokolov.telepager.core.database.dao.BotDao
import ag.sokolov.telepager.core.database.dao.RecipientDao
import ag.sokolov.telepager.core.result.Result.Success
import ag.sokolov.telepager.core.telegram.TelegramBotApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class RecipientServiceMenuViewModel @Inject constructor(
    private val botDao: BotDao,
    private val recipientDao: RecipientDao,
    private val telegramBotApi: TelegramBotApi,
    private val recipientRepository: RecipientRepository,
    @Dispatcher(IO) private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    val uiState = recipientRepository.getRecipients()
        .map { RecipientServiceMenuState(recipients = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RecipientServiceMenuState()
        )

    fun addRecipient(id: Long) {
        viewModelScope.launch {
            withContext(ioDispatcher) {
                val botToken = botDao.getBotToken().firstOrNull()
                val getUserResult = telegramBotApi.getChat(botToken!!, id)
                if (getUserResult is Success) {
                    val user = getUserResult.data!!
                    recipientDao.addRecipient(
                        id = user.id,
                        firstName = user.firstName!!,
                        lastName = user.lastName,
                        username = user.username
                    )
                }
            }
        }
    }
}
