package com.ard.dailyislam.quran.ayatlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.navArgs
import com.ard.dailyislam.R
import com.ard.dailyislam.quran.model.ayat.Ayat
import com.ard.dailyislam.quran.repository.AyatRepository
import kotlinx.android.synthetic.main.fragment_ayat.*
import kotlinx.android.synthetic.main.toolbar_surah.*


class AyatListFragment : Fragment() {
    private lateinit var vm: AyatListViewModel
    private lateinit var adapter: AyatListAdapter

    private val args: AyatListFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_ayat, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val surah = args.surah

        adapter = AyatListAdapter(args.noSurah)
        ayatList.adapter = adapter


        namaTxt.text = surah!!.nama

        val type: String = surah!!.type.substring(0, 1).toUpperCase() + surah!!.type.substring(1)

        tempatTxt.text = type
        val factory = AyatListFactory(AyatRepository.instance)
        vm = ViewModelProviders.of(this, factory).get(AyatListViewModel::class.java).apply {
            viewState.observe(viewLifecycleOwner, Observer(this@AyatListFragment::handleState))
            if (viewState.value?.data == null)
            {

                getAyats(args.noSurah)
            }
        }

    }

    private fun handleState(viewState: AyatListViewState?) {
        viewState?.let {
            it.data?.let { data -> showData(args.noSurah ,data) }
//            it.error?.let { error -> showError(error) }
        }
    }

    private fun showData(no:String,data: Ayat) {
        adapter.updateData(no,data)
    }



}