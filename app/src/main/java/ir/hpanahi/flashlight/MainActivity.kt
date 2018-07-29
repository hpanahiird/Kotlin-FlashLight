package ir.hpanahi.flashlight

import android.Manifest
import android.content.pm.PackageManager
import android.hardware.Camera
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast

class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_CAMERA_PERMISSION = 10
    private var hasFlash: Boolean = false
    private var camera: Camera? = null
    private var parameters: Camera.Parameters? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hasFlash = checkForFlash()
        if (hasFlash) {
            setupPermission()
        }else{

        }
    }

    private fun checkForFlash():Boolean{
        if (applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)){
            return true
        }else{
            Toast.makeText(this,"Your camera has not any flash",Toast.LENGTH_SHORT).show()
            return false
        }
    }

    private fun setupPermission() {
        val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            checkSelfPermission(Manifest.permission.CAMERA)
        } else {
            PackageManager.PERMISSION_GRANTED
            TODO("VERSION.SDK_INT < M")
        }

        Log.d("permissions", "$permission")
        val permissions = Array(1) { Manifest.permission.CAMERA }
        if (permission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, REQUEST_CODE_CAMERA_PERMISSION)
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_CODE_CAMERA_PERMISSION ->{
                if (grantResults.isNotEmpty() &&grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    camera = Camera.open()
                    parameters = camera!!.parameters
                }
            }
        }
    }

    private fun turnOnFlashlight() {

    }

    private fun turnOfFlashlight() {

    }


}
