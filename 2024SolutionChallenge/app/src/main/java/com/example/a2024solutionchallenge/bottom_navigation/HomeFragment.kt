package com.example.a2024solutionchallenge.bottom_navigation

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.a2024solutionchallenge.R
import com.example.a2024solutionchallenge.adapter.CommunityAdapter
import com.example.a2024solutionchallenge.data.PostData
import com.example.a2024solutionchallenge.databinding.FragmentHomeBinding
import com.example.a2024solutionchallenge.pageforhome.EditActivity

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [HomeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var binding : FragmentHomeBinding
    private lateinit var adapter : CommunityAdapter
    private var itemList : ArrayList<PostData> = ArrayList()

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
    ): View {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        initRecyclerView()

        binding.homeAddBtn.setOnClickListener {
            val intent = Intent(activity, EditActivity::class.java).apply {
                putExtra("type", "ADD")
            }
            requestActivity.launch(intent)
        }


        return binding.root
    }

    private fun setData() {
        /*itemList.add(practiceData("a", false))
        itemList.add(practiceData("b", false))
        itemList.add(practiceData("c", false))*/
    }


    private fun initRecyclerView() {
        setData()
        adapter = CommunityAdapter()
        adapter.itemData = itemList
        binding.fhRv.adapter = adapter
        binding.fhRv.setHasFixedSize(true)
        binding.fhRv.layoutManager = LinearLayoutManager(requireContext())
        /*mBinding.chattingRv.adapter = adapter
         mBinding.chattingRv.setHasFixedSize(true)
         mBinding.chattingRv.layoutManager = manager*/
    }

    private val requestActivity = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { it ->
        when (it.resultCode) {
            AppCompatActivity.RESULT_OK -> {
                val postData = it.data?.getSerializableExtra("data") as PostData

                val flag = it.data?.getIntExtra("flag", -1)
                Log.d("TAG", "flag: $flag")

                when(flag) {
                    //add
                    0 -> {
                        setData()
                    }
                    1 -> {

                    }
                    4 -> {
                    }

                    //finish()
                }
            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment HomeFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}