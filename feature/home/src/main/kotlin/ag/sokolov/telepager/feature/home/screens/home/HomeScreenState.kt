package ag.sokolov.telepager.feature.home.screens.home

import ag.sokolov.telepager.core.model.BotState
import ag.sokolov.telepager.core.model.RecipientState

data class HomeScreenState(
    val botState: BotState? = null,
    val recipientStateList: List<RecipientState> = emptyList(),
)