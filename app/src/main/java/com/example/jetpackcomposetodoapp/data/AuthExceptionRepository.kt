package  com.example.jetpackcomposetodoapp.data

import androidx.lifecycle.MutableLiveData

interface AuthExceptionRepository {
    suspend fun firebaseAuthException(
        email: String,
        password: String,
        isSuccessful: MutableLiveData<Boolean>,
        toastMessage: MutableLiveData<CharSequence>
    )
}