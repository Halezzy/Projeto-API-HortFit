package org.hugoalessi.hortfit.service

import org.hugoalessi.hortfit.model.User
import org.hugoalessi.hortfit.repository.UserRepository
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(private val userRepository: UserRepository) {

    // Salva um novo usuário ou atualiza um existente
    fun saveUser(user: User): User = userRepository.save(user)

    // Recupera um usuário por ID
    fun getUserById(id: Long): User {
        return userRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: $id")
            }
    }

    // Recupera um usuário pelo email
    fun findUserByEmail(email: String): User? = userRepository.findByEmail(email)

    // Recupera todos os usuários
    fun getAllUsers(): List<User> = userRepository.findAll()

    // Atualiza um usuário, se existir
    fun updateUser(id: Long, user: User): User {
        val existingUser = userRepository.findById(id)
            .orElseThrow {
                ResponseStatusException(HttpStatus.NOT_FOUND, "User not found with ID: $id")
            }

        // Atualiza os campos necessários
        existingUser.name = user.name
        existingUser.email = user.email
        existingUser.password = user.password

        return userRepository.save(existingUser) // Salva o usuário atualizado
    }

    // Deleta um usuário por ID
    fun deleteUser(id: Long): Boolean {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            true // Retorna true se o usuário foi deletado com sucesso
        } else {
            false // Retorna false se o usuário não foi encontrado
        }
    }
}
