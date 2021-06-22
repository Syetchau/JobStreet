package com.example.remotejobapp.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.remotejobapp.MainActivity
import com.example.remotejobapp.R
import com.example.remotejobapp.adapter.FavouriteAdapter
import com.example.remotejobapp.databinding.FragmentSavedJobBinding
import com.example.remotejobapp.model.FavouriteJob
import com.example.remotejobapp.viewmodel.RemoteJobViewModel

class SavedJobFragment : Fragment(R.layout.fragment_saved_job),
    FavouriteAdapter.OnItemClickListener {

    private var _binding: FragmentSavedJobBinding?= null
    private val binding get() = _binding!!
    private lateinit var viewModel: RemoteJobViewModel
    private lateinit var favouriteAdapter: FavouriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSavedJobBinding.inflate(inflater, container, false)
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

    private fun setupRecyclerView() {
        favouriteAdapter = FavouriteAdapter(this)

        binding.rvJobsSaved.apply {
            layoutManager = LinearLayoutManager(activity)
            setHasFixedSize(true)
            addItemDecoration(object: DividerItemDecoration(
                activity, LinearLayout.VERTICAL){})
            adapter = favouriteAdapter
        }

        viewModel.getAllFavouriteJob().observe(viewLifecycleOwner, { favJob ->
            favouriteAdapter.differ.submitList(favJob)
            updateUI(favJob)
        })
    }

    private fun updateUI(favJob: List<FavouriteJob>) {
        if (favJob.isNotEmpty()) {
            binding.rvJobsSaved.visibility = View.VISIBLE
            binding.cardNoAvailable.visibility = View.GONE
        } else {
            binding.rvJobsSaved.visibility = View.GONE
            binding.cardNoAvailable.visibility = View.VISIBLE
        }
    }

    override fun onItemClick(job: FavouriteJob, view: View, position: Int) {
        deleteFavouriteJob(job)
    }

    private fun deleteFavouriteJob(job: FavouriteJob) {
        AlertDialog.Builder(activity).apply{
            setTitle("Delete Job")
            setMessage("Are you sure you want to delete this job?")
            setPositiveButton("DELETE") {_,_ ->
                viewModel.deleteFavouriteJob(job)
                Toast.makeText(activity, "Job Deleted", Toast.LENGTH_SHORT).show()
            }
            setNegativeButton("CANCEL", null)
        }.create().show()
    }
}