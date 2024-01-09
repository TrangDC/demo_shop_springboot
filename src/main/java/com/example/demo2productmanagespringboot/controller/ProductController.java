package com.example.demo2productmanagespringboot.controller;

import com.example.demo2productmanagespringboot.model.Cart;
import com.example.demo2productmanagespringboot.model.Category;
import com.example.demo2productmanagespringboot.model.Product;
import com.example.demo2productmanagespringboot.model.ProductForm;
import com.example.demo2productmanagespringboot.repository.IProductRepository;
import com.example.demo2productmanagespringboot.service.ICategoryService;
import com.example.demo2productmanagespringboot.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Optional;

@Controller
@SessionAttributes("cart")
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private IProductService iProductService;

    @Autowired
    private ICategoryService iCategoryService;

    @Value("${file-upload}")
    private String fileUpload;

    @ModelAttribute("categories")
    public Iterable<Category> listCategories() {
        return iCategoryService.findAll();
    }

    @ModelAttribute("cart")
    public Cart setUpCart() {
        return new Cart();
    }

    @GetMapping()
    public ModelAndView listProductsActive() {
        ModelAndView modelAndView = new ModelAndView("/products/list");
        Iterable<Product> products = iProductService.findAll();
        ArrayList<Product> productsTrue = new ArrayList<Product>();

        for (Product product : products) {
            if (product.isActive()) {
                productsTrue.add(product);
            }
        }
        modelAndView.addObject("products", productsTrue);
        return modelAndView;
    }

    @GetMapping("/page")
    public ModelAndView listProductsActive(@PageableDefault(size = 7)Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("/products/page");
        Page<Product> products = iProductService.findAllPage(pageable);
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/add")
    public ModelAndView addForm() {
        ModelAndView modelAndView = new ModelAndView("/products/add");
        modelAndView.addObject("product", new ProductForm());
        return modelAndView;
    }

    @PostMapping("/add")
    public ModelAndView addProduct(@Validated @ModelAttribute("product") ProductForm productForm,
                                   BindingResult bindingResult,
                                   @PageableDefault(size = 7)Pageable pageable) {
        if (bindingResult.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("/products/add");
            modelAndView.addObject("product", productForm);
            return modelAndView;
        } else {
            MultipartFile multipartFile = productForm.getImage();
            String fileName = multipartFile.getOriginalFilename();

            try {
                FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            Product product = new Product(productForm.getName(), productForm.getPrice(),
                    productForm.getDescription(), fileName, productForm.getQuantity(), productForm.getCategory());
            iProductService.save(product);
            ModelAndView modelAndView = new ModelAndView("/products/page");
            Page<Product> products = iProductService.findAllPage(pageable);
            modelAndView.addObject("products", products);
            return modelAndView;
        }
    }

    @GetMapping("/update/{id}")
    public ModelAndView updateForm(@PathVariable Long id) {
        Optional<Product> product = iProductService.findById(id);
        if (product.isPresent()) {
            ModelAndView modelAndView = new ModelAndView("/products/update");
            modelAndView.addObject("product", product.get());
            return modelAndView;
        } else {
            return new ModelAndView("/error_404");
        }
    }

    @PostMapping("/update/{id}")
    public String update(@Validated @ModelAttribute ProductForm productForm,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes,
                         Model model,
                         @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("product", productForm);
            return "/products/update";
        } else {
            Optional<Product> existingProductOptional = iProductService.findById(id);

            if (existingProductOptional.isPresent()) {
                MultipartFile multipartFile = productForm.getImage();
                String fileName = multipartFile.getOriginalFilename();

                try {
                    FileCopyUtils.copy(productForm.getImage().getBytes(), new File(fileUpload + fileName));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                Product existingProduct = existingProductOptional.get();
                existingProduct.setName(productForm.getName());
                existingProduct.setPrice(productForm.getPrice());
                existingProduct.setDescription(productForm.getDescription());
                existingProduct.setImage(fileName);
                existingProduct.setQuantity(productForm.getQuantity());
                existingProduct.setCategory(productForm.getCategory());

                iProductService.save(existingProduct);
                redirectAttributes.addFlashAttribute("message", "Update Successfully");
                return "redirect:/api/products/page";
            } else {
                return "/error_404";
            }
        }
    }

    @GetMapping("/deactivate/{id}")
    public String deactivate(@PathVariable Long id) {

        Optional<Product> productOptional = iProductService.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setActive(false);
            iProductService.save(product);
            return "redirect:/api/products";
        } else {
            return "/error_404";
        }
    }

    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable Long id,
                            @ModelAttribute Cart cart,
                            @RequestParam("action") String action) {
        Optional<Product> productOptional = iProductService.findById(id);

        if(!productOptional.isPresent()) {
            return "/error_404";
        }
        if (action.equals("show")) {
            cart.addProduct(productOptional.get());
            return "redirect:/api/shopping-cart";
        }
        cart.addProduct(productOptional.get());
        return "redirect:/api/products";
    }

    @GetMapping("/sub/{id}")
    public String subFromCart(@PathVariable Long id,
                              @ModelAttribute Cart cart,
                              @RequestParam("action") String action) {
        Optional<Product> productOptional = iProductService.findById(id);
        if(!productOptional.isPresent()) {
            return "/error_404";
        }
        if (action.equals("show")) {
            cart.subProduct(productOptional.get());
            return "redirect:/api/shopping-cart";
        }
        cart.subProduct(productOptional.get());
        return "redirect:/api/products";
    }

    @GetMapping("/sortAsc")
    public String sortAsc(Model model,
                          @PageableDefault(size = 7) Pageable pageable) {
//        Iterable<Product> products = iProductService.sortPriceAscending();
        Page<Product> products = iProductService.sortPriceAscending(pageable);
        model.addAttribute("products", products);
        return "/products/page";
    }

    @GetMapping("/sortDesc")
    public String sortDesc(Model model,
                           @PageableDefault(size = 7) Pageable pageable) {
//        Iterable<Product> products = iProductService.sortPriceDescending();
        Page<Product> products = iProductService.sortPriceDescending(pageable);
        model.addAttribute("products", products);
        return "/products/page";
    }

    @GetMapping("/page/search")
    public ModelAndView search(@RequestParam("keyword") String keyword,
                               @PageableDefault(size = 4) Pageable pageable) {
        ModelAndView modelAndView = new ModelAndView("/products/page_search");
        Page<Product> products = iProductService.searchByWord(keyword, pageable);
        modelAndView.addObject("products", products);
        modelAndView.addObject("keyword", keyword);
        return modelAndView;
    }
}
