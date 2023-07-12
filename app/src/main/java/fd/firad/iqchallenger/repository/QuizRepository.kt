package fd.firad.iqchallenger.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import fd.firad.iqchallenger.model.Question
import fd.firad.iqchallenger.model.QuizResponse
import fd.firad.iqchallenger.model.Result
import javax.inject.Inject

class QuizRepository @Inject constructor(private val reference: DatabaseReference) {

    fun getQuiz(): LiveData<Result> {
        val resultLiveData = MutableLiveData<Result>()
        resultLiveData.value = Result.Loading()
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
                    resultLiveData.value = Result.Success(quizResponse)

                } else {
                    resultLiveData.value = Result.Error(Exception("Failed"))
                }

            }

            override fun onCancelled(error: DatabaseError) {
                resultLiveData.value = Result.Error(Exception(error.message))
            }

        })
        return resultLiveData
    }
}