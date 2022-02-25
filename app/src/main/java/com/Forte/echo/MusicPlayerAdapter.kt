package com.Forte.echo

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Color
import android.graphics.PorterDuff
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.play_listitem.view.*

class MusicPlayerAdapter(
    private val context: Context,
    private val musicPlayList: ArrayList<MusicDataList>,
    private val songsActionCallbacks: SongsAction,
    private var songPosition: Int = -1,
    val view: View = View.inflate(context, R.layout.play_listitem, null)
) : RecyclerView.Adapter<MusicPlayerAdapter.MusicViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MusicViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.music_listitem_layout, parent, false)
        return MusicViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MusicViewHolder, position: Int) {
        var currentItem = musicPlayList[position]

//        Glide.with(context)
//            .load(currentItem.imgSrc)
//            .centerCrop()
//            .into(holder.imgSrc)

        holder.imgSrc.setImageDrawable(ContextCompat.getDrawable(context, currentItem.imgSrc))

        when (position % 5) {
            0 -> holder.imgSrc.setColorFilter(Color.parseColor("GRAY"), PorterDuff.Mode.MULTIPLY)
            1 -> holder.imgSrc.setColorFilter(Color.parseColor("BLUE"), PorterDuff.Mode.MULTIPLY)
            2 -> holder.imgSrc.setColorFilter(Color.parseColor("MAGENTA"), PorterDuff.Mode.MULTIPLY)
            3 -> holder.imgSrc.setColorFilter(Color.parseColor("BLACK"), PorterDuff.Mode.MULTIPLY)
            4 -> holder.imgSrc.setColorFilter(Color.parseColor("RED"), PorterDuff.Mode.MULTIPLY)
        }
        holder.songTitle.text = currentItem.songTitle
        holder.songDesc.text = currentItem.songDesc
        holder.itemView.setOnClickListener {
            val dialog = BottomSheetDialog(context)
            dialog.setContentView(view)
            dialog.show()
            view.playicon.setImageResource(R.drawable.ic_baseline_pause_24)
            view.endDuration.text = formatDuration(musicPlayList[position].Duration)
            view.seekbar.progress = 0
            view.seekbar.max = musicPlayList[position].Duration.toInt()
            songsActionCallbacks.startPlay(currentItem.songUrl)

            view.playicon.setOnClickListener {
                if (songsActionCallbacks.isPlaying()) {
                    view.playicon.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                    songsActionCallbacks.pause()
                } else {
                    view.playicon.setImageResource(R.drawable.ic_baseline_pause_24)
                    songsActionCallbacks.play(currentItem.songUrl)
                }
            }

            view.forwardPlayIcon.setOnClickListener {
                if (songPosition + 1 < musicPlayList.size) {
                    view.playicon.setImageResource(R.drawable.ic_baseline_pause_24)
                    if (songPosition == -1) {
                        songPosition = position
                    } else {
                        songPosition += 1
                    }
                    view.endDuration.text = formatDuration(musicPlayList[songPosition].Duration)
                    songsActionCallbacks.startPlay(musicPlayList[songPosition].songUrl)
                }
            }

            view.backwardPlayIcon.setOnClickListener {
                if (songPosition > -1) {
                    view.playicon.setImageResource(R.drawable.ic_baseline_pause_24)
                    if (songPosition == -1) {
                        songPosition = position
                    } else {
                        songPosition -= 1
                    }
                    view.endDuration.text = formatDuration(musicPlayList[songPosition].Duration)
                    songsActionCallbacks.startPlay(musicPlayList[songPosition].songUrl)
                }
            }
            view.seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, p1: Int, p2: Boolean) {
                    seekBarSetup()
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

                override fun onStopTrackingTouch(seekBar: SeekBar?) = Unit
            })
        }

    }

    private fun seekBarSetup() {
        view.startDuration.text = formatDuration(songsActionCallbacks.trackSong())
    }

    override fun getItemCount(): Int {
        return musicPlayList.size
    }

    class MusicViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imgSrc: ImageView = itemView.findViewById(R.id.musicicon)
        val songTitle: TextView = itemView.findViewById(R.id.songTitle)
        val songDesc: TextView = itemView.findViewById(R.id.songDetails)
    }
}


