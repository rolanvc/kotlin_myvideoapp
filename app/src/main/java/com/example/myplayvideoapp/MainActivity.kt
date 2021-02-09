package com.example.myplayvideoapp

import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.URLUtil
import android.widget.MediaController
import android.widget.TextView
import android.widget.VideoView

private val VIDEO_NAME = "small_toy"

class MainActivity : AppCompatActivity() {
    val videoview by lazy  {findViewById<VideoView>(R.id.videoview)}
    val loading by lazy{ findViewById<TextView>(R.id.loading)}
    val completed by lazy{ findViewById<TextView>(R.id.completed)}
    val currentPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val controller = MediaController(this)
        controller.setMediaPlayer(videoview)

        videoview.setMediaController(controller)
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

        loading.visibility = VideoView.VISIBLE
        completed.visibility = VideoView.INVISIBLE
        videoview.setVideoURI(videoUri)

        videoview.setOnPreparedListener{
            loading.visibility = VideoView.INVISIBLE
            if (currentPos > 0) {
                videoview.seekTo(currentPos)
            } else {
                videoview.seekTo(1)
            }
            videoview.start()
        }
        videoview.setOnCompletionListener {
            completed.visibility = VideoView.VISIBLE
            videoview.seekTo(1);
        }
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