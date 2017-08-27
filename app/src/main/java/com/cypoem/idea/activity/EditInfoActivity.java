package com.cypoem.idea.activity;

import android.Manifest;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.airong.core.utils.ImageLoaderUtil;
import com.airong.core.utils.LogUtils;
import com.bigkoo.pickerview.OptionsPickerView;
import com.cypoem.idea.R;
import com.cypoem.idea.constants.Constants;
import com.cypoem.idea.module.BasicResponse;
import com.cypoem.idea.module.bean.JsonBean;
import com.cypoem.idea.module.bean.UserBean;
import com.cypoem.idea.net.DefaultObserver;
import com.cypoem.idea.net.IdeaApi;
import com.cypoem.idea.net.IdeaApiService;
import com.cypoem.idea.utils.GetJsonDataUtil;
import com.cypoem.idea.utils.UserInfoTools;
import com.cypoem.idea.view.SexView;
import com.google.gson.Gson;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.callback.BitmapLoadCallback;
import com.yalantis.ucrop.model.ExifInfo;
import com.yalantis.ucrop.util.BitmapLoadUtils;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static com.cypoem.idea.constants.Constants.REQUEST_SELECT_PICTURE;
import static com.cypoem.idea.constants.Constants.SAMPLE_CROPPED_IMAGE_NAME;
import static com.cypoem.idea.constants.Constants.TAG;

public class EditInfoActivity extends BaseActivity {
    //  个人简介
    protected static final int INTRODUCE = 100;
    //  格言
    protected static final int SIGN = 101;
    //  笔名
    protected static final int PEN_NAME = 102;
    // 头像
    private static final int HEAD_PIC = 103;
    //  性别
    private static final int SEX = 104;
    //  生日
    private static final int BIRTHDAY = 105;
    //  地址
    private static final int ADDRESS = 106;

