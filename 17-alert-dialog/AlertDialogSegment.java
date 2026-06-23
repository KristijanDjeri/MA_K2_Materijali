// Deo za MainActivity – potvrda pre brisanja (folder 17-alert-dialog/)

import androidx.appcompat.app.AlertDialog;

private void potvrdiBrisanje() {
    new AlertDialog.Builder(this)
            .setTitle("Brisanje")
            .setMessage("Obrisati prvi post iz baze?")
            .setPositiveButton("Da", (dialog, which) -> obrisiPrviPost())
            .setNegativeButton("Ne", null)
            .show();
}

// button.setOnClickListener(v -> potvrdiBrisanje());
