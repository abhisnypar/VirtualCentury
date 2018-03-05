package cricket.ib.virtualcentury.action


interface Actions {

    companion object {
        const val SIGNUP_BUTTON_CLICKED = "signupButtonClicked"
        const val SINGUP_ON_SUCCESS = "signUpSuccess"
        const val LOGIN_ON_SUCCESS = "signUpSuccess"
    }

    fun signupButtonClicked()

    fun singnUpSuccess()

    fun loginSuccess()

}