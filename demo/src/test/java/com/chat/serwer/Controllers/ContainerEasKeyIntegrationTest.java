package com.chat.serwer.Controllers;

import com.chat.serwer.Services.ContainerEasKeyService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
public class ContainerEasKeyIntegrationTest {

    @Autowired
    private ContainerEasKeyService containerEasKeyService;

    @Test
    public void testSetAndGetUserEasKey() {
        String containerId = "test_container_123";
        String userId1 = "user1";
        String userId2 = "user2";
        String encryptedKey1 = "ENCRYPTED_WITH_USER1_RSA_KEY";
        String encryptedKey2 = "ENCRYPTED_WITH_USER2_RSA_KEY";

        // Test setting EAS key for user1
        containerEasKeyService.setUserEasKey(containerId, userId1, encryptedKey1);

        // Test setting EAS key for user2 (same container, different encryption)
        containerEasKeyService.setUserEasKey(containerId, userId2, encryptedKey2);

        // Verify through service layer
        var key1 = containerEasKeyService.getUserEasKey(containerId, userId1);
        var key2 = containerEasKeyService.getUserEasKey(containerId, userId2);

        assertTrue(key1.isPresent());
        assertTrue(key2.isPresent());
        assertEquals(encryptedKey1, key1.get());
        assertEquals(encryptedKey2, key2.get());

        // Verify different users get different encrypted keys
        assertNotEquals(key1.get(), key2.get());

        // Test user access check
        assertTrue(containerEasKeyService.userHasAccessToContainer(containerId, userId1));
        assertTrue(containerEasKeyService.userHasAccessToContainer(containerId, userId2));
        assertFalse(containerEasKeyService.userHasAccessToContainer(containerId, "nonexistent_user"));
    }

    @Test
    public void testGetNonExistentEasKey() {
        var key = containerEasKeyService.getUserEasKey("nonexistent", "user");
        assertFalse(key.isPresent());
    }

    @Test
    public void testRemoveUserFromContainer() {
        String containerId = "test_container_456";
        String userId = "test_user";
        String encryptedKey = "ENCRYPTED_KEY";

        // Set key
        containerEasKeyService.setUserEasKey(containerId, userId, encryptedKey);
        assertTrue(containerEasKeyService.userHasAccessToContainer(containerId, userId));

        // Remove user
        containerEasKeyService.removeUserFromContainer(containerId, userId);
        assertFalse(containerEasKeyService.userHasAccessToContainer(containerId, userId));
    }
}
