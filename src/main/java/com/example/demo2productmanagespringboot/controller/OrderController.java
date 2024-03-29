package com.example.demo2productmanagespringboot.controller;

import com.example.demo2productmanagespringboot.model.*;
import com.example.demo2productmanagespringboot.service.IOrderDetailService;
import com.example.demo2productmanagespringboot.service.IOrderService;
import com.example.demo2productmanagespringboot.service.IProductService;
import com.example.demo2productmanagespringboot.service.IUserService;
import com.example.demo2productmanagespringboot.service.impl.OrderService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.sql.Timestamp;
import java.util.Map;
import java.util.Optional;

@Controller
@SessionAttributes("cart")
@RequestMapping("/api/checkout")
public class OrderController {

    @ModelAttribute("cart")
    public Cart setUpCart() {
        return new Cart();
    }

    @Autowired
    private IProductService iProductService;

    @Autowired
    private IOrderService iorderService;

    @Autowired
    private IOrderDetailService iOrderDetailService;

    @Autowired
    private IUserService iUserService;

    @GetMapping("")
    public String showOrder(HttpSession session, RedirectAttributes redirectAttributes) {
        Cart cart = (Cart) session.getAttribute("cart");

        // Chuyển dữ liệu giỏ hàng sang trang order
        redirectAttributes.addFlashAttribute("cart", cart);

        return "redirect:/api/checkout/order-summary";
    }

    @GetMapping("/order-summary")
    public ModelAndView showOrderSummary(@ModelAttribute("cart") Cart cart) {
        ModelAndView modelAndView = new ModelAndView("order/list");

        modelAndView.addObject("cart", cart);
        return modelAndView;
    }

    @GetMapping("/place-order")
    public ModelAndView payment(@ModelAttribute("cart") Cart cart) {

        //giả sử một user mặc định. User sẽ lấy qua userid từ session
        Optional<User> user = iUserService.findById(Long.valueOf(3));

        if (user.isPresent()) {

            //Tạo đơn hàng cho user
            Order order = new Order();

            order.setUser(user.get());
            order.setPhoneNumber("0968949246");
            order.setDeliveryAddress("Ha Noi");
            order.setOrderDate(new Timestamp(System.currentTimeMillis()));
            order.setStatus("Started");
            order.setTotalPrice(cart.countTotalPayment());

            iorderService.save(order);

            // Lấy danh sách products từ cart, cho vào orderdetail
            Map<Product, Integer> products = cart.getProducts();
            for (Map.Entry<Product, Integer> entry : products.entrySet()) {
                Product product = entry.getKey();
                Long quantity = Long.valueOf(entry.getValue());

                OrderDetail orderDetail = new OrderDetail();
                orderDetail.setOrder(order);
                orderDetail.setProduct(product);
                orderDetail.setQuantity(quantity);
                orderDetail.setPrice(product.getPrice() * quantity);
                iOrderDetailService.save(orderDetail);

                // trừ đi số lượng sản phẩm
                product.setQuantity(product.getQuantity() - quantity);
                iProductService.save(product);
            }
            //xóa giỏ hàng
            cart.deleteAllFromCart();
            ModelAndView modelAndView = new ModelAndView("/order/success");
            return modelAndView;
        }
        return new ModelAndView("/error_404");
    }
}
