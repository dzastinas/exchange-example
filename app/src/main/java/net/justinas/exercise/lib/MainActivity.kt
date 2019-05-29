package net.justinas.exercise.lib

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.justinas.exercise.lib.view.ExchangeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (supportFragmentManager.findFragmentById(R.id.container) == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, ExchangeFragment())
                .commit()
        }
    }
}
