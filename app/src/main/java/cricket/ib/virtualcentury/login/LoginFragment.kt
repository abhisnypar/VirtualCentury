package cricket.ib.virtualcentury.login

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.PreferenceManager
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import butterknife.ButterKnife
import com.nigelbrown.fluxion.Flux
import cricket.ib.virtualcentury.R
import cricket.ib.virtualcentury.action.ActionCreator


class LoginFragment : Fragment(), View.OnClickListener {
    private var actionsCreator: ActionCreator? = null
    private var login: Button? = null
    private var username: EditText? = null
    private var password: EditText? = null
    private var firebaseLogin: FirebaseLoginFlow? = null
    private var facebookLogin: Button? = null
    private var preferences: SharedPreferences? = null

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

        preferences = PreferenceManager.getDefaultSharedPreferences(context)

        return view
    }

    override fun onClick(view: View?) {
        when (view) {
            login -> if (username?.text.isNullOrEmpty() || password?.text.isNullOrEmpty()) {
                setUpUiErrorFields()
            } else {
                firebaseLogin?.signInUser(username?.text?.toString(), password?.text?.toString(), activity)
            }
            facebookLogin -> firebaseLogin?.loginWithFacebook(preferences, activity)
        }
    }

    private fun setUpUiErrorFields() {
        if (username?.text.isNullOrEmpty())
            username?.error = "Field is missing"
        if (password?.text.isNullOrEmpty())
            password?.error = "Field is missing"
    }
}