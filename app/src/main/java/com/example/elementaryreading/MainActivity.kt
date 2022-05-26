package com.example.elementaryreading


import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import androidx.navigation.fragment.findNavController
import androidx.startup.AppInitializer
import app.rive.runtime.kotlin.RiveInitializer
import com.example.elementaryreading.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val readExternalPermissionContract =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isPermissionAccepted ->
            if (!isPermissionAccepted) {
                //set animation?
                val mJob = Job()
                val mScope = CoroutineScope(Dispatchers.Main + mJob)
                fun playHatefulSpeech() = mScope.launch(Dispatchers.IO) {
                    val mMediaPlayer = MediaPlayer.create(
                        application, (application as Context).resources.getIdentifier(
                            "permission_denied",
                            "raw",
                            (application as Context).packageName
                        )
                    )

                    mMediaPlayer.start()
                }
                playHatefulSpeech()

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
        HelperObject.animationCounter = 0
        val hostFragment = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id)
        setContentView(binding.root)
        hostFragment?.findNavController()
            ?.navigate(R.id.action_explanationAnimation_to_rouletteFragment)
    }


}


