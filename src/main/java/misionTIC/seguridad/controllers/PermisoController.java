package misionTIC.seguridad.controllers;

import misionTIC.seguridad.models.Permiso;
import misionTIC.seguridad.repositorios.PermisoRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/permiso")
public class PermisoController {

    @Autowired
    private PermisoRepositorio repositorio;

    @GetMapping("")
    public List<Permiso> index(){
        return this.repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Permiso show(@PathVariable String id){
        Optional<Permiso> opu = this.repositorio.findById(id);
        return opu.orElse(null);
    }

    @PostMapping("")
    public Permiso create(@RequestBody Permiso request){
        return this.repositorio.save(request);
    }

    @PutMapping("/{id}")
    public Permiso update(@PathVariable String id, @RequestBody Permiso request){
        Optional<Permiso> opt = this.repositorio.findById(id);
        if(opt.isPresent()){
            Permiso actual = opt.get();

            if(request.getUrl() != null && !request.getUrl().isBlank())
                actual.setUrl(request.getUrl());
            if(request.getMetodo() != null && !request.getMetodo().isBlank())
                actual.setMetodo(request.getMetodo());;

            return this.repositorio.save(actual);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        Optional<Permiso> opt = this.repositorio.findById(id);

        if(opt.isPresent()){
            this.repositorio.deleteById(id);
        }
    }
}
