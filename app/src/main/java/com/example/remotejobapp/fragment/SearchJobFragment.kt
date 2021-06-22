package com.example.remotejobapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remotejobapp.MainActivity
import com.example.remotejobapp.R
import com.example.remotejobapp.adapter.RemoteJobAdapter
import com.example.remotejobapp.databinding.FragmentSearchJobBinding
import com.example.remotejobapp.utils.Constants
import com.example.remotejobapp.viewmodel.RemoteJobViewModel
import kotlinx.coroutines.*

class SearchJobFragment : Fragment(R.layout.fragment_search_job) {

    private var _binding: FragmentSearchJobBinding?= null
    private val binding get() = _binding!!
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var remoteJobAdapter: RemoteJobAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchJobBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = (activity as MainActivity).viewModel

        if(Constants.checkInternetConnection(requireContext())) {
            searchJob()
            setupRecyclerView()
        } else {
            Toast.makeText(activity, "No Internet Connection", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView() {
        remoteJobAdapter = RemoteJobAdapter()
        binding.rvSearchJobs.apply{
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            adapter = remoteJobAdapter
        }

        viewModel.getSearchRemoteJobResult().observe(viewLifecycleOwner, { remoteJob ->
            if(remoteJob != null) {
                remoteJobAdapter.differ.submitList(remoteJob.jobs)
            }
        })
    }

    private fun searchJob() {
        var job: Job?= null
        binding.etSearch.addTextChangedListener{text ->
            job?.cancel()
            job = MainScope().launch {
                delay(500L)
                text?.let{
                    if(text.toString().isNotEmpty()) {
                        viewModel.searchRemoteJob(text.toString())
                    }
                }
            }
        }
    }
}