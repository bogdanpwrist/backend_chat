package com.chat.serwer.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chat.serwer.DBO.ContainerEasKey_DBO;
import com.chat.serwer.Interfaces.ContainerEasKeyRepository;

@Service
public class ContainerEasKeyService {
    
    @Autowired
    private ContainerEasKeyRepository containerEasKeyRepository;
    
    /**
     * Store an encrypted AES key for a specific user and container
     */
    public ContainerEasKey_DBO setUserEasKey(String containerId, String userId, String encryptedEasKey) {
        ContainerEasKey_DBO easKey = new ContainerEasKey_DBO(containerId, userId, encryptedEasKey);
        return containerEasKeyRepository.save(easKey);
    }
    
    /**
     * Get the encrypted AES key for a specific user and container
     */
    public Optional<String> getUserEasKey(String containerId, String userId) {
        Optional<ContainerEasKey_DBO> easKey = containerEasKeyRepository.findByContainerIdAndUserId(containerId, userId);
        return easKey.map(ContainerEasKey_DBO::getEncryptedEasKey);
    }
    
    /**
     * Get all encrypted AES keys for a specific container (all users)
     */
    public List<ContainerEasKey_DBO> getAllEasKeysForContainer(String containerId) {
        return containerEasKeyRepository.findByContainerId(containerId);
    }
    
    /**
     * Get all encrypted AES keys for a specific user (all containers they have access to)
     */
    public List<ContainerEasKey_DBO> getAllEasKeysForUser(String userId) {
        return containerEasKeyRepository.findByUserId(userId);
    }
    
    /**
     * Remove a user's access to a container by deleting their encrypted AES key
     */
    public void removeUserFromContainer(String containerId, String userId) {
        containerEasKeyRepository.deleteByContainerIdAndUserId(containerId, userId);
    }
    
    /**
     * Remove all AES keys for a container (when deleting the container)
     */
    public void removeAllKeysForContainer(String containerId) {
        containerEasKeyRepository.deleteByContainerId(containerId);
    }
    
    /**
     * Check if a user has access to a container
     */
    public boolean userHasAccessToContainer(String containerId, String userId) {
        return containerEasKeyRepository.findByContainerIdAndUserId(containerId, userId).isPresent();
    }
}
