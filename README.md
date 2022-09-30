
# Fragment Lesson and Alert Dialogs

Sample Fragments application and alert dialog in Android using Kotlin. FOR AUF-CEA STUDENTS USE


## Usage/Examples/Discussions

**Creating the Fragments**\
In Android Studio, right click your package -> New -> Fragment. Name your fragments according to the purpose to minimize confusion along the way. In this project we have 3 fragments and one DialogFragment 

**Main Activity Layout (activity_main.xml)**\
In this layout, we will add a **FrameLayout** to host our fragments and add the buttons for
presentation purposes. The FrameLayout will be containing our fragment via the code in the **MainActivity.kt** 
```xml
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity"
    android:orientation="vertical"
    android:weightSum="2">

    <LinearLayout
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:gravity="center_horizontal"
        android:orientation="vertical"
        android:layout_weight="1">

        <TextView
            android:id="@+id/txt_data"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="SHOULD SHOW DATA"/>

        <Button
            android:id="@+id/btn_show_fragment"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Show Fragment"/>
        <Button
            android:id="@+id/btn_show_fragment_two"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Show Fragment two"/>

        <Button
            android:id="@+id/btn_fragment_send"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Show Send Data Fragment"/>

        <Button
            android:id="@+id/btn_simple_alert_dialog"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Show simple alert dialog"/>

        <Button
            android:id="@+id/btn_show_frag_dialog"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:text="Show fragment dialog"/>

    </LinearLayout>

    <FrameLayout
        android:id="@+id/fragment_container"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"/>

</LinearLayout>
```
**FragmentManager Class (MainActivity.kt)**\
As discussed, Fragments cannot live on their own in an android application, they need to be hosted
by an activity or something similar. **FragmentManager** is the class responsible for performing actions
on your app's fragments such as adding, removing or replacing.

Initiate the fragment manager class:
```Kotlin
val fragmentManager = supportFragmentManager.beginTransaction()
```

In order for the fragment to appear on the designated fragment container we will have to use the **replace** function of
the FragmentManager Class:
```Kotlin
//PARAMETER 1: The Fragment Container
//PARAMETER 2: The Fragment to be used
fragmentManager.replace(binding.fragmentContainer.id,FragmentOne())
```

Commiting the FragmentManager will execute our code above:
```Kotlin
fragmentManager.commit()
```

**Fragment Lifecycle**\
Refer to the **FragmentOne.kt** class to view the full Lifecycle of the Fragment

**Passing data back to activity class (PassingDataFragment.kt)**\
There are multiple ways to pass data back to the activiy of the fragment. One method to use
is creating custom interfaces/listeners in the fragment class and implement that method in
the activity hosting the fragment
```Kotlin
//PassingDataFragment.Kt
lateinit var onSendDataCallback: PassingDataInterface

interface PassingDataInterface{
    fun onSendData(name:String)
}

override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    binding.btnSendData.setOnClickListener{
        //Call this to send data back to the activity
        onSendDataCallback.onSendData(binding.edtName.text.toString())
    }
}
```

In the **MainActivity.kt**, implement the interface
```Kotlin
class MainActivity : AppCompatActivity(), View.OnClickListener, PassingDataFragment.PassingDataInterface
```
Then we need add the override function for the interface that we implemented
```Kotlin
override fun onSendData(name: String) {}
```

**AlertDialogs**\
A dialog is a small window that prompts the user to make a decision or enter additional information. A dialog does not fill the screen and is normally used for modal events that require users to take an action before they can proceed.

Initialize the builder for the AlertDialog Class:
```Kotlin
val builder = AlertDialog.Builder(this)
```

AlertDialogs need a title, message and buttons (upto 3 buttons max)
```Kotlin
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
```

We can also force the user to choose an option on the alert dialogs by default, AlertDialogs
are cancellable when clicked outside the view, we can disable this by adding:
```Kotlin
builder.setCancelable(false)
```

And finally show the AlertDialog:
```Kotlin
builder.show()
```

**Multi-List Alert Dialog**
We can create multi-list alert dialogs which give options to user if needed be.

Create the list of choices for the user, for this example we will create an array of choices:
```Kotlin
val item = arrayOf("C1", "C2", "C3","C4")
```

Create the container for the selected choices of the user:
```Kotlin
var selectedList = ArrayList<Int>()
```

Initiate the alert dialog builder and set the title:
```Kotlin
val builder = AlertDialog.Builder(this)
builder.setTitle("Choices")
```

In order for the Alert dialog to know what type of alert dialog to be use, we have to 
specify it, in this case we will use the **setMultiChoiceitems**

```Kotlin
builder.setMultiChoiceItems(item, null) { dialog, which, isChecked ->
    //WHEN THE USER CHECKS ANY OF THE CHOICES, THIS CODE WILL RUN AND CHECK IF THE
    //CLICKED ITEM IS CHECKED OR NOT
    //IF THE ITEM IS CHECKED, THE ITEM INDEX WILL BE ADD ON THE LIST OTHERWISE IT WILL BE
    //REMOVED FROM THE LIST
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
```

**Show Alert Dialog with Icons**\
We can put an icon on the alert dialog title by setting the following:
```Kotlin
//MORE READABLE WAY OF CREATING ALERTDIALOGS
val builder = AlertDialog.Builder(this)
with(builder){
    setTitle("Dialog with an Icon")
    setMessage("THIS HAS AN ICON")
    setPositiveButton("Ok",null)
    setNegativeButton("Cancel",null)
    setIcon(resources.getDrawable(android.R.drawable.ic_dialog_alert,theme))
}

builder.create().show()
```

**FragmentDialogs (SampleDialogFragment.kt)**\
Is a fragment that is used to make dialogs that float on an activity. It has its own lifecycle which makes it independent from the activity

To create a DialogFragment we have to change the Fragment into DialogFragment
```Kotlin
class SampleDialogFragment : DialogFragment()
```
It has the same properties and methods as the Fragment class. In order to call the Dialog Fragment
We can use the Companion Object generated by Android Studio:

```Kotlin
companion object {
    /**
        * Use this factory method to create a new instance of
        * this fragment using the provided parameters.
        *
        * @param param1 Parameter 1.
        * @param param2 Parameter 2.
        * @return A new instance of fragment SampleDialogFragment.
        */
    // TODO: Rename and change types and number of parameters
    @JvmStatic
    fun newInstance(param1: String, param2: String) =
        SampleDialogFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_PARAM1, param1)
                putString(ARG_PARAM2, param2)
            }
        }
}
```

In the **MainActivity.kt** we can call the Dialog fragment using:
```Kotlin
val sampleDialogFragment = SampleDialogFragment.newInstance("Hello","There")
sampleDialogFragment.show(supportFragmentManager,null)
```

If we need to send data back to the activity, we have to create an interface on the dialog
fragment class:
```Kotlin
lateinit var onDialogCallback: DialogFragmentInterface

interface DialogFragmentInterface{
    fun passData(string: String)
}
```

Then we need to implement that method in the **MainActivity.kt**
```Kotlin
class MainActivity : AppCompatActivity(), View.OnClickListener, SampleDialogFragment.DialogFragmentInterface
```

Then we need to attach the interface back to the DialogFragment
```Kotlin
sampleDialogFragment.onDialogCallback = this
```





