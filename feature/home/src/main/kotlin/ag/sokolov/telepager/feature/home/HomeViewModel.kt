package ag.sokolov.telepager.feature.home

import ag.sokolov.telepager.core.data.BotRepository
import ag.sokolov.telepager.core.data.RecipientRepository
import ag.sokolov.telepager.core.domain.domain.AddBotUseCase
import ag.sokolov.telepager.core.domain.domain.AddRecipientState
import ag.sokolov.telepager.core.domain.domain.AddRecipientUseCase
import ag.sokolov.telepager.core.domain.domain.UpdateRecipientsUseCase
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val botRepository: BotRepository,
    private val recipientRepository: RecipientRepository,
    private val addBotUseCase: AddBotUseCase,
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
        MutableStateFlow<RegistrationState>(RegistrationState())

    val recipientRegistrationStateFlow: StateFlow<RegistrationState> =
        _recipientRegistrationStateFlow.asStateFlow()

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
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = HomeScreenState()
        )

    fun startRecipientRegistration() {
        recipientRegistrationJob = viewModelScope.launch {
            addRecipientUseCase().collect {
                when (it) {
                    is AddRecipientState.Started -> {
                        _recipientRegistrationStateFlow.value = RegistrationState(true, it.tgUrl)
                    }

                    else -> {
                        _recipientRegistrationStateFlow.value = RegistrationState()
                    }
                }
            }
        }
    }

    fun stopRecipientRegistration() {
        recipientRegistrationJob?.cancel()
        recipientRegistrationJob = null
        _recipientRegistrationStateFlow.value = RegistrationState()
    }

    fun addBot(token: String) = viewModelScope.launch { addBotUseCase(token) }

    fun deleteBot() = viewModelScope.launch { botRepository.deleteBot() }

    fun deleteRecipient(id: Long) =
        viewModelScope.launch { recipientRepository.deleteRecipient(id) }
}
