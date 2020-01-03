package com.wiseassblog.jetpacknotesmvvmkotlin.login

import androidx.lifecycle.MutableLiveData
import com.wiseassblog.jetpacknotesmvvmkotlin.common.*
import com.wiseassblog.jetpacknotesmvvmkotlin.model.LoginResult
import com.wiseassblog.jetpacknotesmvvmkotlin.model.User
import com.wiseassblog.jetpacknotesmvvmkotlin.model.repository.IUserRepository
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

/**
 * This approach to ViewModels reduces the complexity of the View by containing specific details about widgets and
 * controls present in the View. The benefit of doing so is to make the View in to a Humble Object; reducing or
 * eliminating the need to test the View.
 *
 * The downside of this approach, is that the ViewModel is no longer re-usable across a variety of Views. In this case,
 * since this ViewModel is only used by a single View, and the application architecture will not change any time soon,
 * losing re-usability in exchange for a simpler View is not a problem.
 */

class UserViewModel(val repo: IUserRepository, uiContext: CoroutineContext): BaseViewModel<LoginEvent<LoginResult>>(uiContext){
    private val userState = MutableLiveData<User>()

    // Control logic
    internal val authAttempt = MutableLiveData<Unit>()
    internal val startAnimation = MutableLiveData<Unit>()

    // UI binding

    internal val signInStatusText = MutableLiveData<String>()
    internal val authButtonText = MutableLiveData<String>()
    internal val satelliteDrawable = MutableLiveData<String>()

    override fun handleEvent(event: LoginEvent<LoginResult>) {
        showLoadingState()

        when (event){
            is LoginEvent.OnStart -> getUser()
            is LoginEvent.OnAuthButtonClick -> onAuthButtonClick()
            is LoginEvent.OnGoogleSignInResult -> onSignInResult(event.result)


        }

    }

    private fun onSignInResult(result: LoginResult) {

    }

    private fun onAuthButtonClick() {
        if (userState.value == null) authAttempt.value = Unit
    }

    private fun getUser() = launch {
        val result = repo.getCurrentUser()

        when (result){
            is Result.Value -> {
                userState.value = result.value

                if (result.value == null) showSignedOutState()

                else showSignedInState()
            }

            is Result.Error -> showErrorState()
        }

    }

    private fun showErrorState() {

    }

    private fun showSignedInState() {

    }

    private fun showSignedOutState() {

    }

    private fun showLoadingState() {

    }

}