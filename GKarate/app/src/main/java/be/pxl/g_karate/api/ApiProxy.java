package be.pxl.g_karate.api;

import com.google.firebase.firestore.FirebaseFirestore;

public class ApiProxy {

    private FirebaseFirestore db;

    public ApiProxy() {
        db = FirebaseFirestore.getInstance();
    }

    public FirebaseFirestore getDb() {
        return this.db;
    }
}
