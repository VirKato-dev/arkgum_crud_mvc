package web.dao;

import javax.persistence.EntityManager;
import org.springframework.stereotype.Repository;
import web.model.User;
@Repository
public class UserDaoHibernateImpl implements UserDao{
    private final EntityManager entityManager;
    public UserDaoHibernateImpl(EntityManager entityManager){
        this.entityManager = entityManager;
    }
    @Override
    public void addUser(User user) {
        entityManager.joinTransaction();
        entityManager.persist(user);
    }
}
