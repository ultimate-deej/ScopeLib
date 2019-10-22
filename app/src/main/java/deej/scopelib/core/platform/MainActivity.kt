package deej.scopelib.core.platform

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import deej.scopelib.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
