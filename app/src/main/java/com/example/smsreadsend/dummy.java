//import android.Manifest;
//import android.app.Notification;
//import android.app.NotificationManager;
//import android.app.admin.DevicePolicyManager;
//import android.content.ComponentName;
//import android.content.ContentResolver;
//import android.content.Context;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.pm.PackageManager;
//import android.database.Cursor;
//import android.location.Location;
//import android.location.LocationListener;
//import android.location.LocationManager;
//import android.media.AudioManager;
//import android.os.Bundle;
//import android.os.Handler;
//import android.os.Looper;
//import android.os.Vibrator;
//import android.provider.ContactsContract;
//import android.support.annotation.NonNull;
//import android.support.constraint.ConstraintLayout;
//import android.support.design.widget.NavigationView;
//import android.support.v4.app.ActivityCompat;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentManager;
//import android.support.v4.app.FragmentTransaction;
//import android.support.v4.app.NotificationCompat;
//import android.support.v4.app.NotificationManagerCompat;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.view.GravityCompat;
//import android.support.v4.widget.DrawerLayout;
//import android.support.v7.app.ActionBarDrawerToggle;
//import android.support.v7.app.AppCompatActivity;
//import android.support.v7.widget.Toolbar;
//import android.telephony.SmsManager;
//import android.util.Log;
//import android.view.Menu;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.ArrayAdapter;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ListView;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//import java.util.Timer;
//import java.util.TimerTask;
//
//import static com.example.smsreadsend.App.CHANNEL_1_ID;
//import static com.example.smsreadsend.App.CHANNEL_2_ID;
//import static com.example.smsreadsend.App.CHANNEL_3_ID;
//
//public class Main_page extends AppCompatActivity implements LocationListener, NavigationView.OnNavigationItemSelectedListener {
//    public static String password, username, phonenumber;
//    private String rmsg, rphno;
//    private TextView tvMsg, tvFrom, tvUsr, tvPhn;
//    private ImageView dp;
//    private Button btnSendSMS;
//    private EditText etPhoneNum, etMessage;
//    private final static int REQUEST_CODE_PERMISSION_SEND_SMS = 123;
//    private ListView lvSMS;
//    private final static int REQUEST_CODE_PERMISSION_READ_SMS = 456;
//    ArrayList<String> smsMsgList = new ArrayList<>();
//    ArrayAdapter arrayAdapter;
//    public static Main_page instance;
//    private LocationManager locationManager;
//    Vibrator vibrator;
//    DevicePolicyManager devicePolicyManager;
//    AudioManager audioManager;
//    ComponentName componentName;
//    Toolbar toolbar;
//    private int ps, pvs;
//    private String pvsnm;
//    public static final String SHARED_PREFS = "SharedPrefs";
//    public static final String TEXT = "text", TEXT1 = "text1", TEXT2 = "text2", TEXT3 = "text3";
//    private Intent ServiceIntent;
//    private NotificationManagerCompat notificationManager;
//    private Handler handler = new Handler(Looper.getMainLooper());
//    private Runnable locationRunnable;
//    private boolean isContinuousLocation = false;
//
//    public static Main_page Instance() {
//        return instance;
//    }
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
//
//        startService();
//        notificationManager = NotificationManagerCompat.from(this);
//        instance = this;
//        etPhoneNum = findViewById(R.id.etPhoneNum);
//        etMessage = findViewById(R.id.etMsg);
//        tvFrom = findViewById(R.id.rphno);
//        tvMsg = findViewById(R.id.rmsg);
//        toolbar = findViewById(R.id.toolbar);
//        NavigationView navigationView = findViewById(R.id.nav_view);
//        View headView = navigationView.getHeaderView(0);
//        tvUsr = headView.findViewById(R.id.usrNm);
//        tvPhn = headView.findViewById(R.id.usrPhN);
//        dp = headView.findViewById(R.id.dp);
//        setSupportActionBar(toolbar);
//        getPwd();
//        getUsr();
//        getPhn();
//        tvUsr.setText(" " + username);
//        tvPhn.setText("+91 " + phonenumber);
//        ServiceIntent = new Intent(getApplicationContext(), BackService.class);
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        btnSendSMS = findViewById(R.id.btnSendSMS);
//        lvSMS = findViewById(R.id.lv_sms);
//        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, smsMsgList);
//        lvSMS.setAdapter(arrayAdapter);
//        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
//        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
//        devicePolicyManager = (DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
//        componentName = new ComponentName(Main_page.this, Controller.class);
//        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
//        Timer t = new Timer();
//
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
//                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
//
//        drawer.addDrawerListener(toggle);
//        toggle.syncState();
//        navigationView.setNavigationItemSelectedListener(this);
//        if (checkPermission(Manifest.permission.READ_SMS)) {
//            refreshInbox();
//        } else {
//            ActivityCompat.requestPermissions(Main_page.this, new String[]{
//                    (Manifest.permission.READ_SMS)}, REQUEST_CODE_PERMISSION_READ_SMS);
//        }
//        if (checkPermission(Manifest.permission.SEND_SMS)) {
//        } else {
//            ActivityCompat.requestPermissions(Main_page.this, new String[]{
//                    (Manifest.permission.SEND_SMS)}, REQUEST_CODE_PERMISSION_SEND_SMS);
//        }
//        if (checkPermission(Manifest.permission.READ_CONTACTS)) {
//        } else {
//            ActivityCompat.requestPermissions(Main_page.this, new String[]{
//                    (Manifest.permission.READ_CONTACTS)}, REQUEST_CODE_PERMISSION_READ_SMS);
//        }
//        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
//        } else {
//            ActivityCompat.requestPermissions(Main_page.this, new String[]{
//                    (Manifest.permission.ACCESS_FINE_LOCATION)}, REQUEST_CODE_PERMISSION_SEND_SMS);
//        }
//        if (checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
//        } else {
//            ActivityCompat.requestPermissions(Main_page.this, new String[]{
//                    (Manifest.permission.ACCESS_COARSE_LOCATION)}, REQUEST_CODE_PERMISSION_SEND_SMS);
//        }
//
//        btnSendSMS.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                sendSMS();
//                vibrator.vibrate(50);
//            }
//        });
//
//        dp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                gotoDP();
//            }
//        });
//
//        dpchange();
//
//        t.scheduleAtFixedRate(new TimerTask() {
//            @Override
//            public void run() {
//                refreshInbox();
//            }
//        }, 0, 5000);
//
//        if (!isLocationServiceRunning) {
//            startService(new Intent(this, LocationService.class));
//            isLocationServiceRunning = true;
//        }
//
//        locationRunnable = new Runnable() {
//            @Override
//            public void run() {
//                if (isContinuousLocation) {
//                    if (ActivityCompat.checkSelfPermission(Main_page.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(Main_page.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//                        ActivityCompat.requestPermissions(Main_page.this, new String[]{
//                                (Manifest.permission.ACCESS_FINE_LOCATION), (Manifest.permission.ACCESS_COARSE_LOCATION)}, REQUEST_CODE_PERMISSION_SEND_SMS);
//                        return;
//                    }
//                    Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
//                    if (location != null) {
//                        double latitude = location.getLatitude();
//                        double longitude = location.getLongitude();
//                        etPhoneNum.setText(rphno);
//                        etMessage.setText("Latitude: " + latitude + "\nLongitude: " + longitude);
//                        sendSMS();
//                    }
//                    handler.postDelayed(this, 5000);
//                }
//            }
//        };
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        dpchange();
//    }
//
//    private boolean checkPermission(String permission) {
//        int checkPermission = ContextCompat.checkSelfPermission(this, permission);
//        return checkPermission == PackageManager.PERMISSION_GRANTED;
//    }
//
//    @Override
//    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
//        switch (requestCode) {
//            case REQUEST_CODE_PERMISSION_SEND_SMS:
//                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                }
//                break;
//        }
//    }
//
//    public void sendSMS() {
//        String msg = etMessage.getText().toString().trim();
//        String phoneNum = etPhoneNum.getText().toString().trim();
//        if (!etMessage.getText().toString().equals("") || !etPhoneNum.getText().toString().equals("")) {
//            SmsManager smsMan = SmsManager.getDefault();
//            smsMan.sendTextMessage(phoneNum, null, msg, null, null);
//            etMessage.setText("");
//            etPhoneNum.setText("");
//        } else {
//            Toast.makeText(Main_page.this, "Enter a message and phone number first.", Toast.LENGTH_SHORT).show();
//        }
//    }
//
//    public void refreshInbox() {
//        ContentResolver contentResolver = getContentResolver();
//        Cursor smsInboxCursor = contentResolver.query(Uri.parse("content://sms/inbox"), null, null, null, null);
//        int indexBody = smsInboxCursor.getColumnIndex("body");
//        int indexAddress = smsInboxCursor.getColumnIndex("address");
//        if (indexBody < 0 || !smsInboxCursor.moveToFirst()) return;
//        arrayAdapter.clear();
//        do {
//            String str = "SMS From: " + smsInboxCursor.getString(indexAddress) + "\n" + smsInboxCursor.getString(indexBody) + "\n";
//            arrayAdapter.add(str);
//            rmsg = smsInboxCursor.getString(indexBody);
//            rphno = smsInboxCursor.getString(indexAddress);
//            tvMsg.setText(rmsg);
//            tvFrom.setText(rphno);
//            if (rmsg.contains("conlocation")) {
//                isContinuousLocation = true;
//                handler.post(locationRunnable);
//            }
//        } while (smsInboxCursor.moveToNext());
//    }
//
//    public void stopContinuousLocation() {
//        isContinuousLocation = false;
//        handler.removeCallbacks(locationRunnable);
//    }
//
//    public void gotoDP() {
//        Intent intent = new Intent(Main_page.this, DisplayPic.class);
//        startActivity(intent);
//    }
//
//    public void dpchange() {
//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
//        ps = sharedPreferences.getInt(TEXT, R.drawable.ic_default_dp);
//        dp.setImageResource(ps);
//    }
//
//    public void getPwd() {
//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
//        password = sharedPreferences.getString(TEXT1, "");
//    }
//
//    public void getUsr() {
//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
//        username = sharedPreferences.getString(TEXT2, "");
//    }
//
//    public void getPhn() {
//        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS, MODE_PRIVATE);
//        phonenumber = sharedPreferences.getString(TEXT3, "");
//    }
//
//    public void startService() {
//        String input = "Service is running";
//        Intent serviceIntent = new Intent(this, BackService.class);
//        serviceIntent.putExtra("inputExtra", input);
//        ContextCompat.startForegroundService(this, serviceIntent);
//    }
//
//    @Override
//    public void onBackPressed() {
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        if (drawer.isDrawerOpen(GravityCompat.START)) {
//            drawer.closeDrawer(GravityCompat.START);
//        } else {
//            super.onBackPressed();
//        }
//    }
//
//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.main_page, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.action_settings) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
//    }
//
//    @Override
//    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
//        int id = item.getItemId();
//
//        if (id == R.id.nav_profile) {
//            Intent intent = new Intent(Main_page.this, Profile.class);
//            startActivity(intent);
//        } else if (id == R.id.nav_logout) {
//            Intent intent = new Intent(Main_page.this, MainActivity.class);
//            startActivity(intent);
//        }
//
//        DrawerLayout drawer = findViewById(R.id.drawer_layout);
//        drawer.closeDrawer(GravityCompat.START);
//        return true;
//    }
//
//    @Override
//    public void onLocationChanged(@NonNull Location location) {
//        // Handle location updates here if necessary
//    }
//
//    @Override
//    public void onStatusChanged(String provider, int status, Bundle extras) {
//        // Handle status changes if necessary
//    }
//
//    @Override
//    public void onProviderEnabled(@NonNull String provider) {
//        // Handle provider enabled if necessary
//    }
//
//    @Override
//    public void onProviderDisabled(@NonNull String provider) {
//        // Handle provider disabled if necessary
//    }
//}