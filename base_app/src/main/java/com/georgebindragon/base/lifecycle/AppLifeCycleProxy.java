package com.georgebindragon.base.lifecycle;

import com.georgebindragon.base.function.FunctionProxy;

/**
 * author：
 *
 * description：
 * action：
 *
 * modification：
 */


public class AppLifeCycleProxy
{
   private static final String TAG = "AppLifeCycleProxy-->";

   private static IAppLifeCycle lifeCycle;
   private static boolean       isInit = false;

   private static void initImp()
   {
      lifeCycle = FunctionProxy.getFunction(IAppLifeCycle.class);
      isInit = true;
   }

   //内部方法, 用于获取具体实现, 可在此添加默认实现
   private static IAppLifeCycle getImp()
   {
      if (!isInit) initImp();
      return lifeCycle;
   }

   //设置服务的具体承载者
   public static void setImp(IAppLifeCycle message)
   {
      isInit = false;
      FunctionProxy.registerFunction(IAppLifeCycle.class, message);
      initImp();
   }

   public static void onAppStart()
   {
      if (null != getImp()) getImp().onAppStart();
   }

   public static void onAppReceiveShutdown()
   {
      if (null != getImp()) getImp().onAppReceiveShutdown();
   }

   public static void onTerminate()
   {
      if (null != getImp()) getImp().onTerminate();
   }

   public static void onAppExit()
   {
      if (null != getImp()) getImp().onAppExit();
   }
}
