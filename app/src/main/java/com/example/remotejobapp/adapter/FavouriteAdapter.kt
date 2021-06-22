package com.example.remotejobapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.remotejobapp.databinding.JobLayoutAdapterBinding
import com.example.remotejobapp.fragment.MainFragmentDirections
import com.example.remotejobapp.model.FavouriteJob
import com.example.remotejobapp.model.Job

class FavouriteAdapter constructor(private val itemClick: OnItemClickListener):
    RecyclerView.Adapter<FavouriteAdapter.FavouriteViewHolder>() {

    private var binding: JobLayoutAdapterBinding?= null

    inner class FavouriteViewHolder(itemBinding: JobLayoutAdapterBinding):
            RecyclerView.ViewHolder(itemBinding.root)

    private val differCallBack = object: DiffUtil.ItemCallback<FavouriteJob>() {
        override fun areItemsTheSame(oldItem: FavouriteJob, newItem: FavouriteJob): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: FavouriteJob, newItem: FavouriteJob): Boolean {
            return oldItem == newItem
        }
    }

    val differ = AsyncListDiffer(this, differCallBack)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouriteViewHolder {
        binding = JobLayoutAdapterBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return FavouriteViewHolder(binding!!)
    }

    override fun onBindViewHolder(holder: FavouriteViewHolder, position: Int) {
        val favouriteJob = differ.currentList[position]

        holder.itemView.apply {
            Glide.with(context)
                .load(favouriteJob.companyLogoUrl)
                .into(binding?.ivCompanyLogo!!)

            binding?.tvCompanyName!!.text = favouriteJob.companyName
            binding?.tvJobLocation!!.text = favouriteJob.candidateRequiredLocation
            binding?.tvJobTitle!!.text = favouriteJob.title
            binding?.tvJobType!!.text = favouriteJob.jobType
            val dateJob = favouriteJob.publicationDate?.split("T")
            binding?.tvDate!!.text = dateJob?.get(0)
            binding?.ibDelete!!.visibility = View.VISIBLE
        }.setOnClickListener { mView ->
            val tags = arrayListOf<String>()
            val job = Job(
                favouriteJob.candidateRequiredLocation, favouriteJob.category,
                favouriteJob.companyLogoUrl, favouriteJob.companyName, favouriteJob.description,
                favouriteJob.id, favouriteJob.jobType, favouriteJob.publicationDate,
                favouriteJob.salary, tags, favouriteJob.title, favouriteJob.url,
            )
            val direction = MainFragmentDirections.actionMainFragmentToJobDetailFragment(job)
            mView.findNavController().navigate(direction)
        }

        holder.itemView.apply{
            binding?.ibDelete!!.setOnClickListener {
                itemClick.onItemClick(favouriteJob, binding?.ibDelete!!, position)
            }
        }
    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    interface OnItemClickListener{
        fun onItemClick(job: FavouriteJob, view: View, position: Int)
    }
}