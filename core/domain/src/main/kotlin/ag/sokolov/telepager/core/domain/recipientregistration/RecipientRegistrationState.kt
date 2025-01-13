package ag.sokolov.telepager.core.domain.recipientregistration

sealed class RecipientRegistrationState {
    data class RegistrationStarted(val tgUrl: String) : RecipientRegistrationState()
    data object RegistrationSuccess : RecipientRegistrationState()
    data object RegistrationFailure : RecipientRegistrationState()
}
