package com.erickogi14gmail.odijotutorapp;

/**
 * Created by Eric on 10/8/2017.
 */

public class Configs {
//    public static final String REGISTER_URL = "http://www.erickogi.co.ke/Sumuni/Odijo/Odijo/Tutor/Register.php";
//    public static final String VERIFY_OTP_URL = "http://www.erickogi.co.ke/Sumuni/Odijo/Odijo/Tutor/Verify_otp.php";
//    public static final String LOGIN_URL = "http://www.erickogi.co.ke/Sumuni/Odijo/Odijo/Tutor/Login.php";
//
//    public static final String UPDATE_PROFILE_URL = "http://www.erickogi.co.ke/Sumuni/Odijo/Odijo/Tutor/Update_Profile.php";
 //   public static final String GET_SUBJECTS_URL = "http://erickogi.co.ke/Sumuni/Odijo/Odijo/Subjects/Get_Levels.php";

 //   public static final String GET_ALL_REQUESTS_URL = "http://www.erickogi.co.ke/Sumuni/Odijo/Odijo/Requests/ViewRequests.php";

 //   public static final String REPLY_REQUEST_URL = "http://www.erickogi.co.ke/Sumuni/Odijo/Odijo/Requests/ReplyRequest.php";

    public static final String GET_UPDATE_REQUESTS_URL = "http://www.erickogi.co.ke/Sumuni/Odijo/Odijo/Requests/ReplyRequest.php";

    // public static final String GET_SUBJECTS_URL = "http://www.erickogi.co.ke/Sumuni/Odijo/Odijo/Subjects/GetSubjects.php";


    public static final String SMS_ORIGIN = "AFRICASTKNG";
    // special character to prefix the otp. Make sure this character appears only once in the sms
    public static final String OTP_DELIMITER = ":";


    private static final String BASE_URL = "http://www.erickogi.co.ke/Sumuni/";

    public static final String REGISTER_URL = BASE_URL + "Login/Register.php";

    public static final String UPDATE_PROFILE_URL = BASE_URL + "Login/Update_Profile.php";

    public static final String VERIFY_OTP_URL = BASE_URL + "Login/Verify_otp.php";


    public static final String LOGIN_URL = BASE_URL + "Login/Login.php";


    public static final String GET_ALL_TUTORS_URL = BASE_URL + "Subjects/FindTutors.php";
    public static final String GET_SUBJECTS_URL = BASE_URL + "Subjects/Get_Levels.php";

    public static final String GET_ADD_REQUEST_URL = BASE_URL + "Scheduling/AddRequest.php";

    public static final String GET_ADD_FAVORITES = BASE_URL + "Favorites/AddFavorites.php";

    public static final String GET_MESSAGES = BASE_URL + "Messaging/viewMessages.php";

    public static final String GET_ALL_REQUESTS_URL = BASE_URL + "Scheduling/ViewRequests.php";

    public static final String REPLY_REQUEST_URL = BASE_URL + "Scheduling/ReplyRequest.php";

    public static final String SESSIONS = BASE_URL + "Scheduling/Sessions.php";





}
