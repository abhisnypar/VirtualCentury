package cricket.ib.virtualcentury.login.loginManager

import java.util.*

data class TokenViewModel(
        var accessToken: String?,
        var expiresIn: Date?,
        var applicationId: String?,
        var lastRefresh: Date?,
        var userId: String?
)