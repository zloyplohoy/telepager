package ag.sokolov.telepager.feature.servicemenu.bot

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
class BotServiceMenuViewModel @Inject constructor(
    private val telegramBotApi: TelegramBotApi,
) : ViewModel() {
    private val _uiState = MutableStateFlow(BotServiceMenuState())
    val uiState: StateFlow<BotServiceMenuState> = _uiState.asStateFlow()

    fun getBot() {
        viewModelScope.launch {
            val getBotResult =
                telegramBotApi.getBot(_uiState.value.botToken)
            when (getBotResult) {
                is Success -> {
                    _uiState.value = _uiState.value.copy(
                        botDetails = getBotResult.data,
                        error = null
                    )
                }

                is Failure -> {
                    _uiState.value = _uiState.value.copy(
                        error = getBotResult.error.message
                    )
                }

                else -> {}
            }
        }
    }

    fun onTokenValueChange(value : String) {
        _uiState.value = _uiState.value.copy(
            botToken = value
        )
    }
}
