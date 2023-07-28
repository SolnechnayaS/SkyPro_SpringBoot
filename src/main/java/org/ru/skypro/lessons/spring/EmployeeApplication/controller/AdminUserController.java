//package org.ru.skypro.lessons.spring.EmployeeApplication.controller;
//
//import org.ru.skypro.lessons.spring.EmployeeApplication.exception.UserNotFoundException;
//import org.ru.skypro.lessons.spring.EmployeeApplication.security.AuthUser;
//import org.ru.skypro.lessons.spring.EmployeeApplication.service.UserService;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/admin")
//public class AdminUserController {
//
//    private final UserService userService;
//
//    public AdminUserController(UserService userService) {
//        this.userService = userService;
//    }
//
//    @ExceptionHandler(UserNotFoundException.class)
//    public ResponseEntity<?> handleError(UserNotFoundException e) {
//        return ResponseEntity.notFound().build();
//    }
//
//    @PostMapping("/user")
//    public void createNewUser(@RequestBody AuthUser user) {
//        userService.addUser(user);
//    }
//
//    @GetMapping("/user")
//    public List<AuthUser> users() {
//        return userService.getAllUsers();
//    }
//
//    @PutMapping("(/user/{id}")
//    public AuthUser changeUser(@PathVariable("id") long id, @RequestBody AuthUser userDTO) {
//        return userService.editUser(id, userDTO);
//    }
//
//    @DeleteMapping("/user/{id}")
//    public ResponseEntity<?> deleteUser(@PathVariable("id") long id) {
//        userService.deleteUserById(id);
//        return ResponseEntity.noContent().build();
//    }
//}
