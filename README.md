# Simple Amazon ABTest Library

## O que é a Simple Amazon ABTest Library?

O objetivo da Simple Amazon ABTest Library é simplificar o uso do serviço [Amazon A/B Testing][amazon-ab-testing] e de sua biblioteca Android.
A idéia surgiu quando tive algumas dificuldades ao tentar integrar um aplicativo Android com o serviço da Amazon, já que o [tutorial][amazon-ab-testing-doc] e o painel de configuração oficiais do serviço são bem extensos e um pouco complicados.

## Como integrar?
Para integrar seu aplicativo com o Amazon A/B Testing você precisa ter uma conta Amazon e se cadastrar no Developer Console.
Recomendo que você leia o [tutorial oficial][amazon-ab-testing-doc] primeiro para entender alguns conceitos do serviço.
Após isso, você pode começar a configurar o seu projeto. Abaixo estão listadas as telas que contém dados que você precisará configurar no seu projeto.

Chaves de identificação

![Chaves de identificação](https://falandodeandroid.files.wordpress.com/2015/07/amazon_dashboard_identifier_keys1.jpg)

Nome do projeto

![Nome do projeto](https://falandodeandroid.files.wordpress.com/2015/07/amazon_dashboard_config_21.jpg)

Configurações do projeto

![Configurações do projeto](https://falandodeandroid.files.wordpress.com/2015/07/amazon_dashboard_config_11.jpg)


Repare que os campos que você precisa configurar estão marcados com um circulo vermelho.


### E o código?
A interface `AmazonABExperiment.java` define métodos que precisam ser implementados para a biblioteca capturar os dados que você configurou no console do serviço. Ela serve para simplificar a configuração dos dados necessários para a realização um teste A/B no serviço da Amazon. Veja:

```java
public interface AmazonABExperiment {
    String getProject();
    String getStringVariable();
    String getViewEvent();
    String getConversionEvent();
    String getApplicationKey();
    String getPrivateKey();
}
```
A classe `ABBlogSubscriptionButtonExperiment` é um exemplo de implementação da interface `AmazonABExperiment`. É ela que disponibiliza todas as informações referentes ao seu teste A/B que está configurado no console da Amazon. Como explicado anteriormente, a proposta é simplificar a configuração dos dados de um teste A/B, centralizando tudo em um objeto configurador como abaixo:

```java
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
```

Agora, o exemplo de uma Activity simples fazendo uso da Simple Amazon ABTest Library.
Repare que o exemplo abaixo implementa a interface `OnExperimentVariationResult` que possui o método de callback `onVariationResult(String)`. É através desse método que é retornada a variação do teste A/B. Repare que a Activity de exemplo também usa a classe `AmazonABTestService`. Ela é o core da Simple Amazon ABTest Library e será apresentada logo mais abaixo. 

Se você tiver curiosidade, compare o código dessa Activity com o código da Activity do exemplo do [tutorial oficial][amazon-ab-testing-doc] e veja qual é mais simple de usar.
Abaixo a nossa `SubscriptionActivity`:

```java
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
```

Por último, a principal classe da Simple Amazon ABTest Library. A `AmazonABTestService` recebe a implementação da `AmazonABExperiment` no construtor e disponibiliza métodos para execução das mesmas ações da biblioteca original da Amazon.



## Limitações atuais


## Roadmap



License
--------

    Copyright 2015 Luis E. Fernandez

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
    
[amazon-ab-testing]: https://developer.amazon.com/appsandservices/apis/manage/ab-testing
[amazon-ab-testing-doc]: https://developer.amazon.com/public/apis/manage/ab-testing/doc/a-b-testing-for-android-fire-os
