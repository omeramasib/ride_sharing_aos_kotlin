package fyp.ride_sharing_aos.adapters

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fyp.ride_sharing_aos.HomeActivity
import fyp.ride_sharing_aos.R
import fyp.ride_sharing_aos.model.Room
import fyp.ride_sharing_aos.tools.Tools
import kotlinx.android.synthetic.main.roomlist_layout.view.*

/**
 * Created by lfreee on 25/1/2018.
 */
class RoomListAdapter(private val c : Context,private val roomlist: MutableList<Room>) : RecyclerView.Adapter<RoomListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val view = LayoutInflater.from(c).inflate(R.layout.roomlist_layout, parent, false)
        return ViewHolder(c,view)
    }

    //this method is binding the data on the list
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindItems(roomlist[position])
    }


    override fun getItemCount(): Int {
        return roomlist.size
    }




class ViewHolder(val c : Context, val view: View): RecyclerView.ViewHolder(view) {

    fun bindItems(room: Room) {

        //ch id, link up one by one
        view.item_roomname.text = room.roomname
        view.item_prefer_time.text = Tools.convertTime(room.prefertime!!)

        view.item_from.text = room.start
        view.item_to.text = room.destination



        view.current_date.text = Tools.convertDate(room.prefertime!!)

        view.item_numpeople.text = room.numOfPeople.toString() + "/4"
        //  view.item_numpeople.text = room.numOfPeople.toString() + "/" + roon.max.toString()

        if(room.maleFil == true)
            view.item_filter.text = "Male Only"
        if(room.femaleFil == true)
            view.item_filter.text = "Female Only"


        // Destination Color
        if(room.destination.equals("HKUST North Gate"))
            view.cardview_color.setBackgroundColor(Color.parseColor("#ff0f0f"))
        if(room.destination.equals("HKUST South Gate"))
            view.cardview_color.setBackgroundColor(Color.parseColor("#ff0f0f"))
        if(room.destination.equals("Diamond Hill MTR"))
            view.cardview_color.setBackgroundColor(Color.parseColor("#222222"))
        if(room.destination.equals("Choi Hung MTR"))
            view.cardview_color.setBackgroundColor(Color.parseColor("#406080"))
        if(room.destination.equals("Hang Hau MTR"))
            view.cardview_color.setBackgroundColor(Color.parseColor("#AFEEEE"))
        if(room.destination.equals("Ngau Tau Kok MTR"))
            view.cardview_color.setBackgroundColor(Color.parseColor("#42e58b"))



        //Please Sync With HomeActivity -> AutoMatching
        view.setOnClickListener {
//            if(FirebaseManager.isRoomIDValid())
//            {
//                val error_msg: ArrayList<String> = ArrayList()
//                error_msg.add(c.getString(R.string.room_join_error_msg))
//                Tools.showDialog(c,c.getString(R.string.room_join_error_title),error_msg)
//            }
//            else
//            {
//                (c as HomeActivity).showProgressDialog(c.getString(R.string.progress_loading))
//                FirebaseManager.joinRoom(room.roomid!!,{
//
//                    c.dismissProgressDialog()
//                    FirebaseManager.detachUserListener()
//                    c.callChatRoom()
//
//                })
//            }

            (c as HomeActivity).joinRoom(room.roomid!!)
        }



    }
}

}