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
        return this.repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Usuario show(@PathVariable String id){
        Optional<Usuario> opu = this.repositorio.findById(id);
        return opu.orElse(null);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public Usuario create(@RequestBody Usuario request){
        String clave = Hash(request.getContraseña());
        request.setContraseña(clave);
        
        return this.repositorio.save(request);
    }

    @PutMapping("/{id}")
    public Usuario update(@PathVariable String id, @RequestBody Usuario request){
        Optional<Usuario> opt = this.repositorio.findById(id);
        if(opt.isPresent()){
            Usuario actual = opt.get();

            if(request.getSeudonimo() != null && !request.getSeudonimo().isBlank())
                actual.setSeudonimo(request.getSeudonimo());
            if(request.getCorreo() != null && !request.getCorreo().isBlank())
                actual.setCorreo(request.getCorreo());
            if(request.getContraseña() != null && !request.getContraseña().isBlank())
                actual.setContraseña(request.getContraseña());
            if(request.getRol() != null)
                actual.setRol(request.getRol());

            return this.repositorio.save(actual);
        }
        return null;
    }


    @PutMapping("/{usuario}/{rol}")
    public Usuario AsignarRol(@PathVariable String usuario,@PathVariable String rol){
        Usuario u = this.repositorio.findById(usuario).orElse(null);
        Rol r = this.rolrep.findById(rol).orElse(null);

        if(u!=null && r!=null){
            u.setRol(r);
            return this.repositorio.save(u);
        }
        return null;
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        Optional<Usuario> opt = this.repositorio.findById(id);

        if(opt.isPresent()){
            this.repositorio.deleteById(id);
        }
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
