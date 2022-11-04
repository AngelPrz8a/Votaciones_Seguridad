package misionTIC.seguridad.controllers;

import misionTIC.seguridad.models.Permiso;
import misionTIC.seguridad.models.PermisoRol;
import misionTIC.seguridad.models.Rol;
import misionTIC.seguridad.repositorios.PermisoRepositorio;
import misionTIC.seguridad.repositorios.PermisoRolRepositorio;
import misionTIC.seguridad.repositorios.RolRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/permisorol")
public class PermisoRolController {


    @Autowired
    private PermisoRolRepositorio repositorio;
    @Autowired
    private PermisoRepositorio permisorepo;
    @Autowired
    private RolRepositorio rolrepo;

    @GetMapping("")
    public List<PermisoRol> index(){
        return this.repositorio.findAll();
    }

    @GetMapping("/{id}")
    public PermisoRol show(@PathVariable String id){
        Optional<PermisoRol> opt =  this.repositorio.findById(id);
        return opt.orElse(null);
    }

    @PostMapping("/{permiso}/{rol}")
    public PermisoRol create(@PathVariable String permiso, @PathVariable String rol){
        PermisoRol pr = new PermisoRol();

        Permiso p = this.permisorepo.findById(permiso).get();
        Rol r = this.rolrepo.findById(rol).get();

        if(p!=null && r!=null){
            pr.setPermiso(p);
            pr.setRol(r);
            return this.repositorio.save(pr);
        }
        return null;
    }

    @PutMapping("/{id}/{permiso}/{rol}")
    public PermisoRol update(@PathVariable String id, @PathVariable String permiso, @PathVariable String rol){

        PermisoRol pr = this.repositorio.findById(id).get();
        Permiso p = this.permisorepo.findById(permiso).get();
        Rol r = this.rolrepo.findById(rol).get();

        if(pr!=null && p!=null && r!=null){

            pr.setPermiso(p);
            pr.setRol(r);

            return this.repositorio.save(pr);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        PermisoRol pr = this.repositorio.findById(id).orElse(null);
        if(pr!=null){
            this.repositorio.deleteById(id);
        }
    }

}
