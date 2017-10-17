package com.sinha.saarth.restmycase.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sinha.saarth.restmycase.R;
import com.sinha.saarth.restmycase.model.MyDataModel;

import java.util.List;

public class MyArreyAdapter extends ArrayAdapter<MyDataModel> {

    List<MyDataModel> modelList;
    Context context;
    private LayoutInflater mInflater;

    // Constructors
    public MyArreyAdapter(Context context, List<MyDataModel> objects) {
        super(context, 0, objects);
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        modelList = objects;
    }

    @Override
    public MyDataModel getItem(int position) {
        return modelList.get(position);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder vh;
        if (convertView == null) {
            View view = mInflater.inflate(R.layout.layout_row_view, parent, false);
            vh = ViewHolder.create((RelativeLayout) view);
            view.setTag(vh);
        } else {
            vh = (ViewHolder) convertView.getTag();
        }

        MyDataModel item = getItem(position);
        vh.textViewSection.setText(item.getSection());
        vh.textViewDefinition.setText(item.getDefinition());
        vh.textViewDescription.setText(item.getDescription());

        return vh.rootView;
    }

    private static class ViewHolder {
        public final RelativeLayout rootView;

        public final TextView textViewSection;
        public final TextView textViewDefinition;
        public final TextView textViewDescription;

        private ViewHolder(RelativeLayout rootView, TextView textViewSection,TextView textViewDefinition,TextView textViewDescription) {
            this.rootView = rootView;
            this.textViewSection= textViewSection;
            this.textViewDefinition=textViewDefinition;
            this.textViewDescription=textViewDescription;
        }

        public static ViewHolder create(RelativeLayout rootView) {

            TextView textViewSection = (TextView) rootView.findViewById(R.id.textViewSection);
            TextView textViewDefinition = (TextView) rootView.findViewById(R.id.textViewDefinition);
            TextView textViewDescription = (TextView) rootView.findViewById(R.id.textViewDescription);
            return new ViewHolder(rootView,textViewSection, textViewDefinition,textViewDescription);
        }
    }
}
