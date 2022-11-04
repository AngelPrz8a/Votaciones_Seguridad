package misionTIC.seguridad.repositorios;

import misionTIC.seguridad.models.PermisoRol;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PermisoRolRepositorio extends MongoRepository<PermisoRol,String> {
}
