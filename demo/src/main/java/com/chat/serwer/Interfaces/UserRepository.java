package com.chat.serwer.Interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chat.serwer.DBO.User_DBO;

@Repository
public interface UserRepository extends JpaRepository<User_DBO, Long> {
    // Custom query methods
    User_DBO findByName(String name);
    Boolean existsByName(String name);
    void deleteByName(String name);
    
    @Query("SELECT u.id FROM User_DBO u WHERE u.name = :name")
    Long findIdByName(@Param("name") String name);
    
    Optional<User_DBO> findById(Long id);
}
