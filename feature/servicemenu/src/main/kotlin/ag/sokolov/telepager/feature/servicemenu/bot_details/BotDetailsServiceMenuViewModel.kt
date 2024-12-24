package ag.sokolov.telepager.feature.servicemenu.bot_details

import ag.sokolov.telepager.core.database.dao.BotDetailsDao
import ag.sokolov.telepager.core.database.entity.asExternalModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BotDetailsServiceMenuViewModel @Inject constructor(
    private val botDetailsDao: BotDetailsDao,
) : ViewModel() {
    val uiState = botDetailsDao.getBotDetails()
        .map { BotDetailsServiceMenuState(botDetails = it?.asExternalModel()) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = BotDetailsServiceMenuState()
        )

    fun setBotDetails(id: Long, name: String, username: String) {
        viewModelScope.launch {
            botDetailsDao.setBotDetails(id, name, username)
        }
    }
}
