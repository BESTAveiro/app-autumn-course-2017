package com.example.bestaveiro.appcurso;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by filipe on 13/05/2016.
 */
public class fotos extends Fragment{

    View myView;
    ArrayList<Bitmap> imgsBitmap;
    GridView gridView;
    ImageView iv;

    private static final int REQUEST_IMAGE_CAPTURE = 111;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.fotos, container, false);

        StaticMethods.removeTabLayout(getActivity());
        StaticMethods.removeFAB(getActivity());

        imgsBitmap=new ArrayList<>();
        gridView= (GridView)myView.findViewById(R.id.gridView);
        Button btn= (Button) myView.findViewById(R.id.btnPhoto);
        iv= (ImageView)myView.findViewById(R.id.imageView2);

        assert btn != null;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null){
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                }
            }
        });

        final GridAdapter adapter=new GridAdapter(imgsBitmap, getActivity());
        gridView.setAdapter(adapter);

        //Apresentar totos //
        DatabaseReference reff = FirebaseDatabase.getInstance().getReference("photos");
        reff.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot ds : dataSnapshot.getChildren()){

                    String imag= ds.getValue(String.class);

                    byte[] decodedString = android.util.Base64.decode(imag, Base64.DEFAULT);
                    Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);

                    imgsBitmap.add(decodedByte);
                    Collections.reverse(imgsBitmap);
                    adapter.notifyDataSetChanged();


                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Toast.makeText(MainActivity.this, "" + position, Toast.LENGTH_SHORT).show();
                Intent i=new Intent(getActivity(), FullscreenActivity.class);
                Bitmap _bitmap=(Bitmap) imgsBitmap.get(position); // your bitmap
                ByteArrayOutputStream _bs = new ByteArrayOutputStream();
                _bitmap.compress(Bitmap.CompressFormat.PNG, 50, _bs);
                i.putExtra("byteArray", _bs.toByteArray());
                startActivity(i);
            }
        });

        return myView;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            encodeBitmapAndSaveToFirebase(imageBitmap);
        }
    }

    public void encodeBitmapAndSaveToFirebase(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String imageEncoded = Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("photos");
        Map<String, String> image = new HashMap<String, String>();
        image.put("photo", imageEncoded);
        ref.push().setValue(image);

    }
}


class GridAdapter extends BaseAdapter
{

    private Context context;
    final ArrayList<Bitmap> images;
    LayoutInflater inflater;

    public GridAdapter(ArrayList<Bitmap> images, Context context) {
        this.images = images;
        this.context = context;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(inflater==null){

            inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        if (convertView==null){

            convertView=inflater.inflate(R.layout.gvlayout,parent,false);

        }

        MyViewHolder2 holder=new MyViewHolder2(convertView);
        holder.imgview.setImageBitmap(images.get(position));




        return convertView;
    }
}
class MyViewHolder2
{

    imgSquare imgview;

    public MyViewHolder2 (View itemView){

        imgview= (imgSquare) itemView.findViewById(R.id.imagesquare);

    }
}


class photo{

    String photo;

    public photo(String photo) {
        this.photo = photo;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}




