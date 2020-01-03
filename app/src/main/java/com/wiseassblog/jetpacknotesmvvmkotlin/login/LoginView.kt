package com.wiseassblog.jetpacknotesmvvmkotlin.login

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.wiseassblog.jetpacknotesmvvmkotlin.R
import com.wiseassblog.jetpacknotesmvvmkotlin.common.ANTENNA_LOOP
import com.wiseassblog.jetpacknotesmvvmkotlin.common.RC_SIGN_IN
import com.wiseassblog.jetpacknotesmvvmkotlin.common.startWithFade
import com.wiseassblog.jetpacknotesmvvmkotlin.login.buildlogic.LoginInjector
import com.wiseassblog.jetpacknotesmvvmkotlin.model.LoginResult
import com.wiseassblog.jetpacknotesmvvmkotlin.note.NoteActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginView : Fragment(){

    private lateinit var viewModel: UserViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onStart() {
        super.onStart()

        viewModel = ViewModelProvider(this, LoginInjector(requireActivity().application).provideUserViewModelFactory()).get(UserViewModel::class.java)

        (root_fragment_login.background as AnimationDrawable).startWithFade()

        setUpClickListeners()
        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.signInStatusText.observe(
            viewLifecycleOwner, Observer { text -> lbl_login_status_display.text = text }
        )

        viewModel.authButtonText.observe(
            viewLifecycleOwner, Observer { btn_auth_attempt.text = it }
        )

        // Extras

        viewModel.startAnimation.observe(
            viewLifecycleOwner,
            Observer {
                imv_antenna_animation.setImageResource(
                    resources.getIdentifier(ANTENNA_LOOP, "drawable", activity?.packageName)
                )
                (imv_antenna_animation.drawable as AnimationDrawable).start()
            }
        )

        viewModel.satelliteDrawable.observe(
            viewLifecycleOwner,
            Observer {
                imv_antenna_animation.setImageResource(
                    resources.getIdentifier(it, "drawable", activity?.packageName)
                )
            }
        )

        viewModel.authAttempt.observe(
            viewLifecycleOwner, Observer { startSignInFlow() }
        )
    }

    private fun setUpClickListeners() {
        btn_auth_attempt.setOnClickListener{ viewModel.handleEvent(LoginEvent.OnAuthButtonClick)}

        imb_toolbar_back.setOnClickListener{ startListActivity()}

        requireActivity().onBackPressedDispatcher.addCallback(this) {
            startListActivity()
        }
    }

    private fun startListActivity() = requireActivity().startActivity(
        Intent(activity, NoteActivity::class.java)
    )

    private fun startSignInFlow(){
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id)).build()

        val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)

        val signInIntent = googleSignInClient.signInIntent

        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val task = GoogleSignIn.getSignedInAccountFromIntent(data)
        var userToken: String? = null

        try {
            val account: GoogleSignInAccount? = task.getResult(ApiException::class.java)

            if (account != null) userToken = account.idToken
        } catch (exception: Exception) {
            Log.d("LOGIN", exception.toString())
        }

        viewModel.handleEvent(
            LoginEvent.OnGoogleSignInResult(
                LoginResult(
                    requestCode,
                    userToken
                )
            )
        )
    }
}