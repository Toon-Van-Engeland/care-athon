package be.pxl.g_karate.api;

import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.concurrent.atomic.AtomicReference;

import be.pxl.g_karate.api.models.Exercise;

public class ExerciseRepo {

    private FirebaseFirestore _db;

    public ExerciseRepo(ApiProxy proxy) {
        _db = proxy.getDb();
    }

    public void addExercise(Exercise exercise) {
        _db.collection("exercises")
                .add(exercise)
                .addOnSuccessListener(docRef -> System.out.println(docRef.getId()))
                .addOnFailureListener(ex -> System.out.println(ex.getMessage()));
    }

    public Exercise getExercise(int id) {
        AtomicReference<Exercise> result = new AtomicReference();
        _db.collection("exercises")
                .get().addOnCompleteListener(snapshot -> {
                    if (snapshot.isSuccessful()) {
                        for (QueryDocumentSnapshot doc : snapshot.getResult()) {
                            if (Integer.getInteger(doc.getId()) == id) {
                                result.set((Exercise)doc.getData());
                            }
                        }
                    } else  {
                        result.set(null);
                        System.out.println("No exercise found with current id!");
                    }
        });
        return result.get();
    }
}
