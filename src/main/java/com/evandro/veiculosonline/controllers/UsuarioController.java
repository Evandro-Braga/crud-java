package com.evandro.veiculosonline.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.evandro.veiculosonline.models.Usuario;
import com.evandro.veiculosonline.models.Veiculo;
import com.evandro.veiculosonline.repository.UsuarioRepository;
import com.evandro.veiculosonline.repository.VeiculoRepository;

import jakarta.servlet.http.HttpSession;

@Controller
public class UsuarioController {

    @Autowired
    private UsuarioRepository ur;

    @Autowired
    private VeiculoRepository vr;

    @GetMapping("/")
    public ModelAndView Index() {
        ModelAndView mv = new ModelAndView("Index");
        List<Veiculo> todosOsVeiculos = (List<Veiculo>) this.vr.findAll();
        mv.addObject("veiculos", todosOsVeiculos);
        return mv;
    }
    
    @GetMapping("/cadastrar/usuario")
    public ModelAndView FormUsuario(){
        ModelAndView mv = new ModelAndView("CadastrarUsuario");
        Usuario usuario = new Usuario();
        mv.addObject("usuarioAtual", usuario);
        return mv;
    }

    @PostMapping("/salvar/usuario")
    public String SalvarUsuario(Usuario usuario){
        this.ur.save(usuario);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public ModelAndView Login(){
        ModelAndView mv = new ModelAndView("Login");
        Usuario usuario = new Usuario();
        mv.addObject("usuario", usuario);
        return mv;
    }

    @GetMapping("/logout")
    public String Logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }

    @PostMapping("/login/getUsuario")
    public ModelAndView getUsuario(Usuario usuario, HttpSession session) {
        ModelAndView mv = new ModelAndView("Login");
        Usuario usuarioSessao = this.ur.findByEmailAndSenha(usuario.getEmail(), usuario.getSenha());
        
        if(usuarioSessao == null) {
            mv.addObject("ErrorMsg", "Usuario nao encontrado!!");
        }else{
            session.setAttribute("usuarioLogado", usuarioSessao);
            System.out.println();
            return Index();
        }
        return mv;
    }

}
