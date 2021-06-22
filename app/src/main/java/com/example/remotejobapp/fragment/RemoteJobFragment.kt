package com.example.remotejobapp.fragment

import android.graphics.Color
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.remotejobapp.MainActivity
import com.example.remotejobapp.R
import com.example.remotejobapp.adapter.RemoteJobAdapter
import com.example.remotejobapp.databinding.FragmentRemoteJobBinding
import com.example.remotejobapp.utils.Constants
import com.example.remotejobapp.viewmodel.RemoteJobViewModel

class RemoteJobFragment : Fragment(R.layout.fragment_remote_job), SwipeRefreshLayout.OnRefreshListener {

    private var _binding: FragmentRemoteJobBinding?= null
    private val binding get() = _binding!!
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var remoteJobAdapter: RemoteJobAdapter
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRemoteJobBinding.inflate(inflater, container, false)
        swipeRefreshLayout = binding.swipeCotainer
        swipeRefreshLayout.setOnRefreshListener(this)
        swipeRefreshLayout.setColorSchemeColors(Color.GREEN, Color.RED, Color.BLUE, Color.DKGRAY)
        swipeRefreshLayout.post {
            swipeRefreshLayout.isRefreshing = true
            setupRecyclerView()
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        setupRecyclerView()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


    override fun onRefresh() {
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        remoteJobAdapter = RemoteJobAdapter()

        binding.rvRemoteJob.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object: DividerItemDecoration(activity, LinearLayout.VERTICAL){})
            adapter = remoteJobAdapter
        }

        fetchData()
    }

    private fun fetchData() {
        if(Constants.checkInternetConnection(requireContext())) {
            viewModel.remoteJobResult().observe(viewLifecycleOwner, { remoteJob ->
                if (remoteJob != null) {
                    remoteJobAdapter.differ.submitList(remoteJob.jobs)
                    swipeRefreshLayout.isRefreshing = false
                }
            })
        } else {
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }
}