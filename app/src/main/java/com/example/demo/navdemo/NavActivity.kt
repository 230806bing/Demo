package com.example.demo.navdemo

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.demo.R
import com.example.demo.databinding.ActivityNavBinding
import com.next.easynavigation.view.EasyNavigationBar

class NavActivity : AppCompatActivity() {

    val tabText = arrayOf("发现", "榜单", "消息", "我")
    private val mHandler: Handler = Handler()

    //未选中icon
    private val normalIcon =
        intArrayOf(
            R.drawable.ic_nav_bar_discover_normal,
            R.drawable.ic_nav_bar_ranking_normal,
            R.drawable.ic_nav_bar_msg_normal,
            R.drawable.ic_nav_bar_mine_normal
        )

    //选中时icon
    private val selectIcon =
        intArrayOf(
            R.drawable.ic_nav_bar_discover_pressed,
            R.drawable.ic_nav_bar_ranking_pressed,
            R.drawable.ic_nav_bar_msg_pressed,
            R.drawable.ic_nav_bar_mine_pressed
        )

    lateinit var binding:ActivityNavBinding
    private var flag = true

    private val fragments: List<Fragment> = arrayListOf(BlankFragment(),BlankFragment2(),BlankFragment3(),BlankFragment4(),BlankFragment5())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.navigationBar.titleItems(tabText)
            .normalIconItems(normalIcon)
            .selectIconItems(selectIcon)
            .fragmentList(fragments)
            .centerImageRes(R.drawable.ic_nav_bar_create)
            .centerLayoutRule(EasyNavigationBar.RULE_BOTTOM)
            .centerLayoutBottomMargin(20)
            .centerAlignBottom(true)
            .fragmentManager(supportFragmentManager)
            .setOnTabClickListener(object : EasyNavigationBar.OnTabClickListener{
                override fun onTabSelectEvent(view: View?, position: Int): Boolean {
                    return false
                }

                override fun onTabReSelectEvent(view: View?, position: Int): Boolean {
                    return false
                }
            })
            .setOnCenterTabClickListener(object: EasyNavigationBar.OnCenterTabSelectListener {


                override fun onCenterTabSelectEvent(view: View?): Boolean {
                    mHandler.post(object : Runnable {
                        override fun run() {
                            if (flag) {
                                binding.navigationBar.centerImage.animate()
                                    .rotation(45.0F).duration =
                                    400
                            } else {
                                binding.navigationBar.centerImage.animate().rotation(0F).duration =
                                    400
                            }
                            flag = !flag
                        }
                    })
                    return false
                }
            })
            .canScroll(true)
            .mode(EasyNavigationBar.NavigationMode.MODE_ADD)
            .build();
    }
}