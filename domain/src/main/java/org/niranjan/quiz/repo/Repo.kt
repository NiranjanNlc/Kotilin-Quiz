package org.niranjan.quiz.repo

class Repo {
}

/*
interface UserRepository {
    fun registerUser(user: User)
    fun loginUser(username: String, password: String): User?
    fun updateUser(user: User)
    fun deleteUser(user: User)
    fun getUserById(userId: String): User?
    fun getUserQuizzes(userId: String): List<Quiz>
    fun getUserScores(userId: String): List<Score>
}

interface QuizRepository {
    fun createQuiz(quiz: Quiz)
    fun updateQuiz(quiz: Quiz)
    fun deleteQuiz(quizId: String)
    fun getQuizById(quizId: String): Quiz?
    fun getQuizzesByUser(userId: String): List<Quiz>
    fun getQuizScores(quizId: String): List<Score>
}

interface QuestionRepository {
    fun createQuestion(question: Question)
    fun updateQuestion(question: Question)
    fun deleteQuestion(questionId: String)
    fun getQuestionById(questionId: String): Question?
    fun getQuestionAnswers(questionId: String): List<Answer>
}

interface AnswerRepository {
    fun createAnswer(answer: Answer)
    fun updateAnswer(answer: Answer)
    fun deleteAnswer(answerId: String)
    fun getAnswerById(answerId: String): Answer?
}

interface ScoreRepository {
    fun saveScore(score: Score)
    fun getScoreByQuizAndUser(quizId: String, userId: String): Score?
    fun getScoresByQuiz(quizId: String): List<Score>
    fun getScoresByUser(userId: String): List<Score>
}

interface HighScoreRepository {
    fun saveHighScore(highScore: HighScore)
    fun getTopHighScores(limit: Int): List<HighScore>
    fun getHighScoresByUser(userId: String): List<HighScore>
}

interface FileRepository {
    fun uploadFile(file: File)
    fun getFileById(fileId: String): File?
    fun deleteFile(fileId: String)
}
*/