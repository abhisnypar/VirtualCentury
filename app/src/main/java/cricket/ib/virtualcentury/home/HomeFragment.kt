package cricket.ib.virtualcentury.home


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.OnClick
import com.facebook.AccessToken
import cricket.ib.virtualcentury.R
import cricket.ib.virtualcentury.login.FirebaseLoginFlow


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {
    private var firebaseLoginFlow: FirebaseLoginFlow? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        ButterKnife.bind(this, view)
        firebaseLoginFlow = FirebaseLoginFlow()
        return view
    }

    @OnClick(R.id.logout)
    fun onLogout() {
        if (AccessToken.getCurrentAccessToken() != null) {
            firebaseLoginFlow?.signOut()
        }
    }
}