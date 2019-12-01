package com.example.bucketlist.ui;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.bucketlist.R;
import com.example.bucketlist.model.Note;

import java.util.ArrayList;

//(1) The ContactAdapter is a child of RecyclerView.Adapter and uses the holder ContactHolder.
//    This means the adapter manages entries of ContactHolder
public class NoteAdapter extends RecyclerView.Adapter<NoteHolder> {

    //(2) The constructor and ArrayList are used to keep track of the information displayed. Though
    //    the RecyclerView.Adapter functions manage the views in the holders, the information in
    //    the views are kept separately and should be managed by the adapter class itself.
    private ArrayList<Note> contactList;
    private Activity context;

    public NoteAdapter(Activity activity){
        context = activity;
        contactList = new ArrayList<Note>();

        //(3) The hard coded entries as shown here illustrate how information can be initially
        //    populated onto a list. It is also possible to pass information onto the constructor
        //    which will be used to populate the list.
    }

    //(4) The onCreateViewHolder function is used to create the views to be used. This function
    //    expects a holder with a view to be returned. The information in the holder will be
    //    overwritten in another function.
    @Override
    public NoteHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //(5) An inflater is again used to populate the view. This information can be directly
        //    taken from a layout.
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.note_row, parent, false);

        //(6) The view created must be given to a holder. The holder will serve as the in-between
        //    system that interacts with the view.
        NoteHolder holder = new NoteHolder(view,context);
        return holder;
    }

    //(7) The onBindViewHolder function is called to replace the information of the view on a
    //    specific position. This is why the position parameter will dictate what information
    //    should be displayed on the specific view.
    @Override
    public void onBindViewHolder(NoteHolder holder, final int position) {
        holder.setName(contactList.get(position).getName());
        holder.setCat(contactList.get(position).getCategory());
        holder.setDescription(contactList.get(position).getDescription());
        holder.setId(contactList.get(position).getId()+""+position);
    }

    //(8) As the list of information grows, the adapter's parent functions must be notified of the
    //    change of size in the list of elements. Thus it needs the getItemCount function to
    //    indicate the current size of the list of elements.
    @Override
    public int getItemCount() {
        return contactList.size();
    }

    //(9) Information can be added later on but the notifyItemInserted function must be called to
    //    tell the system a new piece of information is added.
    public void addItem(Note n){
        contactList.add(n);
        notifyItemInserted(contactList.size()-1);
    }
}
