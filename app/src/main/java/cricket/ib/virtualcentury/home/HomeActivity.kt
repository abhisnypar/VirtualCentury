package cricket.ib.virtualcentury.home

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.view.View
import butterknife.ButterKnife
import com.nigelbrown.fluxion.Annotation.React
import com.nigelbrown.fluxion.Flux
import com.nigelbrown.fluxion.Reaction
import cricket.ib.virtualcentury.R
import cricket.ib.virtualcentury.login.loginclasses.LoginActivity
import cricket.ib.virtualcentury.store.AppStore
import cricket.ib.virtualcentury.store.Reactions

class HomeActivity : AppCompatActivity() {
    private var mAppStore: AppStore? = null

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
        mAppStore = AppStore(Flux.getsInstance())

        val navigation = findViewById<View>(R.id.navigation) as BottomNavigationView
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    private fun setFragment() {
        supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, HomeFragment())
                .commit()
    }

    @React(reactionType = Reactions.LOGOUT_SUCCESS)
    private fun showLoginActivity(reaction: Reaction) = startActivity(Intent(this, LoginActivity::class.java).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK))
}
