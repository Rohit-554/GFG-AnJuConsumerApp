package `in`.jadu.anjuconsumerapp.consumer.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import dagger.hilt.android.AndroidEntryPoint
import `in`.jadu.anjuconsumerapp.R

@AndroidEntryPoint
class ConsumerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_consumer)
    }
}