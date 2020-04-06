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
import com.cp.rxjava.activity.BaseActivity;

import java.util.List;
import java.util.Random;

public class MainActivity extends BaseActivity {
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
                .filter(post -> {
                    Log.e(TAG, "filter id =: " + post.getId());
                    return post.getId() % 2 == 0;
                })
                .flatMap((Function<Post, ObservableSource<Post>>) post -> getCommentsObservable(post))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Post>() {
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

                    }

                    @Override
                    public void onComplete() {
                        loaded();
                    }
                });


//        getPostObservable()
//                .flatMap((Function<Post, ObservableSource<Post>>) post -> getCommentsObservable(post))
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(post -> updatePost(post));
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
        disposables.clear();
    }
}
