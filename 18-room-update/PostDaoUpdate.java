// Dodaj u PostDao.java (folder 18-room-update/)

import androidx.room.Update;
import java.util.List;

@Update
void update(Post post);

@Update
void updateAll(List<Post> posts);

// Alternativa – jedna SQL komanda:
@Query("UPDATE posts SET title = :noviTitle WHERE rowid = (SELECT rowid FROM posts LIMIT 1)")
void updateTitleFirst(String noviTitle);
