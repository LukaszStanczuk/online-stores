package com.onlinestore.user;

import com.onlinestore.user.adresses.Address;
import com.onlinestore.user.role.UserRole;
import com.onlinestore.user.role.UserRoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserCreateServiceTest {

    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userCreateService;
    @Autowired
    UserRoleRepository userRoleRepository;

    @BeforeEach
    void init() {
        userRepository.deleteAll();
    }

    @Test
    void userCreateServiceTest() {
        //given
        when(userRepository.save(any(User.class))).thenReturn(new User());

        //when
        UserDto result = userCreateService.createUser(UserDto.builder()
                .username("user")
                .password("user")
                .avatar("foto")
                .contactPreference("email")
                .address(new Address())
                .userRole(new UserRole())
                .build());

        //then
        verify(userRepository).save(any(User.class));
    }

}
