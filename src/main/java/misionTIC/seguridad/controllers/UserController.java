package misionTIC.seguridad.controllers;

import misionTIC.seguridad.models.Role;
import misionTIC.seguridad.models.User;
import misionTIC.seguridad.repositories.RoleRepository;
import misionTIC.seguridad.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserRepository repositorio;

    @Autowired
    private RoleRepository rp;

    @GetMapping("")
    public List<User> index(){
        ArrayList<User> array = new ArrayList<>();
        List<User> lista = this.repositorio.findAll();
        for(int i = 0; i < lista.size();i++){
            User u = lista.get(i);
            if(u.getRole()!=null){
                Role rol = this.rp.findById(u.getRole()).get();
                u.setRole(rol.getName());
                array.add(u);
            }else{
                u.setRole("No tiene rol");
                array.add(u);
            }
        }
        return array;
    }

    @GetMapping("/{id}")
    public User show(@PathVariable String id){
        User u = this.repositorio.findById(id).orElse(null);
        if(u!=null){
            Role rol= this.rp.findById(u.getRole()).get();
            u.setRole(rol.getName());
            return u;
        }
        return null;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    public User create(@RequestBody User request){
        Role rol = this.rp.findById(request.getRole()).orElse(null);
        if (rol != null){
            String clave = Hash(request.getPassword());
            request.setPassword(clave);
            User u = this.repositorio.save(request);
            return u;
        }
        return null;
    }


    @PutMapping("/{id}")
    public User update(@PathVariable String id,@RequestBody User request){
        User u = this.repositorio.findById(id).orElse(null);
        if(u!=null ){
                if(request.getName() != null && !request.getName().isBlank())
                    u.setName(request.getName());
                if(request.getEmail() != null && !request.getEmail().isBlank())
                    u.setEmail(request.getEmail());
                if(request.getPassword() != null && !request.getPassword().isBlank())
                    u.setPassword(Hash(request.getPassword()));
                if(request.getRole() != null && !request.getRole().isBlank()){
                    Role r = this.rp.findById(request.getRole()).orElse(null);
                    if(r!=null){
                        u.setRole(request.getRole());
                    }else{
                        return null;
                    }
                }
                User user= this.repositorio.save(u);
                return user;
        }
        return null;
    }


    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id){
        User usuario = this.repositorio.findById(id).orElse(null);
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


    //----------------------------

    @PostMapping("/validation")
    public User validar(@RequestBody User request, final HttpServletResponse response ) throws IOException {
        String correo = request.getEmail();
        String contraseña = Hash(request.getPassword());
        User u = this.repositorio.findByEmailAndPassword(correo, contraseña);
        if(u!=null){
            u.setPassword("");
            return u;
        }else{
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
            return null;
        }
    }


}



