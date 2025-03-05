package ag.sokolov.telepager.core.service

import ag.sokolov.telepager.core.data.BotRepository
import ag.sokolov.telepager.core.data.RecipientRepository
import ag.sokolov.telepager.core.model.BotToken
import ag.sokolov.telepager.core.telegram.TelegramBotApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION
import android.provider.Telephony.Sms.Intents.getMessagesFromIntent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.qualifier.named
import org.koin.java.KoinJavaComponent.getKoin

class SmsBroadcastReceiver(
    val botRepository: BotRepository = getKoin().get(),
    val recipientRepository: RecipientRepository = getKoin().get(),
) : BroadcastReceiver(), KoinComponent {

    val telegramBotApi: TelegramBotApi by inject()

    val ioDispatcher: CoroutineDispatcher by inject(named("IO"))

    override fun onReceive(
        context: Context,
        intent: Intent?,
    ) {
        if (intent?.action == SMS_RECEIVED_ACTION) {
            CoroutineScope(ioDispatcher).launch {
                val botToken = getBotToken() ?: return@launch
                val recipientIdList = getRecipientIdList()

                if (recipientIdList.isEmpty()) return@launch

                val messages = getMessagesFromIntent(intent)
                val sender = messages[0].originatingAddress
                val body = messages.joinToString { it.messageBody }

                val relayedMessage = listOf(sender, body).joinToString("\n\n")

                recipientIdList.forEach { recipientId ->
                    telegramBotApi.sendMessage(
                        token = botToken.token,
                        chatId = recipientId,
                        text = relayedMessage
                    )
                }
            }
        }
    }

    suspend fun getBotToken(): BotToken? =
        botRepository.getBotToken().first()

    suspend fun getRecipientIdList(): List<Long> =
        recipientRepository.getRecipientIdList().first()
}
