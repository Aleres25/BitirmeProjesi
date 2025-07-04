package com.example.bitirmeprojesi.repository

import com.example.bitirmeprojesi.entitiy.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import javax.inject.Inject

class AuthRepository @Inject constructor(private val auth: FirebaseAuth, private val database: FirebaseDatabase) {
    fun registerUser(email: String, password: String, name: String, onResult: (Boolean, String?) -> Unit) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val uid = auth.currentUser?.uid ?: return@addOnCompleteListener
                    val user = User(uid = uid, name = name, email = email)

                    database.getReference("Users").child(uid).setValue(user).addOnSuccessListener {
                            onResult(true, null)
                        }.addOnFailureListener {
                            onResult(false, it.localizedMessage)
                        }
                } else {
                    onResult(false, task.exception?.localizedMessage)
                }
            }
    }
}