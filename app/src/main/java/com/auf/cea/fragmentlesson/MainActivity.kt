package com.auf.cea.fragmentlesson

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.auf.cea.fragmentlesson.databinding.ActivityMainBinding
import java.util.ArrayList

class MainActivity : AppCompatActivity(), View.OnClickListener, PassingDataFragment.PassingDataInterface, SampleDialogFragment.DialogFragmentInterface {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnShowFragment.setOnClickListener(this)
        binding.btnShowFragmentTwo.setOnClickListener(this)
        binding.btnFragmentSend.setOnClickListener(this)
        binding.btnSimpleAlertDialog.setOnClickListener(this)
        binding.btnShowFragDialog.setOnClickListener(this)
    }

    override fun onClick(p0: View?) {
        when(p0!!.id){
            (R.id.btn_show_fragment)->{
                val fragmentManager = supportFragmentManager.beginTransaction()
                fragmentManager.replace(binding.fragmentContainer.id,FragmentOne())
                fragmentManager.commit()
            }(R.id.btn_show_fragment_two)->{
                val fragmentManager = supportFragmentManager.beginTransaction()
                fragmentManager.replace(binding.fragmentContainer.id,FragmentTwo())
                fragmentManager.commit()
            }(R.id.btn_fragment_send) ->{
                val fragmentManager = supportFragmentManager.beginTransaction()
                val fragment = PassingDataFragment()
                fragment.onSendDataCallback = this
                fragmentManager.replace(binding.fragmentContainer.id,fragment)
                fragmentManager.commit()
            }(R.id.btn_simple_alert_dialog)->{
                //createDialog()
                showMultiListAlertDialog()
                //showAlertWithIcons()
            }(R.id.btn_show_frag_dialog)->{
                val sampleDialogFragment = SampleDialogFragment.newInstance("Hello","There")
                sampleDialogFragment.onDialogCallback = this
                sampleDialogFragment.show(supportFragmentManager,null)
            }
        }
    }

    override fun onSendData(name: String) {
        binding.txtData.text = name
    }

    private fun createDialog(){
        val builder = AlertDialog.Builder(this)
        builder.setMessage("THIS IS A TEST MESSAGE")
        builder.setTitle("ALERT MESSAGE")
        builder.setPositiveButton("Ok"){dialog,which ->
            Toast.makeText(this,"OK CLICKED",Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Cancel"){dialog,which ->
            Toast.makeText(this,"CANCEL CLICKED",Toast.LENGTH_SHORT).show()
        }
        builder.setNeutralButton("Neutral"){dialog,which ->
            Toast.makeText(this,"NEUTRAL CLICKED",Toast.LENGTH_SHORT).show()
        }
        builder.setCancelable(false)
        builder.show()
    }

    private fun showMultiListAlertDialog(){
        val item = arrayOf("C1", "C2", "C3","C4")
        var selectedList = ArrayList<Int>()
        val builder = AlertDialog.Builder(this)

        builder.setTitle("Choices")
        builder.setMultiChoiceItems(item, null
        ) { dialog, which, isChecked ->
            if (isChecked) {
                selectedList.add(which)
            } else if (selectedList.contains(which)) {
                selectedList.remove(Integer.valueOf(which))
            }
        }

        builder.setPositiveButton("DONE"){dialog,which ->
            val selectedString = ArrayList<String>()

            for(x in selectedList.indices){
                selectedString.add(item[selectedList[x]])
            }

            Toast.makeText(this,"Selected are: " + selectedString.toTypedArray().contentToString(), Toast.LENGTH_SHORT).show()

        }

        builder.show()

    }

    private fun showAlertWithIcons(){
        val builder = AlertDialog.Builder(this)
        with(builder){
            setTitle("Dialog with an Icon")
            setMessage("THIS HAS AN ICON")
            setPositiveButton("Ok",null)
            setNegativeButton("Cancel",null)
            setIcon(resources.getDrawable(android.R.drawable.ic_dialog_alert,theme))
        }

        builder.create().show()
    }

    override fun passData(string: String) {
        binding.txtData.text = string
    }

}