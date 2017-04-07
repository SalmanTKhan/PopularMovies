package com.popularmovies.vpaliy.popularmoviesapp.ui.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.Glide;
import com.popularmovies.vpaliy.domain.model.MovieCover;
import com.popularmovies.vpaliy.popularmoviesapp.R;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.Constants;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.events.ClickedMovieEvent;
import com.popularmovies.vpaliy.popularmoviesapp.ui.utils.wrapper.TransitionWrapper;
import com.squareup.otto.Bus;
import java.util.List;
import butterknife.BindView;
import android.support.annotation.NonNull;
import butterknife.ButterKnife;

public class RelatedMoviesAdapter extends RecyclerView.Adapter<RelatedMoviesAdapter.MovieViewHolder>{

    private static final String TAG=MoviesAdapter.class.getSimpleName();

    private final Bus eventBus;
    private final List<MovieCover> data;
    private final LayoutInflater inflater;
    private boolean hasBeenClicked;

    public RelatedMoviesAdapter(@NonNull Context context,@NonNull List<MovieCover> data, @NonNull Bus eventBus){
        this.eventBus=eventBus;
        this.data=data;
        this.inflater=LayoutInflater.from(context);

    }


    public class MovieViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener{

        @BindView(R.id.movieImage)
        ImageView image;

        @BindView(R.id.movieTitle)
        TextView title;

        @BindView(R.id.movieYear)
        TextView year;

        public MovieViewHolder(View itemView){
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(!hasBeenClicked) {
                hasBeenClicked=true;
                Bundle bundle = new Bundle();
                bundle.putInt(Constants.EXTRA_ID, data.get(getAdapterPosition()).getMovieId());
                eventBus.post(new ClickedMovieEvent(TransitionWrapper.wrap(image, bundle)));
            }
        }

        void bindData(){
            Glide.with(inflater.getContext())
                    .fromResource()
                    .load(R.drawable.poster)
                    .centerCrop()
                    .into(image);
            title.setText(data.get(getAdapterPosition()).getMovieTitle());
            String date= Integer.toString(data.get(getAdapterPosition()).getReleaseYear());
            title.setText(date);
            //install the rest of the data
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public MovieViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View root=inflater.inflate(R.layout.adapter_related_movie,parent,false);
        return new MovieViewHolder(root);
    }

    @Override
    public void onBindViewHolder(MovieViewHolder holder, int position) {
        holder.bindData();
    }
}