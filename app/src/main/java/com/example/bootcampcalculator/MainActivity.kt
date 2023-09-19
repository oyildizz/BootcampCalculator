package com.example.bootcampcalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.example.bootcampcalculator.databinding.ActivityMainBinding
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private var canAddOperation = false
    private var canAddDecimal = true

    override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.btn0.setOnClickListener { appendOnExpression("0", true) }
    binding.btn1.setOnClickListener { appendOnExpression("1", true) }
    binding.btn2.setOnClickListener { appendOnExpression("2", true) }
    binding.btn3.setOnClickListener { appendOnExpression("3", true) }
    binding.btn4.setOnClickListener { appendOnExpression("4", true) }
    binding.btn5.setOnClickListener { appendOnExpression("5", true) }
    binding.btn6.setOnClickListener { appendOnExpression("6", true) }
    binding.btn7.setOnClickListener { appendOnExpression("7", true) }
    binding.btn8.setOnClickListener { appendOnExpression("8", true) }
    binding.btn9.setOnClickListener { appendOnExpression("9", true) }
    binding.btnDot.setOnClickListener { appendOnExpression(".", true) }

//Operators
    binding.btnAdd.setOnClickListener {
        appendOnExpression("+", true)
    }
    binding.btnSubtract.setOnClickListener {
        appendOnExpression("-", true)
    }
    binding.btnMultiply.setOnClickListener { appendOnExpression("*", true) }
    binding.btnDivide.setOnClickListener { appendOnExpression("/", true) }
}

    fun appendOnExpression(string: String,canClear:Boolean){
        if(canClear){
            binding.resultTv.text=""
            binding.dataTv.append(string)
        }else{
            binding.dataTv.append(binding.resultTv.text)
            binding.dataTv.append(string)
            binding.resultTv.text=""
        }
    }


    fun onAllClearClick(view: View) {
        binding.dataTv.text = ""
        binding.resultTv.text = ""
    }


    fun onBackClick(view: View) {
        val string = binding.dataTv.text.toString()
        if(string.isNotEmpty()){
            binding.dataTv.text=string.substring(0,string.length-1)
        }
    }


    fun onOperatorClick(view: View) {}


    fun onDigitClick(view: View) {
        if(view is Button)
        {
            if(view.text == ".")
            {
                if(canAddDecimal)
                    binding.dataTv.append(view.text)

                canAddDecimal = false
            }
            else
                binding.dataTv.append(view.text)

            canAddOperation = true
        }
    }


    fun onEqualClick(view: View) {
        try {
            val expression = ExpressionBuilder(binding.dataTv.text.toString()).build()
            val result = expression.evaluate()
            val longResult = result.toLong()
            if(result == longResult.toDouble()){
                binding.resultTv.text = longResult.toString()
                binding.resultTv.visibility = View.VISIBLE
            }else{
                binding.resultTv.text=result.toString()
                binding.resultTv.visibility = View.VISIBLE
            }
            binding.resultTv.text = "= $result"
        } catch (e: Exception) {
            Log.d("Exception", "message :" + e.message)
            binding.resultTv.text = "Error"
        }
    }
}