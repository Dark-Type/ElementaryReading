package com.example.elementaryreading.mainActivity


import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.startup.AppInitializer
import app.rive.runtime.kotlin.RiveInitializer
import com.example.elementaryreading.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()
    private val readExternalPermissionContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isPermissionAccepted ->
            if (!isPermissionAccepted) {
                viewModel.playHatefulSpeech()
            }

        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AppInitializer.getInstance(applicationContext)
            .initializeComponent(RiveInitializer::class.java)
        fun hideSystemUI() {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            WindowInsetsControllerCompat(
                window,
                window.decorView.findViewById(android.R.id.content)
            ).let { controller ->
                controller.hide(WindowInsetsCompat.Type.systemBars())
                controller.systemBarsBehavior =
                    WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
            }
        }
        hideSystemUI()

        fun checkAudioRecordingPermission(context: Application) =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        if (!checkAudioRecordingPermission(context = application)) {
            readExternalPermissionContract.launch(Manifest.permission.RECORD_AUDIO)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)

        val hostFragment = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id)
        setContentView(binding.root)
//        hostFragment?.findNavController()
//            ?.navigate(R.id.action_explanationAnimation_to_rouletteFragment)
    }


}


