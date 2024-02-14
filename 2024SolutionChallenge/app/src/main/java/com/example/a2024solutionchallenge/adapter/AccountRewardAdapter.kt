package com.example.a2024solutionchallenge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.a2024solutionchallenge.R
import com.example.a2024solutionchallenge.data.AccountRewardData
import com.example.a2024solutionchallenge.data.AchievementData
import com.example.a2024solutionchallenge.data.QuestData
import com.example.a2024solutionchallenge.databinding.ItemAchievementBinding
import com.example.a2024solutionchallenge.databinding.ItemLockRewardBinding
import com.example.a2024solutionchallenge.databinding.ItemQuestBinding


class AccountRewardAdapter : RecyclerView.Adapter<AccountRewardAdapter.ItemViewHolder>(){
    private lateinit var binding : ItemLockRewardBinding
    var itemData = ArrayList<AccountRewardData>()
    private lateinit var context : Context
    private var selectedItem = 0

    inner class ItemViewHolder(private var binding : ItemLockRewardBinding ) : RecyclerView.ViewHolder(binding.root) {
        private var position: Int? = null
        var accountUnlockBtn = binding.unlockBtn
        var accountCharacter = binding.lockRewardCharacter
        var accountLockIv = binding.lockLockIv
        /*var hcUserId = binding.hcUserId*/

        fun bind(itemData: AccountRewardData, position: Int) {
            this.position = position

            //리워드 캐릭터 사진 설정 다시 생각하기 TOdo
            if (itemData.rewardImage != null) {
                Glide.with(context)
                    .load(itemData.rewardImage)
                    .into(accountCharacter)
            } else {
                Glide.with(context)
                    .load(R.drawable.baseline_image_24)
                    .into(accountCharacter)
            }


            binding.unlockBtn.setOnClickListener {
                if (itemData.unlock == true) {
                    //exp 추가 이벤트
                    //캐릭터잠금해제
                } else {
                    Toast.makeText(context, "업적 조건이 완료되지 않았습니다.", Toast.LENGTH_SHORT).show()
                }
            }

            /*binding.root.setOnClickListener {
                //자세히 보기 페이지 이동? Todo
            }*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        binding = ItemLockRewardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.bind(itemData[position], position)
    }

    override fun getItemCount(): Int {
        return itemData.size
    }


    interface ItemClickListener {
        fun onClick(view: View, position: Int, itemId: String)
    }
    private lateinit var itemClickListener: ItemClickListener

    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListener = itemClickListener
    }

    /*private fun setMultipleSelection(binding: ItemLayoutBinding, s: String?, adapterPosition : Int) {
        if(itemData[adapterPosition].isSelected){
            itemData[adapterPosition].isSelected = false
            changeBackground(binding, adapterPosition)
        }else{
            itemData[adapterPosition].isSelected = true
            changeBackground(binding, adapterPosition)
        }
        Log.e("arrayList", itemData.toString());
        notifyDataSetChanged()
    }

    private fun changeBackground(binding: ItemLayoutBinding, position: Int) {
        if(itemData[position].isSelected){
            val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(context , R.color.purple_200)) //승인
            binding.itemLl.backgroundTintList = colorStateList
        }else{
            val colorStateList = ColorStateList.valueOf(ContextCompat.getColor(context , R.color.black)) //승인
            binding.itemLl.backgroundTintList = colorStateList
        }
    }*/

}