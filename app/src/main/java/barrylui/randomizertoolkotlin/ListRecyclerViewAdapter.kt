package barrylui.randomizertoolkotlin

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import barrylui.listrandomizerjava.MainActivityViewModel

class ListRecyclerViewAdapter(theUserList: List<String>?) : RecyclerView.Adapter<ListRecyclerViewAdapter.MyViewHolder>() {
    private val mViewModel: MainActivityViewModel? = null
    private var mItemDeleteClickListener: OnItemDeleteClickListener? = null
    private var mDataset: List<String>?

    init {
        mDataset = theUserList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val playerView = LayoutInflater.from(parent.context).inflate(R.layout.cardview_stringitem, parent, false)
        return MyViewHolder(playerView)
    }

    override fun onBindViewHolder(viewHolder: MyViewHolder, position: Int) {
        val inputted_item = mDataset!![position]
        viewHolder.stringItem.text = inputted_item
    }

    interface OnItemDeleteClickListener {
         fun onItemDelete(view: View, position: Int)
    }

    fun SetOnItemDeleteClickListener(theClickListener: OnItemDeleteClickListener) {
        this.mItemDeleteClickListener = theClickListener
    }

    override fun getItemCount(): Int {
        return mDataset!!.size
    }

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder /*implements View.OnClickListener*/(view) {
        internal var stringItem: TextView
        internal var deleteItem: ImageView

        init {
            stringItem = view.findViewById(R.id.stringItemTextView) as TextView
            deleteItem = view.findViewById(R.id.deleteItemImageView) as ImageView

            deleteItem.setOnClickListener { mItemDeleteClickListener!!.onItemDelete(view, layoutPosition) }
        }
    }

}