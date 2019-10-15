package edu.hust.se.seckill.service;

import edu.hust.se.seckill.dao.UserDao;
import edu.hust.se.seckill.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getById(int id) {
        return userDao.getById(id);
    }
}
