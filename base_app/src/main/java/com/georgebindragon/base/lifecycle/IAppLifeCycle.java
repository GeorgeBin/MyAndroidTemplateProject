package com.georgebindragon.base.lifecycle;

import com.georgebindragon.base.abilities.function.IFunction;

/**
 * author：
 *
 * description：
 * action：
 *
 * modification：
 */


public interface IAppLifeCycle extends IFunction
{
   void onAppStart();

   //	void onAppReceiveBootCompleted();

   void onAppReceiveShutdown();

   void onTerminate();

   void onAppExit();
}
