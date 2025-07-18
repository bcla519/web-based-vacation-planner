package com.example.demo.services;

import com.example.demo.dao.CartItemRepository;
import com.example.demo.dao.CartRepository;
import com.example.demo.dao.CustomerRepository;
import com.example.demo.entities.Cart;
import com.example.demo.entities.CartItem;
import com.example.demo.entities.Customer;
import com.example.demo.entities.StatusType;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
public class CheckoutServiceImpl implements CheckoutService {
    private CustomerRepository customerRepository;
    private CartRepository cartRepository;
    private CartItemRepository cartItemRepository;

    public CheckoutServiceImpl(CustomerRepository customerRepository,
                               CartRepository cartRepository,
                               CartItemRepository cartItemRepository) {
        this.customerRepository = customerRepository;
        this.cartRepository = cartRepository;
        this.cartItemRepository = cartItemRepository;
    }

    @Override
    @Transactional
    public PurchaseResponse placeOrder(Purchase purchase) {
        // retrieve order info
        Cart cart = purchase.getCart();

        if (!purchase.getCartItems().isEmpty()) {
            // update cart status
            cart.setStatus(StatusType.ordered);

            // generate tracking number
            String orderTrackingNumber = generateOrderTrackingNumber();
            cart.setOrderTrackingNumber(orderTrackingNumber);

            // populate cart with cartItems
            Set<CartItem> cartItems = purchase.getCartItems();
            cartItems.forEach(item -> cart.add(item));

            // populate customer with cart
            Customer customer = purchase.getCustomer();
            customer.add(cart);

            // save to repository
            cartRepository.save(cart);

            // return a response
            return new PurchaseResponse(orderTrackingNumber);
        } else {
            return new PurchaseResponse("Cart Empty");
        }
    }

    private String generateOrderTrackingNumber() {
        // generate a random UUID number (UUID version-4)
        return UUID.randomUUID().toString();
    }
}
