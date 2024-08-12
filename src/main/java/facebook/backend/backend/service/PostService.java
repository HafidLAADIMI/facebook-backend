package facebook.backend.backend.service;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import facebook.backend.backend.models.Post;

import facebook.backend.backend.models.User;
import facebook.backend.backend.repository.PostRepository;

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    public Post getPostById(int id) {
        return postRepository.findById(id).orElse(null);
    }

    public void addPost(String text, MultipartFile file,User user) throws IOException {
        Post post =new Post();
        post.setFileType(file.getContentType());
        post.setFile(file.getBytes());
        post.setText(text);
        post.setTimestamp(new Date());
        user.addPost(post);
        postRepository.save(post);
    }

    public void updatePost(String text, MultipartFile file, User user,int id) throws IOException {
        Post post =postRepository.findById(id).orElse(null);
        if(post!=null){
            post.setFileType(file.getContentType());
            post.setFile(file.getBytes());
            post.setText(text);
            post.setTimestamp(new Date());
            user.addPost(post);
            postRepository.save(post);
        } else {
            throw new IllegalArgumentException("Post not found with id: " + id);
        }
       
    }

    public void deletePost(int id) {
         postRepository.deleteById(id);
    
    }

    public List<Post> getALlPosts() {
          return postRepository.findAll();
    }

  

}