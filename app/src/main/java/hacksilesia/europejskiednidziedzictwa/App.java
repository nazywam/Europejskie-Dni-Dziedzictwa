public class App extends Application {

   @Override
   public void onCreate() {
       super.onCreate();

       KontaktSDK.initialize(this)
               .setDebugLoggingEnabled(BuildConfig.DEBUG)
               .setLogLevelEnabled(LogLevel.DEBUG, true);
   }
}