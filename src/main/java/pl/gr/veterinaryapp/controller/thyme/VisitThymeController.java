package pl.gr.veterinaryapp.controller.thyme;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.gr.veterinaryapp.mapper.VisitMapper;
import pl.gr.veterinaryapp.service.VisitService;

@Controller
@RequestMapping("/ui")
@RequiredArgsConstructor
public class VisitThymeController {

    private final VisitService visitService;
    private final VisitMapper mapper;

    @GetMapping("/visits")
    public String getAllVisits(Model model) {
        model.addAttribute("visits", mapper.mapAsList(visitService.getAllVisits()));
        return "visits";
    }
}
