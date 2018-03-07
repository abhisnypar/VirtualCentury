package cricket.ib.virtualcentury.login.signup

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import com.nigelbrown.fluxion.Flux
import cricket.ib.virtualcentury.Application
import cricket.ib.virtualcentury.R
import cricket.ib.virtualcentury.action.ActionCreator
import cricket.ib.virtualcentury.login.loginManager.FirebaseLoginFlow


class SignupFragment : Fragment(), View.OnClickListener {
    private var application: Application? = null
    private var signUp: Button? = null
    private var username: EditText? = null
    private var password: EditText? = null
    private var progressBar: ProgressBar? = null

    private var loginFlowPresenter: FirebaseLoginFlow? = null
    private var actionsCreator: ActionCreator? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        application?.virutalCenturyComponent?.inject(this)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.sign_up_fragment, container, false)
        actionsCreator = ActionCreator(Flux.getsInstance())
        loginFlowPresenter = FirebaseLoginFlow()

        signUp = view?.findViewById(R.id.signup_button)
        signUp?.setOnClickListener(this)

        username = view?.findViewById(R.id.sign_up_username_edit_text)
        password = view?.findViewById(R.id.sign_up_password_edit_text)
        progressBar = view?.findViewById(R.id.sign_up_progress_bar)
        progressBar?.isIndeterminate = true

        return view

    }

    override fun onClick(view: View?) {
        when (view) {
            signUp -> if (username?.text.isNullOrEmpty() || password?.text.isNullOrEmpty()) {
                setUpUiErrorFields()
            } else {
                loginFlowPresenter?.signUpUser(username?.text?.toString()!!, password?.text?.toString()!!, activity!!)
            }
        }
    }

    private fun setUpUiErrorFields() {
        if (username?.text.isNullOrEmpty())
            username?.error = "Field is missing"
        if (password?.text.isNullOrEmpty())
            password?.error = "Field is missing"
    }
}
