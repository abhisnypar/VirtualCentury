package cricket.ib.virtualcentury.action

import com.nigelbrown.fluxion.Flux
import com.nigelbrown.fluxion.FluxActionCreator


class ActionCreator(flux: Flux) : FluxActionCreator(flux), Actions {
    override fun loginSuccess()  = emitAction(Actions.LOGIN_ON_SUCCESS)

    override fun signupButtonClicked() = emitAction(Actions.SIGNUP_BUTTON_CLICKED)

    override fun singnUpSuccess() = emitAction(Actions.SINGUP_ON_SUCCESS)

    override fun logout()  = emitAction(Actions.LOGOUT)

}