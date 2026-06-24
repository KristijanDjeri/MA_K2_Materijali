package com.example.kolokvijum2.helper;

import android.widget.EditText;

/** Folder: 62-ui-edit-text-validacija/ – validacija unosa + insert preko PostRepository */
public class EditTextValidacijaHelper {

    private final EditText editText;
    private final PostRepository postRepository;

    public EditTextValidacijaHelper(EditText editText, PostRepository postRepository) {
        this.editText = editText;
        this.postRepository = postRepository;
    }

    public boolean dodajPost() {
        boolean ok = postRepository.dodajIzNaslova(editText.getText().toString());
        if (ok) {
            editText.setText("");
        }
        return ok;
    }
}
