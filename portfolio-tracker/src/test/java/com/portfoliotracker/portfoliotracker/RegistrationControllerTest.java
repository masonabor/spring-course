package com.portfoliotracker.portfoliotracker.controllers;

import com.portfoliotracker.portfoliotracker.DTO.UserRegistrationDTO;
import com.portfoliotracker.portfoliotracker.events.OnRegistrationCompleteEvent;
import com.portfoliotracker.portfoliotracker.exceptions.UserRegistrationException;
import com.portfoliotracker.portfoliotracker.models.User;
import com.portfoliotracker.portfoliotracker.models.VerificationToken;
import com.portfoliotracker.portfoliotracker.services.UserServiceInterface;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = RegistrationController.class)
class RegistrationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserServiceInterface userService;

    @MockBean
    private ApplicationEventPublisher eventPublisher;

    @MockBean
    private MessageSource messages;

    @Test
    void shouldShowRegistrationForm() throws Exception {
        mockMvc.perform(get("/registration/"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    void shouldRegisterUserSuccessfully() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");

        when(userService.registerNewUserAccount(any(UserRegistrationDTO.class))).thenReturn(user);

        mockMvc.perform(post("/registration/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "test@example.com")
                        .param("password", "12345678"))
                .andExpect(status().isOk())
                .andExpect(view().name("successRegister"))
                .andExpect(model().attributeExists("user"));

        // перевіримо, що івент був опублікований
        ArgumentCaptor<OnRegistrationCompleteEvent> captor = ArgumentCaptor.forClass(OnRegistrationCompleteEvent.class);
        verify(eventPublisher, times(1)).publishEvent(captor.capture());
    }

    @Test
    void shouldReturnErrorIfUserExists() throws Exception {
        when(userService.registerNewUserAccount(any(UserRegistrationDTO.class)))
                .thenThrow(new UserRegistrationException("User already exists"));

        mockMvc.perform(post("/registration/register")
                        .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                        .param("email", "test@example.com")
                        .param("password", "12345678"))
                .andExpect(status().isOk())
                .andExpect(view().name("registration"))
                .andExpect(model().attributeExists("globalError"));
    }

    @Test
    void shouldConfirmRegistrationSuccessfully() throws Exception {
        User user = new User();
        user.setEmail("test@example.com");

        VerificationToken token = new VerificationToken();
        token.setUser(user);
        token.setExpiryDate(new Date(System.currentTimeMillis() + 10000)); // ще дійсний

        when(userService.getVerificationToken("validToken")).thenReturn(token);

        mockMvc.perform(get("/registration/registrationConfirm")
                        .param("token", "validToken"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login.html?lang=en"));

        verify(userService).saveRegisteredUser(user);
    }

    @Test
    void shouldRejectExpiredToken() throws Exception {
        when(messages.getMessage(eq("auth.message.expired"), any(), any(Locale.class)))
                .thenReturn("Token expired");

        VerificationToken expiredToken = new VerificationToken();
        expiredToken.setUser(new User());
        expiredToken.setExpiryDate(new Date(System.currentTimeMillis() - 1000)); // прострочений

        when(userService.getVerificationToken("expired")).thenReturn(expiredToken);

        mockMvc.perform(get("/registration/registrationConfirm")
                        .param("token", "expired"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("/badUser.html*"));
    }
}
