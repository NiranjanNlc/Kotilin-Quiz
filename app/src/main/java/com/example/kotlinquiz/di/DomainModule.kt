package com.example.kotlinquiz.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.Dispatchers
import org.niranjan.quiz.repo.QuestionRepository
import org.niranjan.quiz.repo.QuizRepository
import org.niranjan.quiz.repo.ScoreRepository
import org.niranjan.quiz.usecase.CreateQuizUseCase
import org.niranjan.quiz.usecase.QuestionAnswerUseCase


@Module
@InstallIn(SingletonComponent::class)
class DomainModule {

    @Provides
    fun providesCreateQuestionUseCase( quizRepository: QuizRepository,
                                       questionRepository: QuestionRepository ): CreateQuizUseCase
    = CreateQuizUseCase(quizRepository , questionRepository )
    //proivdes QuestionAnswerUseCase
    @Provides
    fun provideQuestionAnsweruseCase( quizRepository: QuizRepository,
                                      questionRepository: QuestionRepository,
                                        scoreRepository: ScoreRepository
    ) = QuestionAnswerUseCase(quizRepository,questionRepository,scoreRepository)
}