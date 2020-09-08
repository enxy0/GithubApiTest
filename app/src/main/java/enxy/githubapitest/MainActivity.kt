package enxy.githubapitest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import enxy.githubapitest.ui.main.repositories.RepositoriesFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, RepositoriesFragment.newInstance())
                    .commitNow()
        }
    }
}