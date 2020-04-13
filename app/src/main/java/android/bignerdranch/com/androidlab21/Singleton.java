package android.bignerdranch.com.androidlab21;

import java.util.List;

//Синглетный класс для передачи списка между активити
public class Singleton {
    private static Singleton sSingleton;
    private List<JsonItem> items;
    private Singleton()
    {
    }

    public void setItems(List<JsonItem> items) {
        this.items = items;
    }

    public static Singleton getInstance()
    {
        if(sSingleton == null)
        {
            sSingleton = new Singleton();
        }
        return sSingleton;
    }

    public JsonItem getItem(String name)
    {
        for(int i = 0; i < items.size(); i++) {
            if (items.get(i).getName().equals(name)) {
                return items.get(i);
            }
        }
        return null;
    }

    public List<JsonItem> getItems()
    {
        return items;
    }
}
