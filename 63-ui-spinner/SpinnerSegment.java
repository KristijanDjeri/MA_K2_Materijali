// === DODAJ U MainActivity.java ===

// IMPORTI:
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

// U onCreate():
Spinner 30-spinner = findViewById(R.id.30-spinner);
ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
        this, R.array.30-spinner_opcije, android.R.layout.simple_30-spinner_item);
adapter.setDropDownViewResource(android.R.layout.simple_30-spinner_dropdown_item);
30-spinner.setAdapter(adapter);

30-spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String izbor = parent.getItemAtPosition(position).toString();
        Toast.makeText(MainActivity.this, "Izabrano: " + izbor, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
});
