package com.ard.quran.surahList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ard.quran.R
import com.ard.quran.model.surah.Surah
import com.ard.quran.repository.SurahRepository
import kotlinx.android.synthetic.main.fragment_surah.*


class SurahListFragment : Fragment() {
    private lateinit var vm : SurahListViewModel
    private lateinit var adapter : SurahListAdapter
    private lateinit var surahListCopy : MutableList<Surah>

//    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        return super.onCreateView(inflater.inflate(R.layout.fragment_surah), container, savedInstanceState)
//    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_surah, container, false)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenu()

        surahListCopy= ArrayList()
        adapter = SurahListAdapter()
        surahList.adapter = adapter


        val factory = SurahListFactory(SurahRepository.instance)
        vm = ViewModelProviders.of(this, factory).get(SurahListViewModel::class.java).apply {
            viewState.observe(viewLifecycleOwner, Observer(this@SurahListFragment::handleState))

        }
    }

    private fun initMenu() {
        tb_quran.inflateMenu(R.menu.menu_quran)
        tb_quran.setNavigationIcon(R.drawable.ic_baseline_arrow_back_ios_24)

        tb_quran.setOnMenuItemClickListener {
            when(it.itemId){
                R.id.app_bar_search -> {

                    true
                }
                else ->{
                    super.onOptionsItemSelected(it)
                }
            }
        }

        val menu : Menu = tb_quran.menu

        val searchItem = menu.findItem(R.id.app_bar_search)
        val searchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter(surahListCopy,newText)
                return true
            }
        }
        )



    }

    private fun handleState(viewState: SurahListViewState?) {
        viewState?.let {
            it.data?.let { data -> showData(data) }
//            it.error?.let { error -> showError(error) }
        }
    }

    private fun showData(data: MutableList<Surah>) {


        adapter.updateData(data)
        surahListCopy.addAll(data)
        Log.d("tess","" +surahListCopy.size)
    }


}