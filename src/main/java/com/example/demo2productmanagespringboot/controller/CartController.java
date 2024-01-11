//package com.example.demo2productmanagespringboot.controller;
//
//import com.example.demo2productmanagespringboot.model.Cart;
//import com.example.demo2productmanagespringboot.model.Product;
//import com.example.demo2productmanagespringboot.service.IProductService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.Optional;
//
//@Controller
//@SessionAttributes("cart")
//@RequestMapping("/api/shopping-cart")
//public class CartController {
//
//    @Autowired
//    private IProductService iProductService;
//    @ModelAttribute("cart")
//    public Cart setupCart() {
//        return new Cart();
//    }
//
//    @GetMapping("")
//    public ModelAndView showCart(@SessionAttribute("cart") Cart cart) {
//        ModelAndView modelAndView = new ModelAndView("/cart/list");
//        modelAndView.addObject("cart", cart);
//        return modelAndView;
//    }
//
//    @GetMapping("/delete/{id}")
//    public String deleteFromCart(@PathVariable Long id,
//                                 @ModelAttribute Cart cart) {
//        Optional<Product> productOptional = iProductService.findById(id);
//        if (!productOptional.isPresent()) {
//            return "/error_404";
//        } else {
//            cart.deleteProductFromCart(id);
//            return "redirect:/api/shopping-cart";
//        }
//    }
//
////    @GetMapping("/deleteAll")
////    public String deleteFromCart(@ModelAttribute Cart cart) {
////        cart.deleteAllFromCart();
////        return "redirect:/api/shopping-cart";
////    }
//}
