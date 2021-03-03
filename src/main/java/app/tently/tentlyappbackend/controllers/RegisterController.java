package app.tently.tentlyappbackend.controllers;


import app.tently.tentlyappbackend.interfaces.EmailSender;
import app.tently.tentlyappbackend.models.User;
import app.tently.tentlyappbackend.modelsDTO.UserDTO;
import app.tently.tentlyappbackend.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.TemplateEngine;

import javax.servlet.ServletContext;
import java.net.UnknownHostException;
import java.util.Optional;
import java.util.UUID;


@RestController
public class RegisterController {


    private final EmailSender emailSender;
    private final TemplateEngine templateEngine;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;
    private final ServletContext servletContext;

    @Autowired
    public RegisterController(BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService, EmailSender emailSender, TemplateEngine templateEngine, ServletContext servletContext) {

        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
        this.emailSender = emailSender;
        this.templateEngine = templateEngine;
        this.servletContext = servletContext;
    }


    @PostMapping(value = "/register")
    public ResponseEntity<Object> processRegistrationForm(@RequestBody UserDTO userDTO) throws UnknownHostException {
        User user = userService.mapDTOToUser(userDTO);

        Optional<User> userExists = userService.findUserByEmail(user.getEmail());

        if (userExists.isPresent()) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        } else {

            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setConfirmationToken(UUID.randomUUID().toString());
            userService.saveUser(user);
//            servletContext.getServerInfo();
//            String appUrl = "http://" + InetAddress.getLocalHost().getHostAddress();
//            System.out.println(appUrl);
//            Context context = new Context();
//            context.setVariable("header", "TentlyApp");
//            context.setVariable("title", "Potwierdzenie adresu e-mail");
//            context.setVariable("description", "Aby aktywować konto i ustawić hasło kliknij link poniżej:");
//            context.setVariable("link", appUrl + ":4040/confirm?token=" + user.getConfirmationToken());
//            String body = templateEngine.process("template", context);
//            System.out.println(body);
//            emailSender.sendEmail(user.getEmail(), "Potwierdzenie rejestracji", body);
            return new ResponseEntity<>("Activation e-mail was sent to: " + user.getEmail(), HttpStatus.OK);
        }

    }


    @GetMapping(value = "/confirm")
    public ResponseEntity<Object> showConfirmationPage(@RequestParam String token) {

        Optional<User> user = userService.findUserByToken(token);

        if (user.isPresent()) {
            return new ResponseEntity<>(HttpStatus.OK);

        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

    @PostMapping(value = "/confirm")
    public ResponseEntity<Object> setPasswordByConfirmationToken(@RequestParam String token, @RequestParam String password) {

        Optional<User> user = userService.findUserByToken(token);

        if (user.isPresent()) {
            user.get().setPassword(bCryptPasswordEncoder.encode(password));
            user.get().setEnabled(true);
            userService.saveUser(user.get());
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

    }

//    @RequestMapping(value = "/confirm", method = RequestMethod.POST)
//    public ModelAndView processConfirmationForm(ModelAndView modelAndView, @RequestParam Map<String, String> requestParams, RedirectAttributes redir) {
//
//        modelAndView.setViewName("confirm");
//        User user = userService.findByConfirmationToken(requestParams.get("token"));
//        user.setPassword(bCryptPasswordEncoder.encode(requestParams.get("password")));
//        user.setEnabled(true);
//        userService.saveUser(user);
//
//        modelAndView.addObject("successMessage", "Twoje hasło zostało ustawione");
//        return modelAndView;
//    }

}

