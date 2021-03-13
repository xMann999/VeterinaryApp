package pl.gr.veterinaryapp.controller.thyme;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gr.veterinaryapp.service.AnimalService;

@RequiredArgsConstructor
@RequestMapping("/ui")
@Controller
public class AnimalThymeController {

    private final AnimalService animalService;

    @RequestMapping("/animals")
    public String showAnimals(Model model) {
        model.addAttribute("animals", animalService.getAllAnimals());
        return "animals";
    }

}
