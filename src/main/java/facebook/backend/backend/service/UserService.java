package facebook.backend.backend.service;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import facebook.backend.backend.models.User;
import facebook.backend.backend.repository.UserRepository;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepo;

    public User getUserById(int  id) {
        return userRepo.findById(id).orElse(null);
    }

    public void addUser(String username,String password, MultipartFile imageFile) throws IOException {
        User user=new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setImage(imageFile.getBytes());
        user.setImageType(imageFile.getContentType());
        userRepo.save(user);

    }

    public void updateUser(String username, String password, MultipartFile imageFile,int id) throws IOException {
        User user=userRepo.findById(id).orElse(null);
        if(user!=null){
            user.setUsername(username);
            user.setPassword(password);
            user.setImage(imageFile.getBytes());
            user.setImageType(imageFile.getContentType());
            userRepo.save(user);
        }
        else {
            throw new IllegalArgumentException("User not found with id: " + id);
        }
    }

    public void deleteUser(int id) {
        userRepo.deleteById(id);
    }

}
