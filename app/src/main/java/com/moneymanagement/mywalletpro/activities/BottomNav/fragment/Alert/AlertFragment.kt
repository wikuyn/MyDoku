package com.moneymanagement.mywalletpro.activities.BottomNav.fragment.Alert

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.moneymanagement.mywalletpro.databinding.FragmentAlertBinding

class AlertFragment : Fragment() {
    private lateinit var binding: FragmentAlertBinding
    private val downloadUrl = Uri.parse("https://www.w3.org/WAI/ER/tests/xhtml/testfiles/resources/pdf/dummy.pdf")
    private val request = DownloadManager.Request(downloadUrl)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlertBinding.inflate(inflater,container,false)
        binding.download.setOnClickListener {

            request.setTitle("Download File")
            request.setDescription("Download in progress")
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
            request.setAllowedOverMetered(true)
            request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_MOBILE or DownloadManager.Request.NETWORK_WIFI)
            request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_PICTURES, "dummy.pdf")

            val downloadManager = context?.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            downloadManager.enqueue(request)
            Toast.makeText(context,"download",Toast.LENGTH_SHORT).show()
        }

        return binding.root
    }
}