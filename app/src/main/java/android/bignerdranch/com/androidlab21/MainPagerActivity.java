package android.bignerdranch.com.androidlab21;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.List;

public class MainPagerActivity extends FragmentActivity {

    private ViewPager mViewPager;
    private List<JsonItem> mItems;
    private int startingPosition;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_pager);

        mViewPager = (ViewPager) findViewById(R.id.activity_item_pager_view_pager);
        //startingPosition = this.getIntent().getIntExtra("position", 0);
        name = this.getIntent().getStringExtra("name");

        mItems = Singleton.getInstance().getItems(); //Получение списка из синглетного класса

        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                JsonItem item = mItems.get(position);
                //getIntent().putExtra("complexObject", item);
                //return new MainPagerFragment();
               return MainPagerFragment.newInstance(item.getName());
            }

            @Override
            public int getCount() {
                return mItems.size();
            }
        });
        for(int i = 0; i < mItems.size(); i++)
        {
            if(mItems.get(i).getName().equals(name))
            {
                mViewPager.setCurrentItem(i - 1);
                break;
            }
        }
    }
}
