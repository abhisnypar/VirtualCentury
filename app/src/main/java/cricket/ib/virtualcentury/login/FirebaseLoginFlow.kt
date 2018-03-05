package cricket.ib.virtualcentury.login

import android.content.SharedPreferences
import android.support.v4.app.FragmentActivity
import android.widget.Toast
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.nigelbrown.fluxion.Flux
import cricket.ib.virtualcentury.BuildConfig
import cricket.ib.virtualcentury.action.ActionCreator

class FirebaseLoginFlow(
        private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
        private var actionsCreator: ActionCreator? = ActionCreator(Flux.getsInstance())
) {

    fun signUpUser(email: String?, password: String?, activity: FragmentActivity?) {
        if (email != null && password != null) {
            activity?.let {
                firebaseAuth.createUserWithEmailAndPassword(email, password).
                        addOnCompleteListener(it) { task ->
                            if (task.isSuccessful) {
                                actionsCreator?.singnUpSuccess()
                            } else {
                                Toast.makeText(activity, "User not able to register" + task.exception.toString(), Toast.LENGTH_LONG).show()
                            }
                        }
            }
        }
    }

    fun signInUser(email: String?, password: String?, activity: FragmentActivity?) {
        if (email != null && password != null) {
            activity?.let {
                firebaseAuth.signInWithEmailAndPassword(email, password).
                        addOnCompleteListener(it) { task ->
                            if (task.isSuccessful) {
                                actionsCreator?.loginSuccess()
                            } else {
                                Toast.makeText(activity, "User not able to login" + task.exception.toString(), Toast.LENGTH_LONG).show()
                            }
                        }
            }
        }
    }

    fun signOut() = firebaseAuth.signOut()

    fun loginWithFacebook(sharedPreferences: SharedPreferences?, activity: FragmentActivity?) {
        val result = sharedPreferences?.getString(BuildConfig.Access_Token, "")
        return if (result.isNullOrBlank()) {
            loginWithFacebookAsNewUser(sharedPreferences, activity)
        } else {
            loginWithFacebookAsExistingUser(sharedPreferences, activity)
        }
    }

    private fun loginWithFacebookAsExistingUser(sharedPreferences: SharedPreferences?, activity: FragmentActivity?) {
        val accessToken = sharedPreferences?.getString(BuildConfig.Access_Token, "")
        accessToken?.let {
            val credential = FacebookAuthProvider.getCredential(accessToken)
            activity?.let {
                firebaseAuth.signInWithCredential(credential).addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        actionsCreator?.loginSuccess()
                    }
                }
            }
        }
    }

    private fun loginWithFacebookAsNewUser(sharedPreferences: SharedPreferences?, activity: FragmentActivity?) {
        LoginManager.getInstance().logInWithPublishPermissions(activity, arrayListOf("email"))
        LoginManager.getInstance().registerCallback(CallbackManager.Factory.create(), object : FacebookCallback<LoginResult> {

            override fun onSuccess(result: LoginResult?) {
                sharedPreferences?.edit()?.putString(BuildConfig.Access_Token, result?.accessToken?.token)?.apply()
                actionsCreator?.loginSuccess()
            }

            override fun onCancel() = Unit

            override fun onError(error: FacebookException?) = Toast.makeText(activity, error?.message, Toast.LENGTH_LONG).show()

        })
    }
}