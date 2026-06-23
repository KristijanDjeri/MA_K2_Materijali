// Toolbar + Options Menu (folder 57-toolbar-options-menu/)

import android.view.Menu;
import android.view.MenuItem;
import androidx.appcompat.widget.Toolbar;

// onCreate:
Toolbar toolbar = findViewById(R.id.toolbar);
setSupportActionBar(toolbar);

@Override
public boolean onCreateOptionsMenu(Menu menu) {
    getMenuInflater().inflate(R.menu.main_menu, menu);
    return true;
}

@Override
public boolean onOptionsItemSelected(MenuItem item) {
    if (item.getItemId() == R.id.action_refresh) {
        ucitajPostoveSaApi();
        return true;
    } else if (item.getItemId() == R.id.action_delete) {
        potvrdiBrisanje();
        return true;
    } else if (item.getItemId() == R.id.action_settings) {
        Toast.makeText(this, "Podešavanja", Toast.LENGTH_SHORT).show();
        return true;
    }
    return super.onOptionsItemSelected(item);
}
