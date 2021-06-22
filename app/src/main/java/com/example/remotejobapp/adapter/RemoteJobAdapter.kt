package com.example.remotejobapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.remotejobapp.databinding.JobLayoutAdapterBinding
import com.example.remotejobapp.fragment.MainFragmentDirections
import com.example.remotejobapp.model.Job

class RemoteJobAdapter: RecyclerView.Adapter<RemoteJobAdapter.RemoteJobViewHolder>() {

    private var binding: JobLayoutAdapterBinding?= null

    inner class RemoteJobViewHolder(itemBinding: JobLayoutAdapterBinding):
        RecyclerView.ViewHolder(itemBinding.root)

    private val differCallBack = object: DiffUtil.ItemCallback<Job>() {
        override fun areItemsTheSame(oldItem: Job, newItem: Job): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Job, newItem: Job): Boolean {
           return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RemoteJobViewHolder {
        binding = JobLayoutAdapterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return RemoteJobViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: RemoteJobViewHolder, position: Int) {
        val currentJob = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(context)
                .load(currentJob.companyLogoUrl)
                .into(binding?.ivCompanyLogo!!)

            binding?.tvCompanyName!!.text = currentJob.companyName
            binding?.tvJobLocation!!.text = currentJob.candidateRequiredLocation
            binding?.tvJobTitle!!.text = currentJob.title
            binding?.tvJobType!!.text = currentJob.jobType
            val dateJob = currentJob.publicationDate?.split("T")
            binding?.tvDate!!.text = dateJob?.get(0)

        }.setOnClickListener { mView ->
            val direction = MainFragmentDirections.actionMainFragmentToJobDetailFragment(currentJob)
            mView.findNavController().navigate(direction)
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }
}