package misionTIC.seguridad.repositories;

import misionTIC.seguridad.models.Permission;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PermissionRepository extends MongoRepository<Permission,String> {
    Permission findByUrlAndMethod(String url, String method);
}
