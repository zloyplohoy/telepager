package ag.sokolov.telepager.feature.home

import ag.sokolov.telepager.core.model.Bot
import ag.sokolov.telepager.core.model.Recipient

data class HomeScreenState(
    val bot: Bot? = null,
    val recipients: List<Recipient> = emptyList()
)
