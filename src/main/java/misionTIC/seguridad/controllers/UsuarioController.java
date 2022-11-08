package misionTIC.seguridad.controllers;

import misionTIC.seguridad.models.Rol;
import misionTIC.seguridad.models.Usuario;
import misionTIC.seguridad.repositorios.RolRepositorio;
import misionTIC.seguridad.repositorios.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepositorio repositorio;

    @Autowired
    private RolRepositorio rolrep;

    @GetMapping("")
    public List<Usuario> index(){
        ArrayList<Usuario> array = new ArrayList<>();
        List<Usuario> lista = this.repositorio.findAll();
        for(int i = 0; i < lista.size();i++){
            Usuario u = lista.get(i);
            if(u.getRol()!=null){
                Rol rol = this.rolrep.findById(u.getRol()).get();
                u.setRol(rol.getNombre());
                array.add(u);
            }else{
                u.setRol("No tiene rol");
                array.add(u);
            }
        }
        return array;
    }

    @GetMapping("/{id}")
    public Usuario show(@PathVariable String id){
        Usuario u = this.repositorio.findById(id).orElse(null);
        if(u!=null){
            Rol rol= this.rolrep.findById(u.getRol()).get();
            u.setRol(rol.getNombre());
            return u;
        }
        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public String create(@RequestBody Usuario request){
        Rol rol = this.rolrep.findById(request.getRol()).orElse(null);
        if (rol != null){
            String clave = Hash(request.getContraseña());
            request.setContraseña(clave);
            this.repositorio.save(request);
            return "Se registro";
        }
        return "No existe el rol";
    }


    @PutMapping("/{id}")
    public String update(@PathVariable String id,@RequestBody Usuario request){
        Usuario u = this.repositorio.findById(id).orElse(null);
        if(u!=null ){
                if(request.getSeudonimo() != null && !request.getSeudonimo().isBlank())
                    u.setSeudonimo(request.getSeudonimo());
                if(request.getCorreo() != null && !request.getCorreo().isBlank())
                    u.setCorreo(request.getCorreo());
                if(request.getContraseña() != null && !request.getContraseña().isBlank())
                    u.setContraseña(request.getContraseña());
                if(request.getRol() != null && !request.getRol().isBlank()){
                    Rol r = this.rolrep.findById(request.getRol()).orElse(null);
                    if(r!=null){
                        u.setRol(request.getRol());
                    }else{
                        return "El rol no existe";
                    }
                }
                this.repositorio.save(u);
                return "Se actualizo";
        }
        return "El usuario no existe";
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id){
        Usuario usuario = this.repositorio.findById(id).orElse(null);
        if(usuario != null){
            this.repositorio.deleteById(id);
            return "Se elimino correctamente";
        }
        return "No existe el usuario";
    }

    public String Hash(String password){
        MessageDigest md = null;
        try {
            md = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        byte[] hash = md.digest(password.getBytes());
        StringBuilder sb = new StringBuilder();

        for(byte b : hash){
            sb.append(String.format("%02x",b));
        }
        return sb.toString();
    }


}



