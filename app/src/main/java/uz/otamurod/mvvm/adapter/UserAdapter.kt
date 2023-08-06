package uz.otamurod.mvvm.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.otamurod.mvvm.R
import uz.otamurod.mvvm.database.entity.UserEntity
import uz.otamurod.mvvm.databinding.ItemUserBinding

class UserAdapter(private val context: Context, private val list: List<UserEntity>) :
    RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    inner class ViewHolder(val itemUserBinding: ItemUserBinding) :
        RecyclerView.ViewHolder(itemUserBinding.root) {
        fun onBind(userEntity: UserEntity) {
            itemUserBinding.userId.text = String.format("User ID: %s", userEntity.id)
            itemUserBinding.login.text = String.format("Login: %s", userEntity.login)

            Glide.with(context)
                .load(userEntity.avatarUrl)
                .placeholder(R.color.black_400)
                .into(itemUserBinding.avatar)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(list[position])
    }
}