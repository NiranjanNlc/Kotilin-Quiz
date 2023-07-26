package org.niranjan.quiz.modal

import java.util.UUID

data class UserEntity(
    val userId: String = UUID.randomUUID().toString(),
    val username: String,
    val email: String = "nepalikanchi@gmail.com",
    val password: String = "********",
    val profilePicture: String? = "https://res.cloudinary.com/practicaldev/image/fetch/s--W8I17c9H--/c_fill,f_auto,fl_progressive,h_320,q_auto,w_320/https://dev-to-uploads.s3.amazonaws.com/uploads/user/profile_image/577981/6afec0bf-fa5e-401b-a390-565d09e3982b.jpeg"
)
