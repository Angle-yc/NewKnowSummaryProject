package com.angle.hshb.newknowsummaryproject.fragments;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.angle.hshb.newknowsummaryproject.R;
import com.angle.hshb.newknowsummaryproject.activitys.NotificationActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 系统通知栏练习
 */
public class NotificationFragment extends Fragment {

    Unbinder unbinder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View ret = inflater.inflate(R.layout.fragment_notification, container, false);
        unbinder = ButterKnife.bind(this, ret);
        return ret;
    }


    @OnClick(R.id.btn_notification)
    public void onViewClicked() {
        Intent intent = new Intent(getContext(), NotificationActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(getContext(),0,intent,0);
        NotificationManager manager = (NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(getContext())
                .setContentTitle("This is content titile")//通知标题
                .setContentText("This is content text")//通知的内容
                .setWhen(System.currentTimeMillis())//通知时间
                .setSmallIcon(R.drawable.tree)//通知小图标，在通知栏显示的图标
                .setLargeIcon(BitmapFactory.decodeResource
                        (getResources(),R.mipmap.ic_launcher_round))//通知大图标，在下拉下来通知栏的时候，通知布局上显示的图标
                .setContentIntent(pendingIntent)//点击后跳转的页面
                .setAutoCancel(true)//用户点击后就会消失
//                .setVibrate(new long[]{0,1000,1000,1000,1000,1000})//让手机震动，下标0的值表示手机静止时长，下标1手机震动时长，下标2,手机静止时长，以此类推
//                .setLights(Color.GREEN,1000,1000)//通知的LED，1.灯光颜色;2.灯亮时长;3.灯暗时长
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(
                        BitmapFactory.decodeResource(getResources(),R.drawable.background)
                ))//显示一张大图
//                .setStyle(new NotificationCompat.BigTextStyle().bigText("Learn how to build notifications, " +
//                        "send and sync data, and use voice actions. " +
//                        "Get ths official Android IDE and developer tools to build apps for Android"))
                .setPriority(NotificationCompat.PRIORITY_MAX)//设置通知的重要程度
                .build();
        manager.notify(1,notification);//参数一，通知的ID，可以根据这个id将这个通知手动关闭
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
