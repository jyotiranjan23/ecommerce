package com.ecommerce.user.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ecommerce.user.dto.SecureUser;
import com.ecommerce.user.dto.UserRequest;
import com.ecommerce.user.dto.UserResponse;
import com.ecommerce.user.entity.Address;
import com.ecommerce.user.entity.User;
import com.ecommerce.user.repository.UserRepository;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@NoArgsConstructor
public class UserService implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(SecureUser secureUser){
        User user = new User();
        user.setName(secureUser.getName());
        user.setPassword(passwordEncoder.encode(secureUser.getPassword()));
        user.setEmail(secureUser.getEmail());
        user.setRole("User");
        User savedUser = userRepository.save(user);
        return savedUser;
    }

    public UserResponse getUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User does not exist for this userId: "+id));
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        userResponse.setAddresses(user.getAddresses());
        return userResponse;
    }
    public List<UserResponse> getAllUser(){
       List<User> userList = userRepository.findAll();
       if(!userList.isEmpty()){
            return userList.stream().map(user -> mapToUserResponse(user)).toList();
        }
        return new ArrayList<>();
    }

    public UserResponse updateUser(Long id, UserRequest userRequest){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User does not exist for this userId: "+id));
        user.setName(userRequest.getName());
        user.setEmail(userRequest.getEmail());
        List<Address> addressList = userRequest.getAddresses();
        if (addressList != null) {
            user.getAddresses().clear();
            for(Address address: addressList){
            address.setUser(user);
            user.getAddresses().add(address);
            }
        }
        userRepository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setRole(user.getRole());
        userResponse.setAddresses(user.getAddresses());
        return userResponse;
    }

    public void deleteUser(Long id){
        User user = userRepository.findById(id).orElseThrow(() -> new RuntimeException("User does not exist for this userId: "+id));
        userRepository.delete(user);
    }
    private UserResponse mapToUserResponse(User user){
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setName(user.getName());
        userResponse.setEmail(user.getEmail());
        userResponse.setAddresses(user.getAddresses());
        return userResponse;
    }

    @Override
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findByName(username).isEmpty() ? null : userRepository.findByName(username).get();
        if(user == null){
            throw new UsernameNotFoundException("User not found");
        }

        return org.springframework.security.core.userdetails.User
                .builder()
                .username(user.getName())
                .password(user.getPassword())
                .authorities("ROLE_" + user.getRole())
                .build();
    }

    public User findByName(String name){
        return userRepository.findByName(name).orElseThrow(() -> new UsernameNotFoundException("User name not found"));

    }

}
