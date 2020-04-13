package android.bignerdranch.com.androidlab21;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class MainActivity extends SingleFragmentActivity {
/*Второе активити. .*/

    @Override
    protected Fragment createFragment() {
        return MainFragment.newInstance();
    }
}
