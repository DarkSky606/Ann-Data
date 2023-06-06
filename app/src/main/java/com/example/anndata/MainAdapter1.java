package com.example.anndata;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class MainAdapter1 extends FirebaseRecyclerAdapter<MainModel,MainAdapter1.myViewHolder>{

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter1(@NonNull FirebaseRecyclerOptions<MainModel> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull MainModel model) {

        holder.N1.setText(model.getName());
        holder.FI1.setText(model.getfItem());
        holder.FT1.setText(model.getfType());
        holder.DESC.setText(model.getDesc());
        holder.CKD.setText(model.getCkDate());
        holder.CKT.setText(model.getCkTime());
        holder.ADD.setText(model.getAddress());
        holder.CONT.setText(model.getContact());
        Picasso.get()
                .load(model.getDp())
                .into(holder.DP);

        holder.del.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder=new AlertDialog.Builder(holder.N1.getContext());
                builder.setTitle("Are You Sure ?");
                builder.setMessage("Deleted data can't be undo");
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase.getInstance().getReference().child("Don")
                                .child(getRef(position).getKey()).removeValue();
                        Toast.makeText(holder.N1.getContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(holder.N1.getContext(), "Cancelled", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.show();
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item1,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView N1,FI1,FT1,DESC,CKD,CKT,ADD,CONT;
        Button del;
        de.hdodenhof.circleimageview.CircleImageView DP;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            N1=itemView.findViewById(R.id.n1);
            FI1=itemView.findViewById(R.id.fi1);
            FT1=itemView.findViewById(R.id.ft1);
            DESC=itemView.findViewById(R.id.d1);
            CKT=itemView.findViewById(R.id.ckt1);
            CKD=itemView.findViewById(R.id.ckd1);
            ADD=itemView.findViewById(R.id.a1);
            CONT=itemView.findViewById(R.id.c1);
            del=itemView.findViewById(R.id.delbt);
            DP=itemView.findViewById(R.id.card1);

        }

    }
}
