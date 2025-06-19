package vcmsa.ci.musicplaylistmanagerapp

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main2)

        val btnShowDetails = findViewById<Button>(R.id.btnShowDetails)
        val lvDetails = findViewById<ListView>(R.id.lvDetails)
        val btnAverage = findViewById<Button>(R.id.btnAverage)
        val tvAverage = findViewById<TextView>(R.id.tvAverage)
        val btnBack = findViewById<Button>(R.id.btnBack)

        btnShowDetails.setOnClickListener {
            val details = ArrayList<String>()
            for (i in PlaylistData.songTitles.indices) {
                val entry = "${PlaylistData.songTitles[i]} – ${PlaylistData.artistNames[i]} " +
                        "★${PlaylistData.ratings[i]}\n${PlaylistData.comments[i]}"
                details.add(entry)
            }
            val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, details)
            lvDetails.adapter = adapter
        }

        btnAverage.setOnClickListener {
            val avg = if (PlaylistData.ratings.isNotEmpty()) {
                PlaylistData.ratings.sum().toFloat() / PlaylistData.ratings.size
            } else 0f
            tvAverage.text = "Average Rating: %.2f".format(avg)
        }

        btnBack.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
        }
    }
}


