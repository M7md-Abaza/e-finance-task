package com.m7mdabaza.efinancetask.base

import android.content.Context
import androidx.fragment.app.DialogFragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController

open class BaseDialogFragment : DialogFragment() {

    /*val mFragmentManager: FragmentManager by lazy {
        parentFragmentManager
    }
*/
    val navController: NavController by lazy {
        findNavController()
    }

    val mContext: Context by lazy {
        requireContext()
    }

}