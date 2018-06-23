package ru.iqdevelop.barterme.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.iqdevelop.barterme.models.auth.RegistrationModel;
import ru.iqdevelop.barterme.models.auth.UserInfoModel;
import ru.iqdevelop.barterme.models.common.AnswerMessage;
import ru.iqdevelop.barterme.services.AuthService;
import ru.iqdevelop.barterme.services.CustomUserDetailService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomUserDetailService customUserDetailService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage register(@RequestBody RegistrationModel registrationModel, HttpServletRequest request) {
        try {
            logger.info("Start register");

            authService.register(registrationModel);
            doLogin(registrationModel.getEmail(), registrationModel.getPassword(), request);

            return AnswerMessage.getSuccessMessage();
        } catch (Exception e) {
            logger.error(String.format("register ERROR: %s", e.getMessage()));
            return AnswerMessage.getFailMessage(e.getMessage());
        }
    }


    @RequestMapping(value = "/confirmUser", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage confirmUser(Principal principal, @RequestParam("confirmToken") String confirmToken) {
        try {
            logger.info("Start confirmUser");

            authService.confirmUser(confirmToken, principal.getName());

            Object credentials = SecurityContextHolder.getContext().getAuthentication().getCredentials();
            UserDetails userDetails = customUserDetailService.loadUserByUsername(principal.getName());
            SecurityContextHolder.getContext().setAuthentication(new UsernamePasswordAuthenticationToken(principal, credentials, userDetails.getAuthorities()));

            return AnswerMessage.getSuccessMessage();
        } catch (Exception e) {
            logger.error(String.format("confirmUser ERROR: %s", e.getMessage()));
            return AnswerMessage.getFailMessage(Arrays.toString(e.getStackTrace()));
        }
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage getUserInfo(Principal principal) {
        try {
            logger.info("Start getUserInfo");

            if (principal == null) {
                return AnswerMessage.getFailMessage("Пользователь не авторизован");
            }

            List<String> roles = authService.getCurrentRoles(principal.getName());
            String email = principal.getName();
            UserInfoModel model = new UserInfoModel();
            model.setEmail(email);
            model.setRoles(roles);
            return AnswerMessage.getSuccessMessage(model);
        } catch (Exception e) {
            logger.error(String.format("getUserInfo ERROR: %s", e.getMessage()));
            return AnswerMessage.getFailMessage(e.getMessage());
        }
    }

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public ModelAndView login(@RequestParam(value = "error", required = false) String error,
//                              @RequestParam(value = "logout", required = false) String logout) {
//
//        ModelAndView model = new ModelAndView();
//        if (error != null) {
//            model.addObject("error", "Не верная пара логин\\пароль.");
//        }
//
//        if (logout != null) {
//            model.addObject("msg", "Вы вышли из личного кабинета.");
//        }
//        model.setViewName("auth/customLogin");
//
//        return model;
//
//    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    AnswerMessage login(@RequestBody RegistrationModel model, HttpServletRequest request) {
        doLogin(model.getEmail(), model.getPassword(), request);
        return AnswerMessage.getSuccessMessage();
    }


    private void doLogin(String user, String pass, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken authReq = new UsernamePasswordAuthenticationToken(user, pass);
        Authentication auth = authenticationManager.authenticate(authReq);

        SecurityContext sc = SecurityContextHolder.getContext();
        sc.setAuthentication(auth);
        HttpSession session = request.getSession(true);
        session.setAttribute(SPRING_SECURITY_CONTEXT_KEY, sc);
    }

}
