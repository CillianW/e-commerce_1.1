package com.example.e_commerce_11.activities

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */


import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.e_commerce_11.R
import com.example.e_commerce_11.firestore.FireStoreClass
import com.example.e_commerce_11.models.Product
import com.example.e_commerce_11.utilities.Constants
import kotlinx.android.synthetic.main.activity_add_product.*
import kotlinx.android.synthetic.main.activity_register.*
import java.io.IOException

private var productImgURI: Uri? = null
private var productDetails: Product? = null


class AddProductActivity : BaseActivity(), View.OnClickListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_product)

        product_img.setOnClickListener(this)
        btn_save_product.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        if (v != null) {

            when (v.id) {

                R.id.product_img -> {
                    //on pressing the product_img button, check if permission to access camera is granted.
                    //if not, request permission
                    if (ContextCompat.checkSelfPermission(
                            this,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE
                        ) == PackageManager.PERMISSION_GRANTED
                    ) {
                        Constants.imageSelector(this)
                    } else {
                        ActivityCompat.requestPermissions(
                            this,
                            arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.GALLERY_PERMISSION_CODE
                        )
                    }


                }
                R.id.btn_save_product -> {

                    displayProgressDialogue(resources.getString(R.string.please_wait))

                    val id = et_change_product_name.text.toString() + System.currentTimeMillis()

                    productDetails = Product(et_change_product_name.text.toString(), et_change_product_description.text.toString(),
                        "image url",
                        et_price.text.toString(), et_change_quantity.text.toString())

                    //pass the new user object to the FireStore
                    FireStoreClass().createProduct(this@AddProductActivity, productDetails!!)

                    Handler(Looper.getMainLooper()).postDelayed(
                        {
                            finish()
                        },
                        1000
                    )
                }
                }
            }
        }


    //process the result of the permission request
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        //if the returned request code matches our own request code and the user has granted the permission,
        //run the phone's image selector function
        //else, display an error message
        if (requestCode == Constants.GALLERY_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Constants.imageSelector(this)
            } else {
                Toast.makeText(
                    this,
                    "Gallery permission denied. This can be changed in settings.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    }

    //checks that the selected image from the Constants.imageSelector function has been selected successfully,
    //if so, assign it to product_img
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == Constants.IMAGE_REQUEST_CODE) {
                if (data != null) {
                    try {
                        productImgURI = data.data!!

                        //load the image into the img_profile_pic using the URI
                        //img_profile_pic.setImageURI(Uri.parse(profilePictureURI.toString()))

                        //alternatively, we can use the Glide class to perform this action
                        //The Glide class is useful because it accepts various file types as an argument
                        Glide.with(this).load(productImgURI)
                            .placeholder(R.drawable.empty_product).into(product_img)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        Toast.makeText(this, "Cannot use this image", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    //displays a success message to the user upon successful registration
    fun productRegisteredSuccessfully(){

        dismissProgressDialogue()

        Toast.makeText(this@AddProductActivity,
            resources.getString(R.string.user_registered_successfully),
            Toast.LENGTH_SHORT).show()
    }



}
