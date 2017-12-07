package com.tiwipabmin.eatallday.api;

import com.google.firebase.auth.FirebaseAuth;

public class AuthenticationApi {

    private static AuthenticationApi mAuthenticationApi;
    private FirebaseAuth mFirebaseAuthentication;

    private AuthenticationApi(){

        mFirebaseAuthentication = FirebaseAuth.getInstance();
    }

    public static AuthenticationApi getAuthenticationApi() {
        if(mAuthenticationApi == null){
            mAuthenticationApi = new AuthenticationApi();
        }
        return mAuthenticationApi;
    }

    public FirebaseAuth getFirebaseAuthentication() {
        return mFirebaseAuthentication;
    }
}
