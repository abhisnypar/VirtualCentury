package cricket.ib.virtualcentury.aws


import android.content.Context
import com.amazonaws.ClientConfiguration
import com.amazonaws.mobile.auth.core.IdentityManager
import com.amazonaws.mobile.auth.userpools.CognitoUserPoolsSignInProvider
import com.amazonaws.mobile.config.AWSConfiguration
import com.amazonaws.mobileconnectors.pinpoint.PinpointManager

class AWSProvider(val context: Context) {
    val configuration: AWSConfiguration
    private val awsClientConfiguration: ClientConfiguration
    private var pinpointManager: PinpointManager? = null

    val identityManager: IdentityManager
        get() = IdentityManager.getDefaultIdentityManager()

    init {
        this.configuration = AWSConfiguration(context)
        this.awsClientConfiguration = ClientConfiguration()

        val identityManager = IdentityManager(context, configuration)
        IdentityManager.setDefaultIdentityManager(identityManager)
        identityManager.addSignInProvider(CognitoUserPoolsSignInProvider::class.java)

        // Create a CognitoUserPool object to refer to your user pool
    }

//    fun getPinpointManager(): PinpointManager {
//        if (pinpointManager == null) {
//            val cp = identityManager.credentialsProvider
//            val config = PinpointConfiguration(
//                    context, cp, configuration)
//            pinpointManager = PinpointManager(config)
//        }
//        pinpointManager
//    }

    companion object {

        var instance: AWSProvider? = null
            private set

        fun initialize(context: Context) {
            if (instance == null) {
                instance = AWSProvider(context)
            }
        }
    }
}
