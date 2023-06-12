package spring.boot.mislav.sirac.web;

import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import spring.boot.mislav.sirac.user.UserDTO;
import spring.boot.mislav.sirac.user.UserService;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

class UserControllerTest {

    @Mock
    private UserService userService;

    @Test
    void getCurrentUser_ReturnsCurrentUser() {
        // Mock the necessary dependencies
        UserController userController = new UserController(userService);
        UserDTO currentUser = new UserDTO();
        currentUser.setFirstName("John");
        currentUser.setLastName("Doe");
        currentUser.setUsername("john123");
        when(userService.findByUsername("john123")).thenReturn(Optional.of(currentUser));

        // Call the method under test
        ResponseEntity<UserDTO> response = userController.getCurrentUser();

        // Assert the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(currentUser, response.getBody());
    }

    @Test
    void getCurrentUser_ReturnsExpectationFailedWhenCurrentUserNotFound() {
        // Mock the necessary dependencies
        UserController userController = new UserController(userService);
        when(userService.findByUsername("john123")).thenReturn(Optional.empty());

        // Call the method under test
        ResponseEntity<UserDTO> response = userController.getCurrentUser();

        // Assert the result
        assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
    }

}
