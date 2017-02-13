package com.example.lenovo.POC;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/*
* Le code de notre fragment Playlist
*/
public class PlaylistFragment extends Fragment {
    final static String ID_USER = "256767965";
    final static String My_URL = "http://api.deezer.com/user/" + ID_USER + "/playlists";
    //final static String ID_USER="256767965";

    private RecyclerView mRecyclerView;
    private MyRecyclerViewerAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private ProgressBar progressBar;
    private Context mContext;
    private int nbr = 0;
    private String next;
    private boolean lock = false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.playlist_layout, container, false);
        mContext = getContext();
        mRecyclerView = (RecyclerView) rootView.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

// J'ai ajouté un scroll listener pour détecter la fin de la List et récupérer les éléments qui suivent de serveur
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                int totalItemCount = mLayoutManager.getItemCount();
                int lastVisibleItemPosition = mLayoutManager.findLastVisibleItemPosition();

                // Test si c'est le dernier élément de la liste chargé et si il reste des éléments à récupérer par le service web
                if (lastVisibleItemPosition + 1 == totalItemCount && Integer.valueOf(totalItemCount) < nbr) {
                    mRecyclerView.setEnabled(false);


                    if (next != null) {
                        if (!lock) {
                            invokeWS(next, null);
                            lock = true;
                        }
                    }


                }
            }
        });
        if (!lock) {
            invokeWS(My_URL, null);
            lock = true;
        }


        return rootView;

    }


    @Override
    public String toString() {
        return "Playlist";
    }

    //Fonction qui appele le web service Restful
    public void invokeWS(String url, RequestParams params) {
        AsyncHttpClient client = new AsyncHttpClient();
        client.setConnectTimeout(5000);
        client.get(url, params, new JsonHttpResponseHandler() {

            @Override
            public void onStart() {
                lock = true;
                progressBar.setVisibility(View.VISIBLE);
                mRecyclerView.setEnabled(false);
                super.onStart();
            }

            @Override
            public void onProgress(long bytesWritten, long totalSize) {

                progressBar.setProgress((int) totalSize);

                super.onProgress(bytesWritten, totalSize);
            }

            //Fonction qui se déclanche dans le cas de code retourner est 200 par le web service
            @Override
            public void onSuccess(int i, Header[] headers, JSONObject response) {
                progressBar.setVisibility(View.INVISIBLE);
                try {
                    next = response.getString("next");
                } catch (JSONException e) {
                    next = null;
                }
                try {
                    nbr = response.getInt("total");


                    if (response.getInt("total") != 0) {
                        MySharedPreference.edit(getContext(), MySharedPreference.CACHE, response.toString());
                        if (mAdapter == null)
                            mAdapter = new MyRecyclerViewerAdapter(response);
                        else
                            mAdapter.setData(response);
                        mAdapter.notifyDataSetChanged();
                        mRecyclerView.setAdapter(mAdapter);

                        Toast.makeText(mContext, "Les données sont actualisées", Toast.LENGTH_LONG).show();

                    } else {

                        Toast.makeText(mContext, "Erreur URL incorrect", Toast.LENGTH_LONG).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(mContext, "Erreur URL incorrect", Toast.LENGTH_LONG).show();
                }
                lock = false;
            }

            //Fonction qui se déclanche dans le cas de code retourner autre que 200
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progressBar.setVisibility(View.INVISIBLE);
                String result = MySharedPreference.getCache(getContext(), MySharedPreference.CACHE);

                if (!result.equals("non definit ")) {

                    try {
                        JSONObject response = new JSONObject(result);
                        if (mAdapter == null) {
                            try {
                                next = response.getString("next");
                            } catch (JSONException e) {
                                next = null;

                            }


                            mAdapter = new MyRecyclerViewerAdapter(response);
                            mRecyclerView.setAdapter(mAdapter);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                Toast.makeText(mContext, "Erreur de Connexion", Toast.LENGTH_LONG).show();
                Log.i("PlaylistFragment", "methode onFailure :: status =" + statusCode + " erreur= " + errorResponse);
                lock = false;
            }


        });

    }


}
