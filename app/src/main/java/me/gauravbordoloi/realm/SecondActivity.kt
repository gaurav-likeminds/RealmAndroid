package me.gauravbordoloi.realm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.realm.Realm
import kotlinx.android.synthetic.main.activity_second.*

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val realm = Realm.getDefaultInstance()

        button.setOnClickListener {
            realm.beginTransaction()
            realm.copyToRealmOrUpdate(Student(1, System.currentTimeMillis().toInt()))
            realm.commitTransaction()
        }

    }
}