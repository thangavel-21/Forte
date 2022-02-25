package com.Forte.echo

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.Forte.echo.databinding.MusicPlaylistLayoutBinding
import com.Forte.echo.databinding.PermissionLayoutBinding
import kotlinx.android.synthetic.main.permission_layout.*
import java.util.jar.Manifest

class PermissionActivity : AppCompatActivity(), ActivityCompat.OnRequestPermissionsResultCallback {
    private var binding: PermissionLayoutBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = PermissionLayoutBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding?.permissionButton?.setOnClickListener {

            if (ActivityCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    111
                )
            }
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && requestCode == 111) {
            var intent = Intent(this, MediaPlayerActivity::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.anim, R.anim.anim)
            finish()

        } else {

        }
    }

//    fun permissionCheck():Boolean{
//        return ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)!=PackageManager.PERMISSION_GRANTED);
//    }
}