package br.com.luisfernandez.amazonabtest;

/**
 * Created by luis.fernandez on 6/20/15.
 */
public interface AmazonABExperiment {

    String getProject();

    String getStringVariable();

    String getViewEvent();

    String getConversionEvent();

    String getApplicationKey();

    String getPrivateKey();
}
