package com.example.e_commerce_11.activities.ui.products

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.e_commerce_11.R
import com.example.e_commerce_11.activities.*
import com.example.e_commerce_11.activities.ui.base_fragment.BaseFragment
import com.example.e_commerce_11.models.Product
import com.example.e_commerce_11.models.User
import com.example.e_commerce_11.utilities.Constants
import com.example.e_commerce_11.utilities.ItemAdapter
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_products.*
import kotlinx.android.synthetic.main.items_layout.*

class ProductsFragment : BaseFragment() {

    private lateinit var userDetails: User

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        displayProgressDialogue(R.string.please_wait.toString())

        getUserDetails()
        getProducts()

        btn_add_to_cart.setOnClickListener(view?.findViewById(R.id.btn_add_to_cart))

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_products, container, false)

        return view
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.products_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

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

                if (userDetails.admin != 0) {
                    setHasOptionsMenu(true)
                }
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

    private fun getProducts() {

        val products: ArrayList<Product> = ArrayList()

        FirebaseFirestore.getInstance().collection(Constants.PRODUCTS)
            //get request used to retrieve info
            .get()
            .addOnSuccessListener { result ->

                for (x in result) {
                    val item = Product("", "", "", "", "")

                    item.productName = x.getString(Constants.PRODUCT_NAME).toString()
                    item.productDescription = x.getString(Constants.PRODUCT_DESCRIPTION).toString()
                    item.price = x.getString(Constants.PRODUCT_PRICE).toString()
                    item.quantity = x.getString(Constants.PRODUCT_QUANTITY).toString()
                    item.productImgURI = x.getString(Constants.PRODUCT_IMG_URI).toString()

                    products.add(item)

                    // Set the LayoutManager that this RecyclerView will use.
                    rv_Items_List_products.layoutManager = LinearLayoutManager(context)
                    // Adapter class is initialized and list is passed in the param.
                    val itemAdapter = context?.let { ItemAdapter(it, products) }
                    // adapter instance is set to the recyclerview to inflate the items.
                    rv_Items_List_products.adapter = itemAdapter

                    dismissProgressDialogue()
                }
            }
            .addOnFailureListener { e ->
                Log.e(e.toString(), "Error loading products")
                dismissProgressDialogue()
            }
//        return products
    }

}
