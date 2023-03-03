package com.tech.common.views

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import timber.log.Timber

open class BaseFragment : Fragment() {

    override fun onAttach(context: Context) {
        Timber.tag(this.javaClass.simpleName).d("onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Timber.tag(this.javaClass.simpleName).d("onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Timber.tag(this.javaClass.simpleName).d("onCreateView")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.tag(this.javaClass.simpleName).d("onViewCreated")
    }

    override fun onResume() {
        super.onResume()
        Timber.tag(this.javaClass.simpleName).d("onResume")
    }

    override fun onPause() {
        super.onPause()
        Timber.tag(this.javaClass.simpleName).d("onPause")
    }

    override fun onStop() {
        super.onStop()
        Timber.tag(this.javaClass.simpleName).d("onStop")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Timber.tag(this.javaClass.simpleName).d("onDestroyView")
    }

    override fun onDestroy() {
        super.onDestroy()
        Timber.tag(this.javaClass.simpleName).d("onDestroy")
    }

    override fun onDetach() {
        super.onDetach()
        Timber.tag(this.javaClass.simpleName).d("onDetach")
    }
}
