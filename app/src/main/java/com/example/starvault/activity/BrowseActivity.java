package com.example.starvault.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ShareCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.starvault.R;
import com.example.starvault.adapter.CelebrityCardAdapter;
import com.example.starvault.logic.CelebrityRepository;
import com.example.starvault.model.Celebrity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

public class BrowseActivity extends AppCompatActivity {

    private RecyclerView rvCelebrities;
    private CelebrityCardAdapter cardAdapter;
    private boolean isGridMode = false;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rvCelebrities = findViewById(R.id.rvCelebrities);
        rvCelebrities.setLayoutManager(new LinearLayoutManager(this));

        cardAdapter = new CelebrityCardAdapter(this,
                CelebrityRepository.get().getAll(),
                this::showRatingDialog);
        rvCelebrities.setAdapter(cardAdapter);

        refreshSubtitle();

        ItemTouchHelper.SimpleCallback swipeCallback =
                new ItemTouchHelper.SimpleCallback(0,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView rv,
                                          @NonNull RecyclerView.ViewHolder vh,
                                          @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull RecyclerView.ViewHolder vh, int direction) {
                        int pos = vh.getAdapterPosition();
                        Celebrity removed = cardAdapter.removeAt(pos);
                        CelebrityRepository.get().remove(removed);
                        refreshSubtitle();

                        Snackbar.make(rvCelebrities,
                                        removed.getFullName() + " removed",
                                        Snackbar.LENGTH_LONG)
                                .setAction("Undo", v -> {
                                    CelebrityRepository.get().insert(removed);
                                    cardAdapter.insertAt(pos, removed);
                                    refreshSubtitle();
                                }).show();
                    }
                };
        new ItemTouchHelper(swipeCallback).attachToRecyclerView(rvCelebrities);

        FloatingActionButton fabAdd = findViewById(R.id.fabAdd);
        fabAdd.setOnClickListener(v -> showAddDialog());
    }

    private void refreshSubtitle() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle(
                    CelebrityRepository.get().getAll().size() + " celebrities");
        }
    }

    private void showRatingDialog(Celebrity celebrity, int position) {
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_rating, null);
        RatingBar dialogScore = dialogView.findViewById(R.id.dialogScore);
        dialogScore.setRating(celebrity.getScore());

        new AlertDialog.Builder(this)
                .setTitle("Rate " + celebrity.getFullName())
                .setView(dialogView)
                .setPositiveButton("Confirm", (dialog, which) -> {
                    celebrity.setScore(dialogScore.getRating());
                    CelebrityRepository.get().refresh(celebrity);
                    cardAdapter.notifyItemChanged(position);
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showAddDialog() {
        View dialogView = LayoutInflater.from(this)
                .inflate(R.layout.dialog_add, null);
        EditText inputName = dialogView.findViewById(R.id.inputName);
        EditText inputUrl = dialogView.findViewById(R.id.inputUrl);
        RatingBar inputScore = dialogView.findViewById(R.id.inputScore);

        new AlertDialog.Builder(this)
                .setTitle("Add Celebrity")
                .setView(dialogView)
                .setPositiveButton("Add", (dialog, which) -> {
                    String name = inputName.getText().toString().trim();
                    String url = inputUrl.getText().toString().trim();
                    float score = inputScore.getRating();

                    if (!name.isEmpty()) {
                        Celebrity newCeleb = new Celebrity(name,
                                url.isEmpty() ? "" : url, score);
                        CelebrityRepository.get().insert(newCeleb);
                        cardAdapter.refreshData();
                        refreshSubtitle();
                    } else {
                        Toast.makeText(this, "Name is required",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.browse_menu, menu);

        MenuItem findItem = menu.findItem(R.id.action_find);
        SearchView searchView = (SearchView) findItem.getActionView();
        searchView.setQueryHint(getString(R.string.search_hint));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                cardAdapter.getFilter().filter(newText);
                return true;
            }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_sort_name) {
            cardAdapter.sortByName();
        } else if (id == R.id.action_sort_score) {
            cardAdapter.sortByScore();
        } else if (id == R.id.action_toggle) {
            isGridMode = !isGridMode;
            if (isGridMode) {
                rvCelebrities.setLayoutManager(
                        new GridLayoutManager(this, 2));
                item.setTitle("List View");
            } else {
                rvCelebrities.setLayoutManager(
                        new LinearLayoutManager(this));
                item.setTitle("Grid View");
            }
        } else if (id == R.id.action_send) {
            new ShareCompat.IntentBuilder(this)
                    .setType("text/plain")
                    .setChooserTitle(getString(R.string.share_title))
                    .setText(getString(R.string.share_text))
                    .startChooser();
        } else if (id == R.id.action_about) {
            new AlertDialog.Builder(this)
                    .setTitle(getString(R.string.about_title))
                    .setMessage(getString(R.string.about_message))
                    .setPositiveButton("OK", null)
                    .setIcon(R.mipmap.ic_launcher)
                    .show();
        }

        return super.onOptionsItemSelected(item);
    }
}
