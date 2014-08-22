/*******************************************************************************
 * Copyright 2013-2014 eLiveDeftsoft
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package deftsoft.iground.android;

import java.io.File;

import android.annotation.TargetApi;
import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.StrictMode;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.utils.StorageUtils;

import deftsoft.iground.android.CustomizeListView.Constants.Config;

public class UILApplication extends Application {

	@TargetApi(Build.VERSION_CODES.GINGERBREAD)
	@SuppressWarnings("unused")
	@Override
	public void onCreate() {
		if (Config.DEVELOPER_MODE
				&& Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
			StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
					.detectAll().penaltyDialog().build());
			StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
					.detectAll().penaltyDeath().build());
		}

		super.onCreate();

		initImageLoader(getApplicationContext());
	}

	public static DisplayImageOptions options = new DisplayImageOptions.Builder()
			.cacheInMemory(true).cacheOnDisk(true)

			.showImageOnLoading(null)
			.imageScaleType(ImageScaleType.EXACTLY_STRETCHED)
			.resetViewBeforeLoading(true).build();

	@SuppressWarnings("unused")
	public static void initImageLoader(Context context) {
		// // maximum width and height of your images,
		// memoryCacheExtraOptions(1200, 1000) // .memoryCacheExtraOptions(1200,
		// 1000)

		File cacheDir = StorageUtils.getCacheDirectory(context);
		ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(
				context).threadPriority(Thread.NORM_PRIORITY - 2)

		.denyCacheImageMultipleSizesInMemory().memoryCacheSize(2 * 1024 * 1024)
				.diskCacheFileNameGenerator(new Md5FileNameGenerator())

				.tasksProcessingOrder(QueueProcessingType.LIFO).build();
		ImageLoader.getInstance().init(config);

	}
}