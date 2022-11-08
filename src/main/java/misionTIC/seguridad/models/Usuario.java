package misionTIC.seguridad.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document
public class Usuario{

    @Id
    private String _id;
    private String seudonimo;
    private String correo;
    private String contraseña;
    private String rol;

//    @DBRef
//    private Rol rol;


    public Usuario(String seudonimo, String correo, String contraseña, String rol) {
        this.seudonimo = seudonimo;
        this.correo = correo;
        this.contraseña = contraseña;
        this.rol = rol;
    }




    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSeudonimo() {
        return seudonimo;
    }

    public void setSeudonimo(String seudonimo) {
        this.seudonimo = seudonimo;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

   //------------------
    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

}
