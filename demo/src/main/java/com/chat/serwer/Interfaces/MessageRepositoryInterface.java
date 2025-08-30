package com.chat.serwer.Interfaces;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.chat.serwer.DBO.Message_DBO;

@Repository
public interface MessageRepositoryInterface extends JpaRepository<Message_DBO, Long> {
    
    @Query("SELECT m FROM Message_DBO m WHERE m.containerId = :containerId ORDER BY m.sentAt ASC")
    List<Message_DBO> findByContainerIdOrderBySentAt(@Param("containerId") String containerId);
    
    @Query("SELECT m FROM Message_DBO m WHERE m.containerId = :containerId AND m.senderId = :senderId ORDER BY m.sentAt ASC")
    List<Message_DBO> findByContainerIdAndSenderIdOrderBySentAt(@Param("containerId") String containerId, @Param("senderId") String senderId);
    
    @Modifying
    @Transactional
    @Query("UPDATE Message_DBO m SET m.readByUser1 = true WHERE m.containerId = :containerId AND m.senderId != :userId")
    void markAsReadByUser1(@Param("containerId") String containerId, @Param("userId") String userId);
    
    @Modifying
    @Transactional
    @Query("UPDATE Message_DBO m SET m.readByUser2 = true WHERE m.containerId = :containerId AND m.senderId != :userId")
    void markAsReadByUser2(@Param("containerId") String containerId, @Param("userId") String userId);
    
    @Query("SELECT COUNT(m) FROM Message_DBO m WHERE m.containerId = :containerId AND m.senderId != :userId AND ((m.readByUser1 = false AND :userPosition = 1) OR (m.readByUser2 = false AND :userPosition = 2))")
    Long countUnreadMessages(@Param("containerId") String containerId, @Param("userId") String userId, @Param("userPosition") Integer userPosition);
    
    void deleteByContainerId(String containerId);
}
