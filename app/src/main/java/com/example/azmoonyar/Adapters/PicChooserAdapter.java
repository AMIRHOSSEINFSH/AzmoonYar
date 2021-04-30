package com.example.azmoonyar.Adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.azmoonyar.Fragments.Dialog.EditDialogFragment;
import com.example.azmoonyar.R;

import java.io.File;
import java.util.List;

public class PicChooserAdapter extends RecyclerView.Adapter<PicChooserAdapter.MyViewHolder> {

    private EditDialogFragment fragmentContainer;
    private List<String> listPic;
    private SelectingPicListener callBack;
    public PicChooserAdapter(EditDialogFragment fragmentContainer, List<String> listPic) {
        this.fragmentContainer = fragmentContainer;
        this.listPic = listPic;
        callBack=(SelectingPicListener) fragmentContainer;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(fragmentContainer.getContext()).inflate(R.layout.item_pic,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
    holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return listPic.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView img_Pic;
        TextView txtName;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            img_Pic=itemView.findViewById(R.id.img_pic);
            txtName=itemView.findViewById(R.id.txt_pic);

            itemView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    //Toast.makeText(fragmentContainer.getContext(), "Clicked!!", Toast.LENGTH_LONG).show();
                        callBack.onSendBackPath(listPic.get(getAdapterPosition()),bitmap);
                    Toast.makeText(fragmentContainer.getContext(), "Image is Selected !!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        Bitmap bitmap;
        public void bind(int position){
            RequestOptions reqOptions = new RequestOptions()
                    .fitCenter()
                    .override(100, 100);

            File file=new File(listPic.get(position));
            //Bitmap myBitmap = BitmapFactory.decodeFile(file.getPath());
            //img_Pic.setImageBitmap(myBitmap);

//            bitmap = ((BitmapDrawable) img_Pic.getDrawable()).getBitmap();

            Glide.with(fragmentContainer.getContext()).asBitmap().apply(reqOptions).load(listPic.get(position)).into(img_Pic);
            bitmap = BitmapFactory.decodeFile(file.getPath());
            txtName.setText( file.getName() );

        }
    }

    public interface SelectingPicListener{
        void onSendBackPath(String path,Bitmap bitmap);
    }
}