    //  Dialog id
    private static final int DIALOG_ID = 200;
    @BindView(R.id.iv_head_pic)
    ImageView ivHeadPic;
    @BindView(R.id.rl_head_pic)
    RelativeLayout rlHeadPic;
    @BindView(R.id.rl_pen_name)
    RelativeLayout rlPenName;
    @BindView(R.id.rl_sex)
    RelativeLayout rlSex;
    @BindView(R.id.rl_birthday)
    RelativeLayout rlBirthday;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.rl_address)
    RelativeLayout rlAddress;
    @BindView(R.id.tv_sign)
    TextView tvSign;
    @BindView(R.id.rl_sign)
    RelativeLayout rlSign;
    @BindView(R.id.tv_introduce)
    TextView tvIntroduce;
    @BindView(R.id.rl_introduce)
    RelativeLayout rlIntroduce;
    @BindView(R.id.tv_pen_name)
    TextView tvPenName;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.sex_view)
    SexView mSexView;
    @BindView(R.id.tv_user_id)
    TextView tvUserId;
    private int year, month, day;
    /**
     * 省市联动数据
     */
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private double percent = 0.5;
    private String sign = "";
    private String penName = "";
    private String sex = "0.5";
    private String birthday = "";
    private String address = "";
    private String introduction = "";
    private String picPath;
    private boolean isSexChanged;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_edit_info;
    }

    @Override
    protected void init(Bundle savedInstanceState) {
        initDate();
    }

    private void initDate() {
        final Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        setData();
    }

    private void setData() {
        setToolBarTitle("编辑个人资料");
        setUserInfo();
    }

    //  初始化设置用户信息
    private void setUserInfo() {
        UserBean user = UserInfoTools.getUser(this);
        penName = user.getPen_name();
        sign = user.getDictum();
        sex = user.getSex();
        mSexView.setMalePercent(Double.parseDouble(sex));
        birthday = user.getBirthday() == null ? "" : user.getBirthday();
        address = user.getAddress() == null ? "" : user.getAddress();
        introduction = user.getIntroduction() == null ? "" : user.getIntroduction();
        tvSign.setText(sign);
        tvPenName.setText(penName);
        tvDate.setText(birthday);
        tvAddress.setText(address);
        tvIntroduce.setText(introduction);
        tvUserId.setText(user.getUserId());
        ImageLoaderUtil.loadCircleImg(ivHeadPic, IdeaApiService.HOST + user.getIcon(), R.drawable.head_pic);
    }

    private void refreshUserInfo() {
        UserInfoTools.setPenName(this, penName);
        UserInfoTools.setSign(this, sign);
        UserInfoTools.setSex(this, sex);
        UserInfoTools.setBirthday(this, birthday);
        UserInfoTools.setAddress(this, address);
        UserInfoTools.setAudthorBrief(this, introduction);
        //UserInfoTools.setHeadPic(this, picPath);
        mSexView.setMalePercent(Double.parseDouble(sex));
        setUserInfo();
    }

    public static void start(Context context) {
        Intent intent = new Intent(context, EditInfoActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case INTRODUCE:
                introduction = data.getStringExtra("result");
                updateUserInfo();
                break;
            case SIGN:
                sign = data.getStringExtra("result");
                updateUserInfo();
                break;
            case PEN_NAME:
                penName = data.getStringExtra("result");
                updateUserInfo();
                break;
            case UCrop.RESULT_ERROR:
                handleCropError(data);
                break;
            case RESULT_OK://
                selectPicResult(requestCode, data);
                break;
        }
    }

    /**********************************************选择头像部分*********************************************************/
    //  选择图片结果处理
    private void selectPicResult(int requestCode, Intent data) {
        if (requestCode == REQUEST_SELECT_PICTURE) {
            final Uri selectedUri = data.getData();
            if (selectedUri != null) {
                startCropActivity(data.getData());
            } else {
                Toast.makeText(EditInfoActivity.this, R.string.toast_cannot_retrieve_selected_image, Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == UCrop.REQUEST_CROP) {
            handleCropResult(data);
        }
    }

    //  处理选择图片错误的情况
    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e(TAG, "handleCropError: ", cropError);
            showToast(cropError.getMessage());
        } else {
            showToast(R.string.toast_unexpected_error);
        }
    }

    /**
     * 剪切成功
     *
     * @param result
     */
    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            LogUtils.e(resultUri.getPath());
            picPath = resultUri.getPath();
            //  向服务器提交头像数据
            postHeadPic(HEAD_PIC, "");
        } else {
            showToast(R.string.toast_cannot_retrieve_cropped_image);
        }
    }


    private void startCropActivity(@NonNull Uri uri) {
        String destinationFileName = SAMPLE_CROPPED_IMAGE_NAME;
        destinationFileName += ".png";
        UCrop uCrop = UCrop.of(uri, Uri.fromFile(new File(getCacheDir(), destinationFileName)));
        uCrop = uCrop.withAspectRatio(1, 1);
        uCrop = advancedConfig(uCrop);
        uCrop.start(EditInfoActivity.this);
    }

    /**
     * Sometimes you want to adjust more options, it's done via {@link com.yalantis.ucrop.UCrop.Options} class.
     *
     * @param uCrop - ucrop builder instance
     * @return - ucrop builder instance
     */
    private UCrop advancedConfig(@NonNull UCrop uCrop) {
        UCrop.Options options = new UCrop.Options();
        options.setCompressionFormat(Bitmap.CompressFormat.PNG);

        options.setCompressionQuality(40);

        options.setHideBottomControls(true);
        options.setFreeStyleCropEnabled(false);
        return uCrop.withOptions(options);
    }

    private void editIntroduce() {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("what", "个人简介");
        intent.putExtra("type", INTRODUCE);
        startActivityForResult(intent, INTRODUCE);
    }

    private void editSign() {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("what", "格言");
        intent.putExtra("type", SIGN);
        startActivityForResult(intent, SIGN);
    }

    private void editAddress() {
        ShowPickerView();
    }

    private void editBirthday() {
        showDialog(DIALOG_ID);
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        if (id == DIALOG_ID)
            return new DatePickerDialog(this, pickerListener, year, month, day);
        return null;
    }

    //  选择时间对话框
    DatePickerDialog.OnDateSetListener pickerListener = (DatePicker view, int year, int month, int dayOfMonth) -> {
        birthday = year + "-" + (month + 1) + "-" + dayOfMonth;
        updateUserInfo();
    };

    //  编辑性别
    private void editSex() {
        double dSex = Double.parseDouble(sex);
        View dialog = createDialog(R.layout.layout_select_sex, false);
        SexView sexView = (SexView) dialog.findViewById(R.id.sex_view);
        sexView.setCenterColor(Color.parseColor("#FFFFFF"));
        sexView.setMalePercent(dSex);
        SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.seek_bar);
        seekBar.setProgress((int) (dSex * 100));
        Button btnConfirm = (Button) dialog.findViewById(R.id.btn_confirm);
        setListener(seekBar, sexView);
        btnConfirm.setOnClickListener((View v) -> {
            if (isSexChanged) {
                sex = String.valueOf(percent);
                updateUserInfo();
            }
            isSexChanged = false;
            dismissDialog();
        });
    }

    private void setListener(SeekBar seekBar, SexView sexView) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                isSexChanged = true;
                percent = progress / 100.0;
                sexView.setMalePercent(percent);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    private void editPenName() {
        Intent intent = new Intent(this, EditActivity.class);
        intent.putExtra("what", "笔名");
        intent.putExtra("type", PEN_NAME);
        startActivityForResult(intent, PEN_NAME);
    }

    private void pickFromGallery() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermission(Manifest.permission.READ_EXTERNAL_STORAGE,
                    getString(R.string.permission_read_storage_rationale),
                    REQUEST_STORAGE_READ_ACCESS_PERMISSION);
        } else {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            startActivityForResult(Intent.createChooser(intent, getString(R.string.label_select_picture)), REQUEST_SELECT_PICTURE);
        }
    }


    @OnClick({R.id.rl_head_pic, R.id.rl_pen_name, R.id.rl_sex, R.id.rl_birthday,
            R.id.rl_address, R.id.rl_sign, R.id.rl_introduce})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_head_pic:
                pickFromGallery();
                break;
            case R.id.rl_pen_name:
                editPenName();
                break;
            case R.id.rl_sex:
                editSex();
                break;
            case R.id.rl_birthday:
                editBirthday();
                break;
            case R.id.rl_address:
                editAddress();
                break;
            case R.id.rl_sign:
                editSign();
                break;
            case R.id.rl_introduce:
                editIntroduce();
                break;
        }
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {

                    for (int d = 0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);
        }
    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
            //  mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }

    private void ShowPickerView() {// 弹出选择器
        initJsonData();
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, (int options1, int options2, int options3, View v) -> {
            //返回的分别是三个级别的选中位置
            String province = options1Items.get(options1).getPickerViewText();
            String selectedResult;
            if ("北京市".equals(province) || "天津市".equals(province) || "上海市".equals(province)
                    || "香港".equals(province) || "澳门".equals(province) || "深圳市".equals(province)) {
                selectedResult = options1Items.get(options1).getPickerViewText();
            } else {
                selectedResult = options1Items.get(options1).getPickerViewText() + " " +
                        options2Items.get(options1).get(options2);
            }
            address = selectedResult;
            updateUserInfo();
        }).setTitleText("城市选择")
                .setDividerColor(Color.GRAY)
                .setTextColorCenter(Color.GRAY) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.show();
    }

    //  更换头像
    private void postHeadPic(int requestCode, String result) {

        File file = new File(picPath);
        RequestBody imageBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);

        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("user_id", UserInfoTools.getUserId(this))
                .addFormDataPart("type", Constants.HEAD_PIC)
                .addFormDataPart("uploadFile", "head_pic", imageBody);
        List<MultipartBody.Part> parts = builder.build().parts();
        IdeaApi.getApiService()
                .updateHeadPic(parts)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(this, true) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        EventBus.getDefault().post(new UpdateInfoSuccess());
                        showToast(response.getMsg());
                        String url = response.getResult().toString();
                        UserInfoTools.setHeadPic(EditInfoActivity.this, url);
                        ImageLoaderUtil.loadCircleImg(ivHeadPic, IdeaApiService.HOST + url, R.drawable.head_pic);
                    }
                });
    }

    private void updateUserInfo() {
        Map<String, String> map = new HashMap<>();
        map.put("user_id", UserInfoTools.getUserId(this));
        map.put("dictum", sign);
        map.put("pen_name", penName);
        map.put("sex", sex);
        map.put("birthday", birthday);
        map.put("address", address);
        map.put("introduction", introduction);
        IdeaApi.getApiService()
                .updateUserInfo(map)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DefaultObserver<BasicResponse>(this, false) {
                    @Override
                    public void onSuccess(BasicResponse response) {
                        showToast(response.getMsg());
                        refreshUserInfo();
                        EventBus.getDefault().post(new UpdateInfoSuccess());
                    }
                });


    }

    public static class UpdateInfoSuccess {

    }

}
