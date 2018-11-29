package barrylui.listrandomizerjava

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel

import java.util.ArrayList
import java.util.Collections
import java.util.Random

class MainActivityViewModel : ViewModel() {
    val theList: MutableLiveData<MutableList<String>> = MutableLiveData()
    var emptyList = ArrayList<String>()

    init {
        this.theList.value = emptyList
    }

    fun clearList() {
        theList.value!!.clear();
        theList.setValue(theList.value)
    }

    fun addItemToList(input: String) {
        theList.value!!.add(input)
        theList.setValue(theList.value)
    }

    fun removeItemFromList(position: Int) {
        theList.value!!.removeAt(position)
        theList.setValue(theList.value)
    }

    fun shuffleList() {
        Collections.shuffle(theList.value!!)
        theList.setValue(theList.value)
    }

    fun generateRandomNumber(): Int {
        val rng = Random()
        val rngvalue = rng.nextInt(theList.value!!.size)
        return rng.nextInt(theList.value!!.size)
    }

}
