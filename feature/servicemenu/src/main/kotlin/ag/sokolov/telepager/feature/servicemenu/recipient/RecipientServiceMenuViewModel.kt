package ag.sokolov.telepager.feature.servicemenu.recipient

import ag.sokolov.telepager.core.result.Result.Failure
import ag.sokolov.telepager.core.result.Result.Success
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
class RecipientServiceMenuViewModel @Inject constructor(
    private val telegramBotApi: TelegramBotApi,
) : ViewModel() {
    private val _uiState = MutableStateFlow(UserServiceMenuState())
    val uiState: StateFlow<UserServiceMenuState> = _uiState.asStateFlow()

    fun getUser() {
        viewModelScope.launch {
            if (_uiState.value.userId != "") {
                val getUserResult =
                    telegramBotApi.getUser(
                        _uiState.value.botToken,
                        _uiState.value.userId.toLong()
                    )
                when (getUserResult) {
                    is Success -> {
                        _uiState.value = _uiState.value.copy(
                            userDetails = getUserResult.data,
                            error = null
                        )
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

    fun onUserIdValueChange(value: String) {
        if (value.all { it.isDigit() }) {
            _uiState.value = _uiState.value.copy(userId = value)
        }
    }
}
