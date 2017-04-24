package com.example.douban.douban4_b.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LeoLu on 2016/10/27.
 */

public class AndroidTool {


    /**
     * 显示 Toast
     *
     * @param context
     * @param msg     消息
     */
    public static void showToast(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * 显示 Toast
     *
     * @param context
     * @param msg     消息
     */
    public static void showToast(Context context, int msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示 Toast
     *
     * @param context
     * @param msg     消息
     */
    public static void showToastLong(Activity context, String msg) {
        DisplayMetrics metrics = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        // 获取屏幕高度
        int height = metrics.heightPixels;
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_LONG);
        toast.setGravity(Gravity.TOP, 0, height / 10);
        toast.show();
    }

    /**
     * 显示 Toast
     *
     * @param fragment
     * @param msg      消息
     */
    public static void showToast(android.support.v4.app.Fragment fragment, String msg) {
        Toast.makeText(fragment.getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示 Toast
     *
     * @param fragment
     * @param msg      消息
     */
    public static void showToast(android.support.v4.app.Fragment fragment, int msg) {
        Toast.makeText(fragment.getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

    /**
     * 显示 Toast
     *
     * @param msg 消息
     */
    public static void showToast(Fragment fragment, String msg) {
        Toast.makeText(fragment.getActivity(), msg, Toast.LENGTH_SHORT).show();
    }

//    /**
//     * 显示 Toast
//     *
//     * @param msg 消息
//     */
//    public static void showToast(Fragment fragment, int msg) {
//        Toast.makeText(fragment.getActivity(), msg, Toast.LENGTH_SHORT).show();
//    }
//
//    public static void showCustomToast(Activity context, boolean isSelect, String message) {
//        Toast toast = new Toast(context);
//        LayoutInflater inflate = (LayoutInflater)
//                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View v = inflate.inflate(R.layout.custom_toast, null);
//        ImageView imageView = (ImageView) v.findViewById(R.id.img_icon);
//        imageView.setSelected(isSelect);
//        if (message != null) {
//            TextView tv = (TextView) v.findViewById(R.id.tip_title);
//            TextView m = (TextView) v.findViewById(R.id.tip_message);
//            tv.setText(message);
//            m.setText("");
//        }
//        DisplayMetrics metrics = new DisplayMetrics();
//        context.getWindowManager().getDefaultDisplay().getMetrics(metrics);
//        // 获取屏幕高度
//        int height = metrics.heightPixels;
//        toast.setGravity(Gravity.BOTTOM, 0, height / 8);
//        toast.setDuration(Toast.LENGTH_LONG);
//        toast.setView(v);
//        toast.show();
//    }

//    public static void showCustomToast(Activity context, boolean isSelect, int resId) {
//        showCustomToast(context, isSelect, context.getResources().getString(resId));
//    }


    public static void setViewHeight(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        if (null == layoutParams)
            return;
        layoutParams.width = width;
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }


    @SuppressLint("SimpleDateFormat")
    public static String returnTime() {
        SimpleDateFormat sDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd");
        return sDateFormat.format(new Date());
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDay(String time) {
        String showDay = null;
        String nowTime = returnTime();
        try {
            DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date now = df.parse(nowTime);
            Date date = df.parse(time);
            long l = now.getTime() - date.getTime();
            long day = l / (24 * 60 * 60 * 1000);
            if (day >= 365) {
                showDay = time.substring(0, 10);
            } else if (day >= 1 && day < 365) {
                showDay = time.substring(5, 10);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return showDay;
    }

    public static long getCodeTime(long timer) {
        return Math.abs(System.currentTimeMillis() - timer) >= 120000L ? 120000L : Math.abs(System.currentTimeMillis() - timer);
    }

    /**
     * 判断  editText 是否为空
     * true is empty , false otherwise
     *
     * @param editText ed
     * @return
     */
    public static boolean checkEditTextIsEmpty(EditText editText) {
        if (editText == null)
            return true;
        return "".equals(editText.getText().toString().trim());
    }

    /**
     * 判断  TextView 是否为空
     * true is empty , false otherwise
     *
     * @param textView textView
     * @return
     */
    public static boolean checkTextViewIsEmpty(TextView textView) {
        if (textView == null)
            return true;
        return "".equals(textView.getText().toString().trim());
    }

    /**
     * 验证邮箱
     *
     * @param email
     * @return
     */
    public static boolean checkEmail(String email) {
        boolean flag;
        try {
            String check = "^([a-z0-9A-Z]+[-|_|\\.]?)+[a-z0-9A-Z]@([a-z0-9A-Z]+(-[a-z0-9A-Z]+)?\\.)+[a-zA-Z]{2,}$";
            Pattern regex = Pattern.compile(check);
            Matcher matcher = regex.matcher(email);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    /**
     * 验证手机号码
     * mobileNumber
     *
     * @param mobileNumber
     * @return
     */
    public static boolean checkMobileNumber(String mobileNumber) {
        boolean flag;
        try {
//            Pattern regex = Pattern.compile("^(((13[0-9])|(15([0-3]|[5-9]))|(18[0,5-9]))\\d{8})|(0\\d{2}-\\d{8})|(0\\d{3}-\\d{7})$");
//            Pattern regex = Pattern.compile("^((13[0-9])|(15[^4,\\\\D])|(18[0,5-9]))\\\\d{8}$");
            Pattern regex = Pattern.compile("^\\d{11}$");
            Matcher matcher = regex.matcher(mobileNumber);
            flag = matcher.matches();
        } catch (Exception e) {
            flag = false;
        }
        return flag;
    }

    public static boolean checkUsername(EditText editText) {
        return editText != null && (checkMobileNumber(editText.getText().toString()) || checkEmail(editText.getText().toString()));
    }

    /**
     * 32位随机数
     *
     * @return
     */
    public static String getGuidRandom() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replace("-", "");
    }

    /**
     * 时间戳转为时间(年月日，时分秒)
     *
     * @param cc_time 时间戳
     * @return
     */
    public static String getStrTime(String cc_time) {
        String re_StrTime = null;
        //同理也可以转为其它样式的时间格式.例如："yyyy/MM/dd HH:mm"
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(cc_time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));

        return re_StrTime;
    }

    /**
     * 时间转换为时间戳
     *
     * @param timeStr 时间 例如: 2016-03-09
     * @param format  时间对应格式  例如: yyyy-MM-dd
     * @return
     */
    public static long getTimeStamp(String timeStr, String format) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = simpleDateFormat.parse(timeStr);
            long timeStamp = date.getTime();
            return timeStamp;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return 0;
    }
    public static String getMusicDuration(int duration){
        String time = "";
        String minte = "";
        String second = "";
        duration = duration/1000;
        minte = String.valueOf(duration/60);
        second = String.valueOf(duration%60);
        time = minte+":"+second;
        return time;
    }

    public static String getMusicName(String titlename){
        String title = titlename;
        String replaceTitle = "";
        if(title == null){
            title = "";
        }
        try {
            for(int i = 0; i<title.length();i++){
                char  str = title.charAt(i);
                if(str == '-' || str == '.'){
                    replaceTitle = title.substring(0,i);
                    break;
                }
            }
        } catch (Exception e) {
            replaceTitle = "";
        }

        return replaceTitle == "" ? title:replaceTitle;
    }

    public static String getMusicAuthor(String authorname){
        String author = authorname;
        String replaceAuthor = "";
        if(author == null){
            author = "";
        }
        if(author.contains("unknown")){
            author = "未知";
        }
        for(int i = 0;i<author.length();i++){
            char  str = author.charAt(i);
            if(str == '[' ){
                replaceAuthor = author.substring(0,i);
                break;
            }
        }
        return replaceAuthor ==""?author:replaceAuthor;
    }
    public static Bitmap createThumbFromUir(ContentResolver res, Uri albumUri) {
        InputStream in = null;
        Bitmap bmp = null;
        try {
            in = res.openInputStream(albumUri);
            BitmapFactory.Options sBitmapOptions = new BitmapFactory.Options();
            bmp = BitmapFactory.decodeStream(in, null, sBitmapOptions);
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bmp;
    }

    public static void loadIntoUseFitWidth(final Context context, final String imageUrl, int errorImageId, final ImageView imageView) {
        Glide.with(context)
                .load(imageUrl)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable> target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (imageView == null) {
                            return false;
                        }
                        if (imageView.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = imageView.getLayoutParams();
                        WindowManager wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
                        DisplayMetrics display= new DisplayMetrics();
                        wm.getDefaultDisplay().getMetrics(display);
                        params.height = display.heightPixels;
                        params.width = display.widthPixels;
//                        int vw = imageView.getWidth() - imageView.getPaddingLeft() - imageView.getPaddingRight();
//                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
//                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
//                        params.height = vh + imageView.getPaddingTop() + imageView.getPaddingBottom();
                        imageView.setLayoutParams(params);
                        return false;
                    }
                })
                .placeholder(errorImageId)
                .error(errorImageId)
                .into(imageView);
    }

}
