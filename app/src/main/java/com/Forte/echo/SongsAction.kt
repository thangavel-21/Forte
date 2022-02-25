package com.Forte.echo

interface SongsAction {
    fun startPlay(songUrl: String)
    fun play(songUrl : String)
    fun pause()
    fun stop()
    fun isPlaying() : Boolean
    fun trackSong() : Long
}