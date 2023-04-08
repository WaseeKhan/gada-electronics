package com.lucifer.gada.electronics.repositories;

import com.lucifer.gada.electronics.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
