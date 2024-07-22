package com.example.smsreadsend;

import android.Manifest;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.admin.DevicePolicyManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.Image;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Looper;
//import androidx.annotation.Nullable;

import static com.example.smsreadsend.App.CHANNEL_1_ID;
import static com.example.smsreadsend.App.CHANNEL_2_ID;
import static com.example.smsreadsend.App.CHANNEL_3_ID;

// location gain


public class Main_page extends AppCompatActivity implements LocationListener,NavigationView.OnNavigationItemSelectedListener  {
    public static String password,username,phonenumber;
    private String rmsg, rphno;
    private TextView tvMsg, tvFrom,tvUsr,tvPhn;
    private ImageView dp;
    private Button btnSendSMS;
    private EditText etPhoneNum, etMessage;
    private final static int REQUEST_CODE_PERMISSION_SEND_SMS = 123;
    private ListView lvSMS;
    private final static int REQUEST_CODE_PERMISSION_READ_SMS = 456;
    ArrayList<String> smsMsgList = new ArrayList<String>();
    ArrayAdapter arrayAdapter;
    public static Main_page instance;
    private LocationManager locationManager;
    Vibrator vibrator;
    DevicePolicyManager devicePolicyManager;
    AudioManager audioManager;
    ComponentName componentName;
    Toolbar toolbar;
    private int ps,pvs;
    private String pvsnm;
    public static final String SHARED_PREFS="SharedPrefs";
    public static final String TEXT="text",TEXT1="text1",TEXT2="text2",TEXT3="text3";
    private Intent ServiceIntent;
    private NotificationManagerCompat notificationManager;
    public static Main_page Instance() {
        return instance;
    }
    private boolean isLocationServiceRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startService();
        notificationManager = NotificationManagerCompat.from(this);
        instance = this;
        etPhoneNum = (EditText) findViewById(R.id.etPhoneNum);
        etMessage = (EditText) findViewById(R.id.etMsg);
        tvFrom = (TextView) findViewById(R.id.rphno);
        tvMsg = (TextView) findViewById(R.id.rmsg);
        toolbar = findViewById(R.id.toolbar);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headView=navigationView.getHeaderView(0);
        tvUsr = (TextView) headView.findViewById(R.id.usrNm);
        tvPhn = (TextView) headView.findViewById(R.id.usrPhN);
        dp = (ImageView) headView.findViewById(R.id.dp);
        setSupportActionBar(toolbar);
        getPwd();
        getUsr();
        getPhn();
        tvUsr.setText(" "+username);
        tvPhn.setText("+91 "+phonenumber);
        ServiceIntent = new Intent(getApplicationContext(),BackService.class);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        btnSendSMS = (Button) findViewById(R.id.btnSendSMS);
//        btnRefList = (Button) findViewById(R.id.btn_Refresh);
        lvSMS = (ListView) findViewById(R.id.lv_sms);
        arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, smsMsgList);
        lvSMS.setAdapter(arrayAdapter);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        devicePolicyManager=(DevicePolicyManager) getSystemService(Context.DEVICE_POLICY_SERVICE);
        componentName = new ComponentName(Main_page.this,Controller.class);
        audioManager =(AudioManager) getSystemService(Context.AUDIO_SERVICE);
        Timer t = new Timer();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        if (checkPermission(Manifest.permission.READ_SMS)) {
            refreshInbox();
        } else {
            ActivityCompat.requestPermissions(Main_page.this, new String[]{
                    (Manifest.permission.READ_SMS)}, REQUEST_CODE_PERMISSION_READ_SMS);
        }
        if (checkPermission(Manifest.permission.SEND_SMS)) {
        } else {
            ActivityCompat.requestPermissions(Main_page.this, new String[]{
                    (Manifest.permission.SEND_SMS)}, REQUEST_CODE_PERMISSION_SEND_SMS);
        }
        if (checkPermission(Manifest.permission.READ_CONTACTS)) {
        } else {
            ActivityCompat.requestPermissions(Main_page.this, new String[]{
                    (Manifest.permission.READ_CONTACTS)}, REQUEST_CODE_PERMISSION_READ_SMS);
        }
        if (checkPermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
        } else {
            ActivityCompat.requestPermissions(Main_page.this, new String[]{
                    (Manifest.permission.ACCESS_FINE_LOCATION)}, REQUEST_CODE_PERMISSION_SEND_SMS);
        }
        if (checkPermission(Manifest.permission.ACCESS_COARSE_LOCATION)) {
        } else {
            ActivityCompat.requestPermissions(Main_page.this, new String[]{
                    (Manifest.permission.ACCESS_COARSE_LOCATION)}, REQUEST_CODE_PERMISSION_SEND_SMS);
        }

        btnSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendSMS();
                vibrator.vibrate(50);
            }
        });

