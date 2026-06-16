package org.iftm.selenium_webdriver.controller;

import org.iftm.selenium_webdriver.model.Veterinario;
import org.iftm.selenium_webdriver.service.VeterinarioService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class VeterinarioController {

    private final VeterinarioService service;

    public VeterinarioController(VeterinarioService service) {
        this.service = service;
    }

    @GetMapping("/home")
    public String exibirHome(@RequestParam(required = false) String pesquisa, Model model) {
        model.addAttribute("veterinarios", service.listar(pesquisa));
        model.addAttribute("pesquisa", pesquisa == null ? "" : pesquisa);
        model.addAttribute("veterinario", new Veterinario());
        model.addAttribute("modoEdicao", false);
        return "home";
    }

    @PostMapping("/veterinarios")
    public String salvarVeterinario(Veterinario veterinario, RedirectAttributes redirect) {
        service.salvar(veterinario);
        redirect.addFlashAttribute("mensagem", "Veterinário salvo com sucesso.");
        return "redirect:/home";
    }

    @GetMapping("/veterinarios/{id}/editar")
    public String editarVeterinario(@PathVariable Long id,
            @RequestParam(required = false) String pesquisa,
            Model model) {
        service.obterPorId(id).ifPresent(veterinario -> model.addAttribute("veterinario", veterinario));
        model.addAttribute("veterinarios", service.listar(pesquisa));
        model.addAttribute("pesquisa", pesquisa == null ? "" : pesquisa);
        model.addAttribute("modoEdicao", true);
        return "home";
    }

    @GetMapping("/veterinarios/{id}/excluir")
    public String excluirVeterinario(@PathVariable Long id, RedirectAttributes redirect) {
        service.excluir(id);
        redirect.addFlashAttribute("mensagem", "Veterinário excluído com sucesso.");
        return "redirect:/home";
    }
}
