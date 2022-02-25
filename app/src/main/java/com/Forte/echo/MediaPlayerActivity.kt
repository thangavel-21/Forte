package com.Forte.echo

import android.content.ContentValues.TAG
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.Forte.echo.databinding.MusicPlaylistLayoutBinding
import android.util.Log
import android.view.View
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.bottom_sheet_item.view.*


class MediaPlayerActivity : AppCompatActivity(), SongsAction, MediaPlayer.OnCompletionListener {
    private var binding: MusicPlaylistLayoutBinding? = null
    var mp = MediaPlayer()
    var state = PLAYER_STATE.STOPPED;

    private lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = MusicPlaylistLayoutBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        getData()
        setContentView(binding?.root)
        mp.setOnCompletionListener(this)
        binding?.toolBar?.setNavigationOnClickListener {
            val view: View? = layoutInflater?.inflate(R.layout.bottom_sheet_item, null)
            val dialog = BottomSheetDialog(this)
            if (view != null) {
                dialog.setContentView(view)
            }
            dialog.show()
            view?.editIcon?.setOnClickListener {
                Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun getData() {
        val recyclerView = binding?.musicPlayList
        val toolbar = binding?.toolBar
        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.setHasFixedSize(true)
        val arrayList: ArrayList<MusicDataList> = arrayListOf();


        val uri: Uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val selection = MediaStore.Audio.Media.IS_MUSIC + "!=0"
        val rs = contentResolver.query(uri, null, selection, null, null)


        if (rs != null) {
            while (rs.moveToNext()) {
                val url = rs.getString(rs.getColumnIndex(MediaStore.Audio.Media.DATA))
                val songTitle = rs.getString(rs.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME))
                val songDesc = rs.getString(rs.getColumnIndex(MediaStore.Audio.Media.ARTIST))
                val imgSrc = rs.getLong(rs.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID)).toString()
                val songDuration = rs.getLong(rs.getColumnIndex(MediaStore.Audio.Media.DURATION))
                val uri = Uri.parse("content://media/external/audio/albumart")
//                val artUri =Uri.withAppendedPath(uri,imgSrc).toString()
                var imageData = R.drawable.ic_baseline_music_note_24;

                arrayList.add(MusicDataList(imageData, songTitle, songDesc, url,rs.count,songDuration))
//              arrayList.add(MusicDataList(artUri, songTitle, songDesc, url))
            }
        }
        recyclerView?.adapter = MusicPlayerAdapter(this, arrayList, this)

        toolbar?.setOnClickListener {

        }
    }

    override fun startPlay(songUrl: String) {
        mp.stop()
        mp.reset()
        mp.setDataSource(songUrl)
        mp.prepare()
        mp.start()
    }

    override fun play(url: String) {
        try {
            if (!mp.isPlaying) {
                mp.start()
            }
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    override fun pause() {
        mp.pause()
    }

    override fun stop() {
        Log.e(TAG, "Stop :")
    }

    override fun isPlaying(): Boolean {
        if (mp.isPlaying == true)
            return true
        return false
    }

    override fun trackSong(): Long {
        mp.seekTo(mp.currentPosition)
      return  mp.currentPosition.toLong()
    }

    override fun onCompletion(p0: MediaPlayer?) {
        mp.stop()
    }
}