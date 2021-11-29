package com.openclassrooms.poseidon.services;

import java.util.List;
import com.openclassrooms.poseidon.domain.User;

public interface UserService {
    User create(User usr) throws Exception;
    User update(User usr) throws Exception;
    User delete(Integer id) throws Exception;
    List<User> getAll();
    User getById(Integer id) throws Exception;
}
