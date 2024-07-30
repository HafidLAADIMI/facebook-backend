package facebook.backend.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import facebook.backend.backend.models.User;
import facebook.backend.backend.service.UserService;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/user/{id}")
    public ResponseEntity<?> getUserById(@PathVariable int id) {
        User user = userService.getUserById(id);
        if (user != null)
            return new ResponseEntity<>(user, HttpStatus.OK);
        else
            return new ResponseEntity<>("The user Not found", HttpStatus.NOT_FOUND);
    }

    @PostMapping("/user")
    public ResponseEntity<?> addUser(@RequestPart String username,@RequestPart String password, @RequestPart MultipartFile imageFile) {

        try {
            if (username !=null &&  password !=null) {
                userService.addUser(username,password, imageFile);
                return new ResponseEntity<>("the user added", HttpStatus.OK);

            } else
                return new ResponseEntity<>("the user was not added ", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/user/{id}")
    public ResponseEntity<?> updateUser(@RequestPart String username,@RequestPart String password, @RequestPart MultipartFile imageFile,@PathVariable int id) {

        try {
            if (username !=null &&  password !=null) {
                userService.updateUser(username,password, imageFile,id);
                return new ResponseEntity<>("the user updated", HttpStatus.OK);

            } else
                return new ResponseEntity<>("the user was not updated ", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
   
    
    @DeleteMapping("user/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id){
        User user =userService.getUserById(id);
        if(user!=null){
            userService.deleteUser(id);
            return new ResponseEntity<>("the user deleted successfuly", HttpStatus.OK);
        }
        else return new ResponseEntity<>("the user wasn't found",HttpStatus.NOT_FOUND);
    }

}
