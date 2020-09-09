package enxy.githubapitest.ui.details

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import enxy.githubapitest.R
import enxy.githubapitest.data.network.entity.details.Parent
import kotlinx.android.synthetic.main.item_commit_parent.view.*

class ParentAdapter : RecyclerView.Adapter<ParentAdapter.ParentHolder>() {
    private val data: ArrayList<Parent> = ArrayList()

    fun addAll(parents: List<Parent>) {
        data.addAll(parents)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_commit_parent, parent, false
        )
        return ParentHolder(view)
    }

    override fun onBindViewHolder(holder: ParentHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ParentHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(details: Parent) {
            itemView.sha.text = details.sha
        }
    }
}