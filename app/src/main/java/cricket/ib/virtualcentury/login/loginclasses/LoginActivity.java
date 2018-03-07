package cricket.ib.virtualcentury.login.loginclasses;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.facebook.CallbackManager;
import com.nigelbrown.fluxion.Annotation.React;
import com.nigelbrown.fluxion.Flux;
import com.nigelbrown.fluxion.Reaction;

import butterknife.ButterKnife;
import cricket.ib.virtualcentury.R;
import cricket.ib.virtualcentury.home.HomeActivity;
import cricket.ib.virtualcentury.login.signup.SignupFragment;
import cricket.ib.virtualcentury.store.AppStore;
import cricket.ib.virtualcentury.store.Reactions;

public class LoginActivity extends AppCompatActivity {
    private CallbackManager facebookCallbackManager;
    private SharedPreferences sharedPreferences;
    private AppStore mAppStore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        mAppStore = new AppStore(Flux.getsInstance());
        facebookCallbackManager = CallbackManager.Factory.create();
        sharedPreferences = getSharedPreferences("CURRENT", MODE_PRIVATE);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.login_activity_fragment_container, new LoginFragment())
                .commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @React(reactionType = Reactions.SHOW_SIGNUP_SCREEN)
    public void showSignupScreen(Reaction reaction) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.login_activity_fragment_container, new SignupFragment()).addToBackStack(SignupFragment.class.getSimpleName())
                .commit();
    }

    @React(reactionType = Reactions.SHOW_LOGIN_SCREEN)
    public void showLoginScreen(Reaction reaction) {
        getSupportFragmentManager().popBackStack();
    }

    @React(reactionType = Reactions.SHOW_HOME_SCREEN)
    public void showHomeScreen(Reaction reaction) {
        startActivity(new Intent(this, HomeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
}