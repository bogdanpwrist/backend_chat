package com.chat.serwer.Interfaces;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.chat.serwer.DBO.ContainerRepository;

@Repository
public interface ContainerRepositoryInterface extends JpaRepository<ContainerRepository, String> {
    
    @Query("SELECT c FROM ContainerRepository c WHERE c.id = :containerId")
    Optional<ContainerRepository> findByContainerId(@Param("containerId") String containerId);
    
    void deleteById(String id);
}
