package comcesar1287.github.githubapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import comcesar1287.github.githubapp.R
import comcesar1287.github.githubapp.models.UserRepo
import kotlinx.android.synthetic.main.item_repo.view.*

class ReposAdapter(private var context: Context, private var list: List<UserRepo>) : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_repo, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, list[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(context: Context, userRepo: UserRepo) = with(itemView) {
            row_repo_name.text = userRepo.owner.login + "/" + userRepo.name
            if (userRepo.description != null && userRepo.description != "")
                row_repo_description.text = userRepo.description
            else
                row_repo_description.text = context.getString(R.string.no_description)
            if (userRepo.language == null) {
                row_repo_language.visibility = View.GONE
                row_repo_language_icon.visibility = View.GONE
            }
            else
                row_repo_language.text = userRepo.language
            row_repo_star_number.text = userRepo.watchers.toString()

            row_repo_forked.visibility = View.GONE

//            when (filter["sort"]) {
//                "pushed" -> row_repo_date.text = p.format(repo.pushedAt)
//                "updated" -> row_repo_date.text = p.format(repo.updatedAt)
//                null -> row_repo_date.text = p.format(repo.createdAt)
//                else -> row_repo_date.text = p.format(repo.createdAt)
//            }
        }
    }
}