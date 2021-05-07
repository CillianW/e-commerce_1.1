package com.example.e_commerce_11.activities.ui.dashboard

/**
 *Author: Cillian Whelan
 *Student Number: L00162026
 *Course: BSc (Hons) Contemporary Software Development
 *Subject: Project
 */

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.e_commerce_11.R
import com.example.e_commerce_11.activities.SettingsActivity

class DashboardFragment : Fragment() {

    //create the activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //set up the options menu at top right of screen
        //this will display the settings icon
        setHasOptionsMenu(true)
    }

    //Inflate the view for the dashboard fragment
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        textView.text =  resources.getString(R.string.title_dashboard)
        return root
    }

    //called after the setOptionsMenu() function is called
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        inflater.inflate(R.menu.dashboard_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    //similar to an onClickListener except it applies to options menus.
    // i.e. the settings icon at the top right of the screen
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when(id) {

            //run the settings activity when the settings icon is pressed
            R.id.action_settings -> {
                startActivity(Intent(activity, SettingsActivity::class.java))

                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}