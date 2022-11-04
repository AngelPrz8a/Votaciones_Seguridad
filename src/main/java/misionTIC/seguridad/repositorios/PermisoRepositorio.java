package misionTIC.seguridad.repositorios;

import misionTIC.seguridad.models.Permiso;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PermisoRepositorio extends MongoRepository<Permiso,String> {
}
