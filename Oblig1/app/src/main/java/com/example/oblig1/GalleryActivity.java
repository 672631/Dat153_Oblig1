package com.example.oblig1;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.util.Arrays;

public class GalleryActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private ActivityResultLauncher<Intent> imagePickerLauncher;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_gallery);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;


        });

        AppData appData = AppData.getInstance();
        /*if(appData.getPictureTextPairs().isEmpty()) {
            appData.getPictureTextPairs().add(new PictureTextPair("Cat",
                    BitmapFactory.decodeResource(getResources(), R.drawable.cat)));
            appData.getPictureTextPairs().add(new PictureTextPair("Dog",
                    BitmapFactory.decodeResource(getResources(), R.drawable.dop)));
        }*/

        imagePickerLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), result -> {
            if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                Uri imageUri = result.getData().getData();
                try {
                    // Convert URI to Bitmap
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);

                    // Prompt the user for a description
                    promptForDescription(bitmap);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });


       findViewById(R.id.btn_add_picture).setOnClickListener(view -> {
            Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
           imagePickerLauncher.launch(intent);
        });

        RecyclerView recyclerView = findViewById(R.id.recycler_view_gallery);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new GalleryAdapter(AppData.getInstance().getPictureTextPairs()));


    }

    private void promptForDescription(Bitmap bitmap) {
        //Allert dialog with input fields
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Add Picture");

        //Layout for the dialog
        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final EditText inputCorrect = new EditText(this);
        inputCorrect.setHint("Enter correct description");
        layout.addView(inputCorrect);

        final EditText inputIncorrect1 = new EditText(this);
        inputIncorrect1.setHint("Enter incorrect description 1");
        layout.addView(inputIncorrect1);

        final EditText inputIncorrect2 = new EditText(this);
        inputIncorrect2.setHint("Enter incorrect description 2");
        layout.addView(inputIncorrect2);

        builder.setView(layout);

        // Add buttons
        builder.setPositiveButton("Add", (dialog, which) -> {
            String correctDesc = inputCorrect.getText().toString();
            String incorrectDesc1 = inputIncorrect1.getText().toString();
            String incorrectDesc2 = inputIncorrect2.getText().toString();

            // Add PictureTextPair and QuizQuestion
            AppData appData = AppData.getInstance();
            PictureTextPair pair = new PictureTextPair(correctDesc, bitmap);
            appData.getPictureTextPairs().add(pair);

            QuizQuestion question = new QuizQuestion(pair, Arrays.asList(incorrectDesc1, incorrectDesc2));
            appData.getQuizQuestions().add(question);

            // Refresh the RecyclerView
            RecyclerView recyclerView = findViewById(R.id.recycler_view_gallery);
            recyclerView.getAdapter().notifyDataSetChanged();
        });

        builder.setNegativeButton("Cancel", ((dialog, which) -> dialog.cancel()));

        builder.show();
    }

}
