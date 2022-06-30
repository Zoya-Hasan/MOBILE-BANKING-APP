package com.example.mobilebanking.firebase;

import com.example.mobilebanking.util.Suggestion;

import java.util.List;

public interface FirebaseNotifier {
    void suggestionsChanges(List<Suggestion> suggestions);
}
