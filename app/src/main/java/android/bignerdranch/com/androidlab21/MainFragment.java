package android.bignerdranch.com.androidlab21;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainFragment extends Fragment {
    public static final String TAG = "MainFragment";
    public static final String IMAGES_URL = "https://raw.githubusercontent.com/wesleywerner/" +
            "ancient-tech/02decf875616dd9692b31658d92e64a20d99f816/src/images/tech/";

    private RecyclerView mRecyclerView;
    private List<JsonItem> mItems = new ArrayList<>();

    /*
    AsyncTask запускает фоновый поток и выполняет в нем код, содержащийся в методе doInBackground(...)
    Во время загрузки файлов интерфейс будет полностью парализован, что может вызвать ошибку App not responding.
    Поэтому android запрещает сетевые взаимодействия в главном потоке.
     */
    private class FetchItemsTask extends AsyncTask<Void, Void, List<JsonItem>>
    {
        @Override
        protected List<JsonItem> doInBackground(Void... voids) {
            return new ItemFetcher().fetchItems();
        }

        @Override
        protected void onPostExecute(List<JsonItem> items) {
            mItems = items;
            Singleton.getInstance().setItems(mItems);
            setupAdapter();
        }
    }


    public static MainFragment newInstance()
    {
        return new MainFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRetainInstance(true); //Сохранение фрагмента при повороте экрана. Активность пересоздается, фрагмент сохраняется
        new FetchItemsTask().execute(); //Загрузка JSON файла
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_main, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.activity_two_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return v;
    }

    private void setupAdapter()
    {
        if(isAdded())
        {
            mRecyclerView.setAdapter(new ItemAdapter(mItems));
        }
    }

    private class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private int position;
        private JsonItem mJsonItem;
        private TextView mTitleTextView;
        private ImageView mImageView;

        public ItemHolder(View itemView)
        {
            super(itemView);
            itemView.setOnClickListener(this);
            mImageView = (ImageView) itemView.findViewById(R.id.image_recycler_view);
            mTitleTextView = (TextView) itemView.findViewById(R.id.name_recycler_view);
        }

        public void setPosition(int pos)
        {
            position = pos;
        }

        public void bindJsonItem(JsonItem item)
        {
            mJsonItem = item;
            mTitleTextView.setText(item.toString());
            /*
            Использовал стороннюю библиотеку Picasso, ибо она упрощает загрузку картинки до 1 команды
             */
            Picasso.with(getActivity())
                    .load(IMAGES_URL + item.getGraphics())
                    .error(R.drawable.logotry) // На случай когда картинки нет, берется заготовленная
                    .into(mImageView);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getActivity(), MainPagerActivity.class);
            intent.putExtra("name", mJsonItem.getName());
            startActivity(intent);
        }
    }

    private class ItemAdapter extends RecyclerView.Adapter<ItemHolder>
    {
        private List<JsonItem> mJsonItems;

        public ItemAdapter(List<JsonItem> jsonItems)
        {
            mJsonItems = jsonItems;
        }

        @NonNull
        @Override
        public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater inflater = LayoutInflater.from(getActivity());
            View view = inflater.inflate(R.layout.recycler_view_item, parent, false);
            return new ItemHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
            JsonItem jsonItem = mJsonItems.get(position);
            holder.setPosition(position);
            holder.bindJsonItem(jsonItem);
        }

        @Override
        public int getItemCount() {
            return mJsonItems.size();
        }
    }

}
