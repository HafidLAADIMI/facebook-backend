package facebook.backend.backend.models;

import java.util.Date;

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
    private byte[] file;
    private String fileType;
    // handling date
    private Date timestamp;

    // joining the user table
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

}
