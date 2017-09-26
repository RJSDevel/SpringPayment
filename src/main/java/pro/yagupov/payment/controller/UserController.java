package pro.yagupov.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import pro.yagupov.payment.domain.entity.auth.Login;
import pro.yagupov.payment.domain.entity.auth.UserGroup;
import pro.yagupov.payment.service.DatabaseUserDetailsService;
import pro.yagupov.payment.service.UserService;

import java.security.Principal;

/**
 * Created by Yagupov Ruslan on 20.03.17.
 */
@RestController
public class UserController {

    @Autowired
    private DatabaseUserDetailsService userDetailsService;

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/user/registration", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    void registration(@RequestBody Login login) {
        userService.createUser(login);
    }

    @PreAuthorize(UserGroup.ROLE_USER)
    @RequestMapping(value = "/user", method = RequestMethod.GET)
    @ResponseBody
    public UserDetails userInfo(Principal principal) {
        return userDetailsService.loadUserByUsername(principal.getName());
    }
}
