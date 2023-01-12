package mapin.ai.businesscardreader;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.widget.TextView;

import com.googlecode.tesseract.android.TessBaseAPI;

import java.io.File;

import mapin.ai.businesscardreader.utility.Assets;

public class MainActivity extends AppCompatActivity {

    private TextView text;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainViewModel viewModel = new ViewModelProvider(this).get(MainViewModel.class);
        text = findViewById(R.id.text);
        Assets.extractAssets(this);

        if (!viewModel.isInitialized()) {
            String dataPath = Assets.getTessDataPath(this);
            String language = Assets.getLanguage();
            viewModel.initTesseract(dataPath, language, TessBaseAPI.OEM_LSTM_ONLY);
        }

        File imageFile = Assets.getImageFile(this);
        viewModel.recognizeImage(imageFile);

        viewModel.getResult().observe(this, result -> text.setText(result));
    }
}