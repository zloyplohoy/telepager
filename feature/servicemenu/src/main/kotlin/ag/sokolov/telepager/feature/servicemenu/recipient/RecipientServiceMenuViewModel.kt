package ag.sokolov.telepager.feature.servicemenu.recipient

import ag.sokolov.telepager.core.data.RecipientRepository
import ag.sokolov.telepager.core.result.Result.Failure
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipientServiceMenuViewModel @Inject constructor(
    private val recipientRepository: RecipientRepository,
) : ViewModel() {

    private val _error = MutableStateFlow<String>("")
    private val error: StateFlow<String> = _error

    val uiState = combine(
        recipientRepository.getRecipients(),
        error
    ) { recipients, error ->
        RecipientServiceMenuState(recipients = recipients, error = error)
    }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = RecipientServiceMenuState()
        )


    fun addRecipient(code: String) {
        viewModelScope.launch {
            val result = recipientRepository.addRecipient(code, 30000)
            if (result is Failure) {
                _error.value = result.error.javaClass.toString()
            }
        }
    }

    fun deleteRecipient(id: Long) {
        viewModelScope.launch {
            val result = recipientRepository.deleteRecipient(id)
            if (result is Failure) {
                _error.value = result.error.javaClass.toString()
            }
        }
    }
}
