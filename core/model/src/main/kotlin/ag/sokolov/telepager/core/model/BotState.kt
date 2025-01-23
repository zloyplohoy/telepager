package ag.sokolov.telepager.core.model

data class BotState(
    val bot: Bot,
    val isTokenValid: Boolean,
)

object SampleBotState {
    val VALID = BotState(
        bot = Bot(
            id = 123456789L,
            name = "Awesome Telegram Bot",
            username = "awesome_telegram_bot",
        ),
        isTokenValid = true,
    )

    val INVALID = BotState(
        bot = Bot(
            id = 123456789L,
            name = "Awesome Telegram Bot",
            username = "awesome_telegram_bot",
        ),
        isTokenValid = false,
    )
}
