# RecyclerView – lista postova iz baze

**Dodatni segment** (nije u PDF-u, ali česta varijanta).  
**Slično:** Room baza + prikaz podataka (zadaci 5–7).

**Cilj:** Prikaži sve postove iz baze u listi. Klik na stavku → Toast sa naslovom.

---

## Preduslovi

- `05-retrofit-room/` urađen
- Gradle: `implementation 'androidx.20-recyclerview:20-recyclerview:1.3.2'`

---

## Fajlovi koje praviš

| Fajl | Putanja |
|------|---------|
| `item_post.xml` | `res/layout/item_post.xml` |
| `PostAdapter.java` | `.../adapter/PostAdapter.java` |
| Izmene u `activity_main.xml` | dodaj RecyclerView |
| Izmene u `MainActivity.java` | adapter + osvežavanje |

---

## 1. `res/layout/item_post.xml` (ceo fajl)

```xml
<?xml version="1.0" encoding="utf-8"?>
<TextView xmlns:android="http://schemas.android.com/apk/res/android"
    android:id="@+id/tvTitle"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"
    android:padding="12dp"
    android:textSize="16sp" />
```

---

## 2. U `activity_main.xml` dodaj (npr. ispod Button)

```xml
<androidx.20-recyclerview.widget.RecyclerView
    android:id="@+id/recyclerView"
    android:layout_width="match_parent"
    android:layout_height="200dp" />
```

---

## 3. `PostAdapter.java` (ceo fajl)

```java
package com.example.kolokvijum2.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.20-recyclerview.widget.RecyclerView;

import com.example.kolokvijum2.R;
import com.example.kolokvijum2.model.Post;

import java.util.ArrayList;
import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {

    private List<Post> posts = new ArrayList<>();

    public void setPosts(List<Post> posts) {
        this.posts = posts != null ? posts : new ArrayList<>();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = posts.get(position);
        holder.tvTitle.setText(post.getTitle());
        holder.itemView.setOnClickListener(v ->
                Toast.makeText(v.getContext(), post.getTitle(), Toast.LENGTH_SHORT).show()
        );
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle;

        PostViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
        }
    }
}
```

---

## 4. Deo u `MainActivity.java`

### Importi

```java
import androidx.20-recyclerview.widget.LinearLayoutManager;
import androidx.20-recyclerview.widget.RecyclerView;
import com.example.kolokvijum2.adapter.PostAdapter;
import java.util.List;
```

### Polja

```java
private RecyclerView recyclerView;
private PostAdapter postAdapter;
```

### U `onCreate`

```java
recyclerView = findViewById(R.id.recyclerView);
postAdapter = new PostAdapter();
recyclerView.setLayoutManager(new LinearLayoutManager(this));
recyclerView.setAdapter(postAdapter);
osveziListuPostova();
```

### Metoda za osvežavanje

```java
private void osveziListuPostova() {
    List<Post> svi = postDao.getAll();
    postAdapter.setPosts(svi);
}
```

### Pozovi `osveziListuPostova()` posle:

- `postDao.insertAll(...)` u Retrofit callback-u
- `postDao.delete(...)` u `obrisiPrviPost()`

---

## Alternativa

- `GridLayoutManager` umesto `LinearLayoutManager` – mreža umesto liste
- ViewHolder sa više TextView polja (title + body)

---

## Checklist

- [ ] `item_post.xml` kreiran
- [ ] RecyclerView u layoutu
- [ ] PostAdapter kreiran
- [ ] Lista se osvežava posle insert/delete
