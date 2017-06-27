package com.letian.security.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;

import com.letian.security.R;

import java.util.ArrayList;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;

/**
 * Created by acer on 2017/6/3.
 */

public class SendDocActivity extends BackBaseActivity {

    private Button send;

    private ArrayList<String> photoPaths = new ArrayList<>();
    private ArrayList<String> docPaths = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.senddoc_layout);
        setTitle("文档分享");
        send = (Button) findViewById(R.id.bt_send_doc);
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FilePickerBuilder.getInstance().setMaxCount(10)
                        .setSelectedFiles(docPaths)
                        .setActivityTheme(R.style.FilePickerTheme)
                        .pickFile(SendDocActivity.this);
            }
        });


    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode)
        {
            case FilePickerConst.REQUEST_CODE_PHOTO:
                if(resultCode== Activity.RESULT_OK && data!=null)
                {
                    photoPaths = new ArrayList<>();
                    photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
                }
                break;
            case FilePickerConst.REQUEST_CODE_DOC:
                if(resultCode== Activity.RESULT_OK && data!=null)
                {
                    docPaths = new ArrayList<>();
                    docPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_DOCS));
                }
                break;
        }

        if (docPaths.size() > 0) {
            ArrayList<Uri> imageUris = new ArrayList<>();
            for (String url : docPaths) {
                Uri uri1 = Uri.parse(url);
                imageUris.add(uri1);
            }
            Intent mulIntent = new Intent(Intent.ACTION_SEND_MULTIPLE);
            mulIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
            mulIntent.setType("*/*");
            startActivity(Intent.createChooser(mulIntent,"多文件分享"));
        }
    }
}
