package com.example.e_commerce_11.activities.ui.base_fragment

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.example.e_commerce_11.R
import kotlinx.android.synthetic.main.fragment_products.*


open class BaseFragment : Fragment() {

    private lateinit var progressDialog: Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false)
    }

    //this function is used to show the progress dialogue box
    fun displayProgressDialogue(text: String){
        progressDialog = Dialog(requireActivity())

        //set the content of the progress dialogue from a resource file
        progressDialog.setContentView(R.layout.dialogue_progress)

        progressDialog.setCancelable(false)
        progressDialog.setCanceledOnTouchOutside(false)

        //show the dialogue
        progressDialog.show()
    }

    fun dismissProgressDialogue(){
        progressDialog.dismiss()
    }

}