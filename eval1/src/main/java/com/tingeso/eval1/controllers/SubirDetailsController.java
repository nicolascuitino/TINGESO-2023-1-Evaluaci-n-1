package com.tingeso.eval1.controllers;

import com.tingeso.eval1.entities.SubirDataEntity;
import com.tingeso.eval1.entities.SubirDetailsEntity;
import com.tingeso.eval1.services.SubirDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@Controller
@RequestMapping
public class SubirDetailsController {
    @Autowired
    SubirDetailsService subirDetailsService;

    @GetMapping("/detailsUpload")
    public String main() {
        return "detailsUpload";
    }

    @PostMapping("/detailsUpload")
    public String upload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
        subirDetailsService.guardar(file);
        redirectAttributes.addFlashAttribute("mensaje", "¡Archivo cargado correctamente!");
        subirDetailsService.leerCsv("GyST.csv");
        return "redirect:/detailsUpload";
    }

    @GetMapping("/detailsInformation")
    public String listar(Model model) {
        ArrayList<SubirDetailsEntity> details = subirDetailsService.obtenerDetails();
        model.addAttribute("details", details);
        return "fileInformation";
    }


}
