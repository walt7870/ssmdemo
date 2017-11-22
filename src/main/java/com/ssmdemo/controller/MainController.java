package com.ssmdemo.controller;

import com.ssmdemo.model.Customer;

import com.ssmdemo.service.inf.ICustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MainController {
    @Autowired
    ICustomerService customerService;

    @RequestMapping(value = "/ssmdemo/insert")
    @ResponseBody
    public int insert(String name,String password){
        Customer customer = new Customer();
        customer.setName(name);
        customer.setPassword(password);
        return customerService.insert(customer);
    }
    @RequestMapping(value = "/ssmdemo/findAll")
    @ResponseBody
    public List<Customer> findAll(){

        return customerService.findAll();
    }
}
