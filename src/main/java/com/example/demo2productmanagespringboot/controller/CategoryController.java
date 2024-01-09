package com.example.demo2productmanagespringboot.controller;

import com.example.demo2productmanagespringboot.model.Category;
import com.example.demo2productmanagespringboot.model.Product;
import com.example.demo2productmanagespringboot.repository.ICategoryRepository;
import com.example.demo2productmanagespringboot.service.ICategoryService;
import com.example.demo2productmanagespringboot.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/api/categories")
public class CategoryController {

    @Autowired
    private ICategoryService iCategoryService;

    @Autowired
    private IProductService iProductService;


    @GetMapping()
    public ModelAndView listCategories() {
        ModelAndView modelAndView = new ModelAndView("/categories/list");
        Iterable<Category> categories = iCategoryService.findAll();
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/page")
    public ModelAndView listCategories(@PageableDefault(size = 5) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("/categories/page");
        Iterable<Category> categories = iCategoryService.findAllPage(pageable);
        modelAndView.addObject("categories", categories);
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView addForm() {
        ModelAndView modelAndView = new ModelAndView("/categories/add");
        modelAndView.addObject("category", new Category());
        return modelAndView;
    }

    @PostMapping("/add")
    public String add(@ModelAttribute("category") Category category,
                      RedirectAttributes redirectAttributes) {
        iCategoryService.save(category);
        redirectAttributes.addFlashAttribute("message", "Create new category successfully");

        return "redirect:/api/categories";
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Category> category = iCategoryService.findById(id);
        if (category.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/categories/update");
            modelAndView.addObject("category", category.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error-404");
        }
    }

    @PostMapping("/update/{id}")
    public String update(@ModelAttribute("category") Category category,
                         RedirectAttributes redirectAttributes) {
        iCategoryService.save(category);
        redirectAttributes.addFlashAttribute("message", "Update category successfully");
        return "redirect:/api/categories";
    }

    @GetMapping("/view-category/{id}")
    public ModelAndView viewCategory(@PathVariable("id") Long id) {
        Optional<Category> category = iCategoryService.findById(id);
        if (category.isPresent()) {
            Iterable<Product> products = iProductService.findByCategory(category.get());

            ModelAndView modelAndView = new ModelAndView("/products/list");
            modelAndView.addObject("products", products);
            return modelAndView;
        } else {
            return new ModelAndView("/error-404");
        }
    }

    @GetMapping("/deactivate/{id}")
    public String deactivate(@PathVariable Long id) {

        Optional<Category> categoryOptional = iCategoryService.findById(id);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            category.setActive(false);
            iCategoryService.save(category);
            return "redirect:/api/categories";
        } else {
            return "/error-404";
        }
    }
}
