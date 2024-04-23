package ua.edu.ztu.student.zipz221_boyu.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

public class ViewBindingUtil {


    public interface Inflater<VB extends  ViewBinding> {
        VB inflate(LayoutInflater inflater);
    }

    public interface InflaterParent<VB extends  ViewBinding> {
        VB inflate(LayoutInflater inflater, ViewGroup parent, boolean attachToParent);
    }

    public interface Bind<VB extends  ViewBinding> {
        VB bind(View v);
    }
}
