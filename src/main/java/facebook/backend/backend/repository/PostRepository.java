package facebook.backend.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import facebook.backend.backend.models.Post;


@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {
}
