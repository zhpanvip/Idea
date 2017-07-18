package com.cypoem.idea.activity;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.cypoem.idea.R;
import com.cypoem.idea.module.bean.JsonBean;
import com.cypoem.idea.utils.GetJsonDataUtil;
import com.cypoem.idea.view.SexView;
import com.google.gson.Gson;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.OnClick;
import de.hdodenhof.circleimageview.CircleImageView;

public class EditInfoActivity extends BaseActivity {

    private static final int INTRODUCE = 100;
    private static final int SIGN = 101;
    private static final int PEN_NAME = 102;
    private static final int DIALOG_ID = 200;
    @BindView(R.id.iv_head_pic)
    CircleImageView ivHeadPic;
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
    private int year, month, day;
    /**
     * 省市联动数据
     */
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
   // private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private Thread thread;
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;
    private boolean isLoaded = false;
    private int percent;

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
                tvIntroduce.setText(data.getStringExtra("result"));
                break;
            case SIGN:
                tvSign.setText(data.getStringExtra("result"));
                break;
            case PEN_NAME:
                tvPenName.setText(data.getStringExtra("result"));
                break;
        }
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

    DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            tvDate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
        }
    };

    private void editSex() {
        View dialog = createDialog(R.layout.layout_select_sex, false);
        SexView sexView = (SexView) dialog.findViewById(R.id.sex_view);
        sexView.setCenterColor(Color.parseColor("#FFFFFF"));
        SeekBar seekBar = (SeekBar) dialog.findViewById(R.id.seek_bar);
        Button btnConfirm= (Button) dialog.findViewById(R.id.btn_confirm);
        setListener(seekBar,sexView);
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mSexView.setMalePercent(1 - percent / 100.0);
                dismissDialog();
            }
        });

    }

    private void setListener(SeekBar seekBar,SexView sexView) {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                percent =progress;
                sexView.setMalePercent(1 - progress / 100.0);
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

    private void editHeadPic() {

    }


    @OnClick({R.id.rl_head_pic, R.id.rl_pen_name, R.id.rl_sex, R.id.rl_birthday,
            R.id.rl_address, R.id.rl_sign, R.id.rl_introduce})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_head_pic:
                editHeadPic();
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

            /**
             * 添加地区数据
             */
         //   options3Items.add(Province_AreaList);
        }

        // mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

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
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String province = options1Items.get(options1).getPickerViewText();
                String selectedResult;
                if("北京市".equals(province)||"天津市".equals(province)||"上海市".equals(province)
                        ||"香港".equals(province)||"澳门".equals(province)||"深圳市".equals(province)){
                    selectedResult = options1Items.get(options1).getPickerViewText();
                }else {
                    selectedResult = options1Items.get(options1).getPickerViewText() +" "+
                            options2Items.get(options1).get(options2);
                }
                tvAddress.setText(selectedResult);
            }
        }).setTitleText("城市选择")
                .setDividerColor(Color.GRAY)
                .setTextColorCenter(Color.GRAY) //设置选中项文字颜色
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(options1Items, options2Items);//二级选择器
        pvOptions.show();
    }

}
