package comcesar1287.github.githubapp.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import comcesar1287.github.githubapp.R
import comcesar1287.github.githubapp.models.User
import comcesar1287.github.githubapp.utils.GlideApp
import comcesar1287.github.githubapp.utils.KEY_EXTRA_AVATAR
import comcesar1287.github.githubapp.utils.KEY_EXTRA_LOGIN
import comcesar1287.github.githubapp.views.UserDetailsActivity
import comcesar1287.github.githubapp.views.UserReposActivity
import kotlinx.android.synthetic.main.item_user.view.*

class UsersAdapter(private var context: Context, private var list: List<User>) : RecyclerView.Adapter<UsersAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, list[position])
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(context: Context, user: User) = with(itemView) {
            itemView.login.text = user.login
            itemView.type.text = user.type

            GlideApp
                .with(context)
                .load(user.avatarUrl)
                .into(itemView.avatar)

            val intentDetails = Intent(context, UserDetailsActivity::class.java)
            intentDetails.putExtra(KEY_EXTRA_LOGIN, user.login)
            intentDetails.putExtra(KEY_EXTRA_AVATAR, user.avatarUrl)

            val intentRepos = Intent(context, UserReposActivity::class.java)
            intentRepos.putExtra(KEY_EXTRA_LOGIN, user.login)

            itemView.userLayout.setOnClickListener {
                context.startActivity(intentDetails)
            }

            itemView.buttonDetails.setOnClickListener {
                context.startActivity(intentDetails)
            }

            itemView.buttonRepos.setOnClickListener {
                context.startActivity(intentRepos)
            }
        }
    }
}