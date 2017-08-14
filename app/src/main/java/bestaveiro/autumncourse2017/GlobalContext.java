package bestaveiro.autumncourse2017;

import android.app.Application;
import android.content.Context;

/**
 * Created by Ricardo on 15/07/2016.
 */
public class GlobalContext extends Application
{
    private static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext(){
        return mContext;
    }
}
