package de.trusteq.usermanager.repositories

import de.trusteq.usermanager.models.User
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserRepository : CoroutineCrudRepository<User, Int> {
    fun findByNameContaining(name: String): Flow<User>

    fun findByCompanyId(companyId: Int): Flow<User>

    @Query("SELECT * FROM application.users WHERE email = :email")
    fun findByEmail(email: String): Flow<User>
}
