package com.example.e_commerce_11.activities.ui.products

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.e_commerce_11.R
import com.example.e_commerce_11.activities.*
import com.example.e_commerce_11.activities.ui.base_fragment.BaseFragment
import com.example.e_commerce_11.firestore.FireStoreClass
import com.example.e_commerce_11.models.User
import com.example.e_commerce_11.utilities.Button
import com.example.e_commerce_11.utilities.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_products.*

class ProductsFragment : BaseFragment(), View.OnClickListener {

    private lateinit var userDetails : User
    //private lateinit var productsViewModel: ProductsViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        getUserDetails()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_products, container, false)

        //test button for retrieving products from database and logging them
//        val button: android.widget.Button = root.findViewById(R.id.btn_get_products)
//        button.setOnClickListener(this)
        return root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.products_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {

            R.id.action_add -> {
                startActivity(Intent(activity, AddProductActivity::class.java))

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
    private fun getCurrentUserID(): String {

        //get user details from Firebase
        val currentUser = FirebaseAuth.getInstance().currentUser

        var currentUserID = ""

        //store the user ID in currentUser variable
        if (currentUser != null) {
            currentUserID = currentUser.uid
        }

        //return the user ID
        return currentUserID
    }
    private fun getUserDetails(){

        FirebaseFirestore.getInstance().collection(Constants.USERS)
            //find the document by user ID
            .document(getCurrentUserID())
            //get request used to retrieve info
            .get()
            //if successful, store user details to the device
            .addOnSuccessListener { document ->

                //create a User object using the details we just retrieved
                userDetails = document.toObject(User::class.java)!!

                if(userDetails.admin != 0){
                    setHasOptionsMenu(true)
                }
            }
            //display a message if an error occurs
            .addOnFailureListener { e ->
                Log.e("Error",
                    "Error while getting user details.",
                    e)
            }
    }

    private fun getProducts(){
        FirebaseFirestore.getInstance().collection(Constants.PRODUCTS)
            //get request used to retrieve info
            .get()
            //if successful, store user details to the device
            .addOnSuccessListener { result ->

                for (x in result) {
                    Log.i("Products", x.toString())
                }

                }
            }

    override fun onClick(v: View?) {

            if(v != null){

//                when (v.id){
//
//                    R.id.btn_get_products -> {
//                        getProducts()
//                }
//            }
        }
    }

}
