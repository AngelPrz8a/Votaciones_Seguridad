package misionTIC.seguridad.repositorios;

import misionTIC.seguridad.models.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UsuarioRepositorio extends MongoRepository<Usuario,String> {
}
