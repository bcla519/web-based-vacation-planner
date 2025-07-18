package com.example.demo.bootstrap;

import com.example.demo.dao.CustomerRepository;
import com.example.demo.dao.DivisionRepository;
import com.example.demo.entities.Customer;
import com.example.demo.entities.Division;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class BootStrapData implements CommandLineRunner {
    private final CustomerRepository customerRepository;
    private final DivisionRepository divisionRepository;

    @Autowired
    public BootStrapData(CustomerRepository customerRepository, DivisionRepository divisionRepository) {
        this.customerRepository = customerRepository;
        this.divisionRepository = divisionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (customerRepository.count() == 1) {
            Division division = divisionRepository.getReferenceById(42L);

            // customer1
            Customer customer1 = new Customer("Amanda", "Burns", "111 Apple St",
                    "11111", "214-111-1111", division);
            customerRepository.save(customer1);

            // customer2
            Customer customer2 = new Customer("Bernie", "Cox", "111 Banana St",
                    "11111", "214-222-2222", division);
            customerRepository.save(customer2);

            // customer3
            Customer customer3 = new Customer("Courtney", "Davenport", "111 Chestnut St",
                    "11111", "214-333-3333", division);
            customerRepository.save(customer3);

            // customer4
            Customer customer4 = new Customer("David", "Emery", "111 Dandelion St",
                    "11111", "214-444-4444", division);
            customerRepository.save(customer4);

            // customer5
            Customer customer5 = new Customer("Elizabeth", "Frost", "111 Eagle St",
                    "11111", "214-555-5555", division);
            customerRepository.save(customer5);
        }

    }
}
