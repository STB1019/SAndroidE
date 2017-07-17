package it.unibs.ste.smafgsandroide;

import android.os.Bundle;
import android.util.Log;
import android.widget.ProgressBar;
import android.widget.TextView;
import it.unibs.sandroide.lib.BLEContext;
import it.unibs.sandroide.lib.activities.SandroideBaseActivity;
import it.unibs.sandroide.lib.item.generalIO.BLEGeneralIO;
import it.unibs.sandroide.lib.item.generalIO.BLEGeneralIOEvent;
import it.unibs.sandroide.lib.item.generalIO.BLEOnGeneralIOEventListener;

public class MainActivity extends SandroideBaseActivity {

    protected static final String TAG = "MainActivity";

    TextView tvTrimmer;
    TextView tvStatus;

    BLEGeneralIO nanoTrimmer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BLEContext.initBLE(this);

        tvTrimmer =(TextView) findViewById(R.id.tvAnalogValue);
        nanoTrimmer = (BLEGeneralIO) BLEContext.findViewById("nanoSMAFG_rbs_general_io_5");

        if (nanoTrimmer!=null) {
            nanoTrimmer.setOnGeneralIOEventListener(new BLEOnGeneralIOEventListener() {
                @Override
                public void onBoardInitEnded() {
                    nanoTrimmer.setStatus(BLEGeneralIO.GENERAL_IO_AI);
                }

                @Override
                public void onAnalogValueChanged(BLEGeneralIOEvent bleGeneralIOEvent)
                {
                    Log.d(TAG, "analog value changing: " + bleGeneralIOEvent.values[1]);
                    final float val = bleGeneralIOEvent.values[1];
                    final float misurationValue = Math.round(val * 200) / (float) 100;
                    runOnUiThread(new Runnable() {
                                      @Override
                                      public void run() {
                                          tvTrimmer.setText("Valore: " + misurationValue);
                                      }
                                  }
                    );
                    ProgressBar bar = (ProgressBar) findViewById(R.id.progressBar);
                    bar.setProgress((int)(Math.round(val * 100) / (float) 100));
                    bar.setScaleY(3f);

                    tvStatus = (TextView) findViewById(R.id.status);
                }

                @Override
                public void onDigitalInputValueChanged(BLEGeneralIOEvent bleGeneralIOEvent) {

                }

                @Override
                public void onDigitalOutputValueChanged(BLEGeneralIOEvent bleGeneralIOEvent) {

                }

                @Override
                public void onServoValueChanged(BLEGeneralIOEvent bleGeneralIOEvent) {

                }

                @Override
                public void onPWMValueChanged(BLEGeneralIOEvent bleGeneralIOEvent) {

                }

                @Override
                public void onGeneralIOStatusChanged(BLEGeneralIOEvent bleGeneralIOEvent) {

                }

                @Override
                public void onSetGeneralIOParameter(BLEGeneralIOEvent bleGeneralIOEvent) {

                }
            });
        }
    }
}