package misionTIC.seguridad.models;

import org.springframework.data.annotation.Id;

public class PermissionRole {


    @Id
    private String _id;
    private String permission;
    private String role;

    public PermissionRole(String permission, String role) {
        this.permission = permission;
        this.role = role;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
