package fd.firad.iqchallenger.model

data class Question(
    val answers: List<String>?=null,
    val correctIndex: Int?=null,
    val question: String?=null
)