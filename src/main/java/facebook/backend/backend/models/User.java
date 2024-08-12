package facebook.backend.backend.models;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "USER")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long user_id;
    private String username;
    private String password;

    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] image;
    private String imageType;

    // handling the relation between the User and the Post tables
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore 
    private List<Post> posts;

    // ensuring the bidirectionnal relationship between the User and Post models

    // in adding posts
    public void addPost(Post post) {
        posts.add(post);
        post.setUser(this);
    }

    // in deleting posts
    public void deletePost(Post post) {
        posts.remove(post);
        post.setUser(null);
    }

    // @Override
    // public String toString() {
    //     return "User [user_id=" + user_id + ", username=" + username + ", password=" + password + ", image="
    //             + Arrays.toString(image) + ", imageType=" + imageType + ", posts=" + posts + "]";
    // }

    @Override
    public String toString() {
        return "User{" +
                "user_id=" + user_id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", imageType='" + imageType + '\'' +
                '}';
    }

}
