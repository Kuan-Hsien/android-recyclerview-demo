package com.kuanhsien.app.sample.android_recyclerview_demo.ui.swipe_refresh

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.kuanhsien.app.sample.android_recyclerview_demo.R
import kotlinx.android.synthetic.main.activity_swipe_refresh.*


private const val REFRESH_DELAY: Long = 5000 // 5 seconds

class SwipeRefreshActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_swipe_refresh)

        initSwipeRefreshLayout()
    }

    /**
     * Setup swipe refresh layout
     */
    private fun initSwipeRefreshLayout() {

        // [Swipe Refresh Layout]
        // 1. Set Color: (Method a)
        // set single color, default is black
        // root_swipe_refresh_container.setColorSchemeColors(Color.RED)

        // [Swipe Refresh Layout]
        // 1. Set Color: (Method b)
        // loop change color, each for 1 second
        root_swipe_refresh_container.setColorSchemeResources(
            android.R.color.holo_red_light,
            android.R.color.holo_blue_light,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_purple
        )

        // [Swipe Refresh Layout]
        // 2. Set Refresh Listener
        root_swipe_refresh_container.setOnRefreshListener {

            // simulate refresh success after refreshDelay, and close animation
            Handler().postDelayed({
                root_swipe_refresh_container.isRefreshing = false

                Snackbar.make(root_swipe_refresh_container, getString(R.string.refresh_success), Snackbar.LENGTH_SHORT).show()

            }, REFRESH_DELAY)
        }
    }
}