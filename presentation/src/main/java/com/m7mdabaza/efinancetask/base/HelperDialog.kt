package com.m7mdabaza.efinancetask.base

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import com.m7mdabaza.efinancetask.R
import com.m7mdabaza.efinancetask.databinding.TwoChoicesDialogBinding
import com.m7mdabaza.efinancetask.utils.DialogsListener

class HelperDialog(
    private val msg: String? = null,
    private val isSingleChoice: Boolean = false,
    private val cancelText: String? = null,
    private val confirm: String? = null,
    private val icon: Int? = null,
    val mDialogsListener: DialogsListener? = null
) : DialogFragment() {

    private lateinit var binding: TwoChoicesDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.two_choices_dialog,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUp()

        if (!msg.isNullOrEmpty())
            binding.tvMsg.text = msg

        binding.tvCancel.setOnClickListener {
            mDialogsListener?.onDismiss()
            onCancelClickListener?.let { it1 -> it1() }
            dismiss()
        }

        binding.tvConfirm.setOnClickListener {
            mDialogsListener?.onDismiss()
            onConfirmClickListener?.let { it1 -> it1() }
            dismiss()
        }

        if (icon != null)
            binding.img.setImageDrawable(ResourcesCompat.getDrawable(resources, icon, null))

    }

    private fun setUp() {

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        isCancelable = false

        dialog?.window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        if (isSingleChoice) {
            binding.tvCancel.visibility = View.GONE
            binding.tvConfirm.text = "Ok"
            binding.tvConfirm.width = LinearLayout.LayoutParams.WRAP_CONTENT
        }

        if (!cancelText.isNullOrEmpty()) {
            binding.tvCancel.text = cancelText
        }
        if (!confirm.isNullOrEmpty()) {
            binding.tvConfirm.text = confirm
        }

    }

    private var onCancelClickListener: (() -> Unit)? = null
    private var onConfirmClickListener: (() -> Unit)? = null

    fun setOnCancelClickListener(listener: () -> Unit) {
        mDialogsListener?.onDismiss()
        onCancelClickListener = listener
    }

    fun setOnConfirmClickListener(listener: () -> Unit) {
        mDialogsListener?.onDismiss()
        onConfirmClickListener = listener
    }


}