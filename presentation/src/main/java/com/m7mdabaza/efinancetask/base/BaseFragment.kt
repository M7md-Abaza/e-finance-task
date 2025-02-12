package com.m7mdabaza.efinancetask.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.m7mdabaza.efinancetask.utils.NetworkState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

@AndroidEntryPoint
open class BaseFragment : Fragment() {

    val navController: NavController by lazy {
        findNavController()
    }

    val mFragmentManager: FragmentManager by lazy {
        parentFragmentManager
    }

    val mContext: Context by lazy {
        requireContext()
    }

    val mActivity: FragmentActivity by lazy {
        requireActivity()
    }

    val mView: View by lazy {
        requireView()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*val window: Window = mActivity.window
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = mContext.getColor(R.color.purple_500)
*/
    }

    fun helperDialog(
        msg: String?,
        isSingle: Boolean,
        cancelText: String?,
        confirmText: String?,
        icon: Int?,
        cancelListener: (() -> Unit)? = null,
        confirmListener: (() -> Unit)? = null
    ) {
        val dialog = HelperDialog(msg, isSingle, cancelText, confirmText, icon)
        dialog.setOnCancelClickListener {
            dialog.mDialogsListener?.onDismiss()
            if (cancelListener != null) {
                cancelListener()
            }
        }
        dialog.setOnConfirmClickListener {
            dialog.mDialogsListener?.onDismiss()
            if (confirmListener != null) {
                confirmListener()
            }
        }
        dialog.show(mFragmentManager, null)
    }


    companion object {
        private const val TAG = "BaseFragment"
    }

}