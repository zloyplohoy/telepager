package ag.sokolov.telepager.core.service

import ag.sokolov.telepager.core.concurrency.CoroutineDispatchers.IO
import ag.sokolov.telepager.core.concurrency.Dispatcher
import ag.sokolov.telepager.core.data.BotRepository
import ag.sokolov.telepager.core.data.RecipientRepository
import ag.sokolov.telepager.core.model.BotToken
import ag.sokolov.telepager.core.telegram.TelegramBotApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony.Sms.Intents.SMS_RECEIVED_ACTION
import android.provider.Telephony.Sms.Intents.getMessagesFromIntent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SmsBroadcastReceiver : BroadcastReceiver() {
    @Inject
    lateinit var botRepository: BotRepository

    @Inject
    lateinit var recipientRepository: RecipientRepository

    @Inject
    lateinit var telegramBotApi: TelegramBotApi

    @Inject
    @Dispatcher(IO)
    lateinit var ioDispatcher: CoroutineDispatcher

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
