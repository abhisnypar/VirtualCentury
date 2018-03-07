package cricket.ib.virtualcentury.login.loginclasses

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import butterknife.ButterKnife
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.nigelbrown.fluxion.Flux
import cricket.ib.virtualcentury.R
import cricket.ib.virtualcentury.action.ActionCreator
import cricket.ib.virtualcentury.login.loginManager.FirebaseLoginFlow


class LoginFragment : Fragment(), View.OnClickListener {
    private var actionsCreator: ActionCreator? = null
    private var login: Button? = null
    private var username: EditText? = null
    private var password: EditText? = null
    private var firebaseLogin: FirebaseLoginFlow? = null
    private var facebookLogin: LoginButton? = null
    private var preferences: SharedPreferences? = null
    private var mCallbackManager: CallbackManager? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.login_screen_fragment, container, false)
        ButterKnife.bind(this, view)
        firebaseLogin = FirebaseLoginFlow()
        actionsCreator = ActionCreator(Flux.getsInstance())
        val button: Button? = view?.findViewById(R.id.register)
        button?.setOnClickListener({
            actionsCreator?.signupButtonClicked()
        })
        login = view.findViewById(R.id.login)
        login?.setOnClickListener(this)
        username = view.findViewById(R.id.username)
        password = view.findViewById(R.id.password_item)
        facebookLogin = view?.findViewById(R.id.facebook_login)
        facebookLogin?.setOnClickListener(this)
        facebookLogin?.fragment = this

        preferences = PreferenceManager.getDefaultSharedPreferences(context)
        mCallbackManager = CallbackManager.Factory.create()

        return view
    }

    override fun onClick(view: View?) {
        when (view) {
            login -> if (username?.text.isNullOrEmpty() || password?.text.isNullOrEmpty()) {
                setUpUiErrorFields()
            } else {
                firebaseLogin?.signInUser(username?.text?.toString(), password?.text?.toString(), activity)
            }
            facebookLogin -> {
                facebookLogin?.setReadPermissions(arrayListOf("email", "public_profile"))
                facebookLogin?.registerCallback(mCallbackManager, object : FacebookCallback<LoginResult> {

                    override fun onSuccess(result: LoginResult?) {
                        firebaseLogin?.handleAccessToken(result?.accessToken, activity)
                    }

                    override fun onCancel() = Unit

                    override fun onError(error: FacebookException?) = Toast.makeText(activity, error?.message, Toast.LENGTH_LONG).show()

                })
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Pass the activity result back to the Facebook SDK
        mCallbackManager?.onActivityResult(requestCode, resultCode, data)
    }

    private fun setUpUiErrorFields() {
        if (username?.text.isNullOrEmpty())
            username?.error = "Field is missing"
        if (password?.text.isNullOrEmpty())
            password?.error = "Field is missing"
    }
}