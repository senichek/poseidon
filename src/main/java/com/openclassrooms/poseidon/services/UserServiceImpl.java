package com.openclassrooms.poseidon.services;

import java.util.List;

import com.openclassrooms.poseidon.domain.User;
import com.openclassrooms.poseidon.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public User create(User usr) throws Exception {
        User exists = userRepository.findByUsername(usr.getUsername());
        if (exists != null) {
            throw new Exception("This Username is taken. Choose a different one.");
        }
        if (usr.getUsername() == null || usr.getUsername().isEmpty()) {
            throw new Exception("Username is empty or null.");
        } else if (usr.getFullname() == null || usr.getFullname().isEmpty()) {
            throw new Exception("Fullname is empty or null.");
        } else if (usr.getRole() == null || usr.getRole().isEmpty()) {
            throw new Exception("Role is empty or null.");
        } else if (usr.getRawPassword() == null || usr.getRawPassword().isEmpty()) {
            throw new Exception("Password is empty or null.");
        } 
        else {
            usr.setEncodedPassword(passwordEncoder.encode(usr.getRawPassword()));
            User created = userRepository.save(usr);
            log.info("Created {}", created);
            return created;
        }
    }

    @Override
    public User update(User usr) throws Exception {
        User toUpdate = userRepository.findByUsereId(usr.getId());
        if (toUpdate == null) {
            throw new Exception(String.format("Entity with id %s does not exist.", usr.getId()));
        } else if (usr.getUsername() == null || usr.getUsername().isEmpty()) {
            throw new Exception("Username is empty or null.");
        } else if (usr.getFullname() == null || usr.getFullname().isEmpty()) {
            throw new Exception("Fullname is empty or null.");
        } else if (usr.getRole() == null || usr.getRole().isEmpty()) {
            throw new Exception("Role is empty or null.");
        }
        else {
            if (!usr.getUsername().equals(toUpdate.getUsername())) {
                User exists = userRepository.findByUsername(usr.getUsername());
                if(exists != null) {
                    throw new Exception("This Username is taken. Choose a different one.");
                } else {
                    toUpdate.setUsername(usr.getUsername());
                }
            }
            if (!usr.getFullname().equals(toUpdate.getFullname())) {
                toUpdate.setFullname(usr.getFullname());
            }
            if (!usr.getRole().equals(toUpdate.getRole())) {
                toUpdate.setRole(usr.getRole());
            }
            userRepository.save(toUpdate);
            log.info("Updated {}", toUpdate);
            return toUpdate;
        }
    }

    @Override
    public User delete(Integer id) throws Exception {
        User toDelete = userRepository.findByUsereId(id);
        if (toDelete == null) {
            throw new Exception(String.format("Entity with id %s does not exist.", id));
        } else {
            userRepository.delete(toDelete);
            log.info("Deleted {}.", toDelete);
            return toDelete;
        }
    }

    @Override
    public List<User> getAll() {
        return userRepository.findAll();
    }

    @Override
    public User getById(Integer id) throws Exception {
        User user = userRepository.findByUsereId(id);
        if (user == null) {
            throw new Exception(String.format("Entity with id %s does not exist.", id));
        } else {
            return user;
        }
    }
}
