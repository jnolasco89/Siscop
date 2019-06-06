/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sv.org.siscop.caritas.entidades;

public class LoggedUser {

    private static final ThreadLocal<String> userHolder = new ThreadLocal<>();

    public static void logIn(String user) {
        userHolder.set(user);
    }

    public static void logOut() {
        userHolder.remove();
    }

    public static String get() {
        return userHolder.get();
    }
}
