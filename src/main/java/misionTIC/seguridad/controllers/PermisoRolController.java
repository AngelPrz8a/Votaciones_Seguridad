package misionTIC.seguridad.controllers;

import misionTIC.seguridad.models.Permiso;
import misionTIC.seguridad.models.PermisoRol;
import misionTIC.seguridad.models.Rol;
import misionTIC.seguridad.repositorios.PermisoRepositorio;
import misionTIC.seguridad.repositorios.PermisoRolRepositorio;
import misionTIC.seguridad.repositorios.RolRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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
        ArrayList<PermisoRol> array = new ArrayList<>();
        List<PermisoRol> lista = this.repositorio.findAll();
        for(int i = 0 ; i<lista.size(); i++){
            PermisoRol pr = lista.get(i);
            Rol r = this.rolrepo.findById(pr.getRol()).get();
            Permiso p = this.permisorepo.findById(pr.getPermiso()).get();
            pr.setPermiso(p.getUrl());
            pr.setRol(r.getNombre());
            array.add(pr);
        }
        return array;
    }

    @GetMapping("/{id}")
    public PermisoRol show(@PathVariable String id){
        PermisoRol pr =  this.repositorio.findById(id).orElse(null);
        if(pr!=null){
            Permiso p = this.permisorepo.findById(pr.getPermiso()).get();
            Rol r = this.rolrepo.findById(pr.getRol()).get();

            pr.setPermiso(p.getUrl());
            pr.setRol(r.getNombre());

            return pr;
        }
        return null;
    }

    @PostMapping("")
    public String create(@RequestBody PermisoRol request){
        Permiso p = this.permisorepo.findById(request.getPermiso()).orElse(null);
        if(p!=null ){
            Rol r = this.rolrepo.findById(request.getRol()).orElse(null);
            if(r != null){
                this.repositorio.save(request);
                return "Se creo";
            }
            return "El rol no existe";
        }
        return "El permiso no existe";
    }

    @PutMapping("/{id}")
    public String update(@PathVariable String id, @RequestBody PermisoRol request){

        PermisoRol pr = this.repositorio.findById(id).orElseGet(null);
        if(pr!=null){
            Permiso p = this.permisorepo.findById(request.getPermiso()).orElse(null);
            if(p!=null ){
                Rol r = this.rolrepo.findById(request.getRol()).orElse(null);
                if(r != null){

                    if(request.getPermiso()!=null && !request.getPermiso().isBlank())
                        pr.setPermiso(request.getPermiso());
                    if(request.getRol()!=null && !request.getRol().isBlank())
                        pr.setRol(request.getRol());

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
        PermisoRol pr = this.repositorio.findById(id).orElse(null);
        if(pr!=null){
            this.repositorio.deleteById(id);
            return "Se elimino";
        }
        return "El registro no existe";
    }

}
