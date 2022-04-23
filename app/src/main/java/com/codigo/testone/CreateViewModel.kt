package com.codigo.testone

import android.app.DatePickerDialog
import android.util.Log
import android.util.Patterns
import android.view.View
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.RecursiveTask

class CreateViewModel : ViewModel() {

    private val myCalendar = Calendar.getInstance()
    private val emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+"

    private var _success = MutableStateFlow(false)
    val success = _success.asStateFlow()

    val firstName = MutableStateFlow("")
    val lastName = MutableStateFlow("")
    val email = MutableStateFlow("")
    val dob = MutableStateFlow("")
    val gender = MutableStateFlow(false)
    val nationality = MutableStateFlow("")
    val residence = MutableStateFlow("")
    val countryCode = MutableStateFlow("")
    val phoneNumber = MutableStateFlow("")

    val firstNameError = MutableStateFlow<String?>(null)
    val lastNameError = MutableStateFlow<String?>(null)
    val emailError = MutableStateFlow<String?>(null)
    val dobError = MutableStateFlow<String?>(null)
    val nationalityError = MutableStateFlow<String?>(null)
    val residenceError = MutableStateFlow<String?>(null)
    val countryCodeError = MutableStateFlow<String?>(null)
    val phoneNumberError = MutableStateFlow<String?>(null)

    fun createAccount() {
        resetError()
        if (!validateData()) return
        _success.value = true
    }

    private fun validateData() : Boolean {
        return when {
            firstName.value.isEmpty() -> {
                firstNameError.value = "Empty first name"
                false
            }
            firstName.value.length < 3 -> {
                firstNameError.value = "Invalid first name"
                false
            }
            lastName.value.isEmpty() -> {
                lastNameError.value = "Empty last name"
                false
            }
            firstName.value.length < 3 -> {
                lastNameError.value = "Invalid last name"
                false
            }
            email.value.isEmpty() -> {
                emailError.value = "Empty email"
                false
            }
            !Patterns.EMAIL_ADDRESS.matcher(email.value.trim()).matches() -> {
                emailError.value = "Invalid email"
                false
            }
            dob.value.isEmpty() -> {
                dobError.value = "Choose date of birth"
                false
            }
            nationality.value.isEmpty() -> {
                nationalityError.value = "Empty nationality"
                false
            }
            residence.value.isEmpty() -> {
                residenceError.value = "Empty country of residence"
                false
            }
            phoneNumber.value.isNotEmpty() -> {
                if (countryCode.value.isEmpty()) {
                    countryCodeError.value = "Empty country code"
                    return false
                }
                else if (phoneNumber.value.length < 8) {
                    phoneNumberError.value = "Invalid phone number"
                    return false
                }
                return true
            }
            else -> true
        }
    }

    private fun resetError() {
        firstNameError.value = null
        lastNameError.value = null
        emailError.value = null
        dobError.value = null
        nationalityError.value = null
        residenceError.value = null
        countryCodeError.value = null
        phoneNumberError.value = null
    }

    fun openDatePicker(view : View) {
        object : DatePickerDialog(
            view.context,
            date,
            myCalendar.get(Calendar.YEAR),
            myCalendar.get(Calendar.MONTH),
            myCalendar.get(Calendar.DAY_OF_MONTH)
        ) {}.show()
    }

    private val date = DatePickerDialog.OnDateSetListener { _, p1, p2, p3 ->
        myCalendar.set(Calendar.YEAR, p1)
        myCalendar.set(Calendar.MONTH, p2)
        myCalendar.set(Calendar.DAY_OF_MONTH, p3)
        updateDateEditText()
    }

    private fun updateDateEditText() {
        val format = "dd/MM/yyyy"
        val sdf = object : SimpleDateFormat(format, Locale.US) {}
        val dateString = sdf.format(myCalendar.time)
        dob.value = dateString
    }
}