package com.vks.mvvmexample

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import com.vks.mvvmexample.adapter.PostsAdapter
import com.vks.repository.resource.Status
import com.vks.mvvmexample.viewmodel.MyViewModel
import com.vks.mvvmexample.viewmodel.NetworkViewModel
import com.vks.mvvmexample.databinding.FragmentHomeBinding
import javax.inject.Inject

class HomeFragment @Inject constructor(viewModelFactory: ViewModelProvider.Factory) : Fragment() {
    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private val viewModel: MyViewModel by viewModels { viewModelFactory }
    private val networkViewModel by activityViewModels<NetworkViewModel> { viewModelFactory }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.isVisible = false
        binding.progressBar.isActivated = false

        val adapter = PostsAdapter()
        binding.rvPosts.adapter = adapter

        networkViewModel.connectedStatus.observe(viewLifecycleOwner) { isConnected ->
            Log.d(javaClass.name, "Network connected = $isConnected")
            viewModel.isOffline = isConnected.not()
            viewModel.getPosts()
        }
        viewModel.getPostState().observe(viewLifecycleOwner) { resource ->
            binding.progressBar.isVisible = resource.status == Status.LOADING
            binding.progressBar.isActivated = resource.status == Status.LOADING
            if (resource.status == Status.SUCCESS) {
                adapter.postsList = resource.data?.posts ?: listOf()
                adapter.notifyDataSetChanged()
            } else if (resource.status == Status.ERROR) {
                if (viewModel.isOffline) {
                    Toast.makeText(requireContext(), "You are offline", Toast.LENGTH_LONG)
                        .show()
                } else
                    Toast.makeText(requireContext(), "${resource.message}", Toast.LENGTH_LONG)
                        .show()
            }
        }
    }
}