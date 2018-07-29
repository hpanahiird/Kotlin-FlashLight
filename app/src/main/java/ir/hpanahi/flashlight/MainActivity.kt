package ir.hpanahi.flashlight

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.SurfaceTexture
import android.hardware.Camera
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*


@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {

    private val REQUEST_CODE_CAMERA_PERMISSION = 10
    private var hasFlash: Boolean = false
    private var camera: Camera? = null
    private var parameters: Camera.Parameters? = null
    private var isON = false
    private var hasPermission = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        hasFlash = checkForFlash()
        if (hasFlash) {
            setupPermission()
            btn_power_switch.setOnClickListener({
                Log.d("nvjfnf", "lkfmgnf")
                if (isON)
                    turnOfFlashlight()
                else
                    turnOnFlashlight()
            })
        } else {

        }
    }

    private fun checkForFlash(): Boolean {
        return if (applicationContext.packageManager.hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            true
        } else {
            Toast.makeText(this, "Your camera has not any flash", Toast.LENGTH_SHORT).show()
            false
        }
    }

    private fun setupPermission() {
        val permission:Int
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permission = checkSelfPermission(Manifest.permission.CAMERA)
        } else {
            permission = PackageManager.PERMISSION_GRANTED
            hasPermission = true
//            TODO("VERSION.SDK_INT < M")
        }

        Log.d("permissions", "$permission")
        val permissions = Array(1) { Manifest.permission.CAMERA }
        if (permission != PackageManager.PERMISSION_GRANTED) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, REQUEST_CODE_CAMERA_PERMISSION)
            }
        } else {
            camera = Camera.open()
            parameters = camera!!.parameters
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            REQUEST_CODE_CAMERA_PERMISSION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    camera = Camera.open()
                    parameters = camera!!.parameters
                    hasPermission = true
                }
            }
        }
    }

    private fun turnOnFlashlight() {
        if (camera!=null) {
            parameters!!.flashMode = Camera.Parameters.FLASH_MODE_TORCH
            val mDummy = SurfaceTexture(1) // any int argument will do
            camera!!.setPreviewTexture(mDummy)
            camera!!.parameters = parameters
            camera!!.startPreview()
            isON = true
            btn_power_switch.text = getString(R.string.switch_off)
        }
    }

    private fun turnOfFlashlight() {
        if (camera != null) {
            parameters!!.flashMode = Camera.Parameters.FLASH_MODE_OFF
            camera!!.parameters = parameters
            camera!!.stopPreview()
            isON = false
            btn_power_switch.text = getString(R.string.switch_on)
        }
    }

    private fun setViews() {
        btn_power_switch.text = getString(R.string.switch_on)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (camera != null) {
            camera!!.release()
            camera = null
        }
    }

    override fun onResume() {
        super.onResume()
        Log.d("fkmkmfgj","fgfg")
        if (!isCameraEnabled()){
            Log.d("fkmkmfgj","fgfg")
            setViews()
            isON =false
            setupPermission()
        }
    }

    private fun isCameraEnabled(): Boolean {
//        var camera: Camera? = null
        try {
//            camera = Camera.open()
            camera!!.parameters
        } catch (e: RuntimeException) {
            e.printStackTrace()
            return false
        } finally {
//            if (camera != null) camera!!.release()
        }
        return true
    }

}
