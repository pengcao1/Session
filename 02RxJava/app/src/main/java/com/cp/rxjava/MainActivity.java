package com.cp.rxjava;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

import android.os.Bundle;
import android.util.Log;

import com.cp.rxjava.models.Post;
import com.cp.rxjava.requests.ServiceGenerator;

import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private RecyclerView recyclerView;

    private CompositeDisposable disposables = new CompositeDisposable();
    private RecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recycler_view);

        initRecyclerView();

        getPostObservable()
                .flatMap((Function<Post, ObservableSource<Post>>) post -> getCommentsObservable(post))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Post>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Post post) {
                        updatePost(post);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


        getPostObservable()
                .flatMap((Function<Post, ObservableSource<Post>>) post -> getCommentsObservable(post))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(post -> updatePost(post));
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
                });
    }

    private Observable<Post> getCommentsObservable(final Post post) {
        return ServiceGenerator.getRequestApi()
                .getComments(post.getId())
                .map(comments -> {
                    int delay = ((new Random()).nextInt(10) + 1) * 1000;
                    Thread.sleep(delay);
                    Log.d(TAG, "apply: sleeping thread " + Thread.currentThread().getName() + " for " + delay + "ms");
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
        disposables.clear();
    }
}
