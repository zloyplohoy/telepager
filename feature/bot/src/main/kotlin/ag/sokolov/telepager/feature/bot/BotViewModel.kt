package ag.sokolov.telepager.feature.bot

import ag.sokolov.telepager.core.data.BotRepository
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BotViewModel @Inject constructor(
    private val botRepository: BotRepository,
) : ViewModel() {
    val stateFlow = botRepository.getBot()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    fun addBot(token: String) {


    }

    fun deleteBot() = viewModelScope.launch { botRepository.deleteBot() }
}
