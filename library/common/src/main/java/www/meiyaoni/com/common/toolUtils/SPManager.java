package www.meiyaoni.com.common.toolUtils;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import www.meiyaoni.com.common.baseapp.BaseApplication;


/**
 * @author qndroid
 * @description 配置文件工具类
 * @date 2015年1月15日
 */
public class SPManager {
    /*
    保存字段名称
     */
    private static final String USER_TOKEN = "access-token";
    private static final String USER_PHONE = "user_phone";
    private static final String USER_PHOTO = "user_photo";
    private static final String USER_NAME = "user_name";
    private static final String IS_REMMBER_PWD = "is_remmber_pwd";
    private static final String PWD = "password";


    private static SharedPreferences sp = null;
    private static SPManager spManager = null;
    private static Editor editor = null;
    /*
     Preference文件名
     */
    private static final String SHARE_PREFREENCE_NAME = "meiyaoniTech.pre";

    private SPManager() {
        sp = BaseApplication.getAppContext().getSharedPreferences(SHARE_PREFREENCE_NAME, Context.MODE_PRIVATE);
        editor = sp.edit();
    }
    public static SPManager getInstance() {
        if (spManager == null || sp == null || editor == null) {
            spManager = new SPManager();
        }
        return spManager;
    }
    public void putInt(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }

    public int getInt(String key, int defaultValue) {
        return sp.getInt(key, defaultValue);
    }

    public void putLong(String key, Long value) {
        editor.putLong(key, value);
        editor.commit();
    }

    public long getLong(String key, int defaultValue) {
        return sp.getLong(key, defaultValue);
    }

    public void putString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key, String defaultValue) {
        return sp.getString(key, defaultValue);
    }

    public void putFloat(String key, float value) {
        editor.putFloat(key, value);
        editor.commit();
    }

    public boolean isKeyExist(String key) {
        return sp.contains(key);
    }

    public float getFloat(String key, float defaultValue) {
        return sp.getFloat(key, defaultValue);
    }

    public void putBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public boolean getBoolean(String key, boolean defaultValue) {
        return sp.getBoolean(key, defaultValue);
    }

    public void remove(String key) {
        editor.remove(key);
        editor.commit();
    }
    /*
     清除文件所有数据
     */
    public void clearAllCache(){
        editor.clear();
        editor.commit();
    }
    /*
    用户操作
     */
    public void setUserToken(String userToken){
        putString(USER_TOKEN,userToken);
    }
    public String getUserToken(){
        return getString(USER_TOKEN,"");
    }
    public void setUserPhone(String userPhone){
        putString(USER_PHONE,userPhone);
    }
    public String getUserPhone(){
        return getString(USER_PHONE,"");
    }
    public void setUserPhoto(String userPhoto){
        putString(USER_PHOTO,userPhoto);
    }
    public String getUserPhoto(){
        return getString(USER_PHOTO,"");
    }
    public boolean isLogin(){
        return getString(USER_TOKEN,null)==null? false:true;
    }

    public String getUserName() {
        return getString(USER_NAME,"");
    }
    public void setUserName(String userName){
        putString(USER_NAME,userName);
    }

    /*
        记住密码功能
         */
    public boolean isRemmberPwd(){
        return getRemmberPwd();
    }
    public void setRemmberPwd(boolean flag){
        putBoolean(IS_REMMBER_PWD,flag);
    }
    public boolean getRemmberPwd(){
        return getBoolean(IS_REMMBER_PWD,false);
    }
    /**
     * 保存密码
     */
    public void setUserPwd(String pwd){
        putString(PWD,pwd);
    }
    public String getUserPwd(){
        return getString(PWD,"");
    }


    /*
    退出登录
     */
    public void loginOut(){
        putString(USER_TOKEN,"");
    }
}
