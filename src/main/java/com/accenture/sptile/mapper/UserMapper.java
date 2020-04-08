package com.accenture.sptile.mapper;

import com.accenture.sptile.entity.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserMapper {
    User checkUser(User user);
}
