package br.com.luisfernandez.amazonabtest;

import android.content.Context;
import android.util.Log;

import com.amazon.insights.ABTestClient;
import com.amazon.insights.AmazonInsights;
import com.amazon.insights.Event;
import com.amazon.insights.EventClient;
import com.amazon.insights.InsightsCallback;
import com.amazon.insights.InsightsCredentials;
import com.amazon.insights.InsightsOptions;
import com.amazon.insights.Variation;
import com.amazon.insights.VariationSet;

/**
 * Created by luis.fernandez on 6/20/15.
 */
public class AmazonABTestService {

    private static final String TAG = AmazonABTestService.class.getSimpleName();

    private Context mContext;
    private AmazonABExperiment mAmazonABExperiment;
    private ABTestClient mABTestClient;
    private EventClient mEventClient;

    public AmazonABTestService(Context context, AmazonABExperiment amazonABExperiment) {
        this.mContext = context;
        this.mAmazonABExperiment = amazonABExperiment;

        this.init(mContext, mAmazonABExperiment);
    }

    private void init(Context context, AmazonABExperiment amazonABExperiment) {
        InsightsCredentials credentials = AmazonInsights.newCredentials(amazonABExperiment.getApplicationKey(), amazonABExperiment.getPrivateKey());

        InsightsOptions options = AmazonInsights.newOptions(true, true);

        AmazonInsights insightsInstance = AmazonInsights.newInstance(credentials, context, options);
        mABTestClient = insightsInstance.getABTestClient();
        mEventClient = insightsInstance.getEventClient();
    }

    public void registerVariationCallback(final OnExperimentVariationResult onExperimentVariationResult) {
        mABTestClient.getVariations(mAmazonABExperiment.getProject()).setCallback(new InsightsCallback<VariationSet>() {

            @Override
            public void onComplete(VariationSet variations) {
                final Variation variation = variations.getVariation(mAmazonABExperiment.getProject());
                final String text = variation.getVariableAsString(mAmazonABExperiment.getStringVariable(), "");

                Log.d(TAG, String.format("Variation Value: %s", text));
                onExperimentVariationResult.onVariationResult(text);
            }
        });
    }

    public void sendViewEvent() {
        this.recordEvent(mAmazonABExperiment.getViewEvent());
    }

    public void sendConversionEvent() {
        this.recordEvent(mAmazonABExperiment.getConversionEvent());
    }

    public void submitEvents() {
        mEventClient.submitEvents();
    }

    private void recordEvent(String viewEvent) {
        Event event = mEventClient.createEvent(viewEvent);
        mEventClient.recordEvent(event);
    }
}
