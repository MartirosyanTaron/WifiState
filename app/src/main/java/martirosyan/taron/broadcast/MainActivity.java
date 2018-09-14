package martirosyan.taron.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class MainActivity extends AppCompatActivity {


    private WifiManager myWifiManager;
    private Switch myWifiSwitch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myWifiSwitch =findViewById(R.id.swichOnOffID);
        myWifiManager= (WifiManager) getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        myWifiSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b){
                   myWifiManager.setWifiEnabled(true);
                    myWifiSwitch.setText("Wifi is ON");
                }else {
                    myWifiManager.setWifiEnabled(false);
                    myWifiSwitch.setText("Wifi is OFF");
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        IntentFilter intentFilter=new IntentFilter(WifiManager.WIFI_STATE_CHANGED_ACTION);
        registerReceiver(broadcastReceiver,intentFilter);
    }
    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(broadcastReceiver);
    }
    private BroadcastReceiver broadcastReceiver=new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            int wifiState=intent.getIntExtra(WifiManager.EXTRA_WIFI_STATE,WifiManager.WIFI_STATE_UNKNOWN);

            switch (wifiState){
                case WifiManager.WIFI_STATE_ENABLED:
                    myWifiSwitch.setChecked(true);
                    myWifiSwitch.setText("Wifi is ON");
                    break;
                case WifiManager.WIFI_STATE_DISABLED:
                    myWifiSwitch.setChecked(false);
                    myWifiSwitch.setText("Wifi is OFF");
                    break;

            }
        }
    };
}
