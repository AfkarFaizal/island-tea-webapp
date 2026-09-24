package com.islandtea.service;

import com.islandtea.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Seeds the in-memory "database" on application startup with the real
 * details from the Island Tea Co. & Ceylon Coffee Club - Malabe listing,
 * plus a representative Ceylon tea / coffee menu (menu photos were not
 * provided, so items here are illustrative - update via MenuService
 * once real menu data is available).
 */
@Component
public class CafeDataInitializer implements CommandLineRunner {

    private final MenuService menuService;
    private final ReviewService reviewService;
    private final CafeInfoProvider cafeInfoProvider;

    public CafeDataInitializer(MenuService menuService, ReviewService reviewService,
                                CafeInfoProvider cafeInfoProvider) {
        this.menuService = menuService;
        this.reviewService = reviewService;
        this.cafeInfoProvider = cafeInfoProvider;
    }

    @Override
    public void run(String... args) {
        seedCafe();
        seedMenu();
        seedReviews();
    }

    private void seedCafe() {
        Cafe cafe = Cafe.builder()
                .name("Island Tea Co. & Ceylon Coffee Club")
                .tagline("Malabe's calm corner for Ceylon tea, coffee & breakfast")
                .address("ICC Techno Hub, No. 152/7 Chandrika Kumarathunga Mawatha, Malabe")
                .plusCode("WXC4+5V Malabe")
                .phone("070 788 5733")
                .priceRange("Rs 1,000 - 2,000 per person")
                .googleRating(4.9)
                .googleReviewCount(40)
                .addFeature("Dine-in")
                .addFeature("Takeaway")
                .addFeature("No-contact delivery")
                .addFeature("Spacious parking")
                .addFeature("Board games")
                .build();
        cafeInfoProvider.setCafe(cafe);
    }

    private void seedMenu() {
        menuService.addItem(new MenuItem("t1", "Ceylon High-Grown Black Tea",
                "Single-estate high-grown black tea, brewed fresh to order", 350, MenuCategory.CEYLON_TEA, true));
        menuService.addItem(new MenuItem("t2", "Cinnamon Spiced Ceylon Tea",
                "Black tea infused with true Ceylon cinnamon bark", 450, MenuCategory.CEYLON_TEA, true));
        menuService.addItem(new MenuItem("t3", "Ceylon Green Tea",
                "Light, grassy green tea from the island's central highlands", 400, MenuCategory.CEYLON_TEA, false));
        menuService.addItem(new MenuItem("c1", "Ceylon Single Origin Espresso",
                "Locally roasted single-origin espresso, short and intense", 500, MenuCategory.COFFEE, true));
        menuService.addItem(new MenuItem("c2", "Flat White",
                "Espresso with steamed milk and a thin layer of microfoam", 650, MenuCategory.COFFEE, false));
        menuService.addItem(new MenuItem("c3", "Iced Caramel Latte",
                "Espresso, milk, caramel, served over ice", 750, MenuCategory.COFFEE, false));
        menuService.addItem(new MenuItem("b1", "Island Breakfast Plate",
                "Eggs, toast, seasonal fruit and a pot of Ceylon tea", 1200, MenuCategory.BREAKFAST, true));
        menuService.addItem(new MenuItem("b2", "Egg Hoppers (2 pcs)",
                "Crisp-edged bowl-shaped hoppers with a soft egg centre", 550, MenuCategory.BREAKFAST, false));
        menuService.addItem(new MenuItem("s1", "Cheese Toastie",
                "Grilled sandwich with a generous cheese filling", 650, MenuCategory.SNACKS, false));
        menuService.addItem(new MenuItem("s2", "Fish Cutlets (3 pcs)",
                "Sri Lankan-style spiced fish cutlets, deep fried to order", 500, MenuCategory.SNACKS, false));
        menuService.addItem(new MenuItem("d1", "Watalappan",
                "Traditional coconut-jaggery steamed custard", 450, MenuCategory.DESSERTS, true));
        menuService.addItem(new MenuItem("d2", "Chocolate Biscuit Pudding",
                "Layered Sri Lankan party classic", 550, MenuCategory.DESSERTS, false));
    }

    private void seedReviews() {
        reviewService.addReview(new LocalGuideReviewer(
                "Dil", 5.0,
                "Spacious and cool place with a very calm environment inside the cafe. "
                        + "Went around 7.30AM for breakfast and it wasn't crowded at all.",
                LocalDate.now().minusMonths(9), 21, 64));

        reviewService.addReview(new LocalGuideReviewer(
                "Nirmani Samarakoon", 5.0,
                "So calming and situated in the office location. Plenty of parking, amazing "
                        + "games to try, and you can even colour a bookmark to take home.",
                LocalDate.now().minusMonths(7), 981, 3701));

        reviewService.addReview(new RegularReviewer(
                "Nuwan Wijesinghe", 5.0,
                "Wonderful experience. The coffee was excellent, the staff were welcoming, "
                        + "and the place had a relaxing vibe. Highly recommend.",
                LocalDate.now().minusMonths(10)));
    }
}
