package ag.sokolov.telepager.feature.servicemenu

import ag.sokolov.telepager.core.model.TelepagerResult.Failure
import ag.sokolov.telepager.core.model.TelepagerResult.Success
import ag.sokolov.telepager.core.telegram.TelegramBotApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LegacyServiceMenuViewModel @Inject constructor(
    private val telegramBotApi: TelegramBotApi,
) : ViewModel() {
    private val _uiState = MutableStateFlow(ServiceMenuUiState())
    val uiState: StateFlow<ServiceMenuUiState> = _uiState

    fun getBot(token: String) {
        viewModelScope.launch {
            val getBotResult =
                telegramBotApi.getBot(token)
            when (getBotResult) {
                is Success -> {
                    _uiState.value = _uiState.value.copy(
                        botInfo = getBotResult.data,
                        error = null
                    )
                }

                is Failure -> {
                    _uiState.value = _uiState.value.copy(error = getBotResult.error.message)
                }

                else -> {
                    _uiState.value = _uiState.value.copy(error = "Loading")
                }
            }
        }
    }

    fun getUser(token: String, userId: Long) {
        viewModelScope.launch {
            val getUserResult =
                telegramBotApi.getUser(token, userId)
            when (getUserResult) {
                is Success -> {
                    _uiState.value = _uiState.value.copy(
                        userInfo = getUserResult.data,
                        error = null
                    )
                }

                is Failure -> {
                    _uiState.value = _uiState.value.copy(error = getUserResult.error.message)
                }

                else -> {
                    _uiState.value = _uiState.value.copy(error = "Loading")
                }
            }
        }
    }

    fun sendMessage(token: String, userId: Long, message: String) {
        viewModelScope.launch {
            val sendMessageResult =
                telegramBotApi.sendMessage(token, userId, message)
            when (sendMessageResult) {
                is Success -> {
                    _uiState.value = _uiState.value.copy(
                        error = null
                    )
                }

                is Failure -> {
                    _uiState.value = _uiState.value.copy(error = sendMessageResult.error.message)
                }

                else -> {
                    _uiState.value = _uiState.value.copy(error = "Loading")
                }
            }
        }
    }
}
