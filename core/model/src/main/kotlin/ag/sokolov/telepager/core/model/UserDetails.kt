package ag.sokolov.telepager.core.model

data class UserDetails(
    val id: Long,
    val firstName: String,
    val lastName: String? = null,
    val username: String? = null,
)
