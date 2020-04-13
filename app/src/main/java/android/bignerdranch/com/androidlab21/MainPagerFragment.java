package android.bignerdranch.com.androidlab21;

import android.content.Intent;
import android.os.Bundle;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.squareup.picasso.Picasso;

public class MainPagerFragment extends Fragment {
    public static final String ARG_SERIALIZEABLE = "serializeableArg";
    public static final String IMAGES_URL = "https://raw.githubusercontent.com/wesleywerner/" +
            "ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/";

    private String helpText;
    private String graphics;
    private JsonItem mItem;

    public static MainPagerFragment newInstance(String name)
    {
        Bundle args = new Bundle();
        args.putSerializable(ARG_SERIALIZEABLE, Singleton.getInstance().getItem(name));
        MainPagerFragment fragment = new MainPagerFragment();
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Intent i = getActivity().getIntent();
        //mItem = (JsonItem)  i.getSerializableExtra("complexObject");
        mItem = (JsonItem) this.getArguments().getSerializable(ARG_SERIALIZEABLE);
        helpText = mItem.getHelpText();
        graphics = mItem.getGraphics();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main_pager, container, false);
        ImageView imageView = v.findViewById(R.id.view_pager_image_view);
        TextView textView = v.findViewById(R.id.view_pager_text_view);
        WindowManager wm = (WindowManager) getActivity().getSystemService(getActivity().WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        int width = display.getWidth();
        Picasso.with(getActivity()).load(IMAGES_URL + graphics).resize(width - 40,width - 40).error(R.drawable.logotry).into(imageView);
        textView.setText(helpText);
        return v;
    }
}
