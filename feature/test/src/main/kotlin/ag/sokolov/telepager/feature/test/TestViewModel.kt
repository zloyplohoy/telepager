package ag.sokolov.telepager.feature.test

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
class TestViewModel @Inject constructor(
    private val telegramBotApi: TelegramBotApi,
) : ViewModel() {
    private val _uiState = MutableStateFlow(TestUiState())
    val uiState: StateFlow<TestUiState> = _uiState

    fun getBot(token: String) {
        viewModelScope.launch {
            val getBotResult =
                telegramBotApi.getTelegramBot(token)
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
}
