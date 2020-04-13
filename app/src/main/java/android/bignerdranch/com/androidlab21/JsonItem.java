package android.bignerdranch.com.androidlab21;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class JsonItem implements Serializable {
    private String mGraphics;
    private String mName;
    private String mHelpText;



    public String getGraphics() {
        return mGraphics;
    }

    @NonNull
    @Override
    public String toString() {
        return mName;
    }

    public void setGraphics(String graphics) {
        mGraphics = graphics;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getHelpText() {
        return mHelpText;
    }

    public void setHelpText(String helpText) {
        mHelpText = helpText;
    }

}
