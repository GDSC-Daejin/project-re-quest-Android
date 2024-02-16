package com.example.a2024solutionchallenge.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.a2024solutionchallenge.data.AchievementData
import com.example.a2024solutionchallenge.data.QuestData
import com.example.a2024solutionchallenge.databinding.ItemAchievementBinding
import com.example.a2024solutionchallenge.databinding.ItemQuestBinding


class AchievementAdapter : RecyclerView.Adapter<AchievementAdapter.ItemViewHolder>(){
    private lateinit var binding : ItemAchievementBinding
    var itemData = ArrayList<AchievementData>()
    private lateinit var context : Context
    private var selectedItem = 0

    inner class ItemViewHolder(private var binding : ItemAchievementBinding ) : RecyclerView.ViewHolder(binding.root) {
        private var position: Int? = null
        var achievementExp = binding.achievementCompensationTv
        var achievementContent = binding.achievementContent
        var achievementCompleteBtn = binding.achievementCompleteBtn
        /*var hcUserId = binding.hcUserId*/

        fun bind(itemData: AchievementData, position: Int) {
            this.position = position
            achievementExp.text = itemData.exp.toString()
            achievementContent.text = itemData.achievementContent

            binding.achievementCompleteBtn.setOnClickListener {
                if (itemData.complete == true) {
                    //exp 추가 이벤트
                } else {
                    Toast.makeText(context, "Achievement requirements have not been completed.", Toast.LENGTH_SHORT).show()
                }
            }

            /*binding.root.setOnClickListener {
                //자세히 보기 페이지 이동? Todo
            }*/
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        binding = ItemAchievementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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