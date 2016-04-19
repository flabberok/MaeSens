package by.maesens.android.account;

/**
 * Created by Никита on 23.03.2016.
 */
public class AppAccount {

    private static boolean isLogin = false;

    public static boolean isLogin() {
        return isLogin;
    }

    public static void setLogin(boolean login) {
        isLogin = login;
    }

    public static String getToken() {

        //return "Token 5c14721cf4d70264cd203ff7f15256bcb89c3c1b";

        //для тестового сервера
        //return "Token 4d5fc0dea80c0640c0d8608ddbf6f91a15c06b5e";

        // реальный сервер, Витя (профиль со статистикой)
        return "Token 87a80eaa14706cdad01684ba0fb3d80b08cb0bca";
    }

    //id
    public static int getId() {
        //для тестового сервера
        //return 85102;

        // реальный сервер, Витя (профиль со статистикой)
        return 52593;
    }
}
