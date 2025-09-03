package com.chat.serwer.Interfaces;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chat.serwer.DBO.ContainerEasKey_DBO;

@Repository
public interface ContainerEasKeyRepository extends JpaRepository<ContainerEasKey_DBO, ContainerEasKey_DBO.ContainerEasKeyId> {
    
    @Query("SELECT cek FROM ContainerEasKey_DBO cek WHERE cek.containerId = :containerId AND cek.userId = :userId")
    Optional<ContainerEasKey_DBO> findByContainerIdAndUserId(@Param("containerId") String containerId, @Param("userId") String userId);
    
    @Query("SELECT cek FROM ContainerEasKey_DBO cek WHERE cek.containerId = :containerId")
    List<ContainerEasKey_DBO> findByContainerId(@Param("containerId") String containerId);
    
    @Query("SELECT cek FROM ContainerEasKey_DBO cek WHERE cek.userId = :userId")
    List<ContainerEasKey_DBO> findByUserId(@Param("userId") String userId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM ContainerEasKey_DBO cek WHERE cek.containerId = :containerId")
    void deleteByContainerId(@Param("containerId") String containerId);
    
    @Modifying
    @Transactional
    @Query("DELETE FROM ContainerEasKey_DBO cek WHERE cek.containerId = :containerId AND cek.userId = :userId")
    void deleteByContainerIdAndUserId(@Param("containerId") String containerId, @Param("userId") String userId);
}
