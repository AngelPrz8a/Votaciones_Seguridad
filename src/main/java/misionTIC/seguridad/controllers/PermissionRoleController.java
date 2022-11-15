package misionTIC.seguridad.controllers;

import misionTIC.seguridad.models.Permission;
import misionTIC.seguridad.models.PermissionRole;
import misionTIC.seguridad.models.Role;
import misionTIC.seguridad.repositories.PermissionRepository;
import misionTIC.seguridad.repositories.PermissionRoleRepository;
import misionTIC.seguridad.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/permissionrole")
public class PermissionRoleController {


    @Autowired
    private PermissionRoleRepository repositorio;
    @Autowired
    private PermissionRepository pr;
    @Autowired
    private RoleRepository rr;

    @GetMapping("")
    public List<PermissionRole> index(){
        ArrayList<PermissionRole> array = new ArrayList<>();
        List<PermissionRole> lista = this.repositorio.findAll();
        for(int i = 0 ; i<lista.size(); i++){
            PermissionRole pr = lista.get(i);
            Role r = this.rr.findById(pr.getRole()).get();
            Permission p = this.pr.findById(pr.getPermission()).get();
            pr.setPermission(p.getUrl());
            pr.setRole(r.getName());
            array.add(pr);
        }
        return array;
    }

    @GetMapping("/{id}")
    public PermissionRole show(@PathVariable String id){
        PermissionRole pr =  this.repositorio.findById(id).orElse(null);
        if(pr!=null){
            Permission p = this.pr.findById(pr.getPermission()).get();
            Role r = this.rr.findById(pr.getRole()).get();

            pr.setPermission(p.getUrl());
            pr.setRole(r.getName());

            return pr;
        }
        return null;
    }

    @PostMapping("")
    public String create(@RequestBody PermissionRole request){
        Permission p = this.pr.findById(request.getPermission()).orElse(null);
        if(p!=null ){
            Role r = this.rr.findById(request.getRole()).orElse(null);
            if(r != null){
                this.repositorio.save(request);
                return "Se creo";
            }
            return "El rol no existe";
        }
        return "El permiso no existe";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable String id, @RequestBody PermissionRole request){

        PermissionRole pr = this.repositorio.findById(id).orElseGet(null);
        if(pr!=null){
            Permission p = this.pr.findById(request.getPermission()).orElse(null);
            if(p!=null ){
                Role r = this.rr.findById(request.getRole()).orElse(null);
                if(r != null){

                    if(request.getPermission()!=null && !request.getPermission().isBlank())
                        pr.setPermission(request.getPermission());
                    if(request.getRole()!=null && !request.getRole().isBlank())
                        pr.setRole(request.getRole());

                    this.repositorio.save(pr);
                    return "Se actualizo";
                }
                return "El rol no existe";
            }
            return "El permiso no existe";
        }
        return "El registro no existe";

    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable String id){
        PermissionRole pr = this.repositorio.findById(id).orElse(null);
        if(pr!=null){
            this.repositorio.deleteById(id);
            return "Se elimino";
        }
        return "El registro no existe";
    }


    //-------------------------

    //Consulto si el rol tiene en la tabla permiso rol una relacion con el permiso
    @PostMapping("/validate-permission/{role}")
    public PermissionRole validarpermisos(@PathVariable String role, @RequestBody Permission request){
        Role r = this.rr.findById(role).orElse(null);
        Permission p = this.pr.findByUrlAndMethod(request.getUrl(),request.getMethod());
        if(r!=null && p!=null){
            return this.repositorio.findByPermissionAndRole(p.get_id(),r.get_id());
        }
        return null;
    }

}
