package be.pxl.g_karate.api;

import android.util.Log;
import android.view.inputmethod.InputMethodSession;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import be.pxl.g_karate.api.models.Exercise;

public class ExerciseRepo {

    private FirebaseFirestore _db;
    private static List<Exercise> exercises = new ArrayList<>();

    public ExerciseRepo(ApiProxy proxy) {
        _db = proxy.getDb();
    }

    public void addExercise(Exercise exercise) {
        _db.collection("exercises")
                .add(exercise)
                .addOnSuccessListener(docRef -> System.out.println(docRef.getId()))
                .addOnFailureListener(ex -> System.out.println(ex.getMessage()));
    }

    public void getExercises() {
        Exercise result = new Exercise();
        CollectionReference collRef = _db.collection("exercises");
        collRef.get().addOnCompleteListener(querySnapshot -> {
            if (querySnapshot.isSuccessful()) {
                for (QueryDocumentSnapshot doc: querySnapshot.getResult()) {
                    Exercise exercise = doc.toObject(Exercise.class);
                    exercises.add(exercise);
                }
                System.out.println("LIST SIZE = " + exercises.size());
                /*
                List<Integer> handMovLeft = task.getResult().toObject(Exercise.class).getHandMovementsLeft();
                List<Integer> handMovRight = task.getResult().toObject(Exercise.class).getHandMovementsRight();
                for (int i = 0; i < handMovLeft.size(); i++) {
                    result.addHandMovementLeft(handMovLeft.get(i));
                    result.addHandMovementRight(handMovRight.get(i));
                }
                */
            }
        });
    }

    public static List<Exercise> getExercisesList() {
        return exercises;
    }

}
