package com.example.e_commerce_11.firestore

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.Toast
import com.example.e_commerce_11.activities.*
import com.example.e_commerce_11.models.CartItem
import com.example.e_commerce_11.models.Order
import com.example.e_commerce_11.models.Product
import com.example.e_commerce_11.models.User
import com.example.e_commerce_11.utilities.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import kotlinx.coroutines.tasks.await

class FireStoreClass {
    private val myFireStore = FirebaseFirestore.getInstance()


    //register the users details on firestore
    fun registerUser(activity: RegisterActivity, user: User) {

        //create a collection called users if it doesn't already exist
        myFireStore.collection(Constants.USERS)
            //user details will be separated into documents, sorted by user IDs
            .document(user.id)
            //add the user details to the document
            .set(user, SetOptions.merge())
            //if user is registered successfully, call the userRegisteredSuccessfully() method
            .addOnSuccessListener {
                activity.userRegisteredSuccessfully()
            }
            //if an error occurs, log it and display a message
            .addOnFailureListener { e ->
                activity.dismissProgressDialogue()
                Log.e(
                    activity.javaClass.simpleName,
                    "User registration failed",
                    e
                )
            }
    }


    //retrieve current user ID which will be used later
    fun getCurrentUserID(): String {

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

    //get the user details using user ID
    fun getUserDetails(activity: Activity) {

        //make a get request to the FireStore users collection
        myFireStore.collection(Constants.USERS)
            //find the document by user ID
            .document(getCurrentUserID())
            //get request used to retrieve info
            .get()
            //if successful, store user details to the device
            .addOnSuccessListener { document ->

                //log thr user details
                Log.i(activity.javaClass.simpleName, document.toString())

                //create a User object using the details we just retrieved
                val user = document.toObject(User::class.java)

                //get the shared preferences from the activity using private mode
                val sharedPreferences = activity.getSharedPreferences(
                    Constants.APP_PREFERENCES,
                    Context.MODE_PRIVATE
                )

                //use a SharedPreferences object to add a key:value pair (logged_in_username:first name & last name)
                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                editor.putString(
                    Constants.LOGGED_IN_USERNAME,
                    "${user!!.firstName} ${user.surname}"
                )
                editor.apply()


                when (activity) {
                    is LoginActivity -> {
                        if (user != null) {
                            activity.userLoggedInSuccess(user)
                        }
                    }
                    is SettingsActivity -> {
                        if (user != null) {
                            activity.userDetailsSuccess(user)
                        }
                    }
                    is ConfirmDetailsActivity -> {
                        if (user != null) {
                            activity.userDetailsSuccess(user)
                        }
                    }

                }
            }
            //display a message if an error occurs
            .addOnFailureListener { e ->

                when (activity) {
                    is LoginActivity -> {
                        activity.dismissProgressDialogue()
                    }
                    is SettingsActivity -> {
                        activity.dismissProgressDialogue()
                    }
                    is ConfirmDetailsActivity -> {
                        activity.dismissProgressDialogue()
                    }
                }

                Log.e(
                    activity.javaClass.simpleName,
                    "Error while getting user details.",
                    e
                )

            }

    }

    //update the user details on fireStore
    fun updateUserProfile(activity: Activity, userHashMap: HashMap<String, Any>) {

        //send an update request to update user details
        myFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .update(userHashMap)
            //if successful, call the userInfoUpdatedSuccessfully function
            .addOnSuccessListener {
                when (activity) {
                    is UserProfileActivity -> {
                        activity.userInfoUpdatedSuccessfully()
                    }
                }
            }
            //dismiss the progress dialogue and show the user a message if an error occurs
            .addOnFailureListener { e ->
                when (activity) {
                    is UserProfileActivity -> {
                        activity.dismissProgressDialogue()
                    }
                    is AddressActivity -> {
                        activity.dismissProgressDialogue()
                    }
                }
                Log.e(
                    activity.javaClass.simpleName,
                    "Error updating the user details ",
                    e
                )
            }
    }

    fun updateUserProfilePicture(activity: Activity, userHashMap: HashMap<String, Any>) {

        //send an update request to update user details
        myFireStore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .update(userHashMap)
            //dismiss the progress dialogue and show the user a message if an error occurs
            .addOnFailureListener { e ->
                when (activity) {
                    is UserProfileActivity -> {
                        activity.dismissProgressDialogue()
                    }
                }
                Log.e(
                    activity.javaClass.simpleName,
                    "Error updating the user details ",
                    e
                )
            }
    }

    fun createProduct(activity: AddProductActivity, product: Product) {

        //create a collection called users if it doesn't already exist
        myFireStore.collection(Constants.PRODUCTS)
            //user details will be separated into documents, sorted by user IDs
            .document(product.productID)
            //add the user details to the document
            .set(product, SetOptions.merge())
            //if user is registered successfully, call the userRegisteredSuccessfully() method
            .addOnSuccessListener {
                activity.productRegisteredSuccessfully()
            }
            //if an error occurs, log it and display a message
            .addOnFailureListener { e ->
                activity.dismissProgressDialogue()
                Log.e(
                    activity.javaClass.simpleName,
                    "Product registration failed",
                    e
                )
            }
    }

    fun updateProductPicture(
        activity: Activity,
        userHashMap: HashMap<String, Any>,
        productID: String
    ) {

        //send an update request to update user details
        myFireStore.collection(Constants.PRODUCTS)
            .document(productID)
            .update(userHashMap)
            //dismiss the progress dialogue and show the user a message if an error occurs
            .addOnFailureListener { e ->
                when (activity) {
                    is AddProductActivity -> {
                        activity.dismissProgressDialogue()
                    }
                }
                Log.e(
                    activity.javaClass.simpleName,
                    "Error updating the user details ",
                    e
                )
            }
    }

    fun addProductToCart(context: Context, cartItem: CartItem, userID: String) {

        myFireStore.collection(Constants.CARTS)
            //user details will be separated into documents, sorted by user IDs
            .document(cartItem.cartItemID + userID)
            .get()
            //if user is registered successfully, call the userRegisteredSuccessfully() method
            .addOnSuccessListener { it ->


                cartItem.cartItemQuantity = it.get(Constants.CART_ITEM_QUANTITY).toString()

                Log.i("cart item quantity", cartItem.cartItemQuantity)

                if (cartItem.cartItemQuantity == "null" || cartItem.cartItemQuantity == "0") {
                    cartItem.cartItemQuantity = "1"
                } else {
                    cartItem.cartItemQuantity = (cartItem.cartItemQuantity.toInt() + 1).toString()
                }

                //create a collection called users if it doesn't already exist
                myFireStore.collection(Constants.CARTS)
                    //user details will be separated into documents, sorted by user IDs
                    .document(cartItem.cartItemID + userID)
                    .set(cartItem, SetOptions.merge())
                    //if user is registered successfully, call the userRegisteredSuccessfully() method
                    .addOnSuccessListener {
                        Toast.makeText(context, "Item added to cart", Toast.LENGTH_SHORT).show()
                    }
                    //if an error occurs, log it and display a message
                    .addOnFailureListener { e ->
                        Log.e(
                            "Error",
                            "Product registration failed",
                            e
                        )
                        Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()

                    }
            }
            //if an error occurs, log it and display a message
            .addOnFailureListener { e ->
                Log.e(
                    "Error",
                    "Product registration failed",
                    e
                )
            }

    }

    fun updateProductQuantity(quantityHashMap: HashMap<String, Any>, productID: String) {

        Log.i("Firestore", "updateProductQuantity")
        //send an update request to update user details
        myFireStore.collection(Constants.PRODUCTS)
            .document(productID)
            .update(quantityHashMap)
    }

    fun updateCartQuantity(quantityHashMap: HashMap<String, Any>, cartItemID: String) {

        //send an update request to update user details
        myFireStore.collection(Constants.CARTS)
            .document(cartItemID)
            .update(quantityHashMap)
    }

    fun removeProductFromCart(context: Context, cartItem: CartItem, userID: String) {

        myFireStore.collection(Constants.CARTS)
            .document(cartItem.cartItemID + userID)
            .get()
            .addOnSuccessListener { document ->

                cartItem.cartItemQuantity = document.get(Constants.CART_ITEM_QUANTITY).toString()

                if (cartItem.cartItemQuantity <= "1") {
                    myFireStore.collection(Constants.CARTS)
                        .document(cartItem.cartItemID + userID)
                        .delete()
                } else {
                    val cartItemQuantity = HashMap<String, Any>()

                    cartItem.cartItemQuantity = (cartItem.cartItemQuantity.toInt() - 1).toString()
                    cartItemQuantity[Constants.CART_ITEM_QUANTITY] = cartItem.cartItemQuantity

                    updateCartQuantity(cartItemQuantity, cartItem.cartItemID + userID)
                }
            }
            .addOnFailureListener {
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }

    }

    fun generateOrder(order: ArrayList<Order>, activity: Activity): String {

        val STRING_LENGTH = 10;
        val ALPHANUMERIC_REGEX = "[a-zA-Z0-9]+";
        val orderID = (1..STRING_LENGTH)
            .map { i -> kotlin.random.Random.nextInt(0, STRING_LENGTH) }
            .map(ALPHANUMERIC_REGEX::get)
            .joinToString("")

        for (i in order) {

            i.orderID = orderID

            myFireStore.collection(Constants.ORDERS)
                .document(i.orderID + i.orderItemID + i.userID)
                .set(i, SetOptions.merge())
                .addOnSuccessListener {
                    myFireStore.collection(Constants.CARTS)
                        .document(i.orderItemID + i.userID)
                        .delete()
                }
        }

        return orderID
    }

}
