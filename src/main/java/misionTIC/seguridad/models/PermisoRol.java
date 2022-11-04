package misionTIC.seguridad.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class PermisoRol {

    @Id
    private String _id;

    @DBRef
    private Permiso permiso;

    @DBRef
    private Rol rol;

    public PermisoRol() {
    }
    public PermisoRol(Permiso permiso, Rol rol) {
        this.permiso = permiso;
        this.rol = rol;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Permiso getPermiso() {
        return permiso;
    }

    public void setPermiso(Permiso permiso) {
        this.permiso = permiso;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }


}
