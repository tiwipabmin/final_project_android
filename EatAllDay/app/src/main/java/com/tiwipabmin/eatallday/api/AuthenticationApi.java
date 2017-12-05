package com.tiwipabmin.eatallday.api;

import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationApi {

    private static AuthenticationApi authenticationApi;
    private FirebaseAuth firebaseAuth;

    private AuthenticationApi(){

        firebaseAuth = FirebaseAuth.getInstance();
    }

    public static AuthenticationApi getAuthenticationApi() {
        if(authenticationApi == null){
            authenticationApi = new AuthenticationApi();
        }
        return authenticationApi;
    }

    public FirebaseAuth getFirebaseAuth() {
        return firebaseAuth;
    }
}
