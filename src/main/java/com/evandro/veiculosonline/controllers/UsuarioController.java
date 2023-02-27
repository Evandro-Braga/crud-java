package com.evandro.veiculosonline.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.evandro.veiculosonline.models.Usuario;
import com.evandro.veiculosonline.repository.UsuarioRepository;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository ur;
    
    @GetMapping("/cadastrar/usuario")
    public ModelAndView FormUsuario(){
        ModelAndView mv = new ModelAndView("usuario/cadastrar");
        Usuario usuario = new Usuario();
        mv.addObject("usuarioatual", usuario);
        return mv;
    }

    @PostMapping("/salvar/usuario")
    public String SalvarUsuario(Usuario usuario){
        this.ur.save(usuario);
        return "redirect:/usuario/listar";
    }

}
