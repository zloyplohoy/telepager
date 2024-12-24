package ag.sokolov.telepager.feature.servicemenu.bot_token

import ag.sokolov.telepager.core.database.dao.BotTokenDao
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BotTokenServiceMenuViewModel @Inject constructor(
    private val botTokenDao: BotTokenDao,
) : ViewModel() {
    val uiState = combine(
        botTokenDao.getBotToken(),
        botTokenDao.getBotTokenValue(),
        botTokenDao.getIsBotTokenValid()
    ) { botToken, botTokenValue, isBotTokenValid ->
        BotTokenServiceMenuState(
            botToken = botToken,
            botTokenValue = botTokenValue,
            isBotTokenValid = isBotTokenValid
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = BotTokenServiceMenuState()
    )

    fun setBotToken(value: String, isValid: Boolean) {
        viewModelScope.launch {
            botTokenDao.setBotToken(value, isValid)
        }
    }

    fun setIsBotTokenValid(isValid: Boolean) {
        viewModelScope.launch {
            botTokenDao.setIsBotTokenValid(isValid)
        }
    }

    fun deleteBotToken() {
        viewModelScope.launch {
            botTokenDao.deleteBotToken()
        }
    }
}
