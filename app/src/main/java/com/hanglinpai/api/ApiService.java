package com.hanglinpai.api;

import com.hanglinpai.ui.bean.AddChatBean;
import com.hanglinpai.ui.bean.BaseBean;
import com.hanglinpai.ui.bean.ChatListBean;
import com.hanglinpai.ui.bean.ConfigBean;
import com.hanglinpai.ui.bean.ExpertBean;
import com.hanglinpai.ui.bean.HistoryPassBean;
import com.hanglinpai.ui.bean.LoginBean;
import com.hanglinpai.ui.bean.MessageListBean;
import com.hanglinpai.ui.bean.OrderDetailBean;
import com.hanglinpai.ui.bean.OrderDetailPut;
import com.hanglinpai.ui.bean.OrderListBean;
import com.hanglinpai.ui.bean.SpecialListBean;
import com.hanglinpai.ui.bean.UploadBean;

import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.PartMap;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

import static com.hanglinpai.api.ApiConstants.ADDORDERS;
import static com.hanglinpai.api.ApiConstants.CANCERORDER;
import static com.hanglinpai.api.ApiConstants.CHAT;
import static com.hanglinpai.api.ApiConstants.CHATLOG;
import static com.hanglinpai.api.ApiConstants.CONFIG;
import static com.hanglinpai.api.ApiConstants.EDIT;
import static com.hanglinpai.api.ApiConstants.EDITORDERS;
import static com.hanglinpai.api.ApiConstants.EXPECTATION;
import static com.hanglinpai.api.ApiConstants.EXPERT;
import static com.hanglinpai.api.ApiConstants.EXPERTHISTORYLIST;
import static com.hanglinpai.api.ApiConstants.EXPERTLIST;
import static com.hanglinpai.api.ApiConstants.EXPERTSDETAIL;
import static com.hanglinpai.api.ApiConstants.EXPERTVIEW;
import static com.hanglinpai.api.ApiConstants.FEEDBACKS;
import static com.hanglinpai.api.ApiConstants.FORGEPASSWORD;
import static com.hanglinpai.api.ApiConstants.GETCODE;
import static com.hanglinpai.api.ApiConstants.GETSYSTEM;
import static com.hanglinpai.api.ApiConstants.GETUSER;
import static com.hanglinpai.api.ApiConstants.INCONFORMITY;
import static com.hanglinpai.api.ApiConstants.LOGIN;
import static com.hanglinpai.api.ApiConstants.ORDERS;
import static com.hanglinpai.api.ApiConstants.ORDERSDETAIL;
import static com.hanglinpai.api.ApiConstants.REG;
import static com.hanglinpai.api.ApiConstants.RESETPASSWORD;
import static com.hanglinpai.api.ApiConstants.UPLOAD;
import static com.hanglinpai.api.ApiConstants.VERIFIED;

/**
 * @author chi
 * @function Created on 2017/7/26.
 */

public interface ApiService {


    /*
  配置静态参数
    */
    @GET(CONFIG)
    Observable<ConfigBean> config(@Query("version") String version, @Query("device_type") String device_type);

    /*
    注册
    */
    @FormUrlEncoded
    @POST(REG)
    Observable<LoginBean> reg(@Field("device_type") String device_type, @Field("device_token") String device_token, @Field("phone") String phone, @Field("code") String code, @Field("password") String password);

    /*
     审核资料
    */
    @FormUrlEncoded
    @POST(VERIFIED)
    Observable<BaseBean> verified(@Header("access-token") String access_token, @Field("avatar") String avatar, @Field("name") String name, @Field("sex") int sex, @Field("phone") String phone, @Field("company_name") String company_name, @Field("position_name") String position_name, @Field("email") String email, @Field("business_card") String business_card);

    /*
    忘记密码
    */
    @FormUrlEncoded
    @POST(FORGEPASSWORD)
    Observable<BaseBean> forgetPassword(@Field("phone") String phone, @Field("code") String code, @Field("password") String password);

    /*
修改密码
*/
    @FormUrlEncoded
    @POST(RESETPASSWORD)
    Observable<BaseBean> changePassword(@Header("access-token") String access_token, @Field("old_password") String old_password, @Field("new_password") String new_password, @Field("confirm_password") String confirm_password);

    /*
   登入
    */
    @FormUrlEncoded
    @POST(LOGIN)
    Observable<LoginBean> login(@Field("device_type") String device_type, @Field("device_token") String device_token, @Field("username") String username, @Field("password") String password);

    /*
    获取验证码
     */
    @FormUrlEncoded
    @POST(GETCODE)
    Observable<BaseBean> getCode(@Field("phone") String phone);

    /*
    获取用户信息
    */
    @GET(GETUSER)
    Observable<LoginBean> getUser(@Header("access-token") String access_token,@Query("device_token") String device_token);

