package com.example.kolokvijum2;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.kolokvijum2.db.AppDatabase;
import com.example.kolokvijum2.db.PostDao;
import com.example.kolokvijum2.model.Post;

import java.util.List;

/**
 * Sopstveni ContentProvider – izlaže postove iz Room baze preko content:// URI-ja.
 * Folder: 84-content-provider/
 */
public class PostContentProvider extends ContentProvider {

    public static final String AUTHORITY = "com.example.kolokvijum2.provider.posts";
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITY + "/posts");

    private static final int POSTS = 1;
    private static final UriMatcher URI_MATCHER = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        URI_MATCHER.addURI(AUTHORITY, "posts", POSTS);
    }

    private PostDao postDao;

    @Override
    public boolean onCreate() {
        if (getContext() == null) {
            return false;
        }
        postDao = AppDatabase.getInstance(getContext()).postDao();
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection,
                        @Nullable String selection, @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        if (URI_MATCHER.match(uri) != POSTS) {
            throw new IllegalArgumentException("Nepoznat URI: " + uri);
        }

        List<Post> posts = postDao.getAll();
        MatrixCursor cursor = new MatrixCursor(new String[]{"_id", "title", "body"});
        for (Post post : posts) {
            cursor.addRow(new Object[]{post.getId(), post.getTitle(), post.getBody()});
        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        if (URI_MATCHER.match(uri) == POSTS) {
            return "vnd.android.cursor.dir/vnd.com.example.kolokvijum2.post";
        }
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        throw new UnsupportedOperationException("Samo čitanje");
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Samo čitanje");
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values,
                      @Nullable String selection, @Nullable String[] selectionArgs) {
        throw new UnsupportedOperationException("Samo čitanje");
    }
}
