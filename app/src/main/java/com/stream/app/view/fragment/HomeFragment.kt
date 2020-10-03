package com.stream.app.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.stream.app.R
import com.stream.app.repository.SessionManager
import com.stream.app.viewModel.BerandaViewModel
import com.xwray.groupie.GroupAdapter
import com.xwray.groupie.ViewHolder
import kotlinx.android.synthetic.main.btmsht_content_home.*

/**
 * A simple [Fragment] subclass.
 */
class HomeFragment : Fragment() {

    lateinit var viewModel: BerandaViewModel
    private lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        sessionManager = SessionManager(requireContext())

        val userAccessToken = sessionManager.userDetail["ACCESS_TOKEN"]
        val accessToken = "Bearer $userAccessToken"
//
//        val btmsht = root.findViewById<LinearLayout>(R.id.bottom_sheet_firstfrag)
//        val sheetBehaviour = BottomSheetBehavior.from(btmsht)
//        val btn = root.findViewById<Button>(R.id.btn_bottom)
////        val btnDown = root.findViewById<Button>(R.id.btn_bottom_down)
//        sheetBehaviour.state = BottomSheetBehavior.STATE_COLLAPSED
//
////        btnDown.setOnClickListener {
////            sheetBehaviour.state = BottomSheetBehavior.STATE_COLLAPSED
////        }
//
//        btn.setOnClickListener {
//            if (sheetBehaviour.state == BottomSheetBehavior.STATE_HALF_EXPANDED) {
//                sheetBehaviour.state = BottomSheetBehavior.STATE_EXPANDED
//            } else {
//                sheetBehaviour.state = BottomSheetBehavior.STATE_HALF_EXPANDED
//            }
//        }
//
//        val rv_news = root.findViewById<RecyclerView>(R.id.rv_news_firstFrag)
//        val groupAdapter = GroupAdapter<ViewHolder>()
//
//        rv_news.adapter
        viewModel = ViewModelProvider(this).get(BerandaViewModel::class.java)
        observeNotification(accessToken)
        return root
    }

    private fun observeNotification(accessToken: String) {
        viewModel.populateNotification(accessToken)
        viewModel.getNotification().observe( viewLifecycleOwner, Observer {
            val groupAdapter = GroupAdapter<ViewHolder>().apply {
                this.addAll(it)
            }
            rv_btmsht_home.apply {
                this.adapter = groupAdapter
            }
        })
    }

}
