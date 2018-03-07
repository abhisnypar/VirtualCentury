package cricket.ib.virtualcentury.home


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import butterknife.ButterKnife
import cricket.ib.virtualcentury.R
import cricket.ib.virtualcentury.login.loginManager.FirebaseLoginFlow


/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment(), View.OnClickListener {
    private var firebaseLoginFlow: FirebaseLoginFlow? = null
    private var logout: Button? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        ButterKnife.bind(this, view)
        firebaseLoginFlow = FirebaseLoginFlow()
        logout = view.findViewById(R.id.logout)
        logout?.setOnClickListener(this)
        return view
    }

    override fun onClick(view: View?) {
        when(view){
            logout -> firebaseLoginFlow?.signOut()
        }
    }
}