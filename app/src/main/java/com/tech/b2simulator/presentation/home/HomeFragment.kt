package com.tech.b2simulator.presentation.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.tech.b2simulator.common.ViewState
import com.tech.b2simulator.databinding.FragmentHomeBinding
import com.tech.b2simulator.presentation.BaseFragment
import com.tech.common.views.SpacesItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val homeViewModel: HomeViewModel by viewModels()
    private var categoryActionAdapter: CategoryActionAdapter? = null
    private var categoryLocationAdapter: CategoryLocationAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        setUpViews()
        observeData()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    override fun setUpViews() {
        //
//        binding.toolbarContainer.toolbar.title = getString(R.string.title_home)
        ///
        categoryActionAdapter = CategoryActionAdapter(requireContext())
        categoryLocationAdapter = CategoryLocationAdapter(requireContext())
        categoryLocationAdapter?.setItemClickedListener(object :
            CategoryLocationAdapter.CategoryLocationAdapterItemClickedListener {
            override fun onItemClicked(categoryId: Int) {
                findNavController().navigate(HomeFragmentDirections.actionNavigationHomeToNavigationQuestion(categoryId))
            }
        })

        binding.rvCategoryLocation.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategoryLocation.addItemDecoration(SpacesItemDecoration(10))
        binding.rvCategoryLocation.adapter = categoryActionAdapter

        binding.rvCategoryAction.layoutManager =
            LinearLayoutManager(requireActivity(), LinearLayoutManager.HORIZONTAL, false)
        binding.rvCategoryAction.addItemDecoration(SpacesItemDecoration(10))
        binding.rvCategoryAction.adapter = categoryLocationAdapter
        binding.viewWrongAnswers.setOnClickListener {

        }
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun observeData() {
        homeViewModel.categoryActionLiveData.observe(viewLifecycleOwner) {
            if (it is ViewState.Success) {
                categoryActionAdapter?.data = it.data
                categoryActionAdapter?.notifyDataSetChanged()
            }
        }

        homeViewModel.categoryLocationLiveData.observe(viewLifecycleOwner) {
            if (it is ViewState.Success) {
                categoryLocationAdapter?.data = it.data
                categoryLocationAdapter?.notifyDataSetChanged()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        categoryLocationAdapter?.setItemClickedListener(null)
        _binding = null
    }
}