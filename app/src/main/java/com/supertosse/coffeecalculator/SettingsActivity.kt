package com.supertosse.coffeecalculator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    //the name of the shared preferences file
    private val MyPrefrences = "com.supertosse.coffeecalculator.preferences"
    private var sharedPref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        loadRadioButtonsState()
    }

    //Load values from shared preferences, and click appropriate radio buttons
    private fun loadRadioButtonsState() {
        this.sharedPref = getSharedPreferences(MyPrefrences, Context.MODE_PRIVATE)

        when (this.sharedPref!!.getString("waterUnit", "dl")) {
            "dl" -> dl.performClick()
            "cl" -> cl.performClick()
            "grams" -> gramsWater.performClick()
            "oz" -> oz.performClick()
            "cup" -> cup.performClick()
        }

        when (this.sharedPref!!.getString("coffeeUnit", "grams")) {
            "grams" -> grams.performClick()
            "tbsp" -> TbspRadioButton.performClick()
            "oz" -> ozRadioButton.performClick()
        }
        //Log.v("hwz", this.sharedPref!!.getString("coffeeUnit", "grams"))
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
    //Loads mainActivity when back button is clicked
    fun onBackClicked(view: View) {
        finish();
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
    }
}
