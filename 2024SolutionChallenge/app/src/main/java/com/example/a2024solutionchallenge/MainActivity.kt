package com.example.a2024solutionchallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.a2024solutionchallenge.bottom_navigation.AccountFragment
import com.example.a2024solutionchallenge.bottom_navigation.AchievementsFragment
import com.example.a2024solutionchallenge.bottom_navigation.HomeFragment
import com.example.a2024solutionchallenge.bottom_navigation.QuestFragment
import com.example.a2024solutionchallenge.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val mBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private val TAG_HOME = "home_fragment" //커뮤니티
    private val TAG_QUEST = "quest_fragment" //퀘스트
    private val TAG_ACHIEVEMENTS = "achievements_fragment"
    private val TAG_ACCOUNT = "account_fragment"

    private var backPressedTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding.root)
        this.onBackPressedDispatcher.addCallback(this, callback)
        /*oldFragment = HomeFragment()
        oldTAG = TAG_HOME*/
        setFragment(TAG_HOME, HomeFragment())
        //setToolbarView(toolbarType)
        initNavigationBar()
    }

    private fun initNavigationBar() {
        mBinding.bottomNavigationView.
        setOnItemSelectedListener {item ->
            when(item.itemId) {
                R.id.navigation_home -> {
                    //setToolbarView(TAG_HOME, oldTAG)
                    setFragment(TAG_HOME, HomeFragment())

                }

                R.id.navigation_quest -> {
                    setFragment(TAG_QUEST, QuestFragment())
                }

                R.id.navigation_achievement -> {

                    //setToolbarView(TAG_HOME, oldTAG)
                    setFragment(TAG_ACHIEVEMENTS, AchievementsFragment())


                }

                R.id.navigation_account -> {
                    setFragment(TAG_ACCOUNT, AccountFragment())
                }

                else -> {
                    setFragment(TAG_HOME, HomeFragment())
                }

            }
            true
        }


    }

    private fun setFragment(tag : String, fragment: Fragment) {
        val manager : FragmentManager = supportFragmentManager
        val bt = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null) {
            bt.add(R.id.fragment_content, fragment, tag).addToBackStack(null)
        }

        val home = manager.findFragmentByTag(TAG_HOME)
        val quest = manager.findFragmentByTag(TAG_QUEST)
        val account = manager.findFragmentByTag(TAG_ACCOUNT)
        val achievements = manager.findFragmentByTag(TAG_ACHIEVEMENTS)

        if (home != null) {
            bt.hide(home)
        }
        if (quest != null) {
            bt.hide(quest)
        }
        if (account != null) {
            bt.hide(account)
        }
        if (achievements != null) {
            bt.hide(achievements)
        }

        if (tag == TAG_HOME) {
            if (home != null) {
                bt.show(home)
            }
        }
        else if (tag == TAG_QUEST) {
            if (quest != null) {
                bt.show(quest)
            }
        }
        else if (tag == TAG_ACHIEVEMENTS) {
            if (achievements != null) {
                bt.show(achievements)
            }
        }
        else if (tag == TAG_ACCOUNT) {
            if (account != null) {
                bt.show(account)
            }
        }

        bt.commitAllowingStateLoss()
    }

    private val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            // 뒤로가기 클릭 시 실행시킬 코드 입력
            val transaction = supportFragmentManager.beginTransaction()
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_content)
            /*oldFragment = null
            oldTAG = ""*/

            val fragmentManager = supportFragmentManager

            if (System.currentTimeMillis() > backPressedTime + 2000) {
                backPressedTime = System.currentTimeMillis()
                Toast.makeText(this@MainActivity, "\n" + "Press the back button one more time to exit.", Toast.LENGTH_SHORT).show()
                return
            }

            if (System.currentTimeMillis() <= backPressedTime + 2000) {
                if (fragmentManager.backStackEntryCount > 0) {
                    fragmentManager.popBackStack()

                } else {
                    this@MainActivity.finishAffinity()
                }

                if (currentFragment != null) {
                    transaction.remove(currentFragment)

                    transaction.commit()
                }

                this@MainActivity.finishAffinity()
            }
        }
    }
}