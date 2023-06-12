package spring.boot.mislav.sirac.web;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import spring.boot.mislav.sirac.security.jwt.TokenProvider;

@SpringJUnitConfig
class LoginControllerTest {

    @InjectMocks
    private LoginController loginController;

    @Mock
    private TokenProvider tokenProvider;

    @Mock
    private AuthenticationManager authenticationManager;

    @Test
    void authenticate() {
        // Arrange
        LoginController.LoginDTO loginDTO = new LoginController.LoginDTO();
        loginDTO.setUsername("testuser");
        loginDTO.setPassword("testpassword");

        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword());

        Authentication authentication = mock(Authentication.class);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwtToken = "testJWTToken";
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Authorization", "Bearer " + jwtToken);

        ResponseEntity<LoginController.JWTToken> expectedResponse =
                new ResponseEntity<>(new LoginController.JWTToken(jwtToken), httpHeaders, HttpStatus.OK);

        when(authenticationManager.authenticate(authenticationToken)).thenReturn(authentication);
        when(tokenProvider.createToken(authentication)).thenReturn(jwtToken);

        // Act
        ResponseEntity<LoginController.JWTToken> actualResponse = loginController.authenticate(loginDTO);

        // Assert
        assertEquals(expectedResponse.getStatusCode(), actualResponse.getStatusCode());
        assertEquals(expectedResponse.getHeaders(), actualResponse.getHeaders());
        assertEquals(expectedResponse.getBody().getToken(), actualResponse.getBody().getToken());

        verify(authenticationManager).authenticate(authenticationToken);
        verify(tokenProvider).createToken(authentication);
    }
}
