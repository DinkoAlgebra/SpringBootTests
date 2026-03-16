package hr.java.web.helloworld.controller;

import hr.java.web.helloworld.domain.RefreshToken;
import hr.java.web.helloworld.domain.UserInfo;
import hr.java.web.helloworld.dto.AuthRequestDTO;
import hr.java.web.helloworld.dto.JwtResponseDTO;
import hr.java.web.helloworld.dto.RefreshTokenRequestDTO;
import hr.java.web.helloworld.service.JwtService;
import hr.java.web.helloworld.service.RefreshTokenService;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class AuthControllerTest {

  @Mock
  private AuthenticationManager authenticationManager;

  @Mock
  private JwtService jwtService;

  @Mock
  private RefreshTokenService refreshTokenService;

  @InjectMocks
  private AuthController authController;

  private AuthRequestDTO authRequestDTO;

  private RefreshTokenRequestDTO refreshTokenRequestDTO;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);

    authRequestDTO = new AuthRequestDTO();
    authRequestDTO.setUsername("user");
    authRequestDTO.setPassword("password");

    refreshTokenRequestDTO = new RefreshTokenRequestDTO();
    refreshTokenRequestDTO.setToken("refresh-token");
  }
// simulate a successful login...authenticationManager return an authenticated object.
// services return a fake JWT string ("jwt-token") and a fake Refresh Token object.
  @Test
  void testAuthenticateAndGetToken_Success() {
    Authentication authentication = mock(Authentication.class);
    when(authentication.isAuthenticated()).thenReturn(true);
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(authentication);
    when(jwtService.generateToken(anyString())).thenReturn("jwt-token");

    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setToken("refresh-token");
    when(refreshTokenService.createRefreshToken(anyString())).thenReturn(refreshToken);

    JwtResponseDTO response = authController.authenticateAndGetToken(authRequestDTO);

    assertNotNull(response);
    assertEquals("jwt-token", response.getAccessToken());
    assertEquals("refresh-token", response.getToken());
    verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(jwtService, times(1)).generateToken("user");
    verify(refreshTokenService, times(1)).createRefreshToken("user");
  }

  @Test
  void testAuthenticateAndGetToken_Failure() {
    when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenThrow(new UsernameNotFoundException("invalid user request..!!"));

    assertThrows(UsernameNotFoundException.class, () -> authController.authenticateAndGetToken(authRequestDTO));

    verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
    verify(jwtService, never()).generateToken(anyString());
    verify(refreshTokenService, never()).createRefreshToken(anyString());
  }

  @Test
  void testRefreshToken_Success() {
    RefreshToken refreshToken = new RefreshToken();
    refreshToken.setToken("refresh-token");
    refreshToken.setExpiryDate(LocalDateTime.now().plusHours(8).toInstant(ZoneOffset.ofHours(0)));
    UserInfo userInfo = new UserInfo(1l, "user", "password", Set.of());
    refreshToken.setUserInfo(userInfo);

    //Find token in DB, verify it hasn't expired and generate a new JWT.
    when(refreshTokenService.findByToken(anyString())).thenReturn(Optional.of(refreshToken));
    when(refreshTokenService.verifyExpiration(any(RefreshToken.class))).thenReturn(refreshToken);
    when(jwtService.generateToken(anyString())).thenReturn("new-jwt-token");

    JwtResponseDTO response = authController.refreshToken(refreshTokenRequestDTO);

    assertNotNull(response);
    assertEquals("new-jwt-token", response.getAccessToken());
    assertEquals("refresh-token", response.getToken());
    verify(refreshTokenService, times(1)).findByToken("refresh-token");
    verify(refreshTokenService, times(1)).verifyExpiration(refreshToken);
    verify(jwtService, times(1)).generateToken("user");
  }

  @Test
  void testRefreshToken_Failure() {
    when(refreshTokenService.findByToken(anyString())).thenReturn(Optional.empty());

    assertThrows(RuntimeException.class, () -> authController.refreshToken(refreshTokenRequestDTO));

    verify(refreshTokenService, times(1)).findByToken("refresh-token");
    verify(refreshTokenService, never()).verifyExpiration(any(RefreshToken.class));
    verify(jwtService, never()).generateToken(anyString());
  }
}

