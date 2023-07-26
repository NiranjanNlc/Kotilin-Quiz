package org.niranjan.quiz.usecase

import org.niranjan.quiz.modal.UserEntity
import org.niranjan.quiz.repo.UserRepository
import java.util.UUID

class RegisterUserUseCase(
    private val userRepository: UserRepository
) {
    fun registerUser(username: String): UserEntity {
        val user = UserEntity(generateUserId(), username, "", "", null)
        userRepository.registerUser(user)
        return user
    }

    private fun generateUserId(): String {
        // Generate a unique user ID using an appropriate algorithm or library
        // Example: UUID.randomUUID().toString()
        return UUID.randomUUID().toString()
    }
}
