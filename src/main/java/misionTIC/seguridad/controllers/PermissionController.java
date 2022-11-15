package misionTIC.seguridad.controllers;

import misionTIC.seguridad.models.Permission;
import misionTIC.seguridad.repositories.PermissionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    @Autowired
    private PermissionRepository repositorio;

    @GetMapping("")
    public List<Permission> index(){
        return this.repositorio.findAll();
    }

    @GetMapping("/{id}")
    public Permission show(@PathVariable String id){
        Optional<Permission> opu = this.repositorio.findById(id);
        return opu.orElse(null);
    }

    @PostMapping("")
    public Permission create(@RequestBody Permission request){
        return this.repositorio.save(request);
    }

    @PutMapping("/{id}")
    public Permission update(@PathVariable String id, @RequestBody Permission request){
        Optional<Permission> opt = this.repositorio.findById(id);
        if(opt.isPresent()){
            Permission actual = opt.get();

            if(request.getUrl() != null && !request.getUrl().isBlank())
                actual.setUrl(request.getUrl());
            if(request.getMethod() != null && !request.getMethod().isBlank())
                actual.setMethod(request.getMethod());;

            return this.repositorio.save(actual);
        }
        return null;
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        Optional<Permission> opt = this.repositorio.findById(id);

        if(opt.isPresent()){
            this.repositorio.deleteById(id);
        }
    }
}
