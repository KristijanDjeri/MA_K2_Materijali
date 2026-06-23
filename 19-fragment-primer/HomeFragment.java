package com.example.kolokvijum2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.kolokvijum2.db.AppDatabase;
import com.example.kolokvijum2.db.PostDao;

public class HomeFragment extends Fragment {

    private TextView textView;
    private Switch switchPosts;
    private Button button;
    private PostDao postDao;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        textView = view.findViewById(R.id.textView);
        switchPosts = view.findViewById(R.id.switchPosts);
        button = view.findViewById(R.id.button);

        postDao = AppDatabase.getInstance(requireContext()).postDao();

        button.setOnClickListener(v -> {
            int n = postDao.count();
            Toast.makeText(requireContext(),
                    "U bazi ima " + n + " postova", Toast.LENGTH_SHORT).show();
            textView.setText("Broj postova: " + n);
        });

        switchPosts.setOnCheckedChangeListener((btn, isChecked) -> {
            textView.setText(isChecked ? "Switch: ON" : "Switch: OFF");
        });
    }
}
