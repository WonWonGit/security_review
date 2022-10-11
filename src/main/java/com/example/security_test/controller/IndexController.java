package com.example.security_test.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.security_test.dto.UserJoinDTO;
import com.example.security_test.model.User;
import com.example.security_test.repository.UserRepository;
import com.example.security_test.type.RoleEnum;

@Controller
public class IndexController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @GetMapping({"","/"})
    public @ResponseBody String index(){
        return "index";
    }

    @GetMapping("/user")
    public @ResponseBody String user(){
        return "user";
    }

    @GetMapping("/admin")
    public  @ResponseBody String admin(){
        return "admin";
    }

    @GetMapping("/manager")
    @ResponseBody
    public String manager(){
        return "manager";
    }

    @GetMapping("/loginForm")
    public String loginForm(){
        return "loginForm";
    }

    @GetMapping("/joinForm")
    public String joinForm(){
        return "joinForm";
    }

    @PostMapping("/join")
    public String join(UserJoinDTO userJoinDTO){
        String rawPassword = userJoinDTO.getPassword();
        String encPassword = bCryptPasswordEncoder.encode(rawPassword);

        userJoinDTO.setRole(RoleEnum.USER.role());
        userJoinDTO.setPassword(encPassword);
        
        User user = userJoinDTO.toEntity();
        userRepository.save(user);

        return "redirect:/loginForm";
    }
    

}
