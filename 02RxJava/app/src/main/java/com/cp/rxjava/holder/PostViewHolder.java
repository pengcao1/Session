package com.cp.rxjava.holder;

import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.cp.rxjava.R;
import com.cp.rxjava.models.Post;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class PostViewHolder extends RecyclerView.ViewHolder {
    private TextView title;
    private TextView numComments;
    private ProgressBar progressBar;

    public PostViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        numComments = itemView.findViewById(R.id.num_comments);
        progressBar = itemView.findViewById(R.id.progress_bar);
    }

    public void bind(Post post) {
        title.setText(post.getTitle());
        if (post.getComments() == null) {
            startProgressBar();
            numComments.setText("");
        } else {
            stopProgressBar();
            numComments.setText(String.valueOf(post.getComments().size()));
        }
    }

    private void startProgressBar() {
        showProgressBar(true);
    }

    private void stopProgressBar() {
        showProgressBar(false);
    }

    private void showProgressBar(boolean showProgressBar) {
        progressBar.setVisibility(showProgressBar ? View.VISIBLE : View.GONE);
    }
}
