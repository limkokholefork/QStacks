package com.example.android.qstack

import android.app.SearchManager
import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.view.Menu
import androidx.annotation.ColorInt
import androidx.annotation.ColorRes
import androidx.annotation.StyleableRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.avinash.library.FragmentStateSaver
import com.example.android.qstack.menu.DrawerAdapter
import com.example.android.qstack.menu.DrawerItem
import com.example.android.qstack.menu.SimpleItem
import com.example.android.qstack.ui.question.QuestionViewPager
import com.example.android.qstack.ui.tag.TagFragment
import com.example.android.qstack.ui.users.UsersFragment
import com.example.android.qstack.utils.displaySnackBar
import com.yarolegovich.slidingrootnav.SlidingRootNav
import com.yarolegovich.slidingrootnav.SlidingRootNavBuilder
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), DrawerAdapter.OnItemSelectedListener {

    private var screenTitles: Array<String>? = null
    private var screenIcons: Array<Drawable?>? = null
    @Inject
    lateinit var slidingRootNavBuilder: SlidingRootNavBuilder
    private lateinit var slidingRootNav: SlidingRootNav

    private lateinit var fragmentStateSaver: FragmentStateSaver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_Qstack)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)

        slidingRootNavBuilder.withToolbarMenuToggle(toolbar)

        if (savedInstanceState != null) {
            slidingRootNavBuilder.withSavedState(savedInstanceState)
        }
        slidingRootNav = slidingRootNavBuilder.inject()
        screenIcons = loadScreenIcon()
        screenTitles = loadScreenTitles()

        val container = findViewById<FragmentContainerView>(R.id.fragment_container)

        fragmentStateSaver = object : FragmentStateSaver(container, supportFragmentManager) {
            override fun getItem(item: Int): Fragment {
                return when(item){
                    0 -> QuestionViewPager()
                    1 -> TagFragment()
                    2 -> UsersFragment()
                    else -> QuestionViewPager()
                }
            }
        }


        //List of icons and screen titles to be displayed
        @Suppress("UNCHECKED_CAST")
        val testList: List<DrawerItem<DrawerAdapter.ViewHolder>> = listOf(
            createItemFor(QUESTION).setChecked(true),
            createItemFor(TAGS),
            createItemFor(USERS)
        ) as List<DrawerItem<DrawerAdapter.ViewHolder>>

        val adapter = DrawerAdapter(testList)
        adapter.setListener(this)

        val list = findViewById<RecyclerView>(R.id.recycler_view)
        list.isNestedScrollingEnabled = false
        list.layoutManager = LinearLayoutManager(this)
        list.adapter = adapter
        adapter.setSelected(QUESTION)
    }

    //Helper method for icons and titles to be passed into recycler-view
    private fun createItemFor(position: Int): DrawerItem<*> {
        return SimpleItem(
            screenIcons!![position]!!, screenTitles!![position]
        )
            .withIconTint(color(R.color.colorAccent))
            .withTextTint(color(R.color.colorAccent))
            .withSelectedTextTint(color(R.color.colorLight))
            .withSelectedIconTint(color(R.color.colorLight))
    }

    //Helper method for for loading screen titles from resource array
    private fun loadScreenTitles(): Array<String> {
        return resources.getStringArray(R.array.menu_name)
    }

    //Helper method for loading screen icons into an array from resource array
    private fun loadScreenIcon(): Array<Drawable?> {
        val ta = resources.obtainTypedArray(R.array.menu_icons)
        val icons = arrayOfNulls<Drawable>(ta.length())
        for (i in 0 until ta.length()) {
            @StyleableRes
            val id = ta.getResourceId(i, 0)
            if (id != 0) {
                icons[i] = ContextCompat.getDrawable(this, id)
            }
        }
        ta.recycle()
        return icons
    }

    //Set color on views
    @ColorInt
    private fun color(@ColorRes res: Int): Int {
        return ContextCompat.getColor(this, res)
    }

    companion object {
        private const val QUESTION = 0
        private const val TAGS = 1
        private const val USERS = 2
    }

    override fun onItemSelected(position: Int) {
        when (position) {
            0 -> fragmentStateSaver.changeFragment(0)
            1 -> fragmentStateSaver.changeFragment(1)
            2 -> fragmentStateSaver.changeFragment(2)
        }

        slidingRootNav.closeMenu()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        (menu?.findItem(R.id.search_menu)?.actionView as SearchView).apply {
            // Assumes current activity is the searchable activity
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            // Do not iconify the widget; expand it by default
        }
        return true
    }

}