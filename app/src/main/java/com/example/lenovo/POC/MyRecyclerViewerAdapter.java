package com.example.lenovo.POC;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyRecyclerViewerAdapter extends RecyclerView.Adapter<MyRecyclerViewerAdapter.ViewHolder> {
    Context context;
    private JSONObject mDataset;
    private ArrayList<Playlist> playlist;

    public void setData(JSONObject myDataset) {
        mDataset = myDataset;
        try {
            if (playlist == null)
                playlist = new ArrayList<Playlist>();
            for (int i = 0; i < mDataset.getJSONArray("data").length(); i++) {
                if (mDataset.getJSONArray("data").getJSONObject(i).getString("type").equals("playlist")) {

                    playlist.add(new Playlist(mDataset.getJSONArray("data").getJSONObject(i).getString("title")
                            , mDataset.getJSONArray("data").getJSONObject(i).getJSONObject("creator").getString("name")
                            , mDataset.getJSONArray("data").getJSONObject(i).getString("creation_date")
                            , mDataset.getJSONArray("data").getJSONObject(i).getString("nb_tracks")
                            , mDataset.getJSONArray("data").getJSONObject(i).getString("picture_medium")
                    ));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();


        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView, auteur, titres, dateCreation;
        public ImageView mPictureView;

        public ViewHolder(View v) {
            super(v);
            mTextView = (TextView) v.findViewById(R.id.title_playlist);
            mPictureView = (ImageView) v.findViewById(R.id.image_playlist);
            auteur = (TextView) v.findViewById(R.id.textView_auteur);
            dateCreation = (TextView) v.findViewById(R.id.textView_date_creattion);
            titres = (TextView) v.findViewById(R.id.textView_tracks_number);
        }
    }

    public MyRecyclerViewerAdapter(JSONObject myDataset) {
        mDataset = myDataset;
        try {
            if (playlist == null)
                playlist = new ArrayList<Playlist>();

            for (int i = 0; i < mDataset.getJSONArray("data").length(); i++) {
                if (mDataset.getJSONArray("data").getJSONObject(i).getString("type").equals("playlist")) {
                    playlist.add(new Playlist(mDataset.getJSONArray("data").getJSONObject(i).getString("title")
                            , mDataset.getJSONArray("data").getJSONObject(i).getJSONObject("creator").getString("name")
                            , mDataset.getJSONArray("data").getJSONObject(i).getString("creation_date")
                            , mDataset.getJSONArray("data").getJSONObject(i).getString("nb_tracks")
                            , mDataset.getJSONArray("data").getJSONObject(i).getString("picture_medium")
                    ));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();


        }
    }

    @Override
    public MyRecyclerViewerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                                 int viewType) {
        this.context = parent.getContext();
        View v = (View) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.playlist_elem_view, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.mTextView.setText(playlist.get(position).getTitre());
        holder.auteur.setText(playlist.get(position).getAuteur());
        holder.dateCreation.setText(playlist.get(position).getDate_creation());
        holder.titres.setText(playlist.get(position).getNbr_albums());
        if (context != null) {
            Picasso.with(context).load(playlist.get(position).getImage()).resize(160, 160).into(holder.mPictureView);
        } else {
            Log.e("RecyclerViwerAdapger::", "variable context not set ");

        }


    }

    @Override
    public int getItemCount() {
        return playlist != null ? playlist.size() : 0;
    }


}




