package com.m7mdabaza.efinancetask.ui.screens.confirmPayment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.m7mdabaza.domain.entities.transactionHistory.TransactionsHistory
import com.m7mdabaza.efinancetask.R
import com.m7mdabaza.efinancetask.base.BaseFragment
import com.m7mdabaza.efinancetask.base.HelperDialog
import com.m7mdabaza.efinancetask.databinding.FragmentConfirmPaymentBinding
import com.m7mdabaza.efinancetask.ui.screens.MainActivity
import com.m7mdabaza.efinancetask.ui.viewmodels.PaymentViewModel
import com.m7mdabaza.efinancetask.utils.Constants
import com.m7mdabaza.efinancetask.utils.NetworkState
import com.m7mdabaza.efinancetask.utils.Utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ConfirmPaymentFragment : BaseFragment() {

    private lateinit var binding: FragmentConfirmPaymentBinding
    private val viewModel: PaymentViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentConfirmPaymentBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (mActivity as MainActivity).hideBackButton(true)
        (mActivity as MainActivity).setToolbarTitle(getString(R.string.payment))

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        observe()

    }

    private fun init() {


        binding.btnConfirm.setOnClickListener {
            val amount = binding.etAmount.text.toString()
            if (amount.isNotEmpty() && amount.toDouble() > 0) {
                showConfirmationDialog()
            } else {
                binding.textInputLayout.isErrorEnabled = true
                binding.textInputLayout.error =
                    getString(R.string.please_enter_a_valid_enter_amount)
            }
        }

    }


    private fun showConfirmationDialog() {
        binding.textInputLayout.isErrorEnabled = false
        val dialog =
            HelperDialog(
                getString(
                    R.string.are_you_sure_you_want_to_pay_l_e,
                    binding.etAmount.text.toString()
                )
            )
        dialog.setOnConfirmClickListener { confirmPayment() }
        dialog.show(mFragmentManager, null)
    }

    private fun confirmPayment() {
        viewModel.confirmPayment()
    }

    private fun observe() {
        lifecycleScope.launch {
            viewModel.confirmPayment.observe(viewLifecycleOwner) {
                when (it) {
                    is NetworkState.Idle -> {
                        visProgress(false)
                    }

                    is NetworkState.Loading -> {
                        visProgress(true)
                    }

                    is NetworkState.Error -> {
                        visProgress(false)
                        handleSuccess()
                    }

                    is NetworkState.Result<*> -> {
                        visProgress(false)
                        handleSuccess()
                    }
                }

            }

            viewModel.insertTransaction.observe(viewLifecycleOwner) {
                if (it) {
                    showSuccessDialog()
                }
            }
        }
    }

    private fun handleSuccess() {
        viewModel.insertTransaction(
            TransactionsHistory(
                amount = binding.etAmount.text.toString().toDouble(),
                timestamp = System.currentTimeMillis()
            )
        )
    }

    private fun showSuccessDialog() {
        binding.etAmount.text?.clear()
        helperDialog(
            getString(R.string.success),
            false,
            getString(R.string.history),
            getString(R.string.ok),
            R.drawable.ic_success,
            {
                navController.navigate(R.id.transactionsHistoryFragment)
            }
        )
    }

    private fun visProgress(s: Boolean) {
        (mActivity as MainActivity).showProgress(s)
    }

}