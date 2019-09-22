package com.schn.camera2019.ui

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.ViewStub
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.schn.camera2019.BuildConfig
import com.schn.camera2019.R
import com.schn.camera2019.permission.Action
import com.schn.camera2019.permission.EasyPermission
import com.schn.camera2019.permission.PermissionCallback
import com.schn.camera2019.ui.record.RecordView


class MainActivity : AppCompatActivity(), PermissionCallback {

    private var rationaleView: View? = null
    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null

    override fun permissionAccepted(actionCode: Int) {
        System.out.println("permission granted")
    }

    override fun permissionDenied(actionCode: Int) {
        System.out.println("permission denied")
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Action.ACTION_USE_CAMERA.getPermission())
            || ActivityCompat.shouldShowRequestPermissionRationale(this, Action.ACTION_USE_MIC.getPermission())
            || ActivityCompat.shouldShowRequestPermissionRationale(this, Action.ACTION_WRITE_SDCARD.getPermission())
        ) {
            if (dismissPermissionRationale() == 0) {
                createAndShowPermissionRationale(
                    actionCode,
                    R.string.rationale_title,
                    R.string.rationale_subtitle
                )
            }
        } else {
            showSnackBarPermissionMessage(R.string.snack_bar_permission)
        }
    }

    override fun showRationale(actionCode: Int) {
        System.out.println("show rationale message")
        createAndShowPermissionRationale(
            actionCode,
            R.string.rationale_title,
            R.string.rationale_subtitle)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EasyPermission.checkAndRequestPermission(
            this,
            Action.ACTION_USE_CAMERA,
            Action.ACTION_USE_MIC,
            Action.ACTION_WRITE_SDCARD
        )
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, RecordView(), "RecordView").commit()
        initGravitySensor()
    }

    private fun initGravitySensor() {
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_GRAVITY) as Sensor
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermission.checkGrantedPermission(grantResults, requestCode, this)
    }

    private fun showSnackBarPermissionMessage(message: Int) {
        val constraintLayout: ConstraintLayout = findViewById(R.id.id_cl_main_activity)
        val snackBar = Snackbar.make(constraintLayout, getString(message), Snackbar.LENGTH_LONG)
        snackBar.setAction("Go To Settings") {
            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
            intent.data = Uri.parse("package:" + BuildConfig.APPLICATION_ID)
            startActivity(intent)
        }
        // get SnackBar view
        val snackBarView = snackBar.view
        // change SnackBar text color
        val snackBarTextViewId = com.google.android.material.R.id.snackbar_text
        val snackBarTextView = snackBarView.findViewById<TextView>(snackBarTextViewId)
        snackBarTextView.setTextColor(ContextCompat.getColor(this, R.color.colorWhite))
        snackBar.show()
    }

    private fun createAndShowPermissionRationale(action: Int, titleResId: Int, subtitleResId: Int) {
        if (rationaleView == null) {
            rationaleView = (findViewById<ViewStub>(R.id.permission_rationale_stub)).inflate()
        } else {
            rationaleView?.visibility = View.VISIBLE
        }
        (rationaleView?.findViewById(R.id.rationale_title) as TextView).setText(titleResId)
        (rationaleView?.findViewById(R.id.rationale_subtitle) as TextView).setText(subtitleResId)
        rationaleView?.tag = action
    }

    fun onAcceptRationaleClick(view: View) {
        System.out.println(view)
        val action = dismissPermissionRationale()
        System.out.println("action$action")
        EasyPermission.checkAndRequestPermission(
            this,
            Action.ACTION_USE_CAMERA,
            Action.ACTION_USE_MIC,
            Action.ACTION_WRITE_SDCARD
        )
    }

    fun onDismissRationaleClick(view: View) {
        System.out.println(view)
        dismissPermissionRationale()
    }

    /**
     * Dismiss and returns the action associated to the rationale
     * @return
     */
    private fun dismissPermissionRationale(): Int {
        if (rationaleView != null && rationaleView?.visibility == View.VISIBLE) {
            rationaleView?.visibility = View.GONE
            return rationaleView?.tag as Int
        }
        return 0
    }

}