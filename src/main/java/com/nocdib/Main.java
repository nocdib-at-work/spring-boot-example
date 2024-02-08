package com.nocdib;

import com.github.javafaker.Faker;
import java.util.List;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication // same as @Configuration @EnableAutoConfiguration @ComponentScan
@RestController // Any method in the class with HTTP action annotations are exposed as REST endpoints
@RequestMapping("api/v1")
public class Main {

    private final CustomerRepository customerRepository;
    Faker faker = new Faker();

    public Main(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    /*
        Show all saved customers
    */
    @GetMapping("/customers")
    public List<Customer> getCustomers() {

        return customerRepository.findAll();
    }

    /*
        Read as JSON array of customer objects and save them
    */
    @PostMapping("/customer")
    public void addCustomer(@RequestBody NewCustomerRequest request){
        Customer newCustomer = new Customer();
        newCustomer.setName(request.name());
        newCustomer.setEmail(request.email());
        newCustomer.setAge(request.age());
        customerRepository.save(newCustomer);
        System.out.println(request);
    }

    /*
        Read a JSON array of customer objects and save them
     */
    @PostMapping("/customers")
    public void addCustomers(@RequestBody List<NewCustomerRequest> request) {
        request.forEach(customer -> {
            Customer newCustomer = new Customer();
            newCustomer.setName(customer.name());
            newCustomer.setEmail(customer.email());
            newCustomer.setAge(customer.age());
            customerRepository.save(newCustomer);
            System.out.println(newCustomer);
        });
    }

    /*
        Generate a given number of customers
    */
    @PostMapping("/customers/{number}")
    public void generateCustomers(@PathVariable("number") int number){
        while(number > 0) {
            Customer newCustomer = new Customer();
            newCustomer.setName(faker.name().fullName());
            newCustomer.setEmail(faker.internet().emailAddress());
            newCustomer.setAge(faker.number().numberBetween(15, 75));
            customerRepository.save(newCustomer);
            number--;
        }
    }

    /*
       Update customer by id
   */
    @PutMapping("/customer/{customerId}")
    public void removeCustomer(@PathVariable("customerId") Integer id, @RequestBody NewCustomerRequest request) {
        Customer newCustomer = new Customer();
        newCustomer.setId(id);
        newCustomer.setName(request.name());
        newCustomer.setEmail(request.email());
        newCustomer.setAge(request.age());
        customerRepository.save(newCustomer);
    }

    /*
        Delete customer by id
    */
    @DeleteMapping("/customer/{customerId}")
    public void removeCustomer(@PathVariable("customerId") Integer id) {
        customerRepository.deleteById(id);
    }

}

