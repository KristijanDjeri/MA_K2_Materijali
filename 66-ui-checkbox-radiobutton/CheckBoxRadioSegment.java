// Deo za MainActivity (folder 55-checkbox-radiobutton/)

import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;

private CheckBox checkBoxNotifikacije;
private RadioGroup radioGroupSort;

// onCreate:
checkBoxNotifikacije = findViewById(R.id.checkBoxNotifikacije);
radioGroupSort = findViewById(R.id.radioGroupSort);

checkBoxNotifikacije.setOnCheckedChangeListener((buttonView, isChecked) -> {
    textView.setText(isChecked ? "Notifikacije: uključene" : "Notifikacije: isključene");
});

radioGroupSort.setOnCheckedChangeListener((group, checkedId) -> {
    if (checkedId == R.id.radioPoId) {
        textView.setText("Sortiranje: po ID");
    } else if (checkedId == R.id.radioPoTitle) {
        textView.setText("Sortiranje: po naslovu");
    }
});
