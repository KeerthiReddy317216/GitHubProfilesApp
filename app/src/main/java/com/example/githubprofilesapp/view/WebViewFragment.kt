package com.example.githubprofilesapp.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import android.webkit.WebResourceRequest
import com.example.githubprofilesapp.databinding.WebviewBinding

class WebViewFragment() : Fragment() {
    lateinit var binding : WebviewBinding
    companion object {
        private var ARG_URL = "url"

        fun newInstance(url: String): WebViewFragment {
            val fragment = WebViewFragment()
            val bundle = Bundle()
            bundle.putString(ARG_URL, url)
            ARG_URL = url
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = WebviewBinding.inflate(inflater,container,false)
        setupWebView()
            binding.webview.loadUrl(ARG_URL)
        return binding.root
    }

    private fun setupWebView() {
        val webSettings =   binding.webview.settings
        webSettings.javaScriptEnabled = true
        webSettings.domStorageEnabled = true

        binding.webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView,
                request: WebResourceRequest
            ): Boolean {
                view.loadUrl(request.url.toString())
                return true
            }
        }
    }
}