package ag.sokolov.telepager.core.domain.domain

sealed class AddRecipientState {
    data class Started(val tgUrl: String) : AddRecipientState()
    data object Success : AddRecipientState()
    data object Failure : AddRecipientState()
}
