package com.example.auth.service;

import com.example.auth.entity.Roles;
import com.example.auth.entity.User;
import com.example.auth.repository.RolesRepository;
import com.example.auth.repository.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RolesRepository rolesRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RolesRepository rolesRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.rolesRepository = rolesRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public User create(User user) {
        User saveUser = new User();
        saveUser.setEmail(user.getEmail());
        saveUser.setPassword(passwordEncoder.encode(user.getPassword()));
        saveUser.setFullName(user.getFullName());
        
        Roles realtorRole = rolesRepository.findByName("ROLE_REALTOR");
        if (realtorRole == null) {
            realtorRole = new Roles();
            realtorRole.setName("ROLE_REALTOR");
            rolesRepository.save(realtorRole);
        }
        
        saveUser.setRoles(Collections.singletonList(realtorRole));
        saveUser.setPhone(user.getPhone());
        saveUser.setActive(true);
        saveUser.setAccountBalance(0.0);
        int randomNumber = new Random().nextInt(40000000) + 10000000;
        saveUser.setPaymentCode("T7M" + randomNumber);
        return userRepository.save(saveUser);
    }

    @Transactional
    public User createAdmin(User user) {
        User saveUser = new User();
        Roles adminRole = rolesRepository.findByName("ROLE_ADMIN");
        if (adminRole == null) {
            adminRole = new Roles();
            adminRole.setName("ROLE_ADMIN");
            rolesRepository.save(adminRole);
        }
        saveUser.setEmail(user.getEmail());
        saveUser.setPassword(passwordEncoder.encode(user.getPassword()));
        saveUser.setFullName(user.getFullName());
        saveUser.setRoles(Collections.singletonList(adminRole));
        saveUser.setPhone(user.getPhone());
        saveUser.setActive(true);
        saveUser.setAccountBalance(0.0);
        int randomNumber = new Random().nextInt(40000000) + 10000000;
        saveUser.setPaymentCode("T7M" + randomNumber);
        return userRepository.save(saveUser);
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public void updateByEmail(String email, String password, String confirmPassword) {
        User oldUser = userRepository.findByEmail(email);
        if (oldUser != null) {
            oldUser.setPassword(passwordEncoder.encode(password));
            userRepository.save(oldUser);
        }
    }

    public void updateInfo(String email, String fullName, String phone) {
        User oldUser = userRepository.findByEmail(email);
        if (oldUser != null) {
            oldUser.setPhone(phone);
            oldUser.setFullName(fullName);
            userRepository.save(oldUser);
        }
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findUserById(int id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("Couldn't find user"));
    }

    public void deleteById(int id) {
        userRepository.deleteById(id);
    }

    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);
        if (user == null) {
            throw new UsernameNotFoundException("Invalid username or password. Please try again!!");
        }
        Collection<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName()))
                .collect(Collectors.toList());

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(), user.getPassword(), authorities);
    }
}
