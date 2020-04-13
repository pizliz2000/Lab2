package android.bignerdranch.com.androidlab21;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {
    /*Стартовый экран. Показывает иконку до тех пор, пока загружается приложение.
    Связанные файлы/код:
    1)background_splash_screen
    2)styles.xml/style SplashScreen
    3)В android manifest splash screen добавлен как стартовое активити с соответствующей темой*/
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        startActivity(new Intent(this, MainActivity.class));
    }
}
