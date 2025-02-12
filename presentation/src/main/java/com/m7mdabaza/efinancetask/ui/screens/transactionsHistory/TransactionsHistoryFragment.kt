package com.m7mdabaza.efinancetask.ui.screens.transactionsHistory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.m7mdabaza.domain.entities.transactionHistory.TransactionsHistory
import com.m7mdabaza.efinancetask.R
import com.m7mdabaza.efinancetask.base.BaseFragment
import com.m7mdabaza.efinancetask.databinding.FragmentTransactionsHistoryBinding
import com.m7mdabaza.efinancetask.ui.screens.MainActivity
import com.m7mdabaza.efinancetask.ui.viewmodels.TransactionsViewModel
import com.m7mdabaza.efinancetask.utils.Utils.showToast
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TransactionsHistoryFragment : BaseFragment() {

    private lateinit var binding: FragmentTransactionsHistoryBinding
    private val viewModel: TransactionsViewModel by viewModels()

    @Inject
    lateinit var transactionsHistoryAdapter: TransactionsHistoryAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTransactionsHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        data()
        setup()
        observe()
    }

    private fun data() {
        visProgress(true)
        viewModel.getAllTransactions()
    }

    private fun setup() {
        binding.transactionsHistoryRecycler.adapter = transactionsHistoryAdapter

        transactionsHistoryAdapter.setDeleteClickListener {
            visProgress(true)
            viewModel.deleteTransaction(it)
        }
    }


    private fun observe() {
        viewModel.transactionsList.observe(viewLifecycleOwner) {
            visProgress(false)
            if (it.isNotEmpty())
                handleRecyclerAdapter(it)
            else
                showEmptyList()
        }
        viewModel.deleteTransaction.observe(viewLifecycleOwner) {
            visProgress(false)
            if (it != null)
                transactionsHistoryAdapter.deleteItem(it)
            else
                showToast(mContext, getString(R.string.failed_to_delete_transaction))
        }
    }

    private fun handleRecyclerAdapter(it: List<TransactionsHistory>) {
        binding.transactionsHistoryRecycler.visibility = View.VISIBLE
        binding.tvEmptyText.visibility = View.GONE
        transactionsHistoryAdapter.submitList(it.reversed())
    }

    private fun showEmptyList() {
        binding.transactionsHistoryRecycler.visibility = View.GONE
        binding.tvEmptyText.visibility = View.VISIBLE
    }

    private fun visProgress(s: Boolean) {
        (requireActivity() as MainActivity).showProgress(s)
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as MainActivity).hideBackButton(false)
        (requireActivity() as MainActivity).setToolbarTitle(getString(R.string.transactions_history))

    }
}