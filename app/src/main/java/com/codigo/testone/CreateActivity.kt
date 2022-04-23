package com.codigo.testone

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CompoundButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.*
import com.codigo.testone.databinding.ActivityCreateBinding
import kotlinx.coroutines.flow.collectLatest

class CreateActivity : AppCompatActivity() {

    private lateinit var binding : ActivityCreateBinding
    private lateinit var viewModel : CreateViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create)
        viewModel = ViewModelProvider(this)[CreateViewModel::class.java]
        binding.activity = this
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

        changeStatusBarColor()

        binding.switchCompat.setOnCheckedChangeListener { _, p1 -> viewModel.gender.value = p1 }
    }

    override fun onStart() {
        super.onStart()
        lifecycleScope.launchWhenStarted {
            viewModel.success
                .flowWithLifecycle(lifecycle,Lifecycle.State.STARTED)
                .collectLatest {
                    if (it){
                        Toast.makeText(this@CreateActivity,"Account Created SuccessFully",Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }
        }
    }

    private fun changeStatusBarColor() {
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
}