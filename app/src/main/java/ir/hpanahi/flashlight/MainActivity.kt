package ir.hpanahi.flashlight

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_CAMERA_PERMISSION = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setupPermission()
    }

    private fun setupPermission(){
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkSelfPermission(Manifest.permission.CAMERA)
        } else {
            PackageManager.PERMISSION_GRANTED
            TODO("VERSION.SDK_INT < M")
        }

        Log.d("permissions","$permission")
        val permissions = Array(1){Manifest.permission.CAMERA}
        if (permission!=PackageManager.PERMISSION_GRANTED){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions,REQUEST_CODE_CAMERA_PERMISSION)
            }
        }
    }


}
