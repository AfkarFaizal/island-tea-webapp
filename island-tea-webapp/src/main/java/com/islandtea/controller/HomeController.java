package com.islandtea.controller;

import com.islandtea.service.CafeInfoProvider;
import com.islandtea.service.MenuService;
import com.islandtea.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    private final CafeInfoProvider cafeInfoProvider;
    private final MenuService menuService;
    private final ReviewService reviewService;

    public HomeController(CafeInfoProvider cafeInfoProvider, MenuService menuService,
                           ReviewService reviewService) {
        this.cafeInfoProvider = cafeInfoProvider;
        this.menuService = menuService;
        this.reviewService = reviewService;
    }

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("cafe", cafeInfoProvider.getCafe());
        model.addAttribute("signatureItems", menuService.getSignatureItems());
        model.addAttribute("topReviews", reviewService.getTopReviews(3));
        model.addAttribute("averageRating", reviewService.getAverageRating());
        model.addAttribute("activePage", "home");
        return "index";
    }

    @GetMapping("/about")
    public String about(Model model) {
        model.addAttribute("cafe", cafeInfoProvider.getCafe());
        model.addAttribute("activePage", "about");
        return "about";
    }
}
