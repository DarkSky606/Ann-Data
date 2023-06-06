package com.example.anndata;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Picasso;

public class MainAdapter extends FirebaseRecyclerAdapter<MainModel,MainAdapter.myViewHolder>{

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public MainAdapter(@NonNull FirebaseRecyclerOptions<MainModel> options) {
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
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.main_item,parent,false);
        return new myViewHolder(view);
    }

    class myViewHolder extends RecyclerView.ViewHolder{

        TextView N1,FI1,FT1,DESC,CKD,CKT,ADD,CONT;
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
            DP=itemView.findViewById(R.id.card1);
        }
    }
}