//        btnRefList.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                refreshInbox();
//                vibrator.vibrate(50);
//            }
//        });


        dp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoDP();
            }
        });

        dpchange();

        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                refreshInbox();
            }
        },0,5000);

        if (!isLocationServiceRunning) {
            startService(new Intent(this, LocationService.class));
            isLocationServiceRunning = true;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dpchange();
    }

    private boolean checkPermission(String permission){
        int checkPermission = ContextCompat.checkSelfPermission(this,permission);
        return checkPermission == PackageManager.PERMISSION_GRANTED;
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode){
            case  REQUEST_CODE_PERMISSION_SEND_SMS:
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    //btnSendSMS.setEnabled(true);
                }
                break;
        }
    }

    public void sendSMS(){
        String msg =etMessage.getText().toString().trim();
        String phoneNum = etPhoneNum.getText().toString().trim();
        if(!etMessage.getText().toString().equals("") || !etPhoneNum.getText().toString().equals("")) {
            SmsManager smsMan = SmsManager.getDefault();
            smsMan.sendTextMessage(phoneNum, null ,msg , null, null);
            Toast.makeText(Main_page.this, "SMS Sent to " + phoneNum, Toast.LENGTH_LONG).show();
        }
    }


    public String readContact(String cname){

        if(checkPermission(Manifest.permission.READ_CONTACTS)){
            ContentResolver resolver=getContentResolver();
            Cursor cursor=resolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null);
            while(cursor.moveToNext()){
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                String name=cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));

                Cursor phoneCursor=resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);


                while (phoneCursor.moveToNext() && name.contains(cname)) {
                    String yname=phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String phoneNumber=phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    notif1("Contact","Contact has succesfully been sent");
                    String rtn= yname + ": " +phoneNumber;
                    return rtn;
                }
            }
        }
        notif1("Contact","Requested Contact not found");
        return "Not Found";
    }

    public void refreshInbox(){
        ContentResolver cResolver = getContentResolver();
        Cursor smsInboxCursor = cResolver.query(Uri.parse("content://sms/inbox"),
                null,null,null,null);
        int ringerMode = audioManager.getRingerMode();
        int indexBody = smsInboxCursor.getColumnIndex("body");
        int indexAddress = smsInboxCursor.getColumnIndex("address");
        if(indexBody<0|| !smsInboxCursor.moveToFirst()) return;
        rphno=smsInboxCursor.getString(indexAddress);
        rmsg=smsInboxCursor.getString(indexBody);
        tvFrom.setText(rphno);
        tvMsg.setText(rmsg);
        getPwd();
        password.equals(username);
        boolean active = devicePolicyManager.isAdminActive(componentName);

        if (rmsg.contains(username+" getcontact ")) {
            ps=1;
        }
        else if (rmsg.contains(username+" LOCATION")) {
            ps=2;
        }
        else if (rmsg.contains(username+" LOCK")) {
            ps=3;
        }
        else if (rmsg.contains(username+" VIBRATE")) {
            ps=4;
        }
        else if (rmsg.contains(username+" SILENT")) {
            ps=5;
        }
        else if (rmsg.contains(username+" NORMAL")) {
            ps=6;
        }
        else if (rmsg.contains(username+" SOUND")) {
            ps=7;
        }else if(rmsg.contains(username +" STOP")){
            ps=8;
        }
        switch (ps)
        {
            case 1:
                //Contact
                rmsg = rmsg.replace(username+" getcontact ", "");
                if(pvs==1 && pvsnm.equals(rmsg)){  break;}
                else {
                    pvsnm=rmsg;
                    etPhoneNum.setText(rphno);
                    String phoneNu = readContact(rmsg);
                    etMessage.setText(phoneNu);
                    sendSMS();
                }
                break;

            case 2:
                //Location
                if(pvs==2){break;}
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    if (!isLocationServiceRunning) {
                        startService(new Intent(this, LocationService.class));
                        isLocationServiceRunning = true;
                    }
                }
                else {
                    Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
                    onLocationChanged(location);
                    etPhoneNum.setText(rphno);
                    sendSMS();
                    notif2("Location","Location has succesfully been sent");
                }
                break;
            case 3:
                //Lock
                if(pvs==3){break;}
                if (active) {
                    notif3("Lock","Device has been locked by Map my phone");
                    devicePolicyManager.lockNow();
                } else {
                    Intent intent = new Intent(DevicePolicyManager.ACTION_ADD_DEVICE_ADMIN);
                    intent.putExtra(DevicePolicyManager.EXTRA_DEVICE_ADMIN, componentName);
                    intent.putExtra(DevicePolicyManager.EXTRA_ADD_EXPLANATION, "You should enable the app!");
                    startActivityForResult(intent, RESULT_OK);
                }
                break; case 4:
            //Vibrate
            if(pvs==4){break;}
            audioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
            etPhoneNum.setText(rphno);
            etMessage.setText("Done");
            sendSMS();
            break;
            case 5:
                //Silent
                if(pvs==5){break;}
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
                etPhoneNum.setText(rphno);
                etMessage.setText("Done");
                sendSMS();
                break;
            case 6:
                //Normal
                if(pvs==6){break;}
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
                etPhoneNum.setText(rphno);
                etMessage.setText("Done");
                sendSMS();
                break;
            case 7:
                //Sound module
                break;
            case 8:
                if (isLocationServiceRunning) {
                    stopService(new Intent(this, LocationService.class));
                    isLocationServiceRunning = false;
                }
            default:
                //No default;
        }

        pvs=ps;

    }

    public void updateList(final String smsMsg){
        arrayAdapter.insert(smsMsg,0);
        arrayAdapter.notifyDataSetChanged();
    }

    @Override
    public void onLocationChanged(Location location) {

        double latitude=location.getLatitude();
        double longitude=location.getLongitude();
        etMessage.setText("Latitude:"+latitude+"\nLongitude:"+longitude);

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void vibr(View view) {
        vibrator.vibrate(50);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        switch (id) {
            case R.id.action_settings:

                Intent intent;
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_htu:
                Intent intent1;
                intent1 = new Intent(this, MainActivity.class);
                startActivity(intent1);
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
//        Fragment fragment=null;
        int id = item.getItemId();
        Intent intent;

        if (id == R.id.nav_home) {

        }
        else if (id == R.id.nav_support) {

            intent=new Intent(this,SuppActivity.class);
            startActivity(intent);
        }
        else if (id == R.id.nav_feedback) {
            intent=new Intent(this,FeedActivity.class);
            startActivity(intent);

        }
        else if (id == R.id.nav_about) {
            intent=new Intent(this,AboutActivity.class);
            startActivity(intent);
        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void getPwd(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        password = sharedPreferences.getString(TEXT, "#1234");
    }
    public void getUsr(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        username = sharedPreferences.getString(TEXT1, "Username");
    }
    public void getPhn(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        phonenumber = sharedPreferences.getString(TEXT2, "0000 000000");
    }
    public String getdp(){
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFS,MODE_PRIVATE);
        return sharedPreferences.getString(TEXT3, "1");
    }
    public void gotoDP(){
        Intent intent;
        intent=new Intent(this,AvatarSelect.class);
        startActivity(intent);
    }
    public void dpchange(){
        switch(getdp()){
            case "1": dp.setImageResource(R.drawable.avatar1);break;
            case "2": dp.setImageResource(R.drawable.avatar2);break;
            case "3": dp.setImageResource(R.drawable.avatar3);break;
            case "4": dp.setImageResource(R.drawable.avatar4);break;
            case "5": dp.setImageResource(R.drawable.avatar5);break;
            case "6": dp.setImageResource(R.drawable.avatar6);break;
            case "7": dp.setImageResource(R.drawable.avatar7);break;
            case "8": dp.setImageResource(R.drawable.avatar8);break;
            case "9": dp.setImageResource(R.drawable.avatar9);break;
        }
    }
    public void notif1(String title,String text){
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_1_ID)
                .setSmallIcon(R.drawable.ic_add)
                .setContentTitle(title)
                .setContentText(text)
                .build();
        notificationManager.notify(1,notification);
    }
    public void notif2(String title,String text){
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_2_ID)
                .setSmallIcon(R.drawable.ic_add)
                .setContentTitle(title)
                .setContentText(text)
                .build();
        notificationManager.notify(2,notification);
    }
    public void notif3(String title,String text){
        Notification notification = new NotificationCompat.Builder(this,CHANNEL_3_ID)
                .setSmallIcon(R.drawable.ic_add)
                .setContentTitle(title)
                .setContentText(text)
                .build();
        notificationManager.notify(3,notification);
    }

    public void startService(){
//        vibr(v);
        Intent serviceIntent = new Intent(this,BackService.class);
//        serviceIntent.putExtra("name","anything to pass");
        startService(serviceIntent);
    }
    public void stopService(View v){
        vibr(v);
        Intent serviceIntent = new Intent(this,BackService.class);
        stopService(serviceIntent);
        onBackPressed();
    }

    public void updateLocation(double latitude, double longitude) {
    }
}