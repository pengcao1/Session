package com.cp.rxjava.activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import android.os.Bundle;
import android.util.Log;

import com.cp.rxjava.R;
import com.cp.rxjava.RecyclerAdapter;
import com.cp.rxjava.models.Post;
import com.cp.rxjava.requests.ServiceGenerator;

import java.util.List;
import java.util.Random;

public class ActivityGetNetworkData extends BaseActivity {
    private static final String TAG = ActivityGetNetworkData.class.getSimpleName();
    private RecyclerView recyclerView;

    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);

        initRecyclerView();

        getPostObservable()
                .filter(post -> {
                    Log.e(TAG, "onCreate: " + post.getId());
                    return post.getId() < 7;
                })
                .flatMap((Function<Post, ObservableSource<Post>>) post -> getCommentsObservable(post))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getObserver());
    }

    private Consumer<Post> getConsumer() {
        return post -> updatePost(post);
    }

    private Observer<Post> getObserver() {
        return new Observer<Post>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Post post) {
                Log.e(TAG, "onNext: id=" + post.getId());
                updatePost(post);
                loading(post.getTitle());
            }

            @Override
            public void onError(Throwable e) {
                loaded();
            }

            @Override
            public void onComplete() {
                loaded();
            }
        };
    }

    private void updatePost(Post post) {
        adapter.updatePost(post);
    }

    private Observable<Post> getPostObservable() {
        return ServiceGenerator
                .getRequestApi()
                .getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .flatMap((Function<List<Post>, ObservableSource<Post>>) posts -> {
                    adapter.setPosts(posts);
                    return Observable.fromIterable(posts).subscribeOn(Schedulers.io());
                }).observeOn(AndroidSchedulers.mainThread());
    }

    private Observable<Post> getCommentsObservable(final Post post) {
        return ServiceGenerator.getRequestApi()
                .getComments(post.getId())
                .map(comments -> {
                    int delay = ((new Random()).nextInt(10) + 1) * 1000;
                    Log.d(TAG, "apply: sleeping thread " + Thread.currentThread().getName() + " for " + delay + "ms");
                    Thread.sleep(delay);
                    Log.e(TAG, "getCommentsObservable: post id=" + post.getId());
                    post.setComments(comments);
                    return post;
                }).subscribeOn(Schedulers.io());
    }

    private void initRecyclerView() {
        adapter = new RecyclerAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
