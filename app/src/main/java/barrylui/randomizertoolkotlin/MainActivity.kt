package barrylui.randomizertoolkotlin

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import barrylui.listrandomizerjava.MainActivityViewModel

class MainActivity : AppCompatActivity() {

    internal val context: Context = this
    private var mViewModel = MainActivityViewModel()
    private var mRecyclerView: RecyclerView? = null
    private var mAdapter: ListRecyclerViewAdapter? = null
    private var mLayoutManager: RecyclerView.LayoutManager? = null
    private var deleteListButton: ImageButton? = null
    private var addItemToListButton: FloatingActionButton? = null
    private var shuffleListButton: Button? =  null
    private var randomSelectOneButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Set up ViewModel
        mViewModel = ViewModelProviders.of(this).get(MainActivityViewModel::class.java!!)

        //Set up observer to observe the livedata in viewmodel
        val listObserver = Observer<MutableList<String>> { mAdapter!!.notifyDataSetChanged() }
        mViewModel.theList.observe(this, listObserver)


        //Deletes the list
        deleteListButton = findViewById(R.id.deleteListImageButton) as ImageButton
        deleteListButton!!.setOnClickListener {
            mViewModel.clearList()
        }

        //Adds element to the list
        addItemToListButton = findViewById(R.id.fabaddtolistbutton) as FloatingActionButton
        addItemToListButton!!.setOnClickListener {
            //Helper method to allow user to input items
            showAddItemDialog()
        }

        //Allows user to choose between randomizing order of the list or randomly selecting one element from the list
        shuffleListButton = findViewById(R.id.shuffleButton) as Button
        shuffleListButton!!.setOnClickListener {
            //If the list is empty, you cannot do any randomization
            if (mViewModel.theList.value!!.isEmpty()) {
                Toast.makeText(context, getString(R.string.emptylistshuffle), Toast.LENGTH_SHORT).show()
            } else {
                //If list is not empty, shuffle the list
                mViewModel.shuffleList()
                Toast.makeText(this@MainActivity, getString(R.string.listshufflemsg), Toast.LENGTH_LONG).show()
            }
        }
        //Randomly selects one element from the list
        randomSelectOneButton = findViewById(R.id.randomlySelectOneButton) as Button
        randomSelectOneButton!!.setOnClickListener {
            if (mViewModel.theList.value!!.isEmpty()) {
                Toast.makeText(context, getString(R.string.emptylistselectone), Toast.LENGTH_SHORT).show()
            } else {
                //If list is not empty, randomly select one from the list
                randomlySelectOneFromList()

            }
        }

        mRecyclerView = findViewById(R.id.recyclerview) as RecyclerView
        mRecyclerView!!.setHasFixedSize(false)
        mLayoutManager = LinearLayoutManager(this)
        mRecyclerView!!.layoutManager = mLayoutManager

        mAdapter = ListRecyclerViewAdapter(mViewModel.theList.value)
        mRecyclerView!!.adapter = mAdapter
        mAdapter!!.notifyDataSetChanged()

        //Deletes item selected
        mAdapter!!.SetOnItemDeleteClickListener(object : ListRecyclerViewAdapter.OnItemDeleteClickListener{
            override fun onItemDelete(view: View, position: Int) {
                //Viewmodel method to remove item from singleton
                mViewModel.removeItemFromList(position)
            }
        })
    }

    //displays dialog to allow user to add an element to the list
    fun showAddItemDialog() {

        //Get Layout inflater
        val li = LayoutInflater.from(context)
        //Inflate custom dialog
        val addItemDialogPrompt = li.inflate(R.layout.additemdialog, null)

        //Build dialog
        val alertDialogBuilder = AlertDialog.Builder(context)
        alertDialogBuilder.setView(addItemDialogPrompt)

        //Edit text field for user to enter item
        val inputfield = addItemDialogPrompt.findViewById(R.id.editTextDialogUserInput) as EditText

        //Submit button will add item to the List Singleton
        alertDialogBuilder.setCancelable(false).setPositiveButton(getString(R.string.submit)
                //Cancel button will cancel the dialog
        ) { dialogInterface, i ->
            //Get string inputted by user in the edit text field
            val inputItem = inputfield.text.toString()
            //Add element to the list
            mViewModel.addItemToList(inputItem)
        }.setNegativeButton(getString(R.string.cancel)) { dialogInterface, i -> dialogInterface.cancel() }

        //Create the dialog
        val addItemAlertDialog = alertDialogBuilder.create()

        //Activate the keyboard when the dialog fragment is inflated
        addItemAlertDialog.window!!.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM)
        addItemAlertDialog.window!!.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE)
        //Show the dialog
        addItemAlertDialog.show()

    }

    //helper method to randomly select item from list
    fun randomlySelectOneFromList() {

        //Random int to help our random selection
        val random_selection = mViewModel.generateRandomNumber()

        //Build the dialog to display which element was chosen
        val randomlySelectOneDialog = AlertDialog.Builder(this)
                .setCancelable(false)
                .setTitle(mViewModel.theList.value!!.get(random_selection))
                .setMessage(getString(R.string.randomlyselected))
                .setPositiveButton(getString(R.string.retry), null)
                .setNegativeButton(getString(R.string.cancel), null)
                .create()

        //Program buttons to handle user selection
        randomlySelectOneDialog.setOnShowListener(DialogInterface.OnShowListener {
            val retryRandomlySelectOneFromListButton = randomlySelectOneDialog.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
            val cancelButton = randomlySelectOneDialog.getButton(android.app.AlertDialog.BUTTON_NEGATIVE)

            //Option 1: Retry, Randomly select an element from the list again
            retryRandomlySelectOneFromListButton.setOnClickListener(View.OnClickListener {
                randomlySelectOneFromList()
                randomlySelectOneDialog.dismiss()
            })

            //Option 2: Dismiss the dialog and return to the list
            cancelButton.setOnClickListener(View.OnClickListener { randomlySelectOneDialog.dismiss() })
        })
        //Display the dialog
        randomlySelectOneDialog.show()
    }
}
