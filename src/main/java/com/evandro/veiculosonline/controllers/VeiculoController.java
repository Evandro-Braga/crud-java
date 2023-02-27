package com.evandro.veiculosonline.controllers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import com.evandro.veiculosonline.models.Veiculo;
import com.evandro.veiculosonline.repository.VeiculoRepository;

@Controller
public class VeiculoController {

    private static String caminhoimg = "./src/main/imagens/";

    @Autowired
    private VeiculoRepository vr;

    @GetMapping("/")
    public ModelAndView ListarVeiculos() {
        ModelAndView mv = new ModelAndView("Index");
        List<Veiculo> listadeveiculos = (List<Veiculo>) this.vr.findAll();
        mv.addObject("veiculos", listadeveiculos);

        Veiculo veiculo = new Veiculo();
        mv.addObject("veiculoatual", veiculo);
        return mv;
    }

    @PostMapping("/salvar/veiculo")
    public String SalvarVeiculo(Veiculo veiculo, @RequestParam("file") MultipartFile arquivo) {

        String formatPreco = "R$ "+veiculo.getPreco();
        veiculo.setPreco(formatPreco);
        this.vr.save(veiculo);
        
        try {
            if(!arquivo.isEmpty()){
                byte[] bytes = arquivo.getBytes();
                Path caminho = Paths.get(caminhoimg+ String.valueOf(veiculo.getId()) +arquivo.getOriginalFilename());
                Files.write(caminho, bytes);

                veiculo.setImagem(String.valueOf(veiculo.getId()) +arquivo.getOriginalFilename());
                this.vr.save(veiculo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/";
    }

    @GetMapping("/imagens/veiculos/{img}")
    @ResponseBody
    public byte[] carregaImagensDinamicas(@PathVariable("img") String img) {

    File imagemArquivo = new File("./src/main/imagens/"+img);

        try {
            return Files.readAllBytes(imagemArquivo.toPath());
        } catch (IOException e) {
            return null;
        }

    }

    @GetMapping("/detalhe/veiculo/{veiculoId}")
    public ModelAndView detalheVeiculo(@PathVariable("veiculoId") Long id) {
        ModelAndView mv = new ModelAndView("DetalheDoVeiculo");
        Optional<Veiculo> detalheVeiculo = this.vr.findById(id);
        if (detalheVeiculo.isPresent()) {
            mv.addObject("v", detalheVeiculo.get());
        }
        return mv;
    }

    @GetMapping("/deletar/veiculo/{veiculoId}")
    public String deletarVeiculo(@PathVariable("veiculoId") Long id) {
        this.vr.deleteById(id);
        return "redirect:/";
    }
}
