package com.example.demo.navdemo

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.ViewAnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.demo.R
import com.example.demo.databinding.ActivityNavBinding
import com.next.easynavigation.utils.NavigationUtil
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

//    private val createItems = intArrayOf(
//        R.drawable.ic_create_lyrics,
//        R.drawable.ic_create_hum,
//        R.drawable.ic_create_musical_instrument,
//        R.drawable.ic_create_accompaniment,
//        R.drawable.ic_create_upload,
//        R.drawable.ic_create_create
//    )
//
//    private val createTextItems = arrayOf("歌词记录", "哼唱记录", "乐器记录", "伴奏作词", "上传音乐", "创作音乐")

    lateinit var binding: ActivityNavBinding

    private var imageViewCancel: ImageView? = null

    private var llCreateLyrics: LinearLayout? = null
    private var llCreateHum: LinearLayout? = null
    private var llCreateMusicalInstrument: LinearLayout? = null
    private var llCreateAccompaniment: LinearLayout? = null
    private var llCreateUpload: LinearLayout? = null
    private var llCreateCreate: LinearLayout? = null


    private val fragments: List<Fragment> = arrayListOf(
        BlankFragment(),
        BlankFragment2(),
        BlankFragment3(),
        BlankFragment4(),
        BlankFragment5()
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNavBinding.inflate(layoutInflater)
        setContentView(binding.root)




        binding.navigationBar.titleItems(tabText)
            .normalIconItems(normalIcon)
            .selectIconItems(selectIcon)
            .centerImageRes(R.drawable.ic_nav_bar_create)
//            .centerLayoutBottomMargin(20)
//            .centerAlignBottom(true)
            .fragmentList(fragments)
            .fragmentManager(supportFragmentManager)
            .centerLayoutRule(EasyNavigationBar.RULE_CENTER)
            .setOnCenterTabClickListener {
                mHandler.post {
                    binding.navigationBar.centerImage.animate()
                        .rotation(45.0F).duration =
                        400
                    showCreatePage()
                }
                false
            }
//            .canScroll(true)
            .mode(EasyNavigationBar.NavigationMode.MODE_ADD)
            .build();

        binding.navigationBar.setAddViewLayout(createNewPage())
        binding.navigationBar
    }

    @SuppressLint("ObjectAnimatorBinding")
    private fun showCreatePage() {
        startAnimation()
        mHandler.post {
            run {

                //＋ 旋转动画
                imageViewCancel?.animate()?.rotation(45f)?.duration = 400
            }
        }

        llCreateLyrics?.visibility = View.INVISIBLE
        mHandler.postDelayed({
            llCreateLyrics?.visibility = View.VISIBLE
            val fadeAnim: ValueAnimator =
                ObjectAnimator.ofFloat(llCreateLyrics, "translationY", 600f, 0f)
            fadeAnim.duration = 500
            val kickAnimator = KickBackAnimator()
            kickAnimator.setDuration(500F)
            fadeAnim.setEvaluator(kickAnimator)
            fadeAnim.start()
        }, 50 * 0 + 100)

        llCreateHum?.visibility = View.INVISIBLE
        mHandler.postDelayed({
            llCreateHum?.visibility = View.VISIBLE
            val fadeAnim: ValueAnimator =
                ObjectAnimator.ofFloat(llCreateHum, "translationY", 600f, 0f)
            fadeAnim.duration = 500
            val kickAnimator = KickBackAnimator()
            kickAnimator.setDuration(500F)
            fadeAnim.setEvaluator(kickAnimator)
            fadeAnim.start()
        }, 50 * 1 + 100)

        llCreateMusicalInstrument?.visibility = View.INVISIBLE
        mHandler.postDelayed({
            llCreateMusicalInstrument?.visibility = View.VISIBLE
            val fadeAnim: ValueAnimator =
                ObjectAnimator.ofFloat(llCreateMusicalInstrument, "translationY", 600f, 0f)
            fadeAnim.duration = 500
            val kickAnimator = KickBackAnimator()
            kickAnimator.setDuration(500F)
            fadeAnim.setEvaluator(kickAnimator)
            fadeAnim.start()
        }, 50 * 2 + 100)

        llCreateAccompaniment?.visibility = View.INVISIBLE
        mHandler.postDelayed({
            llCreateAccompaniment?.visibility = View.VISIBLE
            val fadeAnim: ValueAnimator =
                ObjectAnimator.ofFloat(llCreateAccompaniment, "translationY", 600f, 0f)
            fadeAnim.duration = 500
            val kickAnimator = KickBackAnimator()
            kickAnimator.setDuration(500F)
            fadeAnim.setEvaluator(kickAnimator)
            fadeAnim.start()
        }, 50 * 3 + 100)

        llCreateUpload?.visibility = View.INVISIBLE
        mHandler.postDelayed({
            llCreateUpload?.visibility = View.VISIBLE
            val fadeAnim: ValueAnimator =
                ObjectAnimator.ofFloat(llCreateUpload, "translationY", 600f, 0f)
            fadeAnim.duration = 500
            val kickAnimator = KickBackAnimator()
            kickAnimator.setDuration(500F)
            fadeAnim.setEvaluator(kickAnimator)
            fadeAnim.start()
        }, 50 * 4 + 100)

        llCreateCreate?.visibility = View.INVISIBLE
        mHandler.postDelayed({
            llCreateCreate?.visibility = View.VISIBLE
            val fadeAnim: ValueAnimator =
                ObjectAnimator.ofFloat(llCreateCreate, "translationY", 600f, 0f)
            fadeAnim.duration = 500
            val kickAnimator = KickBackAnimator()
            kickAnimator.setDuration(500F)
            fadeAnim.setEvaluator(kickAnimator)
            fadeAnim.start()
        }, 50 * 5 + 100)
    }

    private fun createNewPage(): View {
        var createViewGroup = View.inflate(this, R.layout.layout_creat_add_view, null)
        imageViewCancel = createViewGroup.findViewById(R.id.cancel_iv)

//        val layout: LinearLayout.LayoutParams = imageViewCancel?.layoutParams as LinearLayout.LayoutParams
//        layout.bottomMargin = binding.navigationBar.centerImage.marginBottom
//        layout.width = binding.navigationBar.centerImage.width
//        layout.height = binding.navigationBar.centerImage.height
//        imageViewCancel?.layoutParams = layout

        llCreateLyrics = createViewGroup.findViewById(R.id.ll_create_lyrics)
        llCreateHum = createViewGroup.findViewById(R.id.ll_create_hum)
        llCreateMusicalInstrument = createViewGroup.findViewById(R.id.ll_create_musical_instrument)
        llCreateAccompaniment = createViewGroup.findViewById(R.id.ll_create_accompaniment)
        llCreateUpload = createViewGroup.findViewById(R.id.ll_create_upload)
        llCreateCreate = createViewGroup.findViewById(R.id.ll_create_create)
        imageViewCancel?.setOnClickListener {
            closeAnimation()
        }
        llCreateLyrics?.setOnClickListener {
            Toast.makeText(this, "这是歌词记录", Toast.LENGTH_SHORT).show()
        }

        llCreateHum?.setOnClickListener {
            Toast.makeText(this, "这是哼唱记录", Toast.LENGTH_SHORT).show()
        }

        llCreateMusicalInstrument?.setOnClickListener {
            Toast.makeText(this, "这是乐器记录", Toast.LENGTH_SHORT).show()
        }

        llCreateAccompaniment?.setOnClickListener {
            Toast.makeText(this, "这是伴奏", Toast.LENGTH_SHORT).show()
        }

        llCreateUpload?.setOnClickListener {
            Toast.makeText(this, "这是上传", Toast.LENGTH_SHORT).show()
        }

        llCreateCreate?.setOnClickListener {
            Toast.makeText(this, "这是创作", Toast.LENGTH_SHORT).show()
        }


        return createViewGroup
    }

    private fun closeAnimation() {
        mHandler.post { imageViewCancel?.animate()?.rotation(0f)?.duration = 400 }
        binding.navigationBar.centerImage.animate().rotation(0F).duration =
            400
        try {
            val x = NavigationUtil.getScreenWidth(this) / 2
            val y = NavigationUtil.getScreenHeith(this) - NavigationUtil.dip2px(this, 25f)
            val animator = ViewAnimationUtils.createCircularReveal(
                binding.navigationBar.addViewLayout, x,
                y, binding.navigationBar.addViewLayout.height.toFloat(), 0f
            )
            animator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationStart(animation: Animator) {
                    //							layout.setVisibility(View.GONE);
                }

                override fun onAnimationEnd(animation: Animator) {
                    binding.navigationBar.addViewLayout.visibility = View.GONE
                    //dismiss();
                }
            })
            animator.duration = 300
            animator.start()
        } catch (e: java.lang.Exception) {
        }
    }

    private fun startAnimation() {
        mHandler.post {
            run {
                try {
                    //圆形扩展的动画
                    val x = NavigationUtil.getScreenWidth(this) / 2
                    val animator = ViewAnimationUtils.createCircularReveal(
                        binding.navigationBar.addViewLayout, x,
                        (NavigationUtil.getScreenHeith(this) - NavigationUtil.dip2px(
                            this,
                            25f
                        )), 0f, binding.navigationBar.addViewLayout.height.toFloat()
                    )
                    animator.addListener(object : AnimatorListenerAdapter() {
                        override fun onAnimationStart(animation: Animator) {
                            binding.navigationBar.addViewLayout.visibility = View.VISIBLE
                        }

                        override fun onAnimationEnd(animation: Animator) {
                            //							layout.setVisibility(View.VISIBLE);
                        }
                    })
                    animator.duration = 300
                    animator.start()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}