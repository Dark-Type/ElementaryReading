package com.example.elementaryreading


import android.Manifest
import android.app.Application
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.elementaryreading.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        fun checkAudioRecordingPermission(context: Application) =
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.RECORD_AUDIO
            ) == PackageManager.PERMISSION_GRANTED
        if (!checkAudioRecordingPermission(context = application)) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.RECORD_AUDIO), 200)
        }

        binding = ActivityMainBinding.inflate(layoutInflater)

        val hostFragment = supportFragmentManager.findFragmentById(binding.fragmentContainerView.id)
        setContentView(binding.root)
        hostFragment?.findNavController()?.navigate(R.id.action_rouletteFragment_to_findTheLetterFragment)
    }


}