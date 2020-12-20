package com.example.recumultimedia_android

import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.Spanned
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible

class SecondActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        val editText = findViewById<EditText>(R.id.editext)
        val textView = findViewById<TextView>(R.id.textview2)
        val button = findViewById<Button>(R.id.button4)
        editText.inputFilterNumberRange(0..9)

        editText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
            override fun afterTextChanged(s: Editable?) {
                button.isEnabled=editText.text.isNotEmpty()
                textView.isVisible=button.isClickable
            }
        }
        )
    }
}

fun EditText.inputFilterNumberRange(range: IntRange){
    filterMin(range.first)
    filters = arrayOf<InputFilter>(InputFilterMax(range.last))
}

class InputFilterMax(private var max: Int) : InputFilter {
    override fun filter(
            p0: CharSequence, p1: Int, p2: Int, p3: Spanned?, p4: Int, p5: Int
    ): CharSequence? {
        try {
            val replacement = p0.subSequence(p1, p2).toString()
            val newVal = p3.toString().substring(0, p4) + replacement + p3.toString()
                    .substring(p5, p3.toString().length)
            val input = newVal.toInt()
            if (input <= max) return null
        } catch (e: NumberFormatException) {
        }
        return ""
    }
}
fun EditText.filterMin(min: Int){
    onFocusChangeListener = View.OnFocusChangeListener { view, b ->
        if (!b) {
            setTextMin(min)
        }
    }
    setOnEditorActionListener { v, actionId, event ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            setTextMin(min)
        }
        false
    }
}
fun EditText.setTextMin(min: Int){
    try {
        val value = text.toString().toInt()
        if (value < min){
            setText("$min")
        }
    }catch (e: Exception){
        setText("$min")
    }
}