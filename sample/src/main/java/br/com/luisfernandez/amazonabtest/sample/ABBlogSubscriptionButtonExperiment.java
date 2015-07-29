package br.com.luisfernandez.amazonabtest.sample;

import br.com.luisfernandez.amazonabtest.AmazonABExperiment;

/**
 * Created by luis.fernandez on 6/20/15.
 */
public class ABBlogSubscriptionButtonExperiment implements AmazonABExperiment {

    public static final String PROJECT = "project_blogApp";
    public static final String VIEW_EVENT = "viewEvent_screenSubscriptionViewed";
    public static final String SUCCESS_EVENT = "eventSuccess_subscriptionButtonClick";
    public static final String VARIABLE = "variable_buttonColor";

    public static final String APPLICATION_KEY = "c996ea20d1ff4baba5ba304be52c4c8e";
    public static final String PRIVATE_KEY = "tl5Ie1+cW658KyqrZJYdVx0gl7SFeraLTz4pPMHCXOs=";

    @Override
    public String getProject() {
        return PROJECT;
    }

    @Override
    public String getViewEvent() {
        return VIEW_EVENT;
    }

    @Override
    public String getConversionEvent() {
        return SUCCESS_EVENT;
    }

    @Override
    public String getStringVariable() {
        return VARIABLE;
    }

    @Override
    public String getApplicationKey() {
        return APPLICATION_KEY;
    }

    @Override
    public String getPrivateKey() {
        return PRIVATE_KEY;
    }
}
