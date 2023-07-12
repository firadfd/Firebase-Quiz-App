package fd.firad.iqchallenger.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import dagger.hilt.android.lifecycle.HiltViewModel
import fd.firad.iqchallenger.model.Question
import fd.firad.iqchallenger.model.QuizResponse
import fd.firad.iqchallenger.model.Result
import javax.inject.Inject

@HiltViewModel
class QuizViewModel @Inject constructor(private val reference: DatabaseReference) : ViewModel() {
    private val _data = MutableLiveData<Result>()

    val data: LiveData<Result>
        get() = _data

    fun getQuiz(): LiveData<Result> {

        _data.value = Result.Loading()
        reference.addValueEventListener(object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {

                if (snapshot.exists()) {

                    val questionList = mutableListOf<Question>()


                    for (childSnapshot in snapshot.children) {
                        val questionSnapshot = childSnapshot.getValue(Question::class.java)
                        Log.e("TAG", questionSnapshot.toString())
                        if (questionSnapshot != null) {
                            questionList.add(questionSnapshot)
                        }
                    }
                    val quizResponse = QuizResponse(questionList.toList())
                    _data.value = Result.Success(quizResponse)

                } else {
                    _data.value = Result.Error(Exception("Failed"))
                }

            }

            override fun onCancelled(error: DatabaseError) {
                _data.value = Result.Error(Exception(error.message))
            }

        })
        return _data
    }

}