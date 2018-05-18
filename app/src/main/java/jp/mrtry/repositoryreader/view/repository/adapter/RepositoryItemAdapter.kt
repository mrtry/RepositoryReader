package jp.mrtry.repositoryreader.view.repository.adapter

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import jp.mrtry.repositoryreader.R
import jp.mrtry.repositoryreader.databinding.ListRowBinding
import jp.mrtry.repositoryreader.entity.Repository


/**
 * Created by mrtry on 2018/05/15.
 */
class RepositoryItemAdapter: RecyclerView.Adapter<RepositoryItemAdapter.ItemViewHolder>(){
    var repositories: List<Repository> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = DataBindingUtil.inflate<ListRowBinding>(LayoutInflater.from(parent.context), R.layout.list_row, parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.binding.repository = repositories[position]
        holder.binding.executePendingBindings()
    }

    override fun getItemCount(): Int {
        return repositories.size
    }

    class ItemViewHolder(val binding: ListRowBinding) : RecyclerView.ViewHolder(binding.root)
}