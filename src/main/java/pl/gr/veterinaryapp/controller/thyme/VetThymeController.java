package pl.gr.veterinaryapp.controller.thyme;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gr.veterinaryapp.service.VetService;

@RequiredArgsConstructor
@RequestMapping("/ui")
@Controller
public class VetThymeController {

    private final VetService vetService;

    @GetMapping("/vets")
    public String showVets(Model model) {
        model.addAttribute("vets", vetService.getAllVets());
        return "vets";
    }
}
