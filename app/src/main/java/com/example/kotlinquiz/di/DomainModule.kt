package com.example.kotlinquiz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import org.niranjan.quiz.repo.QuestionRepository
import org.niranjan.quiz.repo.QuizRepository
import org.niranjan.quiz.repo.ScoreRepository
import org.niranjan.quiz.repo.UserRepository
import org.niranjan.quiz.usecase.CreateQuizUseCase
import org.niranjan.quiz.usecase.QuestionAnswerUseCase
import org.niranjan.quiz.usecase.SubmitQuizResultUseCase


@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun providesCreateQuestionUseCase( quizRepository: QuizRepository,
                                       questionRepository: QuestionRepository ,
                                        scoreRepository: ScoreRepository): CreateQuizUseCase
    = CreateQuizUseCase(quizRepository , questionRepository , scoreRepository )
    //proivdes QuestionAnswerUseCase
    @Provides
    fun provideQuestionAnsweruseCase( quizRepository: QuizRepository,
                                      questionRepository: QuestionRepository,
                                        scoreRepository: ScoreRepository
    ) = QuestionAnswerUseCase(quizRepository,questionRepository,scoreRepository)
    @Provides
    fun provideFinalResultuseCase( quizRepository: QuizRepository,
                                      scoreRepository: ScoreRepository
    ) = SubmitQuizResultUseCase(quizRepository,scoreRepository)
}