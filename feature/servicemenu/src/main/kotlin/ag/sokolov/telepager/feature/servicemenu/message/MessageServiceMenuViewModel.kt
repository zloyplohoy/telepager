package ag.sokolov.telepager.feature.servicemenu.message

import ag.sokolov.telepager.core.model.TelepagerResult.Failure
import ag.sokolov.telepager.core.model.TelepagerResult.Success
import ag.sokolov.telepager.core.telegram.TelegramBotApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MessageServiceMenuViewModel @Inject constructor(
    private val telegramBotApi: TelegramBotApi,
) : ViewModel() {
    private val _uiState = MutableStateFlow(MessageServiceMenuState())
    val uiState: StateFlow<MessageServiceMenuState> = _uiState.asStateFlow()

    fun sendMessage() {
        viewModelScope.launch {
            if (_uiState.value.userId != "") {
                val getUserResult =
                    telegramBotApi.sendMessage(
                        _uiState.value.botToken,
                        _uiState.value.userId.toLong(),
                        _uiState.value.message
                    )
                when (getUserResult) {
                    is Success -> {
                        _uiState.value = _uiState.value.copy(error = null)
                    }

                    is Failure -> {
                        _uiState.value = _uiState.value.copy(error = getUserResult.error.message)
                    }

                    else -> {}
                }
            }
        }
    }

    fun onTokenValueChange(value: String) {
        _uiState.value = _uiState.value.copy(
            botToken = value
        )
    }

    fun onMessageChange(value: String) {
        _uiState.value = _uiState.value.copy(
            message = value
        )
    }

    fun onUserIdValueChange(value: String) {
        if (value.all { it.isDigit() }) {
            _uiState.value = _uiState.value.copy(userId = value)
        }
    }
}
