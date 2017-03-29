package com.mom.project.pleasemom.activity;

import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.google.firebase.iid.FirebaseInstanceId;
import com.mom.project.pleasemom.MySQLiteOpenHelper;
import com.mom.project.pleasemom.R;
import com.mom.project.pleasemom.adapter.BoardListAdapter;
import com.mom.project.pleasemom.adapter.BoardListCheckingAdapter;
import com.mom.project.pleasemom.adapter.NotiListAdapter;
import com.mom.project.pleasemom.adapter.ReviewGridAdapter;
import com.mom.project.pleasemom.adapter.UserInfoAdapter;
import com.mom.project.pleasemom.common.CommonJSONParse;
import com.mom.project.pleasemom.dto.DataDTO;
import com.mom.project.pleasemom.dto.MemberDTO;
import com.mom.project.pleasemom.dto.ReviewDTO;
import com.mom.project.pleasemom.listener.BackPressCloseListener;
import com.mom.project.pleasemom.listener.SearchOnClickListener;
import com.mom.project.pleasemom.volley.CustomJsonRequest;
import com.mom.project.pleasemom.volley.CustomVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    static Context ctx;
    static Context thisCtx;
    static String mem_num;
    static TextView txtUserInfoName;
    static ImageView imgUserInfoProfile;
    static ListView userInfoListView;

    static AHBottomNavigation bottomNavigation;
    private static String notifyNumber;

    private static RequestQueue mQueue;

    private BackPressCloseListener backPressCloseListener;

    Fragment fragment;

    public static class HomeFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
        ImageView imgView1;
        ImageView imgView2;
        ImageView imgView3;
        ImageView imgView4;

        ReviewGridAdapter adapter;
        GridView gridView;
        ArrayList<ReviewDTO> reviewList;

        Button btnNotiTest;
        Button btnBoardWrite;
        public HomeFragment() { }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_home, null);
            gridView = (GridView)v.findViewById(R.id.reviewGridView);

            btnNotiTest = (Button)v.findViewById(R.id.btnNotiTest);
            btnNotiTest.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Fragment fragment = new BoardListFragment();
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                    fragmentTransaction.replace( R.id.frame, fragment );
                    fragmentTransaction.commit();
                }
            });

            btnBoardWrite = (Button)v.findViewById(R.id.btnBoardWrite);
            btnBoardWrite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ctx, BoardWriteActivity.class);
                    startActivity(intent);
                }
            });

            mQueue = CustomVolley
                    .getInstance(ctx)
                    .getRequestQueue();
            String url = "http://210.125.213.72:8090/SampleProject/android/review/review.jsp";
            final CustomJsonRequest jsonRequest = new CustomJsonRequest(Request.Method.GET, url, new JSONObject(), this, this);
            jsonRequest.setTag("MainActivity");
            mQueue.add(jsonRequest);

            imgView1 = (ImageView)v.findViewById(R.id.imgMainSearch1);
            imgView2 = (ImageView)v.findViewById(R.id.imgMainSearch2);
            imgView3 = (ImageView)v.findViewById(R.id.imgMainSearch3);
            imgView4 = (ImageView)v.findViewById(R.id.imgMainSearch4);

            ArrayList<ImageView> list = new ArrayList<>();
            list.add(imgView1);
            list.add(imgView2);
            list.add(imgView3);
            list.add(imgView4);

            for (int i = 0; i < list.size(); i++){
                list.get(i).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int id = v.getId();

                        Fragment fragment = new BoardListFragment();
                        Bundle bundle = new Bundle();
                        bundle.putInt("imgId", id);
                        fragment.setArguments(bundle);

                        bottomNavigation.setCurrentItem(1);

                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                        fragmentTransaction.replace( R.id.frame, fragment );
                        fragmentTransaction.commit();
                    }
                });
            }
            return v;
        }

        @Override
        public void onSaveInstanceState(Bundle outState) {
            super.onSaveInstanceState(outState);
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("TAG", error.toString());
        }

        @Override
        public void onResponse(JSONObject response) {
            reviewList = new ArrayList<>();
            try {
                JSONArray jsonArray = (JSONArray)response.get("result");
                for (int i = 0; i < jsonArray.length(); i++){
                    JSONObject obj = (JSONObject)jsonArray.get(i);
                    String airportFrom = (String)obj.get("airportFrom");
                    String airportTo = (String)obj.get("airportTo");
                    String diffDate = (String)obj.get("diffDate");

                    JSONObject review = (JSONObject)obj.get("review");

                    int review_num = (int)review.get("review_num");
                    String review_wdate = (String)review.get("review_wdate");
                    Double review_star = (Double)review.get("review_star");
                    String review_content = (String)review.get("review_content");

                    MemberDTO reqDTO = new MemberDTO();
                    reqDTO.setMem_num((int)review.get("review_req_num"));
                    reqDTO.setMem_name((String)review.get("r_name"));
                    reqDTO.setMem_picture((String)review.get("r_pic"));

                    MemberDTO guiDTO = new MemberDTO();
                    guiDTO.setMem_num((int)review.get("review_gui_num"));
                    guiDTO.setMem_name((String)review.get("g_name"));
                    guiDTO.setMem_picture((String)review.get("g_pic"));

                    DataDTO boardDTO = new DataDTO();
                    boardDTO.setStart(airportFrom);
                    boardDTO.setScb_via(String.valueOf(review.get("scb_via")));
                    boardDTO.setArrival(airportTo);

                    ReviewDTO reviewDTO = new ReviewDTO(review_num, review_content, review_star, reqDTO, guiDTO, boardDTO, review_wdate, diffDate);
                    reviewList.add(reviewDTO);
                    reviewList.add(reviewDTO);
                    reviewList.add(reviewDTO);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            adapter = new ReviewGridAdapter(getActivity().getApplicationContext(),R.layout.review_grid_item, reviewList);
            gridView.setAdapter(adapter);
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = new Intent(ctx, ReviewRetrieveActivity.class);
                    intent.putExtra("reviewDTO", reviewList.get(position));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
            });
        }
    }

    public static class BoardListFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
        public static ArrayList<DataDTO> list;
        ListView listView;
        BoardListAdapter adapter;
        Button btn;

        TextView txtStartDate;
        TextView txtLastDate;
        static TextView txtDeparture;
        static TextView txtArrival;
        Button btnSearch;

        public static TextView txtBoardListAlert;
        public static LinearLayout linearCkbox;
        CheckBox ckWait;
        CheckBox ckDuring;
        CheckBox ckConfirm;

        LinearLayout mainLinearLayout;

        public BoardListFragment() { }

        @Override
        public void onStart() {
            super.onStart();
            Bundle extra = getArguments();
            if (extra != null) {
                int id = extra.getInt("imgId");
                String country = "";
                Log.i("TAG", "main imgId: "+id);
                switch (id) {
                    case 2131558717:
                        country = "영국";
                        break;
                    case 2131558716:
                        country = "미국";
                        break;
                    case 2131558718:
                        country = "일본";
                        break;
                    case 2131558719:
                        country = "이탈리아";
                        break;
                }
                mQueue = CustomVolley
                        .getInstance(ctx)
                        .getRequestQueue();

                String url = "http://210.125.213.72:8090/SampleProject/android/board/boardList.jsp";
                url += "?scb_from="+ Uri.encode("인천 국제공항", "UTF-8")+"&scb_to=" + Uri.encode(country, "UTF-8") + "&min=2016-09-01&max=2017-12-31";
                final CustomJsonRequest jsonRequest = new CustomJsonRequest(Request.Method.GET, url, new JSONObject(), this, this);
                jsonRequest.setTag("MainActivity");
                mQueue.add(jsonRequest);
            }
        }

        @Nullable
        @Override
        public View onCreateView(final LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_board_list, null);
            txtBoardListAlert = (TextView)v.findViewById(R.id.txtBoardListAlert);
            linearCkbox = (LinearLayout) v.findViewById(R.id.linearCheckBox);

            ckWait = (CheckBox)v.findViewById(R.id.ckboxWait);
            ckDuring = (CheckBox)v.findViewById(R.id.ckboxDuring);
            ckConfirm = (CheckBox)v.findViewById(R.id.ckboxConfirm);
            List<CheckBox> ckList = new ArrayList<>();
            ckList.add(ckWait);
            ckList.add(ckDuring);
            ckList.add(ckConfirm);
            for (CheckBox ckbox : ckList){
                ckbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        BoardListCheckingAdapter adapter = new BoardListCheckingAdapter(ctx, R.layout.board_list_item, list, ckWait, ckDuring, ckConfirm);
                        listView.setAdapter(adapter);
                    }
                });
            }

            listView=(ListView)v.findViewById(R.id.listView);
            btn=(Button)v.findViewById(R.id.searchBtn);

            mainLinearLayout = (LinearLayout)v.findViewById(R.id.includeSearchLayout);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //클릭시 팝업 윈도우 생성
                    PopupWindow popup = new PopupWindow(v);
                    LayoutInflater inflater = (LayoutInflater) ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    //팝업으로 띄울 커스텀뷰를 설정하고
                    View view = inflater.inflate(R.layout.popup_search_list, null);

                    txtDeparture = (TextView) view.findViewById(R.id.txtSearchDeparture);
                    txtDeparture.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ctx, FindAirportActivity.class);
                            startActivity(intent);
                        }
                    });

                    txtArrival = (TextView) view.findViewById(R.id.txtSearchArrival);
                    txtArrival.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(ctx, FindCountryActivity.class);
                            startActivity(intent);
                        }
                    });

                    txtStartDate = (TextView) view.findViewById(R.id.txtSearchStartDate);
                    txtStartDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar c = Calendar.getInstance();
                            DatePickerDialog dialog = new DatePickerDialog(thisCtx, listener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
                            dialog.show();
                        }

                        private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                txtStartDate.setTextColor(Color.parseColor("#000000"));
                                txtStartDate.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                            }
                        };
                    });

                    txtLastDate = (TextView) view.findViewById(R.id.txtSearchLastDate);
                    txtLastDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Calendar c = Calendar.getInstance();
                            DatePickerDialog dialog = new DatePickerDialog(thisCtx, listener, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
                            dialog.show();
                        }

                        private DatePickerDialog.OnDateSetListener listener = new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                txtLastDate.setTextColor(Color.parseColor("#000000"));
                                txtLastDate.setText(year+"-"+(monthOfYear+1)+"-"+dayOfMonth);
                            }
                        };

                    });
                    btnSearch = (Button)view.findViewById(R.id.btnPopupSearch);
                    btnSearch.setOnClickListener(new SearchOnClickListener(listView, txtDeparture, txtArrival, txtStartDate, txtLastDate));

                    popup.setContentView(view);
                    //팝업의 크기 설정
                    popup.setWindowLayoutMode(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    //팝업 뷰 터치 되도록
                    popup.setTouchable(true);
                    //팝업 뷰 포커스도 주고
                    popup.setFocusable(true);
                    //팝업 뷰 이외에도 터치되게 (터치시 팝업 닫기 위한 코드)
                    popup.setOutsideTouchable(true);
                    popup.setBackgroundDrawable(new BitmapDrawable());
                    //인자로 넘겨준 v 아래로 보여주기
                    popup.showAsDropDown(v);
                }
            });

            return v;
        }

        @Override
        public void onErrorResponse(VolleyError error) { Log.i("TAG", error.toString()); }

        @Override
        public void onResponse(JSONObject response) {
            list=new ArrayList<>();
            JSONArray jsonArray = new JSONArray();
            try {
                jsonArray = response.getJSONArray("boardListResult");

                if (jsonArray.length() == 0){
                    MainActivity.BoardListFragment.txtBoardListAlert.setText("검색 조건에 맞는 게시물이 존재하지 않습니다.\n다른 검색 조건으로 검색해 주세요.");
                    BoardListFragment.linearCkbox.setVisibility(View.INVISIBLE);
                } else{
                    MainActivity.BoardListFragment.txtBoardListAlert.setText("");
                    BoardListFragment.linearCkbox.setVisibility(View.VISIBLE);
                }

                String scb_mediate[]=new String[jsonArray.length()];
                String scb_content[]=new String[jsonArray.length()];
                final String scb_case[]=new String[jsonArray.length()];
                String scb_sdate[]=new String[jsonArray.length()];
                String scb_mem_num[]=new String[jsonArray.length()];
                String scb_num[]=new String[jsonArray.length()];
                String scb_title[]=new String[jsonArray.length()];
                String scb_via[]=new String[jsonArray.length()];
                String scb_wdate[]=new String[jsonArray.length()];
                String start[]=new String[jsonArray.length()];
                String arrival[]=new String[jsonArray.length()];
                Log.i("TAG","제이슨어레이 시작");
                for(int i=0;i<jsonArray.length();i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    scb_mediate[i]=jsonObject.getString("scb_mediate");
                    scb_content[i]=jsonObject.getString("scb_content");
                    scb_case[i]=jsonObject.getString("scb_case");
                    scb_sdate[i]=jsonObject.getString("scb_sdate");
                    scb_mem_num[i]=jsonObject.getString("scb_mem_num");
                    scb_num[i]=jsonObject.getString("scb_num");
                    scb_title[i]=jsonObject.getString("scb_title");
                    scb_via[i]=jsonObject.getString("scb_via");
                    scb_wdate[i]=jsonObject.getString("scb_wdate");
                    start[i]=jsonObject.getString("start");
                    arrival[i]=jsonObject.getString("arrival");
                    //scb_mediate, String scb_content, int scb_case, String scb_sdate, String scb_mem_num, String scb_num, String scb_title, String scb_via, String scb_wdate, String start, String arrival
                    int scb_case_img=R.drawable.ic_sub;
                    switch (scb_case[i]){
                        case "WAIT":
                            scb_case_img=R.drawable.ic_sub;
                            break;
                        case "DURING":
                            scb_case_img=R.drawable.ic_pro;
                            break;
                        case "CONFIRM":
                            scb_case_img=R.drawable.ic_com;
                            break;
                    }
                    list.add(new DataDTO(scb_mediate[i],scb_content[i],scb_case_img,scb_sdate[i],scb_mem_num[i],scb_num[i],scb_title[i],scb_via[i],scb_wdate[i],start[i],arrival[i]));
                }
                Log.i("TAG","제이슨 끝");
                adapter = new BoardListAdapter(getActivity().getApplicationContext(),R.layout.board_list_item, list);
                listView.setAdapter(adapter);
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                        Intent intent = new Intent(getActivity(), BoardRetrieveActivity.class);
                        intent.putExtra("dto", list.get(position));
                        intent.putExtra("mem_num",mem_num);
                        intent.putExtra("scb_case",scb_case[position]);
                        startActivity(intent);
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public static class NotiFragment extends Fragment {
        ListView listView;
        TextView alert;
        public NotiFragment() { }

        @Override
        public void onStart() {
            super.onStart();
            removeNoti();
            MySQLiteOpenHelper helper = new MySQLiteOpenHelper(ctx, null, null, 3);
            SQLiteDatabase db = helper.getReadableDatabase();

            Cursor c = db.query("noti", null, null, null, null, null, "_id desc");
            ArrayList<HashMap<String, String>> list = new ArrayList<>();
            int n = c.getCount();
            Log.i("TAG", "cursor count: " + n);
            if (n == 0){
                alert.setText("받은 알림이 없습니다.");
            } else {
                alert.setText("");

                while (c.moveToNext()) {
                    String _id = c.getString(c.getColumnIndex("_id"));
                    String content = c.getString(c.getColumnIndex("content"));
                    String wdate = c.getString(c.getColumnIndex("wdate"));
                    HashMap<String, String> map = new HashMap<>();
                    map.put("_id", _id);
                    map.put("content", content);
                    map.put("wdate", wdate);
                    list.add(map);
                }
                NotiListAdapter adapter = new NotiListAdapter(ctx, R.layout.noti_list_item, list);
                listView.setAdapter(adapter);
            }
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_noti, null);
            listView = (ListView)v.findViewById(R.id.notiListView);
            alert = (TextView)v.findViewById(R.id.notiAlert);
            return v;
        }
    }

    public static class UserInfoFragment extends Fragment implements Response.Listener<JSONObject>, Response.ErrorListener {
        public UserInfoFragment() { }

        @Override
        public void onStart() {
            super.onStart();
            mQueue = CustomVolley
                    .getInstance(ctx)
                    .getRequestQueue();
            String url = "http://210.125.213.72:8090/SampleProject/android/member/memberInfo.jsp?mem_num=" + mem_num;
            final CustomJsonRequest jsonRequest = new CustomJsonRequest(Request.Method.GET, url, new JSONObject(), this, this);
            jsonRequest.setTag("MainActivity");
            mQueue.add(jsonRequest);
        }

        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View v = inflater.inflate(R.layout.fragment_user_info, null);
            txtUserInfoName = (TextView)v.findViewById(R.id.txtUserInfoName);
            imgUserInfoProfile = (ImageView)v.findViewById(R.id.imgUserInfoProfile);
            userInfoListView = (ListView)v.findViewById(R.id.userInfoListView);

            return v;
        }

        @Override
        public void onErrorResponse(VolleyError error) {
            Log.i("TAG", error.toString());
        }

        @Override
        public void onResponse(JSONObject response) {
            final MemberDTO dto = CommonJSONParse.json2MemberDTO(response);

            txtUserInfoName.setText(dto.getMem_name());
            imgUserInfoProfile.setImageResource(R.drawable.profile_yhy);

            ArrayList<String> userInfoList = new ArrayList<>();
            userInfoList.add("내 정보 보기");
            userInfoList.add("내 글 리스트");
            userInfoList.add("계정관리");
            userInfoList.add("설정");
            userInfoList.add("로그아웃");

            UserInfoAdapter adapter = new UserInfoAdapter(getActivity().getApplicationContext(), R.layout.user_info_item, userInfoList);
            userInfoListView.setAdapter(adapter);

            userInfoListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent intent = null;
                    switch(position){
                        case 0:
                            intent = new Intent(getActivity(), MyPageActivity.class);
                            intent.putExtra("memberDTO", dto);
                            startActivity(intent);
                            break;
                        case 1:
                            //내 글 리스트
                            intent = new Intent(getActivity(), BoardMediateActivity.class);
                            intent.putExtra("memberDTO", dto);
                            startActivity(intent);
                            break;
                        case 2:
                            // 계정관리
                            intent = new Intent(getActivity(), ManageAccountActivity.class);
                            intent.putExtra("memberDTO", dto);
                            startActivity(intent);
                            break;
                        case 3:
                            // 설정
                            intent = new Intent(getActivity(), SettingsActivity.class);
                            intent.putExtra("memberDTO", dto);
                            startActivity(intent);
                            break;
                        case 4:
                            // 로그아웃
                            AlertDialog.Builder alert_confirm = new AlertDialog.Builder(getActivity());
                            alert_confirm.setMessage("로그아웃 하시겠습니까?").setCancelable(false).setPositiveButton("확인",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            SharedPreferences.Editor prefs = ctx.getSharedPreferences(LoginActivity.PREFERENCES_NAME, MODE_PRIVATE).edit();
                                            prefs.remove("login");
                                            prefs.commit();
                                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                                            getActivity().finish();
                                            startActivity(intent);
                                        }
                                    }).setNegativeButton("취소",
                                    new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) { return; }
                                    });
                            AlertDialog alert = alert_confirm.create();
                            alert.show();
                            break;
                    }

                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        backPressCloseListener.onBackPressed();
    }

    @Override public void onSaveInstanceState(Bundle outState){ super.onSaveInstanceState(outState); }
    @Override public void onRestoreInstanceState(Bundle savedInstanceState) { super.onRestoreInstanceState(savedInstanceState); }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        backPressCloseListener = new BackPressCloseListener(this);

        String sss = FirebaseInstanceId.getInstance().getId();
        Log.i("TAG", "token ==>" + sss);
        ctx = getApplicationContext();
        thisCtx = MainActivity.this;

        setContentView(R.layout.activity_main);

        // get mem_num
        SharedPreferences sharedPreferences = getSharedPreferences(LoginActivity.PREFERENCES_NAME, Context.MODE_PRIVATE);
        mem_num = sharedPreferences.getString("login", null);

        fragment = new HomeFragment();
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add( R.id.frame, fragment );
        fragmentTransaction.commit();

        bottomNavigation = (AHBottomNavigation) findViewById(R.id.bottom_navigation);

        AHBottomNavigationItem item1 = new AHBottomNavigationItem(getString(R.string.tab_1), R.drawable.ic_home_24dp, R.color.bottomtab);
        AHBottomNavigationItem item2 = new AHBottomNavigationItem(getString(R.string.tab_2), R.drawable.ic_search_24dp, R.color.bottomtab);
        AHBottomNavigationItem item3 = new AHBottomNavigationItem(getString(R.string.tab_3), R.drawable.ic_review_24dp, R.color.bottomtab);
        AHBottomNavigationItem item4 = new AHBottomNavigationItem(getString(R.string.tab_4), R.drawable.ic_member_24dp, R.color.bottomtab);

        bottomNavigation.addItem(item1);
        bottomNavigation.addItem(item2);
        bottomNavigation.addItem(item3);
        bottomNavigation.addItem(item4);

        bottomNavigation.setDefaultBackgroundColor(Color.parseColor("#FAFAFA"));

        bottomNavigation.setAccentColor(Color.parseColor("#414141"));
        bottomNavigation.setInactiveColor(Color.parseColor("#CCCCCC"));

        updateNoti();
        //  Enables Reveal effect
        // bottomNavigation.setColored(false);

        // bottomNavigation.setCurrentItem(0);

        // Customize notification (title, background, typeface)
        // bottomNavigation.setNotificationBackgroundColor(Color.parseColor("#F63D2B"));

        // Add or remove notification for each item
        // bottomNavigation.setNotification("1", 3);


        bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                // Do something cool here...

                switch (position){
                    case 0:
                        fragment = new HomeFragment();
                        break;
                    case 1:
                        fragment = new BoardListFragment();
                        break;
                    case 2:
                        fragment = new NotiFragment();
                        break;
                    case 3:
                        fragment = new UserInfoFragment();
                        break;
                }
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace( R.id.frame, fragment );
                fragmentTransaction.commit();
                return true;
            }
        });

        bottomNavigation.setOnNavigationPositionListener(new AHBottomNavigation.OnNavigationPositionListener() {
            @Override public void onPositionChange(int y) {
                // Manage the new y position
            }
        });

    }
    private static void updateNoti(){
        if (notifyNumber != null){
            AHNotification notification = new AHNotification.Builder()
                    .setText(notifyNumber)
                    .setBackgroundColor(ContextCompat.getColor(ctx, R.color.colorBottomNavigationNotification))
                    .setTextColor(ContextCompat.getColor(ctx, R.color.bottomtab))
                    .build();
            bottomNavigation.setNotification(notification, 2);
        }
    }
    private static void removeNoti(){
        notifyNumber = null;
        AHNotification notification = null;
        bottomNavigation.setNotification(notification, 2);
    }
    public static void pushNoti(){
        if (notifyNumber == null){
            notifyNumber = "1";
        } else{
            notifyNumber = String.valueOf(Integer.parseInt(notifyNumber) + 1);
        }
        updateNoti();
    }
}
