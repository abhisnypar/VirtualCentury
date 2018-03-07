package cricket.ib.virtualcentury

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

import cricket.ib.virtualcentury.login.loginclasses.LoginActivity

class SplashScreen : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
    }

    override fun onResume() {
        super.onResume()

        try {
            Thread.sleep(3000)
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

    }
}