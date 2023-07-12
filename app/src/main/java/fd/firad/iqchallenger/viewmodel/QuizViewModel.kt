package fd.firad.iqchallenger.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import fd.firad.iqchallenger.model.Result
import fd.firad.iqchallenger.repository.QuizRepository
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(private val repository: QuizRepository) : ViewModel() {
    fun getQuiz(): LiveData<Result> {
        return repository.getQuiz()
    }

}