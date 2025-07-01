package ag.sokolov.telepager.feature.home.screens.recipients

import ag.sokolov.telepager.core.data.RecipientRepository
import ag.sokolov.telepager.core.domain.domain.AddRecipientState
import ag.sokolov.telepager.core.domain.domain.AddRecipientUseCase
import ag.sokolov.telepager.core.domain.domain.UpdateRecipientsUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted.Companion.WhileSubscribed
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class RecipientsViewModel(
    private val recipientRepository: RecipientRepository,
    private val addRecipientUseCase: AddRecipientUseCase,
    private val updateRecipientsUseCase: UpdateRecipientsUseCase,
) : ViewModel() {
    init {
        viewModelScope.launch {
            updateRecipientsUseCase()
        }
    }

    private var recipientRegistrationJob: Job? = null

    private val _recipientRegistrationStateFlow =
        MutableStateFlow(RecipientRegistrationState())

    val recipientRegistrationStateFlow: StateFlow<RecipientRegistrationState> =
        _recipientRegistrationStateFlow.asStateFlow()

    val stateFlow = recipientRepository.getRecipientStateList()
        .stateIn(
            scope = viewModelScope,
            started = WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    fun startRecipientRegistration() {
        recipientRegistrationJob = viewModelScope.launch {
            addRecipientUseCase().collect {
                when (it) {
                    is AddRecipientState.Started -> {
                        _recipientRegistrationStateFlow.value =
                            RecipientRegistrationState(true, it.tgUrl)
                    }

                    else -> {
                        _recipientRegistrationStateFlow.value = RecipientRegistrationState()
                    }
                }
            }
        }
    }

    fun stopRecipientRegistration() {
        recipientRegistrationJob?.cancel()
        recipientRegistrationJob = null
        _recipientRegistrationStateFlow.value = RecipientRegistrationState()
    }

    fun deleteRecipient(id: Long) =
        viewModelScope.launch { recipientRepository.deleteRecipient(id) }
}
