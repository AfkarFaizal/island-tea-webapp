package com.islandtea.controller;

import com.islandtea.model.MenuCategory;
import com.islandtea.model.MenuItem;
import com.islandtea.service.CafeInfoProvider;
import com.islandtea.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
public class MenuController {

    private final MenuService menuService;
    private final CafeInfoProvider cafeInfoProvider;

    public MenuController(MenuService menuService, CafeInfoProvider cafeInfoProvider) {
        this.menuService = menuService;
        this.cafeInfoProvider = cafeInfoProvider;
    }

    @GetMapping("/menu")
    public String menuPage(Model model) {
        model.addAttribute("cafe", cafeInfoProvider.getCafe());
        model.addAttribute("categories", MenuCategory.values());
        model.addAttribute("menuByCategory", menuService.getMenuGroupedByCategory());
        model.addAttribute("activePage", "menu");
        return "menu";
    }

    @GetMapping("/menu/item/{id}")
    public String viewItem(@PathVariable String id, Model model) {
        Optional<MenuItem> item = menuService.getAllItems().stream()
                .filter(i -> i.getId().equals(id))
                .findFirst();
        item.ifPresent(menuService::recordView);
        model.addAttribute("cafe", cafeInfoProvider.getCafe());
        model.addAttribute("item", item.orElse(null));
        model.addAttribute("activePage", "menu");
        return "menu-item";
    }

    /** REST endpoint powering trie-based autocomplete search on the menu page. */
    @GetMapping("/api/menu/search")
    @ResponseBody
    public List<MenuItem> searchMenu(@RequestParam("prefix") String prefix) {
        return menuService.searchByPrefix(prefix);
    }

    @GetMapping("/api/menu/sorted-by-price")
    @ResponseBody
    public List<MenuItem> sortedByPrice() {
        return menuService.getItemsSortedByPrice();
    }
}
