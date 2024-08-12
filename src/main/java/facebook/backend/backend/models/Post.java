package facebook.backend.backend.models;

import java.util.Date;
import java.util.Base64;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Lob;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "POST")
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long post_id;
    private String text;
    
    // handling files
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    @JsonIgnore
    private byte[] file;
    private String fileType;
    
    // handling date
    private Date timestamp;

    // joining the user table
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnore 
    private User user;

    // Transient field for Base64 encoded string
    @JsonProperty("file")
    public String getFileBase64() {
        return this.file != null ? Base64.getEncoder().encodeToString(this.file) : null;
    }

    @Override
    public String toString() {
        return "Post{" +
                "post_id=" + post_id +
                ", text='" + text + '\'' +
                ", fileType='" + fileType + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
