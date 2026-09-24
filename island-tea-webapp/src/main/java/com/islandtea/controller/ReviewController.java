package com.islandtea.controller;

import com.islandtea.service.CafeInfoProvider;
import com.islandtea.service.ReviewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ReviewController {

    private final ReviewService reviewService;
    private final CafeInfoProvider cafeInfoProvider;

    public ReviewController(ReviewService reviewService, CafeInfoProvider cafeInfoProvider) {
        this.reviewService = reviewService;
        this.cafeInfoProvider = cafeInfoProvider;
    }

    @GetMapping("/reviews")
    public String reviews(Model model) {
        model.addAttribute("cafe", cafeInfoProvider.getCafe());
        model.addAttribute("timeline", reviewService.getTimeline());
        model.addAttribute("topReviews", reviewService.getTopReviews(5));
        model.addAttribute("averageRating", reviewService.getAverageRating());
        model.addAttribute("activePage", "reviews");
        return "reviews";
    }
}
