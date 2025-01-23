package ag.sokolov.telepager.core.model

data class RecipientState(
    val recipient: Recipient,
    val isBotBlocked: Boolean,
)

object SampleRecipientState {
    val NAME_ONLY = RecipientState(
        recipient = Recipient(
            id = 111111111L,
            firstName = "William",
        ),
        isBotBlocked = false
    )

    val NAME_AND_USERNAME = RecipientState(
        recipient = Recipient(
            id = 222222222L,
            firstName = "Howard",
            username = "lovecraft"
        ),
        isBotBlocked = false
    )

    val FULL_NAME = RecipientState(
        recipient = Recipient(
            id = 222222222L,
            firstName = "Terry David John",
            lastName = "Pratchett"
        ),
        isBotBlocked = false
    )

    val FULL_NAME_AND_USERNAME = RecipientState(
        recipient = Recipient(
            id = 333333333L,
            firstName = "John Ronald Reuel",
            lastName = "Tolkien",
            username = "real_tolkien"
        ),
        isBotBlocked = false
    )

    val FULL_NAME_AND_USERNAME_EXTRA_LONG = RecipientState(
        recipient = Recipient(
            id = 444444444L,
            firstName = "George Raymond Richard",
            lastName = "Martin",
            username = "yes_i_really_am_george_r_r_martin"
        ),
        isBotBlocked = false
    )

    val FULL_NAME_AND_USERNAME_BOT_BLOCKED = RecipientState(
        recipient = Recipient(
            id = 555555555L,
            firstName = "Joanne Kathleen",
            lastName = "Rowling",
            username = "lvl_80_troll"
        ),
        isBotBlocked = true
    )
}
