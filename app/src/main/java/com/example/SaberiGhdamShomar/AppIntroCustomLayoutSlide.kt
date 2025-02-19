package com.example.SaberiGhdamShomar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class AppIntroCustomLayoutSlide : Fragment() {

    private var layoutResId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutResId = arguments?.getInt(ARG_LAYOUT_RES_ID) ?: 0
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? = inflater.inflate(layoutResId, container, false)

    companion object {
        private const val ARG_LAYOUT_RES_ID = "layoutResId"
        @JvmStatic
        fun newInstance(layoutResId: Int): AppIntroCustomLayoutSlide {
            val customSlide = AppIntroCustomLayoutSlide()
            val args = Bundle()
            args.putInt(ARG_LAYOUT_RES_ID, layoutResId)
            customSlide.arguments = args
            return customSlide
        }
    }
}