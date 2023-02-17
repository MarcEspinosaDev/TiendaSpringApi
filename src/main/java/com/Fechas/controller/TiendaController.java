package com.Fechas.controller;

import com.Fechas.dto.Mensaje;
import com.Fechas.dto.TiendaDto;
import com.Fechas.entity.Tienda;
import com.Fechas.service.TiendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tienda")
@CrossOrigin
public class TiendaController {
    @Autowired
    TiendaService tiendaService;
    //GET
    @GetMapping("/list")
    public ResponseEntity<List<Tienda>> List(){
        List<Tienda> list = tiendaService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    //POST
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody TiendaDto tiendaDto){
        tiendaService.save(new Tienda(tiendaDto.getNombre(),tiendaDto.getFecha(),tiendaDto.getHora()));
        return new ResponseEntity<>(new Mensaje("Tienda creada"), HttpStatus.OK);
    }
    //PUT
    @PutMapping("/update/{id}")
    public ResponseEntity<?> update(@RequestBody TiendaDto tiendaDto, @PathVariable("id") long id){
         if (!tiendaService.existsById(id))
             return new ResponseEntity<>(new Mensaje("id no existe"), HttpStatus.NOT_FOUND);
         Tienda tienda = tiendaService.getOne(id).get();
         tienda.setNombre(tiendaDto.getNombre());
         tienda.setFecha(tiendaDto.getFecha());
         tienda.setHora(tiendaDto.getHora());

         tiendaService.save(tienda);
        return new ResponseEntity<>(new Mensaje("Tienda actualizada"), HttpStatus.OK);
    }
    //DELETE
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable("id")long id){
        if (!tiendaService.existsById(id))
            return new ResponseEntity<>(new Mensaje("id no existe"), HttpStatus.NOT_FOUND);
        tiendaService.delete(id);
        return new ResponseEntity<>(new Mensaje("Tienda borrada"), HttpStatus.OK);
    }


}
