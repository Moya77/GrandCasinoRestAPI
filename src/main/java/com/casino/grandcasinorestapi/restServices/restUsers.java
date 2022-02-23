package com.casino.grandcasinorestapi.restServices;

import com.casino.grandcasinorestapi.Controllers.CasinoController;
import com.casino.grandcasinorestapi.Models.Cliente;
import com.casino.grandcasinorestapi.Models.messajes;
import com.casino.grandcasinorestapi.Models.usuarios;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/usuarios")
@CrossOrigin(origins = "*", methods= {RequestMethod.GET,RequestMethod.POST})
public class restUsers {

    CasinoController casino = new CasinoController();



    @PostMapping("/loggin" )
    @ResponseBody
    public messajes setData(@RequestBody usuarios user){

        return   casino.loggIn(user);

    }

    @PostMapping("/check" )
    @ResponseBody
    public messajes check(@RequestBody Cliente cliente){

        return casino.comprobarNombre(cliente.Nombre);
    }
}

