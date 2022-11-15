package misionTIC.seguridad.repositories;

import misionTIC.seguridad.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User,String> {

    public User findByEmailAndPassword(String email, String password);
}
