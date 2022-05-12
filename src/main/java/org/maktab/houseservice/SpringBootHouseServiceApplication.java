package org.maktab.houseservice;

import org.maktab.houseservice.model.entity.Admin;
import org.maktab.houseservice.model.entity.AppUser;
import org.maktab.houseservice.model.entity.UserRole;
import org.maktab.houseservice.repository.AdminRepository;
import org.maktab.houseservice.repository.UserRepository;
import org.maktab.houseservice.service.EmailSenderService;
import org.maktab.houseservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;

@SpringBootApplication
public class SpringBootHouseServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootHouseServiceApplication.class, args);

    }
}
