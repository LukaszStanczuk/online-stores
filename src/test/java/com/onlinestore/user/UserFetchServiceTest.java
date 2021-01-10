package com.onlinestore.user;

import com.onlinestore.user.adresses.Address;
import com.onlinestore.user.adresses.AddressRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
class UserFetchServiceTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressRepository addressRepository;

    Address savedAddress;
    User savedUser;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        addressRepository.deleteAll();

        Address address = new Address();
        address.setCountry("Polska");
        address.setCity("Gdansk");
        address.setStreet("Grunwaldzka");
        address.setApartmentNumber("22");
        address.setHouseNumber("11");
        address.setPostalCode("80800");
        savedAddress = addressRepository.save(address);

        User user = new User();
        user.setUsername("user");
        user.setPassword("user");
        user.setUserRole(UserRole.ROLE_USER);
        user.setAvatar("avatar");
        user.setAddress(savedAddress);
        user.setContactPreference("email");
        savedUser = userRepository.save(user);

    }

    @Test
    void fetchUserDetails_returnsDetailsOfUser() throws Exception {
        //given

        Long userId = savedUser.getId();

        MockHttpServletRequestBuilder request = get("/user/" + userId)
                .contentType(MediaType.APPLICATION_JSON);

        //when
        MvcResult result = mockMvc.perform(request).andReturn();

        //then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        List<User> users = userRepository.findAll();
        assertThat(users.size()).isEqualTo(1);
        assertThat(users.get(0)).satisfies(p -> {
            assertThat(p.getId()).isNotNull();
            assertThat(p.getAddress()).isNotNull();
            assertThat(p.getAvatar()).isNotNull();
            assertThat(p.getUsername()).isNotNull();
            assertThat(p.getPassword()).isNotNull();
            assertThat(p.getUserRole()).isNotNull();
            assertThat(p.getContactPreference()).isNotNull();
        });
    }

    @Test
    void fetchUserDetails_whenRepositoryIsEmpty_returnInformationAboutEmptyRepository() throws Exception {
        //given
        List<User> users = userRepository.findAll();
        MockHttpServletRequestBuilder request = get("/user/" + users.size() + 1)
                .contentType(MediaType.APPLICATION_JSON);

        //when
        MvcResult result = mockMvc.perform(request).andReturn();

        //then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.NOT_FOUND.value());
    }


    @Test
    void fetchAllUsersDetails_returnsDetailsOfAllUsers() throws Exception {
        //given
        MockHttpServletRequestBuilder request = get("/categories")
                .contentType(MediaType.APPLICATION_JSON);

        //when
        MvcResult result = mockMvc.perform(request).andReturn();

        //then
        MockHttpServletResponse response = result.getResponse();
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//        List<User> users = userRepository.findAll();
//        assertThat(users.size()).isEqualTo(1);
//        assertThat(users.get(0)).satisfies(p -> {
//            assertThat(p.getId()).isNotNull();
//            assertThat(p.getAddress()).isNotNull();
//            assertThat(p.getAvatar()).isNotNull();
//            assertThat(p.getUsername()).isNotNull();
//            assertThat(p.getPassword()).isNotNull();
//            assertThat(p.getUserRole()).isNotNull();
//            assertThat(p.getContactPreference()).isNotNull();
//        });
    }
}
