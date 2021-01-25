package com.app.fixmykix.utils;

public interface Constants {
    int PERMISSION_REQUEST_LOCATION = 1;
    int PERMISSION_REQUEST_PHONE_STATE = 2;
    /*int PERMISSION_REQUEST_LOCATION = 1;
    int PERMISSION_REQUEST_LOCATION = 1;*/

    String FRAGMENT_HOME = "fragment_service_list";

    String KEY_PHONE_NUMBER = "phone_number";
    String KEY_SERVICE_IDS = "service_ids";
    String KEY_ARTIST = "key_artist";
    String KEY_ARTIST_ID = "artist_id";
    String KET_SERVICE_REQUEST = "service_request";
    String KEY_SERVICE = "service";

    int VALID_OTP_LENGTH = 4;

    int PERMISSION_CODE_ACCESS_CAMERA = 1;
    int PERMISSION_CODE_READ_EXTERNAL_STORAGE = 2;
    int REQUEST_CODE_CAMERA = 3;
    int REQUEST_CODE_FILE = 4;
    int REQUEST_CODE_BOOK_SERVICE = 5;
    int REQUEST_CODE_ACCEPT_SERVICE = 6;
    int REQUEST_CODE_ADD_SERVICE = 7;

    String BUNDLE_KEY_TEXT_VIEW_ID = "bundle_key_text_view_id";

    int ROLE_CUSTOMER = 0;
    int ROLE_ARTIST = 1;


    //KEYS for API
    String KEY_X_USER_TOKEN = "X-User-Token";

    // API response code
    public static final int SUCCESS_CODE = 200;
    public static final int SUCCESS_CODE_SECOND = 201;
    public static final int ERROR_CODE_INVALID = 401; // User token has expired
    public static final int INVALID_LOGIN_CREDENTIALS = 422;

    //Activity Request codes
    int REQUEST_CODE_SELECT_SERVICE = 101;

    //Bundle keys
    String SERVICE_DATA = "service_data";

    String[] DRAWER_ITEM_TEXT = {"Home", "My Request", "About us", "Contact us"};

    String SERVICE_CANCELLED = "cancelled";
    String SERVICE_REJECTED = "rejected";
    String SERVICE_PENDING = "pending";
    String SERVICE_ACCEPTED = "accepted";
    String SERVICE_APPROVED = "approved";

}
