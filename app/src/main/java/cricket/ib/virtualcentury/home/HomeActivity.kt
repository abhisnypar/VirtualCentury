package cricket.ib.virtualcentury.home

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import butterknife.ButterKnife
import cricket.ib.virtualcentury.R

class HomeActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                setFragment()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard ->
                //TODO navigation dashboard fragment
                return@OnNavigationItemSelectedListener true
            R.id.navigation_notifications ->
                //TODO navigation notifications fragment
                return@OnNavigationItemSelectedListener true
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        ButterKnife.bind(this)
        setFragment()

        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun setFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
    }
}
