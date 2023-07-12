package fd.firad.iqchallenger.model

sealed class Result() {
    class Success(val quiz: QuizResponse) : Result()
    class Error(val exception: Exception) : Result()
    class Loading : Result()
}
