package fd.firad.iqchallenger.activity

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import fd.firad.iqchallenger.R
import fd.firad.iqchallenger.model.Result
import fd.firad.iqchallenger.viewmodel.QuizViewModel

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: QuizViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        viewModel.getQuiz().observe(this) { result ->
            when (result) {
                is Result.Success -> {

                    val data = result.quiz
                    Toast.makeText(this@MainActivity, data.toString(), Toast.LENGTH_SHORT).show()

                }

                is Result.Loading -> {
                    Toast.makeText(this@MainActivity, "Loading", Toast.LENGTH_SHORT).show()
                }

                is Result.Error -> {
                    val exception = result.exception
                    Toast.makeText(this@MainActivity, "${exception.message}", Toast.LENGTH_SHORT)
                        .show()
                }
            }

        }
    }
}
