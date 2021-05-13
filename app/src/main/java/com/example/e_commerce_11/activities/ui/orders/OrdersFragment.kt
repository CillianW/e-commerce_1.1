package com.example.e_commerce_11.activities.ui.orders

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce_11.R
import com.example.e_commerce_11.activities.ui.base_fragment.BaseFragment
import com.example.e_commerce_11.models.User
import com.example.e_commerce_11.utilities.Constants
import com.example.e_commerce_11.utilities.OrderFragmentItemAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_orders.*


lateinit var userDetails: User

class OrdersFragment : BaseFragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //display a "please wait" message while the fragment is set up
        displayProgressDialogue(R.string.please_wait.toString())

        //retrieve user details which will be used to determine what functionality the options menu will provide
        getUserDetails()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //notificationsViewModel = ViewModelProvider(this).get(NotificationsViewModel::class.java)
        val view = inflater.inflate(R.layout.fragment_orders, container, false)

        return view
    }

    //retrieve user details from the database
    private fun getUserDetails() {

        FirebaseFirestore.getInstance().collection(Constants.USERS)
            //find the document by user ID
            .document(getCurrentUserID())
            //get request used to retrieve info
            .get()
            //if successful, store user details to the device
            .addOnSuccessListener { document ->

                //create a User object using the details we just retrieved
                userDetails = document.toObject(User::class.java)!!

                getOrders()

            }
            //display a message if an error occurs
            .addOnFailureListener { e ->
                Log.e(
                    "Error",
                    "Error while getting user details.",
                    e
                )
            }
    }

    //retrieve the products from the database and pass them to the item adapter to be displayed in the recycler view
    private fun getOrders() {

        val orderSummaries: ArrayList<String> = ArrayList()

        if(userDetails.admin == 1) {

            //query the products collection for all products
            FirebaseFirestore.getInstance().collection(Constants.ORDERS)
//            .whereEqualTo("userID", userDetails.id)
                //get request used to retrieve info
                .get()
                //extract the information from the result of the query and pass it to the item adapter to be displayed
                .addOnSuccessListener { result ->

                    for (x in result) {

//                    orderItem.orderID = x.getString(Constants.ORDER_ID).toString()
//                    orderItem.orderItemName =
//                        x.getString(Constants.ORDER_ITEM_NAME).toString()
//                    orderItem.orderItemID =
//                        x.getString(Constants.ORDER_ITEM_ID).toString()
//                    orderItem.orderItemName =
//                        x.getString(Constants.ORDER_ITEM_NAME).toString()
//                    orderItem.orderItemQuantity =
//                        x.getString(Constants.ORDER_ITEM_QUANTITY).toString()
//                    orderItem.orderItemPrice =
//                        x.getString(Constants.ORDER_ITEM_PRICE).toString()
//                    orderItem.userID = x.getString(Constants.ORDER_USER_ID).toString()

                        if (!orderSummaries.contains(x.getString(Constants.ORDER_ID).toString())) {
                            orderSummaries.add(x.getString(Constants.ORDER_ID).toString())
                        }


                    }

                    // Set the LayoutManager that this RecyclerView will use.
                    rv_Items_List_orders.layoutManager = LinearLayoutManager(context)
                    // Adapter class is initialized and list is passed in the param.
                    val itemAdapter = context?.let { OrderFragmentItemAdapter(it, orderSummaries) }
                    // adapter instance is set to the recyclerview to inflate the items.
                    rv_Items_List_orders.adapter = itemAdapter

                    dismissProgressDialogue()
                }
                //log any errors that may occur
                .addOnFailureListener { e ->
                    Log.e(e.toString(), "Error loading products")
                    dismissProgressDialogue()
                }
        }
        else{
            //query the products collection for all products
            FirebaseFirestore.getInstance().collection(Constants.ORDERS)
            .whereEqualTo("userID", userDetails.id)
                //get request used to retrieve info
                .get()
                //extract the information from the result of the query and pass it to the item adapter to be displayed
                .addOnSuccessListener { result ->

                    for (x in result) {

//                    orderItem.orderID = x.getString(Constants.ORDER_ID).toString()
//                    orderItem.orderItemName =
//                        x.getString(Constants.ORDER_ITEM_NAME).toString()
//                    orderItem.orderItemID =
//                        x.getString(Constants.ORDER_ITEM_ID).toString()
//                    orderItem.orderItemName =
//                        x.getString(Constants.ORDER_ITEM_NAME).toString()
//                    orderItem.orderItemQuantity =
//                        x.getString(Constants.ORDER_ITEM_QUANTITY).toString()
//                    orderItem.orderItemPrice =
//                        x.getString(Constants.ORDER_ITEM_PRICE).toString()
//                    orderItem.userID = x.getString(Constants.ORDER_USER_ID).toString()

                        if (!orderSummaries.contains(x.getString(Constants.ORDER_ID).toString())) {
                            orderSummaries.add(x.getString(Constants.ORDER_ID).toString())
                        }


                    }

                    // Set the LayoutManager that this RecyclerView will use.
                    rv_Items_List_orders.layoutManager = LinearLayoutManager(context)
                    // Adapter class is initialized and list is passed in the param.
                    val itemAdapter = context?.let { OrderFragmentItemAdapter(it, orderSummaries) }
                    // adapter instance is set to the recyclerview to inflate the items.
                    rv_Items_List_orders.adapter = itemAdapter

                    dismissProgressDialogue()
                }
                //log any errors that may occur
                .addOnFailureListener { e ->
                    Log.e(e.toString(), "Error loading products")
                    dismissProgressDialogue()
                }
        }
    }

    //get current user id from firebase
    //the user id is used to get the user details
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
}