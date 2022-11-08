package misionTIC.seguridad.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

public class PermisoRol {

    @Id
    private String _id;
    private String permiso;
    private String rol;


    public PermisoRol(String permiso, String rol) {
        this.permiso = permiso;
        this.rol = rol;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPermiso() {
        return permiso;
    }

    public void setPermiso(String permiso) {
        this.permiso = permiso;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
