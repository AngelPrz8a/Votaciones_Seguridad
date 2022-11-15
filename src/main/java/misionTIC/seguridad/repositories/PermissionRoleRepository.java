package misionTIC.seguridad.repositories;

import misionTIC.seguridad.models.PermissionRole;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PermissionRoleRepository extends MongoRepository<PermissionRole,String> {
    PermissionRole findByPermissionAndRole(String permission, String role);
}
