package org.hugoalessi.hortfit.controller

import org.hugoalessi.hortfit.model.User
import org.hugoalessi.hortfit.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(private val userService: UserService) {

    // Endpoint POST - Criar um novo usuário
    @PostMapping
    fun createUser(@RequestBody user: User): ResponseEntity<User> {
        val savedUser = userService.saveUser(user)
        return ResponseEntity.status(HttpStatus.CREATED).body(savedUser) // 201 Created
    }

        @PostMapping("/login")
        fun loginUser(@RequestBody loginRequest: LoginRequest): ResponseEntity<User> {
            val user = userService.findUserByEmail(loginRequest.email)
            return if (user != null && user.password == loginRequest.password) {
                ResponseEntity.ok(user)
            } else {
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null)
            }
        }


    // Endpoint GET - Buscar um usuário por ID
    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        return try {
            val user = userService.getUserById(id)
            ResponseEntity.ok(user) // 200 OK
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) // 404 Not Found
        }
    }

    // Endpoint GET - Buscar todos os usuários
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = userService.getAllUsers()
        return if (users.isNotEmpty()) {
            ResponseEntity.ok(users) // 200 OK
        } else {
            ResponseEntity.noContent().build() // 204 No Content
        }
    }

    // Endpoint PUT - Atualizar um usuário existente
    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @RequestBody updatedUser: User): ResponseEntity<User> {
        return try {
            val updated = userService.updateUser(id, updatedUser)
            ResponseEntity.ok(updated) // 200 OK
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body(null) // 404 Not Found
        }
    }

    // Endpoint DELETE - Excluir um usuário pelo ID
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        return try {
            // Chama o serviço para tentar excluir o usuário
            val deleted = userService.deleteUser(id)

            // Se o usuário foi deletado com sucesso, retorna 204 No Content
            if (deleted) {
                ResponseEntity.noContent().build() // 204 No Content
            } else {
                // Se o usuário não foi encontrado, retorna 404 Not Found
                ResponseEntity.status(HttpStatus.NOT_FOUND).build() // 404 Not Found
            }
        } catch (e: Exception) {
            // Caso ocorra qualquer outro erro (por exemplo, erro de banco de dados)
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .build() // 500 Internal Server Error
        }
    }
}
