package com.hanglinpai.api;

public class ApiConstants {


    public static final String BASE_HOST = "http://www.hanlintemp.com";//t13.geetemp.com
    public static final String IMG_URL = "http://www.hanlintemp.com";//www.hanlintemp.com
    public static final String THIS_DEVICE_SYSTEM = "Android";
    public static final String SUCCESS = "0";
    public static final String ERRO = "5112";

    public static final String ORDERS = "/api/v1/orders";//GET
    public static final String ORDERSDETAIL = "/api/v1/orders/{id}";//GET  /{id}
    public static final String ADDORDERS = "/api/v1/orders";//POST
    public static final String EDITORDERS = "/api/v1/orders/{id}";//PUT
    public static final String EXPERTSDETAIL = "/api/v1/experts/{id}";//GET
    public static final String EXPERTLIST = "/api/v1/order/expert-rmd";//GET
    public static final String EXPERTHISTORYLIST = "/api/v1/order/expert-history-rmd";//GET

    public static final String UPLOAD = "/api/v1/common/upload";//
    public static final String GETCODE = "/api/v1/user/send-msg";//
    public static final String LOGIN = "/api/v1/user/login";
    public static final String REG = "/api/v1/user/register";
    public static final String OUTLOGIN = "/api/v1/user/logout";
    public static final String FEEDBACKS = "/api/v1/feedbacks";
    public static final String FORGEPASSWORD = "/api/v1/user/forget-pwd";
    public static final String RESETPASSWORD = "/api/v1/user/reset-pwd";
    public static final String VERIFIED = "/api/v1/user/verified";
    public static final String EDIT = "/api/v1/user/edit";
    public static final String CONFIG = "/api/v1/config/config";

    public static final String CANCERORDER = "/api/v1/order/cancel";
    public static final String EXPERT = "/api/v1/order/expert-select";
    public static final String INCONFORMITY = "/api/v1/order/expert-unsuitable";
    public static final String EXPERTVIEW = "/api/v1/order/expert-view";
    public static final String EXPECTATION = "/api/v1/order/review";
    public static final String CHAT = "/api/v1/chats";
    public static final String CHATLOG = "/api/v1/chats";
    public static final String GETUSER = "/api/v1/user/info";
    public static final String GETSYSTEM = "/api/v1/messages";
    public static final String SHAREWEB = "/app/share/share.html?share=true";


    /**
     * 获取对应的host
     *
     * @param hostType host类型
     * @return host
     */
    public static String getHost(int hostType) {
        String host;
        switch (hostType) {
            case HostType.BASE_DATA_URL:
                host = BASE_HOST;
                break;
            case HostType.BASE_PIC_URL:
                host = IMG_URL;
                break;
            default:
                host = "";
                break;
        }
        return host;
    }
}
