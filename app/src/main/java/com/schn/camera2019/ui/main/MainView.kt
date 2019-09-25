package com.schn.camera2019.ui.main

import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.Display
import android.view.View
import android.view.ViewStub
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.Snackbar
import com.schn.camera2019.BuildConfig
import com.schn.camera2019.R
import com.schn.camera2019.base.acitivity.BaseMvpActivity
import com.schn.camera2019.permission.Action
import com.schn.camera2019.permission.EasyPermission
import com.schn.camera2019.permission.PermissionCallback
import com.schn.camera2019.rx.Event
import com.schn.camera2019.rx.RxBus
import com.schn.camera2019.ui.camera.CameraView
import com.schn.camera2019.ui.camera.DialogListener
import com.schn.camera2019.ui.gallery.GalleryView
import com.schn.camera2019.ui.settings.SettingsView
import com.schn.camera2019.util.ConstantUtil
import com.schn.camera2019.util.LogUtils
import com.schn.camera2019.util.PreferenceUtil
import kotlinx.android.synthetic.main.activity_main.*

class MainView : BaseMvpActivity<MainContract.View, MainContract.Presenter>(), MainContract.View,
    PermissionCallback, SensorEventListener, DialogListener {

    override fun createPresenter(): MainContract.Presenter {
        return MainPresenter()
    }

    override fun findMvpView(): MainContract.View {
        return this
    }
    var TAG: String = "MainView.class"
    private var mAccelCurrent: Float = 0.0F
    private var mAccelLast: Float = 0.0F
    private var mAccel: Float = 0.0F
    private var mDisplay: Display? = null
    private var lastUpdate: Long = 0L
    private var rationaleView: View? = null
    private var sensorManager: SensorManager? = null
    private var sensor: Sensor? = null
    private var permissionFromSettings = false

    override fun showRationale(actionCode: Int) {
        LogUtils.d(TAG,"show rationale message")
        createAndShowPermissionRationale(
            actionCode,
            R.string.rationale_title,
            R.string.rationale_subtitle
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.findViewById<TextView>(R.id.titleTv).text = "GALLERY"
        val videoRecordIv = toolbar.findViewById<ImageView>(R.id.record)
        videoRecordIv.setOnClickListener {
            showCamera()
        }
        val settingsIv = toolbar.findViewById<ImageView>(R.id.settings)
        settingsIv.setOnClickListener {
            showSettings()
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            //checking permissions
            checkPermissions()
        } else {
            showGalleyFragment()
        }
    }

    private fun checkPermissions() {
        EasyPermission.checkAndRequestPermission(
            this,
            Action.ACTION_USE_CAMERA,
            Action.ACTION_USE_MIC,
            Action.ACTION_WRITE_SDCARD
        )
    }

    override fun permissionAccepted(actionCode: Int) {
        LogUtils.d(TAG,"permission granted")
        showGalleyFragment()
    }

    private fun showGalleyFragment() {
        LogUtils.d(TAG,"showing galley fragment")
        if (supportFragmentManager.findFragmentByTag("tag.gallery") == null) {
            val beginTransaction = supportFragmentManager.beginTransaction()
            beginTransaction.replace(R.id.fragment_container, GalleryView(), "tag.gallery")
            beginTransaction.addToBackStack("tag.gallery")
            beginTransaction.commit()
        }
    }

    override fun permissionDenied(actionCode: Int) {
        LogUtils.d(TAG,"permission denied")
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Action.ACTION_USE_CAMERA.getPermission())
            || ActivityCompat.shouldShowRequestPermissionRationale(this, Action.ACTION_USE_MIC.getPermission())
            || ActivityCompat.shouldShowRequestPermissionRationale(this, Action.ACTION_WRITE_SDCARD.getPermission())
        ) {
            if (dismissPermissionRationale() == 0) {
                LogUtils.d(TAG,"showing rationale view")
                createAndShowPermissionRationale(
                    actionCode,
                    R.string.rationale_title,
                    R.string.rationale_subtitle
                )
            }
        } else {
            LogUtils.d(TAG,"showing snack bar message")
            showSnackBarPermissionMessage(R.string.snack_bar_permission)
        }
    }

    private fun showSettings() {

        val cameraView = SettingsView()

        cameraView.show(supportFragmentManager, "camera")

    }

    private fun showCamera() {
        if (EasyPermission.canRecordVideo(this)) {
            initGravitySensor()
            val cameraView = CameraView()
            cameraView.show(supportFragmentManager, "camera")
        } else {
            Toast.makeText(this, R.string.need_all_permissions, Toast.LENGTH_LONG).show()
        }
    }

    override fun onDismis() {
        sensorManager?.unregisterListener(this@MainView)
    }

    private fun initGravitySensor() {
        mAccel = 0.00f
        mAccelCurrent = SensorManager.GRAVITY_EARTH
        mAccelLast = SensorManager.GRAVITY_EARTH
        lastUpdate = System.currentTimeMillis()
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mDisplay = windowManager.defaultDisplay
        sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) as Sensor
        sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onAccuracyChanged(sensor: Sensor?, p1: Int) {

    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let { e ->
            if (e.sensor.type == Sensor.TYPE_ACCELEROMETER) {
                getAccelerometer(e)
            }
        }
    }

    private fun getAccelerometer(event: SensorEvent) {
        val threshold = PreferenceUtil.getString(ConstantUtil.EXTRA_KEY_THRESHOLD, ConstantUtil.DEFAULT_THRESHOLD)
        // value for checking the violation
        val thresholdDouble = threshold.toDouble()

        val values = event.values
        val actualTime = event.timestamp
        val x: Float = values[0]
        val y: Float = values[1]
        val z: Float = values[2]
        LogUtils.d(TAG,"-----------------")
        LogUtils.d(TAG,"x $x y $y z $z")
        mAccelLast = mAccelCurrent
        mAccelCurrent = Math.sqrt(((x * x) + (y * y) + (z * z)).toDouble()).toFloat()
        val delta: Float = mAccelCurrent - mAccelLast
        mAccel = (mAccel * 0.9f) + delta
        LogUtils.d(TAG,"acceleration current $mAccelCurrent")
        LogUtils.d(TAG,"acceleration previous $mAccelLast")
        LogUtils.d(TAG,"acceleration delta $delta")
        LogUtils.d(TAG,"acceleration $mAccel")
        LogUtils.d(TAG,"threshold value $thresholdDouble")
        if (mAccel >= thresholdDouble) {
            LogUtils.d(TAG,"movement detected")
            lastUpdate = actualTime
            RxBus.post(Event.AccelerationEvent())
        }
        val changeEvent = Event.AccelerationChangeEvent()
        changeEvent.value = mAccel
        RxBus.post(changeEvent)
        LogUtils.d(TAG,"-----------------")
    }

    override fun onResume() {
        super.onResume()
        if (permissionFromSettings) {
            permissionFromSettings = false
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                //checking permissions
                checkPermissions()
            } else {
                showGalleyFragment()
            }
        }
        if(supportFragmentManager.findFragmentByTag("camera") != null){
            sensorManager?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onPause() {
        super.onPause()
        sensorManager?.unregisterListener(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermission.checkGrantedPermission(grantResults, requestCode, this)
    }

    private fun showSnackBarPermissionMessage(message: Int) {
        val constraintLayout: ConstraintLayout = findViewById(R.id.id_cl_main_activity)
        val snackBar = Snackbar.make(constraintLayout, getString(message), Snackbar.LENGTH_INDEFINITE)
        snackBar.setAction("Go To Settings") {
            permissionFromSettings = true
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

    /**
     * Retry permission check.
     */
    fun onAcceptRationaleClick(view: View) {
        LogUtils.d(TAG,view)
        val action = dismissPermissionRationale()
        LogUtils.d(TAG,"action$action")
        checkPermissions()
    }

    fun onDismissRationaleClick(view: View) {
        LogUtils.d(TAG,view)
        dismissPermissionRationale()
        finish()
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

    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount == 1) {
            finish()
        } else {
            super.onBackPressed()
        }
    }
}