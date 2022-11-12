package com.galib.natorepbs2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

public class ConsumerSMSActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consumer_smsactivity);
        ((TextView)findViewById(R.id.consumerSMSSample)).setText(
                getResources().getString(R.string.sms_consumer_details)
                + "\n\t" + getResources().getString(R.string.sms_application_1)
                + "\n\t\t" + getResources().getString(R.string.sms_application_complete_sample)
                + "\n\t\t" + getResources().getString(R.string.sms_application_deposite_sample)
                + "\n\t" + getResources().getString(R.string.sms_application_2)
                + "\n\t\t" + getResources().getString(R.string.sms_bill_sample)
        );
    }
}