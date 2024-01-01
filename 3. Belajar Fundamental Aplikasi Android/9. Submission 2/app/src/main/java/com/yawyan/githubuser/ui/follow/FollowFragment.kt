package com.yawyan.githubuser.ui.follow


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yawyan.githubuser.R
import com.yawyan.githubuser.databinding.FragmentFollowBinding
import com.yawyan.githubuser.ui.main.UserAdapter

class FollowFragment : Fragment(R.layout.fragment_follow) {

    companion object {
        var USERNAME: String = ""
        var POSITION = "position"
    }

    private lateinit var adapter: UserAdapter
    private lateinit var binding: FragmentFollowBinding
    private lateinit var username: String
    private var position: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        arguments?.let {
            position = it.getInt(POSITION)
            username = it.getString(USERNAME)!!
        }
        showLoading(true)
        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowering.layoutManager = layoutManager

        if (isAdded && !isDetached) {
            val ViewModel = ViewModelProvider(
                this,
                ViewModelProvider.NewInstanceFactory()
            ).get(FollowViewModel::class.java)

            ViewModel.listfollowering.observe(viewLifecycleOwner) { followersing ->
                if (followersing != null) {
                    adapter = UserAdapter(followersing)
                    binding.rvFollowering.adapter = adapter
                    showLoading(false)
                }
            }
            if (position == 1) {
                ViewModel.setListFollowers(username, "Followers")
            } else {
                ViewModel.setListFollowers(username, "Following")
            }

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }

    }


}