    /*
    反馈
     */
    @FormUrlEncoded
    @POST(FEEDBACKS)
    Observable<BaseBean> feedBacks(@Header("access-token") String access_token, @Field("content") String content, @Field("images[]") List<String> images);


    /*
  获取系统消息
 */
    @GET(GETSYSTEM)
    Observable<MessageListBean> getSystemMessage(@Header("access-token") String access_token, @Query("page") String page, @Query("per-page") String pageSize, @Query("expand") String expand);

    /*
     编辑资料
    */
    @FormUrlEncoded
    @POST(EDIT)
    Observable<BaseBean> edit(@Header("access-token") String access_token, @Field("avatar") String avatar);

    @FormUrlEncoded
    @POST(EDIT)
    Observable<BaseBean> edit(@Header("access-token") String access_token, @Field("status") int status);

    /*
   上传文件
    */
    @Multipart
    @POST(UPLOAD)
    Observable<UploadBean> upload(@PartMap() Map<String, RequestBody> maps);


    /*
 新增订单
*/
    @FormUrlEncoded
    @POST(ADDORDERS)
    Observable<BaseBean> addOrder(@Header("access-token") String access_token, @Field("industry_type") String industry_type, @Field("service_type[]") List<String> service_type, @Field("service_dates[]") List<String> service_dates, @Field("service_time") String service_time, @Field("site") String site, @Field("desc") String desc);

    /*
  编辑订单详情
 */
    @PUT(EDITORDERS)
    Observable<BaseBean> editOrders(@Header("access-token") String access_token, @Path("id") String id, @Body OrderDetailPut object);

    /*
  获取订单
 */
    @GET(ORDERS)
    Observable<OrderListBean> getOrderList(@Header("access-token") String access_token, @Query("expand") String expand, @Query("page") String page, @Query("per-page") String per_page, @Query("type") String type);

    /*
  获取订单详情
 */
    @GET(ORDERSDETAIL)
    Observable<OrderDetailBean> getOrderDetail(@Header("access-token") String access_token, @Path("id") String id, @Query("expand") String expand);


    /*
  取消订单
 */
    @FormUrlEncoded
    @POST(CANCERORDER)
    Observable<BaseBean> cancerOrder(@Header("access-token") String access_token, @Field("order_id") String order_id, @Field("type") String type, @Field("cancel_reason") String cancel_reason, @Field("cancel_reason_desc") String cancel_reason_desc);

    /*
  确认专家更换专家
 */
    @FormUrlEncoded
    @POST(EXPERT)
    Observable<BaseBean> expert(@Header("access-token") String access_token, @Field("order_id") String order_id, @Field("expert_id") String expert_id, @Field("type") String type, @Field("view_type") String view_type, @Field("replace_reason") String replace_reason);

    /*
  不符合专家
 */
    @FormUrlEncoded
    @POST(INCONFORMITY)
    Observable<BaseBean> inconformity(@Header("access-token") String access_token, @Field("order_id") String order_id, @Field("expert_id") String expert_id, @Field("unsuitable_reason") String unsuitable_reason);

    /*
确认放弃约访
*/
    @FormUrlEncoded
    @POST(EXPERTVIEW)
    Observable<BaseBean> expertView(@Header("access-token") String access_token, @Field("order_id") String order_id, @Field("type") String type, @Field("view_time[]") List<String> view_time, @Field("giveup_reason") String giveup_reason);


    /*
评价
*/
    @FormUrlEncoded
    @POST(EXPECTATION)
    Observable<BaseBean> evaluate(@Header("access-token") String access_token, @Field("order_id") String order_id, @Field("review") String review, @Field("expectation") String expectation);


    /*
订单沟通
*/
    @FormUrlEncoded
    @POST(CHAT)
    Observable<ChatListBean> chat(@Header("access-token") String access_token, @Field("order_id") String order_id, @Field("last_chat_id") String chat_id, @Field("content") String content);

    /*
订单沟通
*/
    @GET(CHATLOG)
    Observable<ChatListBean> chatList(@Header("access-token") String access_token, @Query("order_id") String order_id, @Query("type") String type, @Query("chat_id") String chat_id);


    /*
专家详情
*/
    @GET(EXPERTSDETAIL)
    Observable<ExpertBean> expertsDetail(@Header("access-token") String access_token, @Path("id") String id, @Query("expand") String expand);

    /*
专家列表
*/
    @GET(EXPERTLIST)
    Observable<SpecialListBean> expertsList(@Header("access-token") String access_token, @Query("order_id") String order_id);
    /*
专家历史列表
*/
    @GET(EXPERTHISTORYLIST)
    Observable<HistoryPassBean> expertHistorysList(@Header("access-token") String access_token, @Query("order_id") String order_id);

}
