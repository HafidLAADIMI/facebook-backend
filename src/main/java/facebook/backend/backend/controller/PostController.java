package facebook.backend.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import facebook.backend.backend.models.Post;
import facebook.backend.backend.models.User;
import facebook.backend.backend.service.PostService;
import facebook.backend.backend.service.UserService;

@RestController
public class PostController {
    @Autowired
    private PostService postService;
    @Autowired
    private UserService userService;

    @GetMapping("/post/{id}")
    public ResponseEntity<?> getPostById(@PathVariable int id) {
        Post post = postService.getPostById(id);
        if (post != null)
            return new ResponseEntity<>(post, HttpStatus.OK);
        else
            return new ResponseEntity<>("The post not found", HttpStatus.NOT_FOUND);

    }

    @PostMapping("/post")
    public ResponseEntity<?> addPost(@RequestPart("text") String text, @RequestPart("file") MultipartFile file,@RequestParam("userId") Integer userId) {
        try {

            if (text != null || file != null) {
                
                User user=userService.getUserById(userId);
                System.out.println(user);
                postService.addPost(text, file,user);
                return new ResponseEntity<>("the post added successfuly", HttpStatus.OK);

            } else
                return new ResponseEntity<>("you have to provide a file or a text", HttpStatus.BAD_REQUEST);

        }

        catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @PutMapping("/post/{id}")
    public ResponseEntity<?> updatePost(@PathVariable int id,@RequestPart("text") String text, @RequestPart("file") MultipartFile file,@RequestParam("userId") Integer userId) {
        try {

            if (text != null || file != null) {
                
                User user=userService.getUserById(userId);
                System.out.println(user);
                postService.updatePost(text, file,user,id);
                return new ResponseEntity<>("the post updated successfuly", HttpStatus.OK);

            } else
                return new ResponseEntity<>("you have to provide a file or a text", HttpStatus.BAD_REQUEST);

        }

        catch (Exception e) {

            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @DeleteMapping("post/{id}")
    public ResponseEntity<?> deletePost(@PathVariable int id){
        Post post =postService.getPostById(id);
        if(post!=null){
            postService.deletePost(id);
            return new ResponseEntity<>("the post deleted successfuly", HttpStatus.OK);
        }
        else return new ResponseEntity<>("the post wasn't found",HttpStatus.NOT_FOUND);
    }

}
