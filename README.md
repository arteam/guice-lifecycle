guice-lifecycle
===============
Basic Lifecycle implementation for Guice

* Override **LifecycleConfig**
```java
public class TestLifecycleConfig extends LifecycleConfig {

    @Inject
    private HttpClient httpClient;

    @Inject
    private MQClient mqClient;

    @Override
    public void configure() {
        addStartupTask(new StartupTask("start-mq-client") {
            @Override
            public void start() throws Exception {
                mqClient.registerHandler();
            }
        });
        addShutdownTask(new ShutdownTask("close-mq-client") {
            @Override
            public void shutdown() throws Exception {
                mqClient.closeConnection();
            }
        });
    }
}
```

* Bind it in *Guice* module
```java
Guice.createInjector(new AbstractModule() {
    @Override
    protected void configure() {
        bind(LifecycleConfig.class).to(TestLifecycleConfig.class);
    }
});
```  

* Obtain **Lifecycle** object and call *manage* method. Startup tasks will be performed immediatly and a runtime shutdown hook will be registered to perform shutdown tasks.
```java
 injector.getInstance(Lifecycle.class).manage();
```
