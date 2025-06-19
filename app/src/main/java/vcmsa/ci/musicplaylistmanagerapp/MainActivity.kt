package vcmsa.ci.musicplaylistmanagerapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.ListView
import android.widget.RatingBar
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity


//Define a data class to store song details
data class Song(
    val title: String,
    val artist: String,
    val rating: Int,
    val comment: String,
)

class MainActivity : AppCompatActivity() {

//Playlist and UI components
    private val playlist = ArrayList<Song>()
    private lateinit var listView: ListView
    private lateinit var adapter: ArrayAdapter<String>

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        //views from activity main .xml
        listView = findViewById(R.id.lvPlaylist)
        val btnAdd = findViewById<Button>(R.id.btnAddToPlaylist)

        // Initialize and assign adapter
        adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, formatPlaylist())
        listView.adapter = adapter

        // Handle add button click
        btnAdd.setOnClickListener {
            showAddSongDialog()
        }
        // Remove song when tapped
        listView.setOnItemClickListener { _, _, position, _ ->
            val removedSong = playlist.removeAt(position)
            Toast.makeText(this, "Removed: ${removedSong.title}", Toast.LENGTH_SHORT).show()
            refreshPlaylist()
        }
    }

    // Opens dialog to enter song details
    private fun showAddSongDialog() {
        val dialogView = LayoutInflater.from(this).inflate(R.layout.dialog_add_song, null)

        val etTitle = dialogView.findViewById<EditText>(R.id.DialogSong)
        val etArtist = dialogView.findViewById<EditText>(R.id.DialogArtist)
        val ratingBar = dialogView.findViewById<RatingBar>(R.id.DialogRating)
        val etComment = dialogView.findViewById<EditText>(R.id.DialogComment)

    object  {
        val songTitles = ArrayList<String>()
        val artistNames = ArrayList<String>()
        val ratings = ArrayList<Int>()
        val comments = ArrayList<String>()
    }


        AlertDialog.Builder(this)
            .setTitle("Add Song to Playlist")
            .setView(dialogView)
            .setPositiveButton("Add") { _, _ ->
                val title = etTitle.text.toString().trim()
                val artist = etArtist.text.toString().trim()
                val rating = ratingBar.rating.toInt()
                val comment = etComment.text.toString().trim()

                if (title.isNotEmpty() && artist.isNotEmpty()) {
                    playlist.add(Song(title, artist, rating, comment))
                    refreshPlaylist()
                } else {
                    Toast.makeText(this, "Please enter both title and artist", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    // Converts the playlist into readable text for the ListView
    private fun formatPlaylist(): List<String> {
        return playlist.map {
            "${it.title} – ${it.artist} ★${it.rating}\n${it.comment}"
        }
    }

    // Updates the ListView after changes
    private fun refreshPlaylist() {
        adapter.clear()
        adapter.addAll(formatPlaylist())
        adapter.notifyDataSetChanged()
    }
}

