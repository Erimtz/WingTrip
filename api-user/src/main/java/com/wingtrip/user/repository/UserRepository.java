package com.wingtrip.user.repository;

import com.wingtrip.user.dto.UserDTO;
import com.wingtrip.user.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserDTO> findByUsername(String username);

    boolean existByEmail(String email);
}
