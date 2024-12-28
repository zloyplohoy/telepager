package ag.sokolov.telepager.core.model

data class Recipient(
    val id: Long,
    val isDeleted: Boolean = false,
    val firstName: String,
    val lastName: String? = null,
    val username: String? = null,
    val isBotBlocked: Boolean,
)
