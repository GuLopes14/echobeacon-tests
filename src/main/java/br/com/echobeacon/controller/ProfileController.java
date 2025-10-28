package br.com.echobeacon.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {

    @GetMapping("/profile")
    public String showProfile(Model model) {
        // Sem autenticação - perfil não disponível
        model.addAttribute("userName", "Usuário");
        model.addAttribute("userEmail", "usuario@exemplo.com");
        model.addAttribute("userPicture", "");
        return "profile";
    }
}
