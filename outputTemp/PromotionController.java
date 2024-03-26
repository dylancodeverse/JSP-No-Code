package scaffold.framework.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import scaffold.framework.demo.entity.Promotion;

import scaffold.framework.demo.service.PromotionService;

@Controller
@RequestMapping("/promotion")

public class PromotionController {

    private PromotionService promotionService;

    public PromotionRepositoryController(PromotionRepositoryService promotionRepositoryService) {
        this.promotionRepositoryService = promotionRepositoryService;
    }

    @GetMapping("/list")
    public String listPromotionRepositorys(Model model) {
        model.addAttribute("promotionRepositorys", promotionRepositoryService.findAll());
        return "pages/promotionRepository/list";
    }

    @GetMapping("/add")
    public String showAddPromotionRepositoryForm(Model model) {
        return "pages/promotionRepository/add";
    }

    @PostMapping("/add")
    public String addPromotionRepository(PromotionRepository promotionRepository) {
        promotionRepositoryService.save(promotionRepository);
        return "redirect:/promotionRepositorys/list";
    }

    @GetMapping("/edit{id}")
    public String showEditPromotionRepositoryForm(@PathVariable Long id, Model model) {
        model.addAttribute("promotionRepository", promotionRepositoryService.findById(id));
        return "pages/promotionRepository/edit";
    }

    @PostMapping("/edit")
    public String editPromotionRepository(PromotionRepository promotionRepository) {
        // No error handling for yet
        if (promotionRepository.getId() != null) {
            promotionRepositoryService.save(promotionRepository);
        }
        return "redirect:/promotionRepositorys/list";
    }

    @GetMapping("/delete{id}")
    public String deletePromotionRepository(@PathVariable Long id) {
        promotionRepositoryService.deleteById(id);
        return "redirect:/promotionRepositorys/list";
    }
}
