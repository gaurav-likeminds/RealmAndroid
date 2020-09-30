package me.gauravbordoloi.realm

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import io.realm.ObjectChangeSet
import io.realm.Realm
import io.realm.RealmObjectChangeListener
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var listener: RealmObjectChangeListener<Student>
    private lateinit var realm: Realm
    private lateinit var student: Student

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        realm = Realm.getDefaultInstance()
        realm.beginTransaction()
        student = realm.copyToRealmOrUpdate(Student(1, System.currentTimeMillis().toInt()))
        realm.commitTransaction()

        listener = object : RealmObjectChangeListener<Student> {
            override fun onChange(student: Student, changeSet: ObjectChangeSet?) {
                Log.e("Tag", "The student was changed")
                Toast.makeText(
                    this@MainActivity,
                    "Callback called on main activity",
                    Toast.LENGTH_SHORT
                ).show()
                if (changeSet == null || changeSet.isDeleted) {
                    Log.i("Tag", "The student was deleted")
                    return
                }

                for (fieldName in changeSet.changedFields) {
                    text_view.text = student.mark.toString()
                }
            }
        }

        text_view.text = student.mark.toString()
        student.addChangeListener(listener)

        button.setOnClickListener {
            startActivity(Intent(this, SecondActivity::class.java))
        }

    }

}