package com.letian.security.activity;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.letian.security.R;
import com.letian.security.bean.BaseFile;
import com.letian.security.utils.MyFileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import io.realm.Realm;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener , View.OnClickListener{

    private static final String TAG = "MainActivity";
    private int MAX_ATTACHMENT_COUNT = 10;
    private ArrayList<String> photoPaths = new ArrayList<>();
    private ArrayList<String> docPaths = new ArrayList<>();

    private Realm mRealm;
    private Button protect;
    private Button unProtect;
    private Button accoutMana;
    private Button systemSet;


    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRealm = Realm.getInstance(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        protect = (Button) findViewById(R.id.bt_protected);
        unProtect = (Button) findViewById(R.id.bt_unprotect);
        accoutMana = (Button) findViewById(R.id.bt_account_manager);
        systemSet = (Button) findViewById(R.id.bt_system_settting);
        protect.setOnClickListener(this);
        unProtect.setOnClickListener(this);
        accoutMana.setOnClickListener(this);
        systemSet.setOnClickListener(this);
        setSupportActionBar(toolbar);
        ActivityCompat.requestPermissions(MainActivity.this, PERMISSIONS_STORAGE, 100);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "欢迎来到Security,您可以去使用了。", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return false;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_slideshow) {
//            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//            intent.setType("*/*");//设置类型，我这里是任意类型，任意后缀的可以这样写。
//            intent.addCategory(Intent.CATEGORY_OPENABLE);
//            startActivityForResult(intent,1);
            Intent intent = new Intent(this, ProtectActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_manage) {
            FilePickerBuilder.getInstance().setMaxCount(10)
                    .setSelectedFiles(docPaths)
                    .setActivityTheme(R.style.FilePickerTheme)
                    .pickFile(this);
        } else if (id == R.id.nav_share) {
            Intent intent = new Intent(this, AccountActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_send) {
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
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
        Log.e("FanLetian", docPaths.toString());
        ShowDialog();
    }

    public void savePath(List<String> list) {
        for (String path : list) {
            File file = new File(path);
            mRealm.beginTransaction();
            BaseFile baseFile = mRealm.createObject(BaseFile.class);
            baseFile.setFileName(file.getName());
            baseFile.setFilePath(file.getParentFile().getAbsolutePath() + "/" + "." + file.getName() + "0");
            mRealm.commitTransaction();
        }
    }


    private void ShowDialog() {
        //创建退出对话框
        AlertDialog.Builder isExit = new AlertDialog.Builder(this);
        //设置对话框消息
        isExit.setMessage("您确定要对选中的这些文件进行加保护吗？");
        // 添加选择按钮并注册监听
        isExit.setPositiveButton("确定", listener);
        isExit.setNegativeButton("取消", listener);
        //对话框显示
        isExit.show();
    }

    /**监听对话框里面的button点击事件*/
    DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener()
    {
        public void onClick(DialogInterface dialog, int which)
        {
            switch (which)
            {
                case AlertDialog.BUTTON_POSITIVE:// "确认"按钮退出程序
                    savePath(docPaths);
                    MyFileUtils.protectFileList(docPaths);
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_protected:
                Intent intent = new Intent(this, ProtectActivity.class);
                startActivity(intent);
                break;
            case R.id.bt_unprotect:
                FilePickerBuilder.getInstance().setMaxCount(10)
                        .setSelectedFiles(docPaths)
                        .setActivityTheme(R.style.FilePickerTheme)
                        .pickFile(this);
                break;
            case R.id.bt_account_manager:
                Intent intent1 = new Intent(this, AccountActivity.class);
                startActivity(intent1);
                break;
            case R.id.bt_system_settting:
                Intent intent2 = new Intent(this, SettingActivity.class);
                startActivity(intent2);
                break;
        }
    }
}
