package com.chat.serwer.Interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chat.serwer.DBO.UserContainerRepository;

@Repository
public interface UserContainerRepositoryInterface extends JpaRepository<UserContainerRepository, Long> {
    
    @Query("SELECT uc FROM UserContainerRepository uc WHERE uc.userId = :userId")
    List<UserContainerRepository> findByUserId(@Param("userId") String userId);
    
    @Query("SELECT uc FROM UserContainerRepository uc WHERE uc.containerId = :containerId")
    List<UserContainerRepository> findByContainerId(@Param("containerId") String containerId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM UserContainerRepository uc WHERE uc.containerId = :containerId")
    void deleteByContainerId(@Param("containerId") String containerId);
}
