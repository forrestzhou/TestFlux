package com.forrest.testflux.dispatcher;


import com.forrest.testflux.flux.action.base.Action;
import com.forrest.testflux.flux.store.Store;

/**
 * Flux的Dispatcher模块
 * Forrest
 */
public class Dispatcher {

    public final String TAG=Dispatcher.class.getSimpleName();

    private static Dispatcher instance;

    private volatile Store currentStore;

    public static Dispatcher get() {
        if (instance == null) {
            instance = new Dispatcher();
        }
        return instance;
    }

    Dispatcher() {}

    public void register(Object subscriber,final Store store) {

        if(store!=null){
            store.register(subscriber);
        }
        this.currentStore=store;
    }

    public void unregister(Object subscriber,final Store store) {
        if(store!=null){
            store.unRegister(subscriber);
        }
        if(currentStore==store){
            currentStore=null;
        }
    }

    public void dispatch(Action action) {
        post(action);
    }

    /**
     * 用于防止跳转到其他页面，再回到当前页面的时候，currentStore不一致
     * @param subscriber
     * @param store
     */
    public void reCheckStore(Object subscriber,final Store store){
        if(currentStore!=store){
            register(subscriber,store);
        }
    }


    private void post(final Action action) {
        if(currentStore!=null){
            currentStore.onAction(action);
        }

    }
}
