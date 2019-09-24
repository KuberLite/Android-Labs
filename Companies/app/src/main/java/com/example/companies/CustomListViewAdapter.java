package com.example.companies;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class CustomListViewAdapter extends ArrayAdapter<CompanyModel> {
    Context context;

    public CustomListViewAdapter(Context context, int resourceId,
                                 List<CompanyModel> items) {
        super(context, resourceId, items);
        this.context = context;
    }

    private class ViewHolder {
        TextView txtEmail;
        TextView txtPhone;
        TextView txtLocation;
        TextView txtLink;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        CompanyModel rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context
                .getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.singleview, null);
            holder = new ViewHolder();
            holder.txtEmail = (TextView) convertView.findViewById(R.id.emailSingle);
            holder.txtPhone = (TextView) convertView.findViewById(R.id.phoneSingle);
            holder.txtLocation = (TextView) convertView.findViewById(R.id.locationSingle);
            holder.txtLink = (TextView) convertView.findViewById(R.id.LinkSingle);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        holder.txtEmail.setText(rowItem.email);
        holder.txtPhone.setText(rowItem.phone);
        holder.txtLocation.setText(rowItem.location);
        holder.txtLink.setText(rowItem.socialNetworkLink);

        return convertView;
    }
}
