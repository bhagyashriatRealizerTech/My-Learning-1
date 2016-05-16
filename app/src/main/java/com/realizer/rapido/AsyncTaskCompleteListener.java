package com.realizer.rapido;

/**
 * Created by Win on 10/26/2015.
 */
interface AsyncTaskCompleteListener<T> {

    public void onTaskComplete(T result,int pos);
}
