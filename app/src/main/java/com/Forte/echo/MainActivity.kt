package com.Forte.echo

import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import com.Forte.echo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var binding:ActivityMainBinding?=null
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        Handler().postDelayed({
            if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                val intent = Intent(this,PermissionActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.anim,R.anim.anim)
                finish()
            } else if(shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                AlertDialog.Builder(this)
                    .setTitle("Requesting Permission")
                    .setMessage("Allow us to fetch and show songs on your device")
                    .setPositiveButton("Allow", object :DialogInterface.OnClickListener{
                        @RequiresApi(Build.VERSION_CODES.M)
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                    } )
                    .setNegativeButton("Cancel",object :DialogInterface.OnClickListener{
                        @RequiresApi(Build.VERSION_CODES.M)
                        override fun onClick(p0: DialogInterface?, p1: Int) {
                            Toast.makeText(applicationContext,"You denied to fetching songs",Toast.LENGTH_SHORT).show()
                            p0?.dismiss()
                        }
                    })
                    .show()
            }
            else{
                var intent = Intent(this,MediaPlayerActivity::class.java)
                startActivity(intent)
                overridePendingTransition(R.anim.anim,R.anim.anim)
                finish()
            }

        },2000)

    }
}