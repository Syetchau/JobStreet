package com.example.remotejobapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebViewClient
import androidx.navigation.fragment.navArgs
import com.example.remotejobapp.MainActivity
import com.example.remotejobapp.R
import com.example.remotejobapp.databinding.FragmentJobDetailBinding
import com.example.remotejobapp.model.FavouriteJob
import com.example.remotejobapp.model.Job
import com.example.remotejobapp.viewmodel.RemoteJobViewModel
import com.google.android.material.snackbar.Snackbar

class JobDetailFragment : Fragment(R.layout.fragment_job_detail) {

    private var _binding: FragmentJobDetailBinding?= null
    private val binding get() =  _binding!!
    private lateinit var currentJob: Job
    private val args: JobDetailFragmentArgs by navArgs()
    private lateinit var viewModel: RemoteJobViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentJobDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = (activity as MainActivity).viewModel
        currentJob = args.job!!
        setupWebView()
        initClickEvent(view)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupWebView() {
        binding.webView.apply{
            webViewClient = WebViewClient()
            currentJob.url?.let {
                loadUrl(it)
            }
        }
        val setting = binding.webView.settings
        setting.javaScriptEnabled = true
        setting.setAppCacheEnabled(true)
        setting.cacheMode = WebSettings.LOAD_DEFAULT
        setting.setSupportZoom(false)
        setting.builtInZoomControls = false
        setting.displayZoomControls = false
        setting.textZoom = 100
        setting.blockNetworkImage = false
        setting.loadsImagesAutomatically = true
    }

    private fun initClickEvent(view: View) {
        binding.fabAddFavorite.setOnClickListener {
            addFavouriteJob(view)
        }
    }

    private fun addFavouriteJob(view: View) {
        val favouriteJob = FavouriteJob(
            0,
            currentJob.candidateRequiredLocation, currentJob.category, currentJob.companyLogoUrl,
            currentJob.companyName, currentJob.description, currentJob.id, currentJob.jobType,
            currentJob.publicationDate, currentJob.salary, currentJob.title, currentJob.url,
        )
        viewModel.addFavouriteJob(favouriteJob)
        Snackbar.make(view, "Job Saved Successfully", Snackbar.LENGTH_LONG).show()
    }
}