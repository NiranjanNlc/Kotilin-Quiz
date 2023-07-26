package org.niranjan.quiz.repo

import org.niranjan.quiz.modal.FileEntity


interface FileRepository {
    fun uploadFile(file: FileEntity)
    fun getFileById(fileId: String): FileEntity?
    fun deleteFile(fileId: String)
}