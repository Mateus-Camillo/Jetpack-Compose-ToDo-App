package  com.example.jetpackcomposetodoapp.data

import androidx.lifecycle.MutableLiveData

interface AuthExceptionRepository {
    suspend fun firebaseAuthException(
        authenticationResult: Result<String>,
        isSuccessful: MutableLiveData<Boolean>,
        toastMessage: MutableLiveData<CharSequence>
    )
}