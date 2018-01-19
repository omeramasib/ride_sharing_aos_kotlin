package fyp.ride_sharing_aos.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.ColorSpace
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import fyp.ride_sharing_aos.R
import kotlinx.android.synthetic.main.activity_signup.*
import android.widget.RadioButton
import fyp.ride_sharing_aos.HomeActivity
import fyp.ride_sharing_aos.model.User
import fyp.ride_sharing_aos.tools.Tools


class SignupActivity : AppCompatActivity() {


    private lateinit var mAuth : FirebaseAuth
    private lateinit var database: FirebaseDatabase
    private lateinit var dbRef: DatabaseReference

    var email : String? = null
    var password : String? = null
    var gender_selectedId : String? = null
    var gender : String? = null
    var identity_selectedId : String? = null
    var identity : String? = null
    var username: String? = null

    var gender_radioButton :RadioButton? = null
    var identity_radioButton :RadioButton? = null





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()
        dbRef = database.reference
        InitView()

    }

    override fun onResume() {
        super.onResume()
    }

    fun InitView()
    {
        setContentView(R.layout.activity_signup)
        supportActionBar?.hide()
        gender_radioButton = findViewById<View>(radio_gender.checkedRadioButtonId) as RadioButton
        identity_radioButton = findViewById<View>(radio_identity.checkedRadioButtonId) as RadioButton


        signup_button.setOnClickListener({
            getDataFromView()

            if (inputValidation())
            {
                mAuth.createUserWithEmailAndPassword(email.toString(), password.toString())
                        .addOnCompleteListener { task: Task<AuthResult> ->
                            if (task.isSuccessful)
                            {
                                val userId = mAuth.currentUser?.uid
                                val registerRef = dbRef.child("users").child(userId)
                                val user = User(username, userId.toString(), email, identity,gender)

                                registerRef.setValue(user).addOnSuccessListener()
                                {
                                    Toast.makeText(this, "Authentication Success.", Toast.LENGTH_SHORT).show()
                                    val intent = Intent(this@SignupActivity, HomeActivity::class.java)
                                    startActivity(intent)
                                    this.finish()
                                }
                            }
                            else {
                                // If sign in fails, display a message to the user.
                                Toast.makeText(this, "Authentication failed.", Toast.LENGTH_SHORT).show()
                            }
                        }
            }
        })

    }





    fun getDataFromView()
    {
        password = signup_password.text.toString()
        gender = gender_radioButton?.text.toString()
        identity = identity_radioButton?.text.toString()
        username = signup_username.text.toString()
        email = signup_itsc.text.toString()

        if(identity.equals("Student"))
        {
            email = email+"@connect.ust.hk"
        }
        else if(identity.equals("Staff"))
        {
            email = email+"@connect.ust.hk"
        }

    }


    //https://kotlinlang.org/docs/reference/functions.html
    fun inputValidation() : Boolean
    {
        val uname  = signup_username.text.toString()
        val password = signup_password.text.toString()
        val error_msg: ArrayList<String> = ArrayList()

        //Reset The ErrorView
        password_require.setTextColor(Color.BLACK)
        username_require.setTextColor(Color.BLACK)


        //Username Validation
        if (  (uname.length < 6 || uname.length > 8) || uname.isEmpty() ) {
            error_msg.add(getString(R.string.signup_username_error))
            username_require.setTextColor(Color.RED)
        }

        //Password Validation
        if (password.length < 6 || password.isEmpty()) {
            error_msg.add(getString(R.string.signup_password_error))
            password_require.setTextColor(Color.RED)
        }

        if ( !password.matches("[0-9]+".toRegex()) && (password.matches("[a-z]+".toRegex()) || password.matches("[A-Z]+".toRegex()))) {
            error_msg.add(getString(R.string.signup_password_error2))
            password_require.setTextColor(Color.RED)
        }

        if(error_msg == null)
        {
            return true
        }
        else
        {
            Tools.showDialog(this@SignupActivity, "Alert", error_msg)
            return false
        }
    }



}
