package community.xyab.controller;

import community.xyab.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final BoardService boardService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("boards", boardService.findAll());
        return "index";
    }
}
