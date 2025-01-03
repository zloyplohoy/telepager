package ag.sokolov.telepager.feature.home

import ag.sokolov.telepager.core.data.BotRepository
import ag.sokolov.telepager.core.data.RecipientRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    botRepository: BotRepository,
    recipientRepository: RecipientRepository,
) : ViewModel() {
    val stateFlow = combine(
        botRepository.getBot(),
        recipientRepository.getRecipients()
    ) { bot, recipients ->
        HomeScreenState(
            bot = bot,
            recipients = recipients
        )
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeScreenState()
        )
}
