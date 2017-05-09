package com.letian.security.activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.letian.security.R;
import com.letian.security.bean.BaseFile;
import com.letian.security.utils.MyFileUtils;
import com.letian.security.utils.ToastUtil;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by acer on 2017/5/5.
 */

public class ProtectActivity extends BackBaseActivity {

    private Realm mRealm;
    private RecyclerView mRecyclerView;
    RealmResults<BaseFile> results1;
    private int mPostion;
    private BaseFile mBaseFile;

    private CommonAdapter<BaseFile> mAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.protect_layout);
        setTitle("已保护的文档");
        mRealm = Realm.getInstance(this);
        loadList();
        mRecyclerView = (RecyclerView) findViewById(R.id.protec_recycler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        //添加分割线
        mRecyclerView.addItemDecoration(new DividerItemDecoration(
                this, DividerItemDecoration.HORIZONTAL));
        mAdapter = new CommonAdapter<BaseFile>(this, R.layout.item_list, results1) {
            @Override
            protected void convert(ViewHolder holder, final BaseFile baseFile, final int position) {
                holder.setText(R.id.protect_file_name, baseFile.getFileName());
                holder.getView(R.id.protect_file_item).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mPostion = position;
                        mBaseFile = baseFile;
                        ShowDialog(baseFile.getFileName(), position);
                    }
                });
            }
        };
        mRecyclerView.setAdapter(mAdapter);
    }

    public RealmResults<BaseFile> loadList() {
        results1 = mRealm.where(BaseFile.class).findAll();
        for (BaseFile baseFile : results1) {
            Log.e("FanLeTian", baseFile.getFileName());
            Log.e("FanLeTian", baseFile.getFilePath());
        }
        return results1;
    }


    private void ShowDialog(String fileName, int postion) {
        //创建退出对话框
        AlertDialog.Builder isExit = new AlertDialog.Builder(this);
        //设置对话框消息
        isExit.setMessage("您确定要对" + fileName + "进行去保护吗？");
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
                    MyFileUtils.unProtectFile(mBaseFile.getFilePath(), mBaseFile.getFileName());
                    //先查找到数据
                    final RealmResults<BaseFile> userList = mRealm.where(BaseFile.class).findAll();
                    mRealm.executeTransaction(new Realm.Transaction() {
                        @Override
                        public void execute(Realm realm) {
                            userList.remove(mPostion);
                        }
                    });
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            ToastUtil.showToast(ProtectActivity.this, "去保护成功");
                        }
                    });
                    loadList();
                    mAdapter.notifyDataSetChanged();
                    break;
                case AlertDialog.BUTTON_NEGATIVE:// "取消"第二个按钮取消对话框
                    break;
                default:
                    break;
            }
        }
    };


}
