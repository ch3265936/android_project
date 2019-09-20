package www.meiyaoni.com.common.toolUtils;
import android.content.Context;
import android.widget.Toast;
/**
 * Created by Administrator on 2017/3/8.
 */

public class ToastUtils {

    public static Toast mToast;
    private static ToastUtils mInstance;
    private static Context mContext;
    private ToastUtils(Context context){
        this.mContext = context;
    }
    public static ToastUtils getInstance(Context context){
        if (mInstance==null){
            mInstance = new ToastUtils(context);
        }
        return mInstance;
    }


    /**
     * 短时间显示吐司
     * @param msg
     */
    public static void showShotToast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示吐司
     * @param msg
     */
    public static void showLongToast(String msg){
        Toast.makeText(mContext,msg,Toast.LENGTH_LONG).show();
    }
    /**
     * 短时间显示Toast
     *
     * @param strResId
     */
    public static void showShort(int strResId) {
        showShotToast(mContext.getResources().getString(strResId));
    }
    public static void showShort(String text) {
        showShotToast(text);
    }
    /**
     * 长时间显示Toast
     *
     * @param strResId
     */
    public static void showLong(int strResId) {
        showLongToast(mContext.getResources().getString(strResId));
    }
    public static void showLong(String text){
        showLongToast(text);
    }
    public static void showShortTime(String msg,int duration){
        Toast.makeText(mContext,msg,duration).show();
    }
    /**
     * 自定义显示Toast时间
     *
     * @param strResId
     * @param duration
     */
    public static void showShortTime(int strResId, int duration) {
        Toast.makeText(mContext,mContext.getResources().getString(strResId),duration).show();
    }
}
