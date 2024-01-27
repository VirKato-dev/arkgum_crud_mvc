package web.service;

import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import web.dao.UserDao;
import web.model.User;
@Service
@Transactional
public class UserServiceImpl implements UserService{
    private final UserDao userDao;

    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
    }
}
