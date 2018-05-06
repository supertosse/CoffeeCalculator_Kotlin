package com.supertosse.coffeecalculator

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.SeekBar
import kotlinx.android.synthetic.main.activity_main.*
import java.text.DecimalFormat

class MainActivity() : AppCompatActivity() {

    private var isCoffeeToWater: Boolean = false
    private var waterUnit: String? = null
    private var waterUnitMultiplier: Float = 0.toFloat()
    private var coffeeUnit: String? = null
    private var coffeeUnitMultiplier: Float = 0.toFloat()

    private val myPreferences = "com.supertosse.coffeecalculator.preferences"
    private var sharedPref: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sharedPref = this.getSharedPreferences(this.myPreferences, 0)
        setupSeekBar(this)
        setupInput(this)
        loadValues()
        resultBox.visibility = View.INVISIBLE
    }
    override fun onResume() {
        super.onResume()
        loadValues()
    }

    private fun loadValues() {
        this.waterUnit = sharedPref!!.getString("waterUnit", getString(R.string.dl))
        this.coffeeUnit = sharedPref!!.getString("coffeeUnit", "grams")
        this.isCoffeeToWater = sharedPref!!.getBoolean("isCoffeeToWater", false)

        if (!this.isCoffeeToWater) {
            waterToCoffeRadioButton.performClick()
        } else {
            coffeToWaterRadioButton.performClick()
        }

        when (this.waterUnit) {
            "dl" -> this.waterUnitMultiplier = 100f
            "cl" -> this.waterUnitMultiplier = 10f
            "grams" -> this.waterUnitMultiplier = 1f
            "oz" -> this.waterUnitMultiplier = 29.6f
            "cup" -> this.waterUnitMultiplier = 236f
        }
        when (this.coffeeUnit) {
            "grams" -> this.coffeeUnitMultiplier = 1f
            "tbsp" -> this.coffeeUnitMultiplier = 10f
            "oz" -> this.coffeeUnitMultiplier = 28.3f
        }
    }
    private fun setupSeekBar(m: MainActivity) {
        ratioSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                m.updateOutput()
                updateSeekaBarText(i)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar) {}
            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        val sharedPref = getSharedPreferences(myPreferences, Context.MODE_PRIVATE)
        val seekBarValue = sharedPref.getInt("ratio", 18) - 10
        ratioSeekBar.progress = seekBarValue
    }
    private fun setupInput(m: MainActivity) {
        amountInput.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                m.updateOutput()
            }
            override fun afterTextChanged(editable: Editable) {}
        })
    }
    fun setCoffeeToWater(view: View) {
        this.isCoffeeToWater = true
        unitTextView.text = this.coffeeUnit
        amountInput.setText("")
        resultBox.visibility = View.INVISIBLE
        unitTextView.hint = "coffee"
        updateOutput()

        val editor = sharedPref!!.edit()
        editor.putBoolean("isCoffeeToWater", true)
        editor.apply()
    }
    fun setWaterToCoffee(view: View) {
        this.isCoffeeToWater = false
        unitTextView.text =this.waterUnit
        amountInput.setText("")
        resultBox.visibility = View.INVISIBLE
        amountInput.hint = "water"
        updateOutput()

        val editor = sharedPref!!.edit()
        editor.putBoolean("isCoffeeToWater", false)
        editor.apply()
    }
    fun updateOutput(){
        val inputString = amountInput.text.toString()
        //To avoid app stopping if invalid input
        if (inputString == "." || inputString.isEmpty()) {
            resultBox.visibility = View.INVISIBLE
            return
        }
        var df = DecimalFormat("#")
        var result = inputString.toFloat()
        val outputString1: String
        val outputString2: String
        resultBox.visibility = View.VISIBLE

        if (this.isCoffeeToWater) {
            result *= (ratioSeekBar.progress + 10).toFloat()
            result *= this.coffeeUnitMultiplier
            result /= this.waterUnitMultiplier

            if (waterUnit == "dl" || waterUnit == "oz" || waterUnit == "cup") {
                df = DecimalFormat("#.#")
            }
            outputString1 = "You need ${df.format(result.toDouble())} $waterUnit of water, for $inputString $coffeeUnit of coffee"
            outputString2 = "${df.format(result.toDouble())} $waterUnit"
        }else{
            result /= (ratioSeekBar.progress + 10).toFloat()
            result *= this.waterUnitMultiplier
            result /= this.coffeeUnitMultiplier

            if (coffeeUnit == "tbsp") {
                df = DecimalFormat("#.#")
            }else if(coffeeUnit == "oz") {
                df = DecimalFormat("#.###")
            }
            outputString1 = "You need ${df.format(result.toDouble())} ${this.coffeeUnit} of coffee, for $inputString ${this.waterUnit} of water"
            outputString2 = "${df.format(result.toDouble())} ${this.coffeeUnit}"
        }
        resultTextView.text = outputString1
        resultTextView2.text = outputString2
    }
    fun updateSeekaBarText(i: Int) {
        var seekBarText = "Ratio: 1/" + (i + 10)

        seekBarText += when {
            i == 8 -> "(Golden Ratio)"
            i < 8 -> "(Strong)"
            else -> "(weak)"
        }

        ratioTextView.text = seekBarText
        val editor = sharedPref!!.edit()
        editor.putInt("ratio", i + 10)
        editor.apply()
    }
    fun openPreferences(view: View) {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left)
    }

}