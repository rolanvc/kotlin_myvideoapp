package com.example.myplayvideoapp

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.URLUtil
import android.widget.VideoView

private val VIDEO_NAME = "small_toy.mp4"

class MainActivity : AppCompatActivity() {
    val videoview by lazy  {findViewById<VideoView>(R.id.videoview)}

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    private fun getURI(videoname:String): Uri {
        if (URLUtil.isValidUrl(videoname)) {
            //  an external URL
            return Uri.parse(videoname);
        } else { //  a raw resource
            return Uri.parse("android.resource://" + getPackageName() +
                    "/raw/" + videoname);
        }
    }
    private fun initPlayer() {
        var videoUri:Uri = getURI(VIDEO_NAME)
        videoview.setVideoURI(videoUri)
        videoview.start()
    }
    private fun releasePlayer(){
        videoview.stopPlayback()
    }
    protected override fun onStop() {
        super.onStop()
        releasePlayer()
    }
    protected override fun onStart() {
        super.onStart()
        initPlayer()
    }
    protected override fun onPause() {
        super.onPause()
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            videoview.pause()
        }
    }


}