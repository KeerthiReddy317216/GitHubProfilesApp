package com.example.githubprofilesapp

import android.os.Bundle
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubprofilesapp.databinding.ActivityMainBinding
import com.example.githubprofilesapp.model.GHRepo
import com.example.githubprofilesapp.view.GitHubProfilesAdapter
import com.example.githubprofilesapp.view.WebViewFragment
import com.example.githubprofilesapp.viewmodel.GHProfileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: GitHubProfilesAdapter
    private lateinit var viewModel: GHProfileViewModel
    private var repoList: List<GHRepo> = emptyList()
    private var searchResults: List<GHRepo> = emptyList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = ViewModelProvider(this)[GHProfileViewModel::class.java]
        adapter = GitHubProfilesAdapter(this, repoList) { url ->
            val fragment = WebViewFragment.newInstance(url)
            binding.searchBar.visibility = GONE
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        }
        binding.recyclerview.let {
            it.layoutManager = LinearLayoutManager(this)
            it.adapter = adapter
        }
        lifecycleScope.launch {
            viewModel.getRepositories(applicationContext).collect { list ->
                repoList = list
                searchResults = repoList
                if(list.isNotEmpty()) {
                    adapter.updateList(repoList)
                }else{
                    withContext(Dispatchers.Main) {
                        binding.errorView.visibility = VISIBLE
                    }
                }
            }
        }
        supportFragmentManager.addOnBackStackChangedListener {
            //To update the visibility of search bar based on the fragment transaction
            val currentFragment = supportFragmentManager.findFragmentById(R.id.fragment_container)
            if (currentFragment is WebViewFragment) {
                binding.searchBar.visibility = GONE
            } else {
                binding.searchBar.visibility = VISIBLE
            }
        }
        binding.searchBar.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                getSearchResults(query ?: "")
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                getSearchResults(newText ?: "")
                return true
            }
        })
    }
    fun getSearchResults(query: String) {
        searchResults = if (query.isBlank()) {
            repoList
        } else {
            searchResults.filter {//Filtering the items based on id and name of the git repositories
                it.name.contains(query, ignoreCase = true) ||  it.id.toString().contains(query, ignoreCase = true)
            }
        }
        //Error view handling when the search results are empty
        if(searchResults.isNotEmpty()) {
            binding.errorView.visibility = GONE
        }else{
           binding.errorView.visibility = VISIBLE
        }
        adapter.updateList(searchResults)
        searchResults = repoList
    }
}
