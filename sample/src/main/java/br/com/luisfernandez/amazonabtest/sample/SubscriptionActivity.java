package br.com.luisfernandez.amazonabtest.sample;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.luisfernandez.amazonabtest.AmazonABTestService;
import br.com.luisfernandez.amazonabtest.OnExperimentVariationResult;
import br.com.luisfernandez.simple_amazon_abtest_lib.R;

public class SubscriptionActivity extends AppCompatActivity implements OnExperimentVariationResult {

    private AmazonABTestService mAmazonABTestService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.initAmazonAB();
    }

    private void initAmazonAB() {
        mAmazonABTestService = new AmazonABTestService(getApplicationContext(), new ABBlogSubscriptionButtonExperiment());
    }

    @Override
    protected void onResume() {
        super.onResume();

        mAmazonABTestService.registerVariationCallback(this);
        mAmazonABTestService.sendViewEvent();
    }

    @Override
    public void onPause() {
        super.onPause();

        mAmazonABTestService.submitEvents();
    }

    public void onButtonClick(View view) {
        mAmazonABTestService.sendConversionEvent();
    }

    @Override
    public void onVariationResult(String textColor) {
        this.setButtonColor(textColor);
    }

    private void setButtonColor(String color) {
        Button buttonSubscribe = (Button) findViewById(R.id.buttonSubscribe);
        buttonSubscribe.setBackgroundColor(Color.parseColor(color));
    }
}
