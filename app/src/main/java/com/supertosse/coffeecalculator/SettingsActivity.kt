package com.supertosse.coffeecalculator

import android.content.Context
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    private val myPreferences = "com.supertosse.coffeecalculator.preferences"
    private var sharedPref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)
        this.sharedPref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        loadRadioButtonsState()
    }
    private fun loadRadioButtonsState() {
        when (this.sharedPref!!.getString("waterUnit", "dl")) {
            "dl" -> dl_water_radio_button.performClick()
            "cl" -> cll_water_radio_button.performClick()
            "grams" -> grams_water_radio_button.performClick()
            "oz" -> fl_ozl_water_radio_button.performClick()
            "cup" -> cupl_water_radio_button.performClick()
        }
        when (this.sharedPref!!.getString("coffeeUnit", "grams")) {
            "grams" -> gramsl_coffee_radio_button.performClick()
            "tbsp" -> tbsp_coffee_radio_button.performClick()
            "oz" -> oz_coffee_radio_button.performClick()
        }
    }
    //onClick methods for water unit radio group
    fun onRadioButtonDlSelected(view: View) {
        val editor = sharedPref!!.edit()
        editor.putString("waterUnit", "dl")
        editor.apply()
    }
    fun onRadioButtonClSelected(view: View) {
        val editor = sharedPref!!.edit()
        editor.putString("waterUnit", "cl")
        editor.apply()
    }
    fun onRadioButtonGramsWaterSelected(view: View) {
        val editor = sharedPref!!.edit()
        editor.putString("waterUnit", "grams")
        editor.apply()
    }
    fun onRadioButtonOzSelected(view: View) {
        val editor = sharedPref!!.edit()
        editor.putString("waterUnit", "oz")
        editor.apply()
    }
    fun onRadioButtonCupSelected(view: View) {
        val editor = sharedPref!!.edit()
        editor.putString("waterUnit", "cup")
        editor.apply()
    }
    //onClick methods for coffee unit radio group
    fun onRadioButtonGramsSelected(view: View) {
        val editor = sharedPref!!.edit()
        editor.putString("coffeeUnit", "grams")
        editor.apply()
    }
    fun onRadioButtonTbspSelected(view: View) {
        val editor = sharedPref!!.edit()
        editor.putString("coffeeUnit", "tbsp")
        editor.apply()
    }
    fun onCoffeeRadioButtonOzSelected(view: View) {
        val editor = sharedPref!!.edit()
        editor.putString("coffeeUnit", "oz")
        editor.apply()
    }
    fun onBackClicked(view: View) {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}