package com.example.a2024solutionchallenge.bottom_navigation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2024solutionchallenge.R
import com.example.a2024solutionchallenge.adapter.CommunityAdapter
import com.example.a2024solutionchallenge.adapter.QuestAdapter
import com.example.a2024solutionchallenge.data.QuestData
import com.example.a2024solutionchallenge.databinding.FragmentQuestBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [QuestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class QuestFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentQuestBinding
    private lateinit var adapter : QuestAdapter
    private var questItemList = ArrayList<QuestData>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQuestBinding.inflate(inflater, container, false)

        initRecyclerView()

        return binding.root
    }

    private fun setData() {
        /*itemList.add(practiceData("a", false))
        itemList.add(practiceData("b", false))
        itemList.add(practiceData("c", false))*/
    }


    private fun initRecyclerView() {
        setData()
        adapter = QuestAdapter()
        adapter.itemData = questItemList
        binding.questRv.adapter = adapter
        binding.questRv.setHasFixedSize(true)
        binding.questRv.layoutManager = LinearLayoutManager(requireContext())
        /*mBinding.chattingRv.adapter = adapter
         mBinding.chattingRv.setHasFixedSize(true)
         mBinding.chattingRv.layoutManager = manager*/
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment QuestFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            QuestFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}