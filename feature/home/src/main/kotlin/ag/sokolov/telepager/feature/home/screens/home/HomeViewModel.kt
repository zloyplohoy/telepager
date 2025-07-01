package ag.sokolov.telepager.feature.home.screens.home

import ag.sokolov.telepager.core.data.BotRepository
import ag.sokolov.telepager.core.data.RecipientRepository
import ag.sokolov.telepager.core.domain.domain.UpdateRecipientsUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class HomeViewModel(
    botRepository: BotRepository,
    recipientRepository: RecipientRepository,
    private val updateRecipientsUseCase: UpdateRecipientsUseCase,
) : ViewModel() {
    init {
        viewModelScope.launch {
            updateRecipientsUseCase()
        }
    }

    val stateFlow =
        combine(
            botRepository.getBotState(),
            recipientRepository.getRecipientStateList()
        ) { botState, recipientStateList ->
            HomeScreenState(
                botState = botState,
                recipientStateList = recipientStateList
            )
        }.stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5000),
            initialValue = HomeScreenState()
        )
}