package misionTIC.seguridad.repositorios;

import misionTIC.seguridad.models.Rol;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RolRepositorio extends MongoRepository<Rol,String> {
}
