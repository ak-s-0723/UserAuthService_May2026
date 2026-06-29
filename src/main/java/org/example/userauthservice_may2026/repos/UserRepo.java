package org.example.userauthservice_may2026.repos;

import org.example.userauthservice_may2026.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Long> {
}
