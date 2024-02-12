package com.example.a2024solutionchallenge.adapter

import com.bumptech.glide.Glide
import com.example.a2024solutionchallenge.R
import com.example.a2024solutionchallenge.data.PostData
import com.example.a2024solutionchallenge.databinding.ItemHomeCommunityBinding
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView


class CommunityAdapter : RecyclerView.Adapter<CommunityAdapter.ItemViewHolder>(){
    private lateinit var binding : ItemHomeCommunityBinding
    var itemData = ArrayList<PostData>()
    private lateinit var context : Context
    private var selectedItem = 0

    inner class ItemViewHolder(private var binding : ItemHomeCommunityBinding ) : RecyclerView.ViewHolder(binding.root) {
        private var position: Int? = null
        var hcEditBtn = binding.hcEditBtn //수정 버튼
        var hcFavoriteBtn = binding.hcFavoriteBtn //좋아요
        var hcFavoriteTv = binding.hcFavoriteTv
        var hcProfile = binding.hcUserProfile //프로필이미지
        var hcContentIv = binding.hcIv
        var hcContentTv = binding.hcContentTv
        /*var hcUserId = binding.hcUserId*/

        fun bind(itemData: PostData, position: Int) {
            this.position = position
            hcFavoriteTv.text = itemData.postFavoriteCnt?.toString()
            hcContentTv.text = itemData.postContent
            if (itemData.postContentImage != null) {
                Glide.with(context)
                    .load(itemData.postContentImage)
                    .into(hcContentIv)
            } else {
                Glide.with(context)
                    .load(R.drawable.baseline_image_24)
                    .into(hcContentIv)
            }

            binding.hcEditBtn.setOnClickListener {
                //
            }

            binding.hcFavoriteBtn.setOnClickListener {
                //좋아요 카운트 늘리고 추가
                val temp = itemData.postFavoriteCnt?.plus(1)
            }

            binding.root.setOnClickListener {
                //자세히 보기 페이지 이동? Todo
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        context = parent.context
        binding = ItemHomeCommunityBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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