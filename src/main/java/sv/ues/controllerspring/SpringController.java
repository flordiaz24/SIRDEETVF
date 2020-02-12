/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.ues.controllerspring;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sv.ues.dao.RolesDao;
import sv.ues.dominio.Rol;

/**
 *
 * @author daniel
 */
@RestController
@RequestMapping(value="prueba" )
public class SpringController {
    
   // private RolesDao rolesDao=new RolesDao();
    
    @RequestMapping("/zx")
    @ResponseBody
    public String getData(){
        return "adsadas0";
    }
    
   /* @GetMapping(value="/getTodos")
    public ResponseEntity<List<Rol>> getAllRoles() throws Exception{
        List<Rol> lsRoles=rolesDao.obtener_roles();
        return new ResponseEntity<List<Rol>>(lsRoles,HttpStatus.OK);
        
    }*/
}
