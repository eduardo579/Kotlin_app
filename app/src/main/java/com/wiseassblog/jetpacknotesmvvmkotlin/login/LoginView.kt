package com.wiseassblog.jetpacknotesmvvmkotlin.login

import android.content.Intent
import android.graphics.drawable.AnimationDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.wiseassblog.jetpacknotesmvvmkotlin.R
import com.wiseassblog.jetpacknotesmvvmkotlin.common.startWithFade
import com.wiseassblog.jetpacknotesmvvmkotlin.login.buildlogic.LoginInjector
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

        viewModel = ViewModelProviders.of(this, LoginInjector(requireActivity().application).provideUserViewModelFactory()).get(UserViewModel::class.java)

        (root_fragment_login.background as AnimationDrawable).startWithFade()

        setUpClickListeners()
    }

    private fun setUpClickListeners() {
        btn_auth_attempt.setOnClickListener{ viewModel.handleEvent(LoginEvent.OnAuthButtonClick)}

        imb_toolbar_back.setOnClickListener{ startListActivity()}
    }

    private fun startListActivity() = requireActivity().startActivity(
        Intent(activity, NoteActivity::class.java)
    )
}