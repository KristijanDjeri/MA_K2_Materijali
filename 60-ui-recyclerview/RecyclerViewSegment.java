// === DODAJ U MainActivity.java ===

// IMPORTI:
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.kolokvijum2.adapter.PostAdapter;

// U activity_main.xml dodaj (npr. ispod Button):
// <androidx.recyclerview.widget.RecyclerView
//     android:id="@+id/recyclerView"
//     android:layout_width="match_parent"
//     android:layout_height="200dp" />

// POLJA:
private RecyclerView recyclerView;
private PostAdapter postAdapter;

// U onCreate():
recyclerView = findViewById(R.id.recyclerView);
postAdapter = new PostAdapter();
recyclerView.setLayoutManager(new LinearLayoutManager(this));
recyclerView.setAdapter(postAdapter);
osveziListuPostova();

// METODA (pozovi posle inserta u bazu):
private void osveziListuPostova() {
    List<Post> svi = postDao.getAll();
    postAdapter.setPosts(svi);
}
