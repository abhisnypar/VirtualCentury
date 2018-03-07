package cricket.ib.virtualcentury.login.loginManager

import android.content.Context
import android.support.v4.app.FragmentActivity
import android.util.Log
import android.widget.Toast
import com.facebook.AccessToken
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nigelbrown.fluxion.Flux
import cricket.ib.virtualcentury.action.ActionCreator

class FirebaseLoginFlow(
        private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance(),
        private var actionsCreator: ActionCreator? = ActionCreator(Flux.getsInstance()),
        private val firebaseDB: DatabaseReference = FirebaseDatabase.getInstance().reference
) {

    fun signUpUser(email: String?, password: String?, activity: FragmentActivity?) {
        if (email != null && password != null) {
            activity?.let {
                firebaseAuth.createUserWithEmailAndPassword(email, password).
                        addOnCompleteListener(it) { task ->
                            if (task.isSuccessful) {
                                actionsCreator?.singnUpSuccess()
                            } else {
                                Toast.makeText(activity, "User not able to register" + task.exception.toString(), Toast.LENGTH_LONG).show()
                            }
                        }
            }
        }
    }

    fun signInUser(email: String?, password: String?, activity: FragmentActivity?) {
        if (email != null && password != null) {
            activity?.let {
                firebaseAuth.signInWithEmailAndPassword(email, password).
                        addOnCompleteListener(it) { task ->
                            if (task.isSuccessful) {
                                actionsCreator?.loginSuccess()
                            } else {
                                Toast.makeText(activity, "User not able to login" + task.exception.toString(), Toast.LENGTH_LONG).show()
                            }
                        }
            }
        }
    }

    fun signOut() {
        firebaseAuth.signOut()
        actionsCreator?.logout()
    }

    fun handleAccessToken(accessToken: AccessToken?, activity: FragmentActivity?) {
        accessToken?.let {
            val credential = FacebookAuthProvider.getCredential(accessToken.token)
            activity?.let {
                FirebaseAuth.getInstance().signInWithCredential(credential).addOnCompleteListener(activity) { task ->
                    if (task.isSuccessful) {
                        saveTokenToFirebaseDB(accessToken, activity.baseContext)
                    }
                }
            }
        }
    }

    private fun saveTokenToFirebaseDB(accessToken: AccessToken?, context: Context?) {
        val tokenViewModel = TokenViewModel(
                accessToken = accessToken?.token,
                expiresIn = accessToken?.expires,
                applicationId = accessToken?.applicationId,
                userId = accessToken?.userId,
                lastRefresh = accessToken?.lastRefresh
        )

        firebaseDB.child(accessToken?.userId).setValue(tokenViewModel).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                retrieveFacebookCredentialsFromDB()
                actionsCreator?.loginSuccess()
            }

        }
    }

    private fun retrieveFacebookCredentialsFromDB() {
        firebaseDB.addValueEventListener(object : ValueEventListener {
            override fun onCancelled(databaseError: DatabaseError?) {
                Log.d("DB", databaseError.toString())
            }

            override fun onDataChange(dataSnapshot: DataSnapshot?) {
                Log.d("DB", dataSnapshot.toString())
                actionsCreator?.loginSuccess()
            }
        })
    }
}