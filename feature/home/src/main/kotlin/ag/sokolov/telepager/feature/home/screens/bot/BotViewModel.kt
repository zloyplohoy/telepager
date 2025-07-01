package ag.sokolov.telepager.feature.home.screens.bot

import ag.sokolov.telepager.core.data.BotRepository
import ag.sokolov.telepager.core.domain.domain.AddBotUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BotViewModel(
    private val botRepository: BotRepository,
    private val addBotUseCase: AddBotUseCase,
) : ViewModel() {
    val stateFlow = botRepository.getBotState()
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = null
        )

    fun addBot(token: String) = viewModelScope.launch { addBotUseCase(token) }

    fun deleteBot() = viewModelScope.launch { botRepository.deleteBot() }
}
