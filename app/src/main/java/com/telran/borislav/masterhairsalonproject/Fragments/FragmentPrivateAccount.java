package com.telran.borislav.masterhairsalonproject.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.telran.borislav.masterhairsalonproject.Models.Master;
import com.telran.borislav.masterhairsalonproject.R;

import java.io.IOException;

/**
 * Created by Boris on 09.06.2017.
 */

public class FragmentPrivateAccount extends Fragment implements View.OnClickListener {
    private TextView nameMaster;
    private TextView lastNameMaster;
    private TextView addressMaster;
    private ImageView fotoMaster;
    public static final int REQUEST = 1;
    private FragmentPrivateAccountListener listener;

    public void setListener(FragmentPrivateAccountListener listener) {
        this.listener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_private_account, container, false);
        setHasOptionsMenu(true);
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        nameMaster = (TextView) view.findViewById(R.id.name_master_txt);
        lastNameMaster = (TextView) view.findViewById(R.id.last_name_master_txt);
        fotoMaster = (ImageView) view.findViewById(R.id.input_foto_master);
        addressMaster = (TextView) view.findViewById(R.id.address_master_txt);
        fotoMaster.setOnClickListener(this);
        listener.reDownloadMasterInfo();

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.edit, menu);  // Use filter.xml from step 1
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == R.id.action_filter){
            listener.editMasterInfo();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View view) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = null;

        switch (requestCode) {
            case REQUEST:
                if (resultCode == getActivity().RESULT_OK) {
                    Uri selectedImage = data.getData();
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    fotoMaster.setImageBitmap(bitmap);


                }

        }
    }

    public void fillInfo(Master master) {
        nameMaster.setText(master.getName());
        lastNameMaster.setText(master.getLastName());
        addressMaster.setText(master.getAddressMaster().getAddress());
    }

    public interface FragmentPrivateAccountListener{
        void reDownloadMasterInfo();
        void editMasterInfo();
    }
}