package cricket.ib.virtualcentury.store

import com.nigelbrown.fluxion.Annotation.Action
import com.nigelbrown.fluxion.Annotation.Store
import com.nigelbrown.fluxion.Flux
import com.nigelbrown.fluxion.FluxAction
import com.nigelbrown.fluxion.FluxStore
import cricket.ib.virtualcentury.action.Actions

@Store
class AppStore(flux: Flux) : FluxStore(flux) {

    init {
        registerActionSubscriber(this)
    }

    @Action(actionType = Actions.SIGNUP_BUTTON_CLICKED)
    fun showSignUpScreen(fluxAction: FluxAction) = emitReaction(Reactions.SHOW_SIGNUP_SCREEN)

    @Action(actionType = Actions.SINGUP_ON_SUCCESS)
    fun showLoginFragmentScreen(fluxAction: FluxAction) = emitReaction(Reactions.SHOW_LOGIN_SCREEN)

    @Action(actionType = Actions.LOGIN_ON_SUCCESS)
    fun showHomeFragmentScreen(fluxAction: FluxAction) = emitReaction(Reactions.SHOW_HOME_SCREEN)
}