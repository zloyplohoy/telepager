package ag.sokolov.telepager.feature.servicemenu.bot

import ag.sokolov.telepager.core.data.BotRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BotServiceMenuViewModel @Inject constructor(
    private val botRepository: BotRepository,
) : ViewModel() {
    val uiState = botRepository.getBot()
        .map { BotServiceMenuState(bot = it) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BotServiceMenuState()
        )

    fun addBot(token: String) {
        viewModelScope.launch {
            botRepository.addBot(token)
        }
    }

    fun deleteBot() {
        viewModelScope.launch{
            botRepository.deleteBot()
        }
    }
}